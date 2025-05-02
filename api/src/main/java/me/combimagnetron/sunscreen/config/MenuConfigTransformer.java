package me.combimagnetron.sunscreen.config;

import me.combimagnetron.passport.config.Config;
import me.combimagnetron.passport.config.element.ConfigElement;
import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.passport.util.Pair;
import me.combimagnetron.passport.util.condition.Condition;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.element.impl.*;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.Color;
import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.logic.action.Argument;
import me.combimagnetron.sunscreen.logic.action.ArgumentType;
import me.combimagnetron.sunscreen.logic.action.adapter.TypeAdapter;
import me.combimagnetron.sunscreen.menu.*;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.SectionElement;
import me.combimagnetron.sunscreen.menu.input.Input;
import me.combimagnetron.sunscreen.menu.input.InputHandler;
import me.combimagnetron.sunscreen.style.Text;
import me.combimagnetron.sunscreen.util.*;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public interface MenuConfigTransformer {

    Collection<MenuTemplate> read(Path folder);

    @Nullable MenuTemplate handle(Config config, String fileName);

    class Impl implements MenuConfigTransformer {
        public final static String ErrorMessage = "\nPlease revisit the menu config or contact plugin developers.\nWhen contacting the developers please provide the fault code in the square brackets and your config file.";

        @Override
        public Collection<MenuTemplate> read(Path folder) {
            List<MenuTemplate> menuTemplates = new ArrayList<>();
            for (File file : Objects.requireNonNull(folder.toFile().listFiles())) {
                if (!file.getName().endsWith(".menu")) {
                    continue;
                }
                Config config = Config.file(file.toPath());
                MenuTemplate menuTemplate = handle(config, file.getName());
                if (menuTemplate != null) {
                    menuTemplates.add(menuTemplate);
                }
            }
            return menuTemplates;
        }

        private boolean exists(String entry, Config config) {
            return config.nodes().stream().anyMatch(configElement -> configElement.name().equals(entry)) ||
                    config.sections().stream().anyMatch(configElement -> configElement.name().equals(entry));
        }

        private String get(String entry, Config config) {
            return ((Node<String>)config.nodes().stream().filter(configElement -> configElement.name().equals(entry)).findFirst().get()).value();
        }

        @Override
        public @Nullable MenuTemplate handle(Config config, String fileName) {
            if (!exists("identifier", config)) {
                SunscreenLibrary.library().logger().error("[CR001] No identifier found in config file \"{}\".{}", fileName, ErrorMessage);
                return null;
            }
            Identifier identifier = Identifier.split(get("identifier", config));
            if (!exists("type", config)) {
                SunscreenLibrary.library().logger().error("[CR002] No menu type defined in config file \"{}\".{}", fileName, ErrorMessage);
                return null;
            }
            String menuType = get("type", config);
            MenuTemplate.Type openedMenuType = MenuTemplate.Type.valueOf(menuType.toUpperCase());
            if (openedMenuType == null) {
                SunscreenLibrary.library().logger().error("[CR003] Invalid menu type \"{}\" in config file \"{}\".{}", menuType, fileName, ErrorMessage);
                return null;
            }
            MenuTemplate openedMenu = MenuTemplate.simple(openedMenuType, identifier);
            if (openedMenu == null) {
                SunscreenLibrary.library().logger().error("[CR004] Failed to create menu from config file \"{}\".{}", fileName, ErrorMessage);
                return null;
            }
            if (!exists("divs", config)) {
                SunscreenLibrary.library().logger().error("[CR005] No divs defined in config file \"{}\".{}", fileName, ErrorMessage);
                return null;
            }
            Section divsSection = config.sections().stream().filter(configElement -> configElement.name().equals("divs")).findFirst().get();
            for (ConfigElement divConfigElement : divsSection.elements()) {
                if (divConfigElement instanceof Section divSection) {
                    Identifier id = Identifier.split(((Node<String>)divSection.find("identifier")).value());
                    Section elements = divSection.find("elements");
                    Node<Double> orderNode = divSection.find("order");
                    double order = 0;
                    if (orderNode != null) {
                        if (orderNode.value() != null) {
                            order = orderNode.value();
                        }
                    }
                    Collection<Section> elementConfigElements = new LinkedList<>();
                    elements.elements().stream().filter(c -> c instanceof Section).map(c -> (Section) c).sorted(Comparator.comparing(c -> {
                        Node<Integer> integerNode = c.find("order");
                        if (integerNode == null) {
                            return 0;
                        }
                        if (integerNode.value() == null) {
                            return 0;
                        }
                        return integerNode.value();
                    })).forEachOrdered(elementConfigElements::add);
                    for (ConfigElement configElement : elements.elements()) {
                        System.out.println(configElement.name());
                        if (!(configElement instanceof Section section)) {
                            continue;
                        }
                        elementConfigElements.add(section);
                    }
                    System.out.println(elementConfigElements.size() + " " + identifier.string());
                    if (elementConfigElements.isEmpty()) {
                        SunscreenLibrary.library().logger().error("[CR006] No elements found in config file \"{}\".{}", divConfigElement.name(), fileName, ErrorMessage);
                        return null;
                    }
                    Node<String> conditionNode = divSection.find("condition");
                    Condition condition = null;
                    if (conditionNode != null) {
                        if (conditionNode.value() != null) {
                            String conditionString = conditionNode.value();
                            condition = Condition.of(conditionString);
                        }
                    }
                    Section positionSection = divSection.find("position");
                    List<RuntimeDefinableGeometry.GeometryBuilder<?>> geometryBuilders = List.of(Position.config(positionSection), Size.config(divSection.find("size")));
                    CachedConfigDiv div = new CachedConfigDiv(elementConfigElements, id, geometryBuilders, condition, order);
                    openedMenu.div(div);
                } else {
                    SunscreenLibrary.library().logger().error("[CR006] Invalid div \"{}\" in config file \"{}\".{}", divConfigElement.name(), fileName, ErrorMessage);
                }
            }
            return openedMenu;
        }

    }

    interface ElementTransformer {
        Map<String, ElementTransformer> Transformers = Map.of(
                "image", new ImageElementTransformer(),
                "button", new ButtonElementTransformer(),
                "text", new TextElementTransformer(),
                "shape", new ShapeElementTransformer(),
                "text_input", new TextInputElementTransformer(),
                "selector", new SelectorElementTransformer()
        );

        static Element<?> transform(Section section, String fileName) {
            if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("type"))) {
                SunscreenLibrary.library().logger().error("[CR008] No element type defined in config file \"{}\".{}", fileName, Impl.ErrorMessage);
                return null;
            }
            String elementType = ((Node<String>)section.elements().stream().filter(configElement -> configElement.name().equals("type")).findFirst().get()).value();
            ElementTransformer transformer = Transformers.get(elementType);
            if (transformer == null) {
                SunscreenLibrary.library().logger().error("[CR009] Invalid element type \"{}\" in config file \"{}\".{}", elementType, fileName, Impl.ErrorMessage);
                return null;
            }
            return transformer.transform(section);
        }

        Element<?> transform(Section section);

        private static Canvas text(Section section, Canvas canvas) {
            Section textSection = section.find("text");
            String text = ((Node<String>)textSection.find("text")).value();
            Section positionSection = textSection.find("position");
            Position.PositionBuilder positionBuilder = Position.config(positionSection);
            Position position = positionBuilder.finish(canvas.size(), canvas.size());
            Vec2i vec2d = ViewportHelper.fromPosition(position);
            Text.Font font = Text.Fonts.stream().filter(f -> f.name().equals(((Node<String>)textSection.find("font")).value())).findFirst().orElse(Text.Font.vanilla());
            Text style = Text.text(text, font);
            Section colorSection = textSection.find("color");
            Color color = Color.of(((Node<Integer>)colorSection.find("red")).value(), ((Node<Integer>)colorSection.find("green")).value(), ((Node<Integer>)colorSection.find("blue")).value());
            return canvas.text(style, vec2d, color);
        }

        static Map<String, Canvas> get(Section section) {
            Map<String, Canvas> canvases = new LinkedHashMap<>();
            if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("canvas"))) {
                SunscreenLibrary.library().logger().error("[CR008] No canvas defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                return canvases;
            }
            ConfigElement canvasElement = section.elements().stream().filter(configElement -> configElement.name().equals("canvas")).findFirst().get();
            boolean hasText;
            if (canvasElement instanceof Node canvasNode) {
                Node<String> node = (Node<String>)canvasNode;
                if (!node.value().startsWith("file(")) {
                    return canvases;
                }
                Canvas file = file(node.value().substring(5, node.value().length() - 1));
                canvases.put(node.name(), file);
                return canvases;
            } else if (canvasElement instanceof Section canvasSection) {
                hasText = canvasSection.elements().stream().anyMatch(configElement -> configElement.name().equals("text"));
                boolean hasSplit = canvasSection.elements().stream().anyMatch(configElement -> configElement.name().equals("split"));
                boolean shouldSplit = hasSplit && ((Node<String>) canvasSection.find("split")).value() != null;
                if (shouldSplit) {
                    int split = ((Node<Integer>) canvasSection.find("split")).value();
                    String image = ((Node<String>) canvasSection.find("image")).value();
                    Canvas canvas = file(image.substring(5, image.length() - 1));
                    ArrayList<String> types = ((Node<ArrayList<String>>)canvasSection.find("order")).value();
                    int y = (int) (canvas.size().y() / split);
                    for (int i = 0; i < canvas.size().y(); i+= y) {
                        Canvas sub = canvas.sub(Vec2i.of(canvas.size().x(), y), Vec2i.of(0, i));
                        if (hasText) {
                            sub = text(canvasSection, sub);
                        }
                        canvases.put(types.get(i/y), sub);
                    }
                    return canvases;
                }
                for (ConfigElement element : canvasSection.elements()) {
                        if (element instanceof Node canvasNode) {
                            Node<String> node = (Node<String>)canvasNode;
                            if (node.value().startsWith("file(")) {
                                Canvas file = file(node.value().substring(5, node.value().length() - 1));
                                if (hasText) {
                                    file = text(canvasSection, file);
                                }
                                canvases.put(node.name(), file);
                            }
                            return canvases;
                        } else if (element instanceof Section canvasNode) {
                            Section canvasSection1 = (Section) element;
                            if (canvasSection1.elements().stream().noneMatch(configElement -> configElement.name().equals("file"))) {
                                SunscreenLibrary.library().logger().error("[CR008] No file defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                                return canvases;
                            }
                            String fileName = ((Node<String>)canvasSection1.find("canvas")).value();
                            Canvas file = file(fileName.substring(5, fileName.length() - 1));
                            if (hasText) {
                                file = text(canvasSection, file);
                            }
                            canvases.put(canvasNode.name(), file);
                        }
                }
                return canvases;
            } else {
                SunscreenLibrary.library().logger().error("[CR009] Invalid canvas \"{}\" in config file \"{}\".{}", canvasElement.name(), section.name(), Impl.ErrorMessage);
                return canvases;
            }
        }

        static Canvas file(String fileName) {
            Path path = Path.of(fileName);
            if (!SunscreenLibrary.library().path().resolve(path).toFile().exists()) {
                SunscreenLibrary.library().logger().error("[CR010] File \"{}\" does not exist.{}", fileName, Impl.ErrorMessage);
                return null;
            }
            return Canvas.image(Canvas.ImageProvider.file(path));
        }

        class ImageElementTransformer implements ElementTransformer {
            @Override
            public Element<?> transform(Section section) {
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("identifier"))) {
                    SunscreenLibrary.library().logger().error("[CR011] No identifier defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Identifier identifier = Identifier.split(((Node<String>)section.find("identifier")).value());
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("position"))) {
                    SunscreenLibrary.library().logger().error("[CR012] No position defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Section positionSection = section.find("position");
                Position.PositionBuilder position = Position.config(positionSection);
                Map<String, Canvas> canvases = get(section);
                if (canvases.isEmpty()) {
                    SunscreenLibrary.library().logger().error("[CR013] No canvas found in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                SimpleBufferedElement element = ImageElement.imageElement(canvases.values().stream().findAny().get(), identifier, position);
                return element;
            }
        }

        class ShapeElementTransformer implements ElementTransformer {

            @Override
            public Element<?> transform(Section section) {
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("identifier"))) {
                    SunscreenLibrary.library().logger().error("[CR014] No identifier defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Identifier identifier = Identifier.split(((Node<String>)section.find("identifier")).value());
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("position"))) {
                    SunscreenLibrary.library().logger().error("[CR015] No position defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Section positionSection = section.find("position");
                Position.PositionBuilder position = Position.config(positionSection);
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("size"))) {
                    SunscreenLibrary.library().logger().error("[CR016] No size defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Section sizeSection = section.find("size");
                Size.SizeBuilder size = Size.config(sizeSection);
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("color"))) {
                    SunscreenLibrary.library().logger().error("[CR017] No color defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Color color;
                Section colorSection = section.find("color");
                int red = ((Node<Integer>)colorSection.find("red")).value();
                int green = ((Node<Integer>)colorSection.find("green")).value();
                int blue = ((Node<Integer>)colorSection.find("blue")).value();
                color = Color.of(red, green, blue);
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("shape"))) {
                    SunscreenLibrary.library().logger().error("[CR018] No shape defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                String shape = ((Node<String>)section.find("shape")).value();
                if (shape == null) {
                    SunscreenLibrary.library().logger().error("[CR019] No shape found in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                if (shape.equals("rectangle")) {
                    SimpleBufferedElement element = ShapeElement.rectangle(null, identifier, position, color);
                    element.geometry(size);
                    return element;
                } else if (shape.equals("circle")) {

                }
                return null;
            }
        }

        class ButtonElementTransformer implements ElementTransformer {
            @Override
            public Element<?> transform(Section section) {
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("identifier"))) {
                    SunscreenLibrary.library().logger().error("[CR014] No identifier defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Identifier identifier = Identifier.split(((Node<String>)section.find("identifier")).value());
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("position"))) {
                    SunscreenLibrary.library().logger().error("[CR015] No position defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Section positionSection = section.find("position");
                Position.PositionBuilder position = Position.config(positionSection);
                Map<String, Canvas> canvases = get(section);
                if (canvases.isEmpty()) {
                    SunscreenLibrary.library().logger().error("[CR016] No canvas found in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Map<ButtonElement.State, Canvas> icons = new HashMap<>();
                canvases.forEach((key, value) -> icons.put(ButtonElement.State.valueOf(key.toUpperCase()), value));
                Size size = Size.pixel(canvases.values().stream().findAny().get().size());
                boolean hasSize = section.elements().stream().anyMatch(configElement -> configElement.name().equals("size"));
                if (hasSize) {
                    size = null;
                }
                SimpleBufferedElement element = ButtonElement.buttonElement(size, identifier, position, icons);
                if (hasSize) {
                    element.geometry(Size.config(section.find("size")));
                }
                return element;
            }
        }

        class TextElementTransformer implements ElementTransformer {
            @Override
            public Element<?> transform(Section section) {
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("identifier"))) {
                    SunscreenLibrary.library().logger().error("[CR017] No identifier defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Identifier identifier = Identifier.split(((Node<String>)section.find("identifier")).value());
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("position"))) {
                    SunscreenLibrary.library().logger().error("[CR018] No position defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Section positionSection = section.find("position");
                Position.PositionBuilder position = Position.config(positionSection);
                Text.Font font = Text.Fonts.stream().filter(f -> f.name().equals(((Node<String>)section.find("font")).value())).findFirst().orElse(Text.Font.vanilla());
                String text = ((Node<String>)section.find("text")).value();
                if (text == null) {
                    SunscreenLibrary.library().logger().error("[CR020] No text found in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Text text1 = Text.text(text, font);
                SimpleBufferedElement element = TextElement.textElement(identifier, position, text1);
                return element;
            }
        }

        private static boolean exists(String entry, Section section) {
            return section.elements().stream().anyMatch(configElement -> configElement.name().equals(entry));
        }

        private static <A> A get(Class<A> clazz, String entry, Section section) {
            if (!exists(entry, section)) {
                SunscreenLibrary.library().logger().error("[CR021] No {} defined in config file \"{}\".{}", entry, section.name(), Impl.ErrorMessage);
                return null;
            }
            return ((Node<A>)section.find(entry)).value();
        }

        private static String get(String entry, Section section) {
            return get(String.class, entry, section);
        }

        class TextInputElementTransformer implements ElementTransformer {

            @Override
            public Element<?> transform(Section section) {
                Identifier identifier = Identifier.split(ElementTransformer.get("identifier", section));
                if (!ElementTransformer.exists("position", section)) {
                    SunscreenLibrary.library().logger().error("[CR018] No position defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("size"))) {
                    SunscreenLibrary.library().logger().error("[CR019] No input handler defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Position.PositionBuilder position = Position.config(section.find("position"));
                Size.SizeBuilder size = Size.config(section.find("size"));
                TextInputElement element = TextInputElement.of(null, identifier, position, null);
                element.add(size);
                RuntimeDefinable.Type<InputHandler, OpenedMenu> type = new RuntimeDefinable.Impl.Type<>(InputHandler.class, OpenedMenu::inputHandler);
                element.add(type);
                Map<Interactable.ActionType, ActionWrapper> actionWrappers = Map.of();//Impl.action(section);
                if (actionWrappers != null) {
                    actionWrappers.forEach(element::action);
                }
                return element;
            }

        }

        class SelectorElementTransformer implements ElementTransformer {
            @Override
            public Element<?> transform(Section section) {
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("identifier"))) {
                    SunscreenLibrary.library().logger().error("[CR022] No identifier defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Identifier identifier = Identifier.split(((Node<String>)section.find("identifier")).value());
                if (section.elements().stream().noneMatch(configElement -> configElement.name().equals("position"))) {
                    SunscreenLibrary.library().logger().error("[CR023] No position defined in config file \"{}\".{}", section.name(), Impl.ErrorMessage);
                    return null;
                }
                Section positionSection = section.find("position");
                Position.PositionBuilder position = Position.config(positionSection);
                SelectorElement.Builder selectorElement = SelectorElement.selectorElement(null, identifier, null);
                Section entries = section.find("entries");
                selectorElement.spacing(0);
                Size size = null;
                for (ConfigElement element : entries.elements()) {
                    Map<ButtonElement.State, Canvas> canvases1 = get((Section) element).entrySet().stream().map(entry -> {
                        ButtonElement.State state = ButtonElement.State.valueOf(entry.getKey().toUpperCase());
                        Canvas canvas = entry.getValue();
                        return Map.entry(state, canvas);
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    ButtonElement buttonElement = ButtonElement.buttonElement(Size.pixel(canvases1.values().stream().findAny().get().size()), null, Position.pixel(0, 0), canvases1);
                    selectorElement.button(buttonElement);
                    if (size == null) {
                        size = buttonElement.size();
                    }
                }
                selectorElement.size(size);
                SelectorElement element = selectorElement.build();
                element.geometry(position);
                return element;
            }
        }

    }

}
