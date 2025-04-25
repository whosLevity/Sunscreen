package me.combimagnetron.sunscreen.menu.builtin.editor.element;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.Color;
import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.menu.builtin.editor.EditorMenu;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.element.impl.ButtonElement;
import me.combimagnetron.sunscreen.element.impl.ImageElement;
import me.combimagnetron.sunscreen.element.impl.ShapeElement;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.style.Text;
import me.combimagnetron.sunscreen.util.*;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class CheckerBoardEditorElement extends SimpleBufferedElement implements Interactable {
    private final LinkedHashMap<Identifier, Pair<Element<Canvas>, Boolean>> elements = new LinkedHashMap<>();
    private final Vec2d tileSize;

    public static CheckerBoardEditorElement of(Size size, Identifier identifier, Position position, Vec2d tileSize) {
        return new CheckerBoardEditorElement(size, identifier, position, tileSize);
    }

    public void element(Element<Canvas> element) {
        elements.put(element.identifier(), Pair.of(element, false));
    }

    CheckerBoardEditorElement(Size size, Identifier identifier, Position position, Vec2d tileSize) {
        super(size, identifier, position);
        this.tileSize = tileSize;
    }

    @Override
    public Canvas canvas() {
        return render();
    }

    private Canvas render() {
        Canvas result = Canvas.image(size());
        result.fill(Vec2d.of(0, 0), size().vec2d(), Color.lightGray());
        for (int x = 0; x < size().x().pixel(); x++) {
            for (int y = 0; y < size().y().pixel(); y++) {
                if ((x + y) % 2 == 0) {
                    result.fill(Vec2d.of(x, y).mul(tileSize), tileSize, Color.white());
                }
            }
        }
        for (Pair<Element<Canvas>, Boolean> element : elements.values()) {
            Canvas elementCanvas = element.k().canvas();
            result.place(elementCanvas, ViewportHelper.fromPosition(element.k().position()));
        }
        return result;
    }

    @Override
    public <T> Element<Canvas> style(Style<T> style, Position pos2D, T t) {
        return null;
    }

    @Override
    public <T> Element<Canvas> style(Style<T> style, T t) {
        return null;
    }

    @Override
    public boolean reactiveToHover() {
        return true;
    }

    @Override
    public boolean reactiveToClick() {
        return true;
    }

    public Collection<Element<Canvas>> selected() {
        return elements.values().stream().filter(Pair::v).map(Pair::k).filter(e -> !e.identifier().namespace().string().equals("toolbar_internal263") && e.identifier().key().string().equals("hover_262")).toList();
    }

    @Override
    public boolean hover(Vec2d pos) {
        /*for (Element element : elements.values()) {
            if (element.identifier().key().string().equals("hover_262") && !HoverHelper.isHovered(pos, ViewportHelper.fromPosition(element.position()).mul(EditorMenu.PreviewScale), element.size())) {
                System.out.println(element.identifier() + " true");
                elements.remove(element.identifier());
                elements.remove(Identifier.of("toolbar_internal263", "hover_263"));
                return true;
            }
        }*/
        return false;
    }

    private Canvas generateToolBar() {
        Div div = Div.div(Identifier.of("editor", "toolbar"))
                .size(Size.pixel(195, 17))
                .add(
                        ShapeElement.rectangle(Size.pixel(194, 15), Identifier.of("toolbar", "background"), Position.pixel(1, 1), EditorMenu.Colors.Background)
                )
                .add(
                        ButtonElement.buttonElement(Size.pixel(55, 17), Identifier.of("toolbar", "ai_assistant"), Position.pixel(0, 0))
                                .standard(Canvas.image(Vec2d.of(55, 17)).fill(Vec2d.of(0, 0), Vec2d.of(55, 17), Color.of(255, 255, 255)).text(Text.text("Assist", Text.Font.vanilla()), Vec2d.of(19, 5)))
                                .hover(Canvas.image(Vec2d.of(55, 17)).fill(Vec2d.of(0, 0), Vec2d.of(55, 17), Color.of(255, 255, 255)))
                                .click(Canvas.image(Vec2d.of(55, 17)).fill(Vec2d.of(0, 0), Vec2d.of(55, 17), Color.of(255, 255, 255)))
                                .build()
                );

        return div.render(null);
    }

    @Override
    public boolean click(Vec2d pos) {
        boolean update = false;
        for (Pair<Element<Canvas>, Boolean> element : elements.values()) {
            if (element == null) {
                continue;
            }
            if (element.k().identifier().key().string().equals("hover_262")) {
                elements.remove(element.k().identifier());
                elements.remove(Identifier.of("toolbar_internal263", "hover_263"));
                update = true;
            }
            if (!HoverHelper.isHovered(pos, ViewportHelper.fromPosition(element.k().position()).mul(EditorMenu.PreviewScale), element.k().size().vec2d().mul(EditorMenu.PreviewScale))) {
                continue;
            }
            Canvas canvas1 = Canvas.image(element.k().size().vec2d().add(4, 4));
            canvas1.fill(Vec2d.of(1, 1), Vec2d.of(element.k().size().x().pixel(), 1), Color.of(77, 155, 230));
            canvas1.fill(Vec2d.of(element.k().size().x().pixel() + 1, 1), Vec2d.of(1, element.k().size().y().pixel()), Color.of(77, 155, 230));
            canvas1.fill(Vec2d.of(1, element.k().size().y().pixel() + 1), Vec2d.of(element.k().size().x().pixel(), 1), Color.of(77, 155, 230));
            canvas1.fill(Vec2d.of(1, 1), Vec2d.of(1, element.k().size().y().pixel()), Color.of(77, 155, 230));
            canvas1.fill(Vec2d.of(0, 0), Vec2d.of(2, 2), Color.of(72, 74, 119));
            canvas1.fill(Vec2d.of(element.k().size().x().pixel() + 1, 0), Vec2d.of(2, 2), Color.of(72, 74, 119));
            canvas1.fill(Vec2d.of(0, element.k().size().y().pixel() + 1), Vec2d.of(2, 2), Color.of(72, 74, 119));
            canvas1.fill(Vec2d.of(element.k().size().x().pixel() + 1, element.k().size().y().pixel() + 1), Vec2d.of(2, 2), Color.of(72, 74, 119));
            ImageElement imageElement = ImageElement.imageElement(canvas1, Identifier.of(element.k().identifier().key().string(), "hover_262"), Position.pixel(element.k().position().x().pixel() - 2, element.k().position().y().pixel() - 2));
            ImageElement toolBar = ImageElement.imageElement(generateToolBar(), Identifier.of("toolbar_internal263", "hover_263"), Position.pixel(element.k().position().x().pixel(), element.k().position().y().pixel() - 25));
            elements.put(Identifier.of(element.k().identifier().key().string(), "hover_262"), Pair.of(imageElement, true));
            elements.put(Identifier.of("toolbar_internal263", "hover_263"), Pair.of(toolBar, false));
            return true;
        }
        return update;
    }

    @Override
    public Map<ActionType, ActionWrapper> actions() {
        return Map.of();
    }
}
