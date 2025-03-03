package me.combimagnetron.sunscreen.menu.builtin.editor.element;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.builtin.editor.EditorMenu;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;

public class SectionElement extends SimpleBufferedElement {
    public SectionElement(Vec2d size, Identifier identifier, Position position) {
        super(size, identifier, position);
    }

    public static SectionElement sectionElement(Identifier identifier, Position position, Vec2d size) {
        return new SectionElement(size, identifier, position);
    }

    @Override
    public Canvas canvas() {
        canvas = canvas.fill(Vec2d.of(0,0), size(), EditorMenu.Colors.Background);
        canvas = canvas.fill(Vec2d.of(1,1), Vec2d.of(size().x() - 1, size().y() - 1), EditorMenu.Colors.Secondary);
        canvas = canvas.fill(Vec2d.of(2,2), Vec2d.of(size().x() - 2, size().y() - 2), EditorMenu.Colors.Background);
        return canvas;
    }

    @Override
    public Element position(Position pos) {
        return null;
    }

    @Override
    public <T> Element style(Style<T> style, Position pos2D, T t) {
        return null;
    }

    @Override
    public <T> Element style(Style<T> style, T t) {
        return null;
    }
}
