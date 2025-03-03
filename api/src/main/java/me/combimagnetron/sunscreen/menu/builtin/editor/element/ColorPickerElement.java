package me.combimagnetron.sunscreen.menu.builtin.editor.element;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.Color;
import me.combimagnetron.sunscreen.image.Pixel;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;

public class ColorPickerElement implements Element {
    private final Identifier identifier;
    private Position position;
    private Canvas canvas;

    private ColorPickerElement(Identifier identifier, Position position, Vec2d size) {
        this.identifier = identifier;
        this.position = position;
        render(size);
    }

    public static ColorPickerElement colorPicker(Identifier identifier, Position position, Vec2d size) {
        return new ColorPickerElement(identifier, position, size);
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public Canvas canvas() {
        return canvas;
    }

    private void render(Vec2d size) {
        canvas = Canvas.image(size);
        int width = size.xi();
        int height = size.yi();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int r = x * 255 / width;
                int g = y * 255 / height;
                int b = 255 - r; // Invert red for contrast
                int color = Color.of(r, g, b).rgb();
                canvas.pixel(Pixel.of(x, y, color));
            }
        }
    }

    @Override
    public Position position() {
        return position;
    }

    @Override
    public Element position(Position pos) {
        this.position = pos;
        return this;
    }

    @Override
    public <T> Element style(Style<T> style, Position pos2D, T t) {
        return null;
    }

    @Override
    public <T> Element style(Style<T> style, T t) {
        return null;
    }

    @Override
    public Element size(Vec2d size) {
        return null;
    }
}
