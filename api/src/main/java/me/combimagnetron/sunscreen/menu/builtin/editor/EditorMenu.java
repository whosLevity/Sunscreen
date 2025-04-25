package me.combimagnetron.sunscreen.menu.builtin.editor;

import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.config.MenuConfigTransformer;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.element.impl.*;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.Color;
import me.combimagnetron.sunscreen.menu.*;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.CheckerBoardEditorElement;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.SectionElement;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.sidebar.ElementTabElement;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.sidebar.LayerTabElement;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.element.div.ScrollableDiv;
import me.combimagnetron.sunscreen.menu.timing.Tick;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.style.Text;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Scheduler;
import me.combimagnetron.sunscreen.util.Vec2d;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@SuppressWarnings("unchecked")
public class EditorMenu extends OpenedMenu.FloatImpl {
    public final static double PreviewScale = 0.7806122449;
    private final LinkedHashMap<Identifier, Element<Canvas>> editorElements = new LinkedHashMap<>();
    private final SunscreenUser<?> viewer;
    private final Vec2d previewSize;
    private SidebarTab lastTab;
    private SidebarTab currentTab;

    public EditorMenu(SunscreenUser<?> viewer) {
        super(viewer, MenuTemplate.simple(MenuTemplate.Type.FLOAT, null));
        this.viewer = viewer;
        this.previewSize = viewer.screenSize().pixel().mul(PreviewScale);
        divHashMap.putAll((build()));
        forceDivGeometry();
        open(viewer);
        hide(Identifier.of("editor", "preview"));
        hide(Identifier.of("editor", "creation_popup"));
    }

    @Override
    public boolean tick(@NotNull Tick tick) {
        super.tick(tick);
        return tickSidebar();
    }

    private Map<Identifier, Div<Canvas>> build() {
        MenuTemplate template = MenuTemplate.simple(MenuTemplate.Type.FLOAT, Identifier.of("editor", "menu"));
        CheckerBoardEditorElement editorElement = CheckerBoardEditorElement.of(Size.pixel(viewer.screenSize().pixel()), Identifier.of("editor", "background"), Position.pixel(0, 0), Vec2d.of(8, 8));
        Div<Canvas> editor = Div.div(Identifier.of("editor", "preview"))
                .scale(Vector3d.vec3(PreviewScale))
                .size(Size.pixel(viewer.screenSize().pixel().x(), viewer.screenSize().pixel().y()))
                .position(Position.position()
                        .x()
                            .percentage(100 - (PreviewScale * 100))
                            .pixel(-8/PreviewScale)
                        .back()
                        .y()
                            .pixel(2/PreviewScale)
                        .back()
                )
                .add(
                        editorElement
                );
        editorElements.put(Identifier.of("editor", "image"), ImageElement.imageElement(Canvas.image(Canvas.ImageProvider.file(Path.of("assets/sunscreen/editor/island.png"))), Identifier.of("editor", "image"), Position.position().x().percentage(50).back().x().percentage(50).back()));
        editorElements.put(Identifier.of("editor","shape"), ShapeElement.rectangle(Size.pixel(100, 100), Identifier.of("editor", "shape"), Position.pixel(100, 100), Color.of(255, 0, 0)));
        editorElements.values().forEach(editorElement::element);
        Map<Identifier, Div<Canvas>> divs = new LinkedHashMap<>();
        Div<Canvas> sidebar = constructSidebar();
        divs.put(sidebar.identifier(), sidebar);
        Div<Canvas> editbar = constructEditbar();
        divs.put(editbar.identifier(), editbar);
        Div<Canvas> creationPopup = creationPopup();
        divs.put(creationPopup.identifier(), creationPopup);
        return divs;
    }

    private Div<Canvas> constructEditbar() {
        Size editbarSize = Size.pixel(viewer.screenSize().pixel().x() - 4, viewer.screenSize().pixel().y() - previewSize.y());
        return Div.div(Identifier.of("editor", "editbar"))
                .size(editbarSize)
                .position(
                        Position.position()
                                .x()
                                .pixel(2)
                            .back()
                                .y()
                                .pixel(250)
                            .back()
                )
                .add(
                        SectionElement.sectionElement(Identifier.of("editbar", "background"), Position.pixel(0, 0), editbarSize)
                );
    }

    private boolean tickSidebar() {
        Div<Canvas> sidebar = (Div<Canvas>) divHashMap.get(Identifier.of("editor", "sidebar"));
        if (sidebar == null) {
            return false;
        }
        if (lastTab == currentTab) {
            return false;
        }
        switch (currentTab) {
            case ELEMENT:
                sidebar.hide(Identifier.of("sidebar", "layer_tab"));
                sidebar.hide(Identifier.of("sidebar", "files_tab"));
                divHashMap.entrySet().stream().filter(entry -> entry.getKey().namespace().string().equals("preview") || entry.getKey().namespace().string().equals("element_tab") || entry.getKey().namespace().string().equals("files_tab")).forEach(entry -> {
                    Div<?> previewDiv = divHashMap.get(entry.getKey());
                    if (previewDiv != null) {
                        hide(previewDiv);
                    }
                });
                ElementTabElement elementTab = ElementTabElement.of(Size.pixel(sidebar.size().x(), sidebar.size().y() - 15), Identifier.of("sidebar", "element_tab"), Position.pixel(0, 30));
                SelectorElement selectorElement = SelectorElement.selectorElement(Size.pixel(sidebar.size().x(), 15), Identifier.of("element_tab", "selector_bar"), Position.pixel(2, 16)).horizontal()
                        .button(
                                ButtonElement.buttonElement(Size.pixel(33, 11), Identifier.of("element_tab", "local"), Position.pixel(2, 2))
                                        .standard(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Colors.Background).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Secondary).text(Text.text("Local", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                        .hover(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Secondary).text(Text.text("Local", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                        .click(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Quaternary).text(Text.text("Local", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                        .click(event -> System.out.println("Local"))
                                        .build()
                        )
                        .button(
                                ButtonElement.buttonElement(Size.pixel(39, 11), Identifier.of("element_tab", "asset_lib"), Position.pixel(2, 2))
                                        .standard(Canvas.image(Vec2d.of(39, 11)).fill(Vec2d.of(0, 0), Vec2d.of(39, 11), Colors.Background).fill(Vec2d.of(1, 1), Vec2d.of(37, 9), Colors.Secondary).text(Text.text("Asset Lib", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                        .hover(Canvas.image(Vec2d.of(39, 11)).fill(Vec2d.of(0, 0), Vec2d.of(39, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(37, 9), Colors.Secondary).text(Text.text("Asset Lib", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                        .click(Canvas.image(Vec2d.of(39, 11)).fill(Vec2d.of(0, 0), Vec2d.of(39, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(37, 9), Colors.Quaternary).text(Text.text("Asset Lib", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                        .click(event -> System.out.println("Asset Lib"))
                                        .build()
                        )
                        .build();
                sidebar.add(
                        elementTab
                ).add(
                        selectorElement
                );
                break;
            case FILES:
                sidebar.hide(Identifier.of("sidebar", "layer_tab"));
                sidebar.hide(Identifier.of("sidebar", "element_tab"));
                divHashMap.entrySet().stream().filter(entry -> entry.getKey().namespace().string().equals("preview") || entry.getKey().namespace().string().equals("element_tab") || entry.getKey().namespace().string().equals("layer_tab")).forEach(entry -> {
                    Div<?> previewDiv = divHashMap.get(entry.getKey());
                    if (previewDiv != null) {
                        hide(previewDiv);
                    }
                });
                break;
            case LAYER:
                Div<?> editor = divHashMap.get(Identifier.of("editor", "preview"));
                sidebar.hide(Identifier.of("sidebar", "element_tab"));
                sidebar.hide(Identifier.of("sidebar", "files_tab"));
                CheckerBoardEditorElement editorElement = (CheckerBoardEditorElement) editor.elements().stream().filter(element -> element instanceof CheckerBoardEditorElement).map(element -> element).findFirst().get();
                LayerTabElement layerTab = LayerTabElement.of(Size.pixel(sidebar.size().x(), sidebar.size().y() - 15), Identifier.of("sidebar", "layer_tab"), Position.pixel(0, 15), editorElements);
                divHashMap.entrySet().stream().filter(entry -> entry.getKey().namespace().string().equals("element_tab") || entry.getKey().namespace().string().equals("files_tab")).forEach(entry -> {
                    Div<Canvas> previewDiv = (Div<Canvas>) divHashMap.get(entry.getKey());
                    if (previewDiv != null) {
                        hide(previewDiv);
                    }
                });
                layerTab.consumer(e -> {
                    currentTab = SidebarTab.ELEMENT;
                    SelectorElement element = (SelectorElement) sidebar.elements().stream().filter(el -> el.identifier().equals(Identifier.of("sidebar", "selector_bar"))).findFirst().get();
                    element.select(Identifier.of("selector_bar", "element"));
                    Identifier elementId = Identifier.of("editor", "creation_popup");
                    show(elementId);
                    focus(elementId);
                });
                Collection<Element<Canvas>> selected = editorElement.selected();
                int add = 0;
                for (Element<Canvas> preview : editorElements.values()) {
                    Identifier previewId = Identifier.of("preview", preview.identifier().key().string());
                    if (divHashMap.get(previewId) != null) {
                        Div<Canvas> previewDiv = (Div<Canvas>) divHashMap.get(previewId);
                        show(previewDiv);
                        continue;
                    }
                    double scale = (double) 18 / Math.max(preview.size().x().pixel(), preview.size().y().pixel());
                    Div<Canvas> previewDiv = Div.div(previewId)
                            .size(preview.size())
                            .position(Position.position().x().pixel(5.5).back().y().pixel((14 + add)).back())
                            .add(preview)
                            .scale(Vector3d.vec3(scale))
                            .order(1);
                    divHashMap.put(previewId, previewDiv);
                    BufferedImage canvas = ((Canvas.StaticImpl)previewDiv.render(viewer)).image();
                    forceDivGeometry();
                    render(previewDiv);
                    add += 25;
                }
                for (Element<Canvas> element : editorElements.values()) {
                    if (selected.contains(element)) {
                        layerTab.selected(element.identifier());
                        continue;
                    }
                    layerTab.unselected(element.identifier());
                }
                sidebar.add(
                        layerTab
                );
                break;
            default:
                break;
        }
        update(sidebar);
        lastTab = currentTab;
        return true;
    }

    private Div<Canvas> creationPopup() {
        Div<Canvas> div = Div.div(Identifier.of("editor", "creation_popup"))
                .size(Size.pixel(200, 200))
                .position(
                        Position.position()
                                .x()
                                .percentage(50)
                                .pixel(-100)
                                .back()
                                .y()
                                .percentage(50)
                                .pixel(-100)
                                .back()
                )
                .add(
                        SectionElement.sectionElement(Identifier.of("creation_popup", "background"), Position.pixel(0, 0), Size.pixel(200, 200))
                ).add(
                        ShapeElement.rectangle(Size.pixel(196, 11), Identifier.of("creation_popup", "text_input_background"), Position.pixel(3, 14), Colors.Secondary)
                ).add(
                        TextInputElement.of(Size.pixel(100, 9), Identifier.of("creation_popup", "text_input"), Position.pixel(96, 15), inputHandler())
                ).add(
                        TextElement.textElement(Identifier.of("creation_popup", "text"), Position.pixel(3, 3), Text.text("Create a new element", Text.Font.vanilla())).style(Style.color(), Colors.PrimaryText)
                ).add(
                        TextElement.textElement(Identifier.of("creation_popup", "name_field_text"), Position.pixel(5, 16), Text.text("Name", Text.Font.vanilla())).style(Style.color(), Colors.SecondaryText)
                ).add(
                        ShapeElement.rectangle(Size.pixel(196, 11), Identifier.of("creation_popup", "element_type_background"), Position.pixel(3, 27), Colors.Secondary)
                ).add(
                        TextElement.textElement(Identifier.of("creation_popup", "type_dropdown_text"), Position.pixel(5, 29), Text.text("Type", Text.Font.vanilla())).style(Style.color(), Colors.SecondaryText)
                )
                .order(1);
        DropdownElement.Builder builder = DropdownElement.dropdownElement(Size.pixel(100, 100), Identifier.of("creation_popup", "element_type_dropdown"), Position.pixel(96, 28));
        for (Map.Entry<String, MenuConfigTransformer.ElementTransformer> entry : MenuConfigTransformer.ElementTransformer.Transformers.entrySet()) {
            String string = entry.getKey();
            string = string.replace("_", " ");
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : string.split(" ")) {
                stringBuilder.append(s.substring(0, 1).toUpperCase());
                stringBuilder.append(s.substring(1));
                stringBuilder.append(" ");
            }
            ButtonElement element = ButtonElement.buttonElement(Size.pixel(100, 9), Identifier.of("creation_popup", "layer"), Position.pixel(2, 2))
                    .standard(Canvas.image(Vec2d.of(100, 9)).fill(Vec2d.of(0, 0), Vec2d.of(100, 9), Colors.Background).text(Text.text(stringBuilder.toString(), Text.Font.vanilla()), Vec2d.of(2, 8)))
                    .hover(Canvas.image(Vec2d.of(100, 9)).fill(Vec2d.of(0, 0), Vec2d.of(100, 9), Colors.Tertiary).text(Text.text(stringBuilder.toString(), Text.Font.vanilla()), Vec2d.of(2, 8)))
                    .click(Canvas.image(Vec2d.of(100, 9)).fill(Vec2d.of(0, 0), Vec2d.of(100, 9), Colors.Tertiary).text(Text.text(stringBuilder.toString(), Text.Font.vanilla()), Vec2d.of(2, 8))).build();
            builder.button(element);
        }
        div.add(builder.build());
        ButtonElement button = ButtonElement.buttonElement(Size.pixel(96, 9), Identifier.of("creation_popup", "close"), Position.pixel(2, 187))
                .standard(Canvas.image(Vec2d.of(96, 9)).fill(Vec2d.of(0, 0), Vec2d.of(96, 9), Colors.Background).text(Text.text("Close", Text.Font.vanilla()), Vec2d.of(2, 8)))
                .hover(Canvas.image(Vec2d.of(96, 9)).fill(Vec2d.of(0, 0), Vec2d.of(96, 9), Colors.Tertiary).text(Text.text("Close", Text.Font.vanilla()), Vec2d.of(2, 8)))
                .click(Canvas.image(Vec2d.of(96, 9)).fill(Vec2d.of(0, 0), Vec2d.of(96, 9), Colors.Tertiary).text(Text.text("Close", Text.Font.vanilla()), Vec2d.of(2, 8)))
                .click(e -> {
                    if (e.coords() == null) {
                        return;
                    }
                    unfocus();
                    Scheduler.delay(() -> hide(Identifier.of("editor", "creation_popup")), 1);
                })
                .build();
        div.add(button);
        return div;
    }

    private Div<Canvas> constructSidebar() {
        double sidebarWidth = viewer.screenSize().pixel().mul(1 - PreviewScale).sub(6, 0).x();
        Vec2d sizeBarSize = Vec2d.of(sidebarWidth, previewSize.y());
        return Div.div(Identifier.of("editor", "sidebar"))
                .size(Size.pixel(sizeBarSize))
                .position(
                        Position.position()
                                .x()
                                .pixel(2)
                                .back()
                                .y()
                                .pixel(2)
                                .back()
                )
                .add(
                        SectionElement.sectionElement(Identifier.of("sidebar", "background"), Position.pixel(0, 0), Size.pixel(sidebarWidth, 15))
                )
                .add(
                        SectionElement.sectionElement(Identifier.of("sidebar", "background_w"), Position.pixel(0, 14), Size.pixel(sidebarWidth, previewSize.y() - 15))
                )
                .add(
                        SelectorElement.selectorElement(Size.pixel(sidebarWidth, 15), Identifier.of("sidebar", "selector_bar"), Position.pixel(2, 2)).horizontal()
                                .button(
                                        ButtonElement.buttonElement(Size.pixel(33, 11), Identifier.of("selector_bar", "layer"), Position.pixel(2, 2))
                                                .standard(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Colors.Background).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Secondary).text(Text.text("Layer", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .hover(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Secondary).text(Text.text("Layer", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .click(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Quaternary).text(Text.text("Layer", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .click(event -> this.currentTab = SidebarTab.LAYER)
                                                .build()
                                )
                                .button(
                                        ButtonElement.buttonElement(Size.pixel(39, 11), Identifier.of("selector_bar", "element"), Position.pixel(2, 2))
                                                .standard(Canvas.image(Vec2d.of(39, 11)).fill(Vec2d.of(0, 0), Vec2d.of(39, 11), Colors.Background).fill(Vec2d.of(1, 1), Vec2d.of(37, 9), Colors.Secondary).text(Text.text("Element", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .hover(Canvas.image(Vec2d.of(39, 11)).fill(Vec2d.of(0, 0), Vec2d.of(39, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(37, 9), Colors.Secondary).text(Text.text("Element", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .click(Canvas.image(Vec2d.of(39, 11)).fill(Vec2d.of(0, 0), Vec2d.of(39, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(37, 9), Colors.Quaternary).text(Text.text("Element", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .click(event -> this.currentTab = SidebarTab.ELEMENT)
                                                .build()
                                )
                                .button(
                                        ButtonElement.buttonElement(Size.pixel(19, 11), Identifier.of("selector_bar", "file"), Position.pixel(2, 2))
                                                .standard(Canvas.image(Vec2d.of(19, 11)).fill(Vec2d.of(0, 0), Vec2d.of(19, 11), Colors.Background).fill(Vec2d.of(1, 1), Vec2d.of(17, 9), Colors.Secondary).text(Text.text("File", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .hover(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Secondary).text(Text.text("File", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .click(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Quaternary).text(Text.text("File", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .click(event -> this.currentTab = SidebarTab.FILES)
                                                .build()
                                ).build()
                );
    }

    enum SidebarTab {
        ELEMENT,
        FILES,
        LAYER
    }

    public static class Colors {
        public static final Color Background = Color.of(9, 10, 20);
        public static final Color Secondary = Color.of(16, 20, 31);
        public static final Color Tertiary = Color.of(21, 29, 40);
        public static final Color Quaternary = Color.of(32, 46, 55);
        public static final Color Fifth = Color.of(51, 65, 75);
        public static final Color Sixth = Color.of(76, 91, 101);

        public static final Color PrimaryText = Color.of(199, 220, 208);
        public static final Color SecondaryText = Color.of(155, 171, 178);

    }


}
