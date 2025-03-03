package me.combimagnetron.sunscreen.config;

import me.combimagnetron.passport.config.Config;
import me.combimagnetron.passport.config.element.ConfigElement;
import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.passport.user.User;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.div.Div;
import me.combimagnetron.sunscreen.menu.element.impl.ButtonElement;
import me.combimagnetron.sunscreen.menu.element.impl.ImageElement;
import me.combimagnetron.sunscreen.menu.element.impl.TextElement;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public interface MenuConfigTransformer {

    Menu handle(Config config, String fileName, SunscreenUser<?> user);

    class Impl implements MenuConfigTransformer {
        private final static String ErrorMessage = "\nPlease revisit the menu config or contact plugin developers.\nWhen contacting the developers please provide the fault code in the square brackets and your config file.";

        @Override
        public Menu handle(Config config, String fileName, SunscreenUser<?> user) {
            Optional<Section> menuSectionOptional = config.sections().stream().findFirst();
            if (menuSectionOptional.isEmpty()) {
                SunscreenLibrary.library().logger().error("[CR001] No menu section found in config file \"{}\".{}", fileName, ErrorMessage);
                return null;
            }
            Section menuSection = menuSectionOptional.get();
            if (menuSection.elements().stream().noneMatch(configElement -> configElement.name().equals("type"))) {
                SunscreenLibrary.library().logger().error("[CR002] No menu type defined in config file \"{}\".{}", fileName, ErrorMessage);
                return null;
            }
            String menuType = ((Node<String>)menuSection.elements().stream().filter(configElement -> configElement.name().equals("type")).findFirst().get()).value();
            Menu menu = null;
            switch (menuType) {
                case "float":
                    menu = new Menu.Float(user);
                    break;
                case "single":
                    menu = new Menu.Single(user);
                    break;
                case null:
                    break;
                default:
                    SunscreenLibrary.library().logger().error("[CR003] Invalid menu type \"{}\" in config file \"{}\".{}", menuType, fileName, ErrorMessage);
                    return null;
            }
            if (menu == null) {
                SunscreenLibrary.library().logger().error("[CR004] Failed to create menu from config file \"{}\".{}", fileName, ErrorMessage);
                return null;
            }
            if (menuSection.elements().stream().noneMatch(configElement ->  configElement.name().equals("divs"))) {
                SunscreenLibrary.library().logger().error("[CR005] No divs defined in config file \"{}\".{}", fileName, ErrorMessage);
                return null;
            }
            Section divsSection = (Section)menuSection.elements().stream().filter(configElement -> configElement.name().equals("divs")).findFirst().get();
            for (ConfigElement divConfigElement : divsSection.elements()) {
                if (divConfigElement instanceof Section divSection) {
                    Div div = Div.div(Identifier.split(((Node<String>)divSection.find("identifier")).value()));
                    Section elements = divSection.find("elements");
                    for (ConfigElement elementConfigElement : elements.elements()) {
                        if (elementConfigElement instanceof Section elementSection) {
                            div.add(ElementTransformer.transform(elementSection, fileName, user));
                        } else {
                            SunscreenLibrary.library().logger().error("[CR007] Invalid element \"{}\" in config file \"{}\".{}", elementConfigElement.name(), fileName, ErrorMessage);
                        }
                    }
                    menu.div(div);
                } else {
                    SunscreenLibrary.library().logger().error("[CR006] Invalid div \"{}\" in config file \"{}\".{}", divConfigElement.name(), fileName, ErrorMessage);
                }
            }
            return null;
        }

    }

    class ElementTransformer {
        private static final Map<String, Class<? extends Element>> ElementTypes = Map.of(
                "button", ButtonElement.class,
                "text", TextElement.class,
                "image", ImageElement.class
        );

        static Element transform(Section section, String config, SunscreenUser<?> user) {
            String type = section.name();
            if (!ElementTypes.containsKey(type)) {
                SunscreenLibrary.library().logger().error("[CR008] Type \"{}\" not expected for element in config file \"{}\".{}", type, config, Impl.ErrorMessage);
                return null;
            }
            Identifier identifier = Identifier.split(section.find("identifier"));
            Position position = Position.RegexPositionParser.parse(((Section) section.find("position")).find("x"), ((Section) section.find("position")).find("y"), user);
            Element element = switch (type) {
                //case "button" -> ButtonElement.buttonElement(Vec2d.of(26, 26), position, identifier, null);
                case "image" -> {
                    String canvasType = ((Node<String>)section.find("canvas")).value().split("\\(")[0];
                    Canvas canvas = switch (canvasType) {
                        case "file" -> Canvas.image(Canvas.ImageProvider.file(Path.of(((Node<String>)section.find("canvas")).value().replace("file(", "").replace(")", ""))));
                        case "url" -> Canvas.image(Canvas.ImageProvider.url(((Node<String>)section.find("canvas")).value().replace("file(", "").replace(")", "")));
                        default -> null;
                    };
                    yield ImageElement.imageElement(canvas, identifier, position);
                }
                default -> null;
            };
            if (element == null) {
                SunscreenLibrary.library().logger().error("[CR009] Failed to create element from config file \"{}\".{}", config, Impl.ErrorMessage);
            }
            return element;
        }

    }

}
