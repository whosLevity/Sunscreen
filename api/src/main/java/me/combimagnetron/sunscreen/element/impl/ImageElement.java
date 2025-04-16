package me.combimagnetron.sunscreen.element.impl;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;

public class ImageElement extends SimpleBufferedElement {
    private Canvas canvas;

    protected ImageElement(Canvas canvas, Identifier identifier, Position position) {
        super(Size.pixel(canvas.size().x(), canvas.size().y()), identifier, position);
        this.canvas = canvas;
    }

    public static ImageElement imageElement(Canvas canvas, Identifier identifier, RuntimeDefinableGeometry.GeometryBuilder<?> position) {
        ImageElement imageElement = new ImageElement(canvas, identifier, Position.pixel(0, 0));
        imageElement.geometry(position);
        return imageElement;
    }

    public static ImageElement imageElement(Canvas canvas, Identifier identifier, Position position) {
        return new ImageElement(canvas, identifier, position);
    }

    @Override
    public <T> Element style(Style<T> style, Position pos2D, T t) {
        this.canvas = style.edit(canvas(), pos2D, t);
        return this;
    }

    @Override
    public <T> Element style(Style<T> style, T t) {
        return style(style, Position.pixel(0,0), t);
    }

    @Override
    public Canvas canvas() {
        return canvas;
    }
}
