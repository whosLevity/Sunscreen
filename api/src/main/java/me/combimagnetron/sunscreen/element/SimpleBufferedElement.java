package me.combimagnetron.sunscreen.element;

import me.combimagnetron.sunscreen.menu.Geometry;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.menu.editor.EditableField;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.util.RuntimeDefinable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SimpleBufferedElement implements Element<Canvas> {
    private Size size;
    protected final Identifier identifier;
    protected Position position;
    protected List<RuntimeDefinable.Type<?, ?>> geometryBuilders = new ArrayList<>();
    protected Canvas canvas;

    public SimpleBufferedElement(Size size, Identifier identifier, Position position) {
        this.size = size;
        this.canvas = Canvas.image(size);
        this.identifier = identifier;
        this.position = position;
    }

    @Override
    public @NotNull Element<Canvas> position(Position pos) {
        this.position = pos;
        return this;
    }

    @Override
    public void add(RuntimeDefinable.Type<?, ?> definable) {
        geometryBuilders.add(definable);
    }

    @Override
    public @NotNull Identifier identifier() {
        return identifier;
    }

    @Override
    public @NotNull Collection<RuntimeDefinable.Type<?, ?>> definables() {
        return geometryBuilders;
    }

    @Override
    public @NotNull Element<Canvas> geometry(RuntimeDefinableGeometry.GeometryBuilder<?> geometry) {
        geometryBuilders.add(geometry);
        return this;
    }

    @Override
    public abstract @NotNull Canvas canvas();

    @Override
    public @NotNull Position position() {
        return position;
    }

    @Override
    public @NotNull Element<Canvas> geometry(Geometry geometry) {
        if (geometry instanceof Position) {
            this.position = (Position) geometry;
        } else if (geometry instanceof Size) {
            this.canvas = Canvas.image((Size) geometry);
            this.size = (Size) geometry;
        }
        return this;
    }

    @Override
    public @NotNull Size size() {
        return size;
    }

    @Override
    public @NotNull Element<Canvas> size(Size size) {
        this.size = size;
        return this;
    }

}
