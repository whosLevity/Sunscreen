package me.combimagnetron.sunscreen.menu.builtin.editor;

import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.Color;
import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.CheckerBoardEditorElement;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.SectionElement;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.sidebar.ElementTabElement;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.sidebar.LayerTabElement;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.div.Div;
import me.combimagnetron.sunscreen.menu.element.impl.*;
import me.combimagnetron.sunscreen.menu.timing.Tick;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.style.Text;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;

@SuppressWarnings("unchecked")
public class EditorMenu extends Menu.FloatImpl {
    public final static double PreviewScale = 0.7806122449;
    private final LinkedHashMap<Identifier, Element<Canvas>> editorElements = new LinkedHashMap<>();
    private final SunscreenUser<?> viewer;
    private final Vec2d previewSize;
    private SidebarTab lastTab;
    private SidebarTab currentTab;

    public EditorMenu(SunscreenUser<?> viewer) {
        super(viewer);
        this.viewer = viewer;
        this.previewSize = viewer.screenSize().pixel().mul(PreviewScale);
        build();
        open(viewer);
        hide(Identifier.of("editor", "preview"));
        hide(Identifier.of("editor", "creation_popup"));
    }

    @Override
    public boolean tick(@NotNull Tick tick) {
        super.tick(tick);
        return tickSidebar();
    }

    private void build() {
        CheckerBoardEditorElement editorElement = CheckerBoardEditorElement.of(viewer.screenSize().pixel(), Identifier.of("editor", "background"), Position.pixel(0, 0), Vec2d.of(8, 8));
        Div<Canvas> editor = Div.div(Identifier.of("editor", "preview"))
                .scale(Vector3d.vec3(PreviewScale))
                .size(viewer.screenSize().pixel())
                .position(Position.position(viewer)
                        .x()
                            .percentage(100 - (PreviewScale * 100))
                            .pixel(-8/PreviewScale)
                        .back()
                        .y()
                            .pixel(2/PreviewScale)
                        .back()
                        .finish()
                )
                .add(
                        editorElement
                );
        //editor.condition(Condition.of("%player_health% <= 10"));
        editorElements.put(Identifier.of("editor", "image"), ImageElement.imageElement(Canvas.image(Canvas.ImageProvider.file(Path.of("assets/sunscreen/editor/island.png"))), Identifier.of("editor", "image"), Position.center(viewer)));
        editorElements.put(Identifier.of("editor","shape"), ShapeElement.rectangle(Vec2d.of(100, 100), Identifier.of("editor", "shape"), Position.pixel(100, 100), Color.of(255, 0, 0)));
        editorElements.values().forEach(editorElement::element);
        div(editor);
        Div<Canvas> sidebar = constructSidebar();
        div(sidebar);
        Div<Canvas> editbar = constructEditbar();
        div(editbar);
        Div<Canvas> creationPopup = creationPopup();
        div(creationPopup);
    }

    private Div<Canvas> constructEditbar() {
        Vec2d editbarSize = Vec2d.of(viewer.screenSize().pixel().x() - 4, viewer.screenSize().pixel().y() - previewSize.y());
        return Div.div(Identifier.of("editor", "editbar"))
                .size(editbarSize)
                .position(
                        Position.position(viewer)
                                .x()
                                .pixel(2)
                            .back()
                                .y()
                                .pixel(previewSize.y()/PreviewScale + 2/PreviewScale)
                            .back()
                                .finish()
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
                ElementTabElement elementTab = ElementTabElement.of(Vec2d.of(sidebar.size().x(), sidebar.size().y() - 15), Identifier.of("sidebar", "element_tab"), Position.pixel(0, 30));
                SelectorElement selectorElement = SelectorElement.selectorElement(Vec2d.of(sidebar.size().x(), 15), Identifier.of("element_tab", "selector_bar"), Position.pixel(2, 16)).horizontal()
                        .button(
                                ButtonElement.buttonElement(Vec2d.of(33, 11), Identifier.of("element_tab", "local"), Position.pixel(2, 2))
                                        .standard(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Colors.Background).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Secondary).text(Text.text("Local", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                        .hover(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Secondary).text(Text.text("Local", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                        .click(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Quaternary).text(Text.text("Local", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                        .click(event -> System.out.println("Local"))
                                        .build()
                        )
                        .button(
                                ButtonElement.buttonElement(Vec2d.of(39, 11), Identifier.of("element_tab", "asset_lib"), Position.pixel(2, 2))
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
                LayerTabElement layerTab = LayerTabElement.of(Vec2d.of(sidebar.size().x(), sidebar.size().y() - 15), Identifier.of("sidebar", "layer_tab"), Position.pixel(0, 15), editorElements);
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
                    show(divHashMap.get(Identifier.of("editor", "creation_popup")));
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
                    double scale = (double) 18 / Math.max(preview.size().xi(), preview.size().yi());
                    Div<Canvas> previewDiv = Div.div(previewId)
                            .size(preview.size())
                            .position(Position.position(viewer).x().percentage(50 + add).back().y().percentage(50 + add).back().finish())
                            .scale(Vector3d.vec3(scale))
                            .add(preview);
                    div(previewDiv);
                    render(previewDiv);
                    add += 5;
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
                .size(Vec2d.of(200, 200))
                .position(
                        Position.position(viewer)
                                .x()
                                .percentage(50)
                                .pixel(-100)
                                .back()
                                .y()
                                .percentage(50)
                                .pixel(-100)
                                .back()
                                .finish()
                )
                .add(
                        SectionElement.sectionElement(Identifier.of("creation_popup", "background"), Position.pixel(0, 0), Vec2d.of(200, 200))
                ).add(
                        ShapeElement.rectangle(Vec2d.of(197, 15), Identifier.of("creation_popup", "text_input_background"), Position.pixel(3, 14), Colors.Secondary)
                ).add(
                        TextInputElement.of(Vec2d.of(100, 13), Identifier.of("creation_popup", "text_input"), Position.pixel(96, 15), inputHandler())
                ).add(
                        TextElement.textElement(Identifier.of("creation_popup", "text"), Position.pixel(3, 3), Text.text("Create a new element", Text.Font.vanilla())).style(Style.color(), Colors.PrimaryText)
                ).add(
                        TextElement.textElement(Identifier.of("creation_popup", "name_field_text"), Position.pixel(5, 18), Text.text("Name", Text.Font.vanilla())).style(Style.color(), Colors.SecondaryText)
                ).order(1);
        return div;
    }

    private Div<Canvas> constructSidebar() {
        double sidebarWidth = viewer.screenSize().pixel().mul(1 - PreviewScale).sub(6, 0).x();
        Vec2d sizeBarSize = Vec2d.of(sidebarWidth, previewSize.y());
        return Div.div(Identifier.of("editor", "sidebar"))
                .size(sizeBarSize)
                .position(
                        Position.position(viewer)
                                .x()
                                .pixel(2)
                                .back()
                                .y()
                                .pixel(2)
                                .back()
                                .finish()
                )
                .add(
                        SectionElement.sectionElement(Identifier.of("sidebar", "background"), Position.pixel(0, 0), Vec2d.of(sidebarWidth, 15))
                )
                .add(
                        SectionElement.sectionElement(Identifier.of("sidebar", "background_w"), Position.pixel(0, 14), Vec2d.of(sidebarWidth, previewSize.y() - 15))
                )
                .add(
                        SelectorElement.selectorElement(Vec2d.of(sidebarWidth, 15), Identifier.of("sidebar", "selector_bar"), Position.pixel(2, 2)).horizontal()
                                .button(
                                        ButtonElement.buttonElement(Vec2d.of(33, 11), Identifier.of("selector_bar", "layer"), Position.pixel(2, 2))
                                                .standard(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Colors.Background).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Secondary).text(Text.text("Layer", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .hover(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Secondary).text(Text.text("Layer", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .click(Canvas.image(Vec2d.of(33, 11)).fill(Vec2d.of(0, 0), Vec2d.of(33, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(31, 9), Colors.Quaternary).text(Text.text("Layer", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .click(event -> this.currentTab = SidebarTab.LAYER)
                                                .build()
                                )
                                .button(
                                        ButtonElement.buttonElement(Vec2d.of(39, 11), Identifier.of("selector_bar", "element"), Position.pixel(2, 2))
                                                .standard(Canvas.image(Vec2d.of(39, 11)).fill(Vec2d.of(0, 0), Vec2d.of(39, 11), Colors.Background).fill(Vec2d.of(1, 1), Vec2d.of(37, 9), Colors.Secondary).text(Text.text("Element", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .hover(Canvas.image(Vec2d.of(39, 11)).fill(Vec2d.of(0, 0), Vec2d.of(39, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(37, 9), Colors.Secondary).text(Text.text("Element", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .click(Canvas.image(Vec2d.of(39, 11)).fill(Vec2d.of(0, 0), Vec2d.of(39, 11), Color.white()).fill(Vec2d.of(1, 1), Vec2d.of(37, 9), Colors.Quaternary).text(Text.text("Element", Text.Font.vanilla()), Vec2d.of(2, 9)))
                                                .click(event -> this.currentTab = SidebarTab.ELEMENT)
                                                .build()
                                )
                                .button(
                                        ButtonElement.buttonElement(Vec2d.of(19, 11), Identifier.of("selector_bar", "file"), Position.pixel(2, 2))
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
