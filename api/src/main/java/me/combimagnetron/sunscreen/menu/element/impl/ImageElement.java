package me.combimagnetron.sunscreen.menu.element.impl;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;

public class ImageElement extends SimpleBufferedElement {
    private Canvas canvas;

    protected ImageElement(Canvas canvas, Identifier identifier, Position position) {
        super(canvas.size(), identifier, position);
        this.canvas = canvas;
    }

    public static ImageElement imageElement(Canvas canvas, Identifier identifier, Position position) {
        return new ImageElement(canvas, identifier, position);
    }

    @Override
    public Element position(Position pos) {
        this.position = pos;
        return this;
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
