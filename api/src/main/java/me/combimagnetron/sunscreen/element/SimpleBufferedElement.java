package me.combimagnetron.sunscreen.element;

import me.combimagnetron.sunscreen.menu.Geometry;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.util.Vec2d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SimpleBufferedElement implements Element<Canvas> {
    private Size size;
    protected final Identifier identifier;
    protected Position position;
    protected List<RuntimeDefinableGeometry.GeometryBuilder<?>> geometryBuilders = new ArrayList<>();
    protected Canvas canvas;

    public SimpleBufferedElement(Size size, Identifier identifier, Position position) {
        this.size = size;
        this.canvas = Canvas.image(size);
        this.identifier = identifier;
        this.position = position;
    }

    @Override
    public SimpleBufferedElement position(Position pos) {
        this.position = pos;
        return this;
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public Collection<RuntimeDefinableGeometry.GeometryBuilder<?>> definables() {
        return geometryBuilders;
    }

    @Override
    public Element<Canvas> geometry(RuntimeDefinableGeometry.GeometryBuilder<?> geometry) {
        geometryBuilders.add(geometry);
        return this;
    }

    @Override
    public abstract Canvas canvas();

    @Override
    public Position position() {
        return position;
    }

    @Override
    public Element<Canvas> geometry(Geometry geometry) {
        if (geometry instanceof Position) {
            this.position = (Position) geometry;
        } else if (geometry instanceof Size) {
            this.size = (Size) geometry;
        }
        return this;
    }

    @Override
    public Size size() {
        return size;
    }

    @Override
    public Element<Canvas> size(Size size) {
        this.size = size;
        return this;
    }

}
