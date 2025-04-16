package me.combimagnetron.sunscreen.menu.builtin.editor.element;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.menu.builtin.editor.EditorMenu;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;

public class SectionElement extends SimpleBufferedElement {
    public SectionElement(Size size, Identifier identifier, Position position) {
        super(size, identifier, position);
    }

    public static SectionElement sectionElement(Identifier identifier, Position position, Size size) {
        return new SectionElement(size, identifier, position);
    }

    public static SectionElement sectionElement(Identifier identifier, RuntimeDefinableGeometry.GeometryBuilder<?> position, Size size) {
        SectionElement sectionElement = new SectionElement(size, identifier, null);
        sectionElement.geometry(position);
        return sectionElement;
    }

    @Override
    public Canvas canvas() {
        canvas = canvas.fill(Vec2d.of(0,0), size().vec2d(), EditorMenu.Colors.Background);
        canvas = canvas.fill(Vec2d.of(1,1), Vec2d.of(size().x().pixel() - 2, size().y().pixel() - 2), EditorMenu.Colors.Secondary);
        canvas = canvas.fill(Vec2d.of(2,2), Vec2d.of(size().x().pixel() - 4, size().y().pixel() - 4), EditorMenu.Colors.Background);
        return canvas;
    }

    @Override
    public <T> Element<Canvas> style(Style<T> style, Position pos2D, T t) {
        return null;
    }

    @Override
    public <T> Element<Canvas> style(Style<T> style, T t) {
        return null;
    }
}
