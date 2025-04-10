package me.combimagnetron.sunscreen.menu.element;

import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.util.Vec2d;

public abstract class SimpleBufferedElement implements Element<Canvas> {
    private Vec2d size;
    protected final Identifier identifier;
    protected Position position;
    protected Canvas canvas;

    public SimpleBufferedElement(Vec2d size, Identifier identifier, Position position) {
        this.size = size;
        this.canvas = Canvas.image(size);
        this.identifier = identifier;
        this.position = position;
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public abstract Canvas canvas();

    @Override
    public Position position() {
        return position;
    }

    public Vec2d size() {
        return size;
    }

    public Element size(Vec2d size) {
        this.size = size;
        return this;
    }

}
