package me.combimagnetron.sunscreen.menu.builtin.editor.element.sidebar;

import me.combimagnetron.sunscreen.event.ClickElementEvent;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.Color;
import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.menu.builtin.editor.EditorMenu;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.SectionElement;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.menu.input.Input;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.style.Text;
import me.combimagnetron.sunscreen.util.HoverHelper;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

public class LayerTabElement extends SimpleBufferedElement implements Interactable {
    private final Map<Identifier, Element<Canvas>> elements;
    private final Collection<Identifier> selected = new LinkedList<>();
    private Consumer<ClickElementEvent<?>> elementEventConsumer = (e) -> {};

    public static LayerTabElement of(Size size, Identifier identifier, Position position, Map<Identifier, Element<Canvas>> elements) {
        return new LayerTabElement(size, identifier, position, elements);
    }

    public LayerTabElement(Size size, Identifier identifier, Position position, Map<Identifier, Element<Canvas>> elements) {
        super(size, identifier, position);
        this.elements = elements;
    }

    @Override
    public Canvas canvas() {
        return render();
    }

    public void selected(Identifier identifier) {
        selected.add(identifier);
    }

    public void consumer(Consumer<ClickElementEvent<?>> consumer) {
        this.elementEventConsumer = consumer;
    }

    public void unselected(Identifier identifier) {
        selected.remove(identifier);
    }

    private Canvas render() {
        Canvas result = Canvas.image(size());
        result.place(SectionElement.sectionElement(Identifier.of("_"), Position.pixel(0, 0), size()).canvas(), Vec2i.of(0, 0));
        int y = 1;
        for (Element<Canvas> element : elements.values()) {
            Canvas elementCanvas = Canvas.image(Vec2i.of(size().vec2i().x() - 2, 23));
            if (selected.contains(element.identifier())) {
                elementCanvas.fill(Vec2i.of(1, 1), Vec2i.of(size().vec2i().x(), 23), Color.white());
            } else {
                elementCanvas.fill(Vec2i.of(1, 1), Vec2i.of(size().x().pixel() - 2, 23), EditorMenu.Colors.Background);
            }
            elementCanvas.fill(Vec2i.of(2, 2), Vec2i.of(size().x().pixel() - 2, 21), EditorMenu.Colors.Secondary);
            elementCanvas.fill(Vec2i.of(3, 3), Vec2i.of(19, 19), EditorMenu.Colors.Tertiary);
            elementCanvas.text(Text.text(element.identifier().key().string(), Text.Font.vanilla()), Vec2i.of(24, 10), EditorMenu.Colors.PrimaryText);
            elementCanvas.text(Text.text("1 element", Text.Font.five()), Vec2i.of(23, 17), EditorMenu.Colors.SecondaryText);
            elementCanvas.text(Text.text(element.size().x().pixel() + "x" + element.size().y().pixel() + "px", Text.Font.five()), Vec2i.of(23, 23), EditorMenu.Colors.SecondaryText);
            result.place(elementCanvas, Vec2i.of(1, y));
            y += 22;
        }
        result.fill(Vec2i.of(3, size().y().pixel() - 13), Vec2i.of(size().x().pixel() - 6, 10), EditorMenu.Colors.Secondary);
        result.text(Text.text("Add Layer", Text.Font.vanilla()), Vec2i.of(4, size().y().pixel() - 5), EditorMenu.Colors.PrimaryText);
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

    @Override
    public boolean hover(Vec2i pos) {
        return false;
    }

    @Override
    public boolean click(Vec2i pos) {
        if (pos == null) {
            return false;
        }
        if (HoverHelper.isHovered(pos, Vec2i.of(3, size().y().pixel() - 13), Vec2i.of(size().x().pixel() - 6, 10))) {
            elementEventConsumer.accept(new ClickElementEvent<Element>(this, pos, new Input.Type.MouseClick(false)));
            return true;
        }
        return false;
    }

    @Override
    public Map<ActionType, ActionWrapper> actions() {
        return Map.of();
    }
}
