package me.combimagnetron.sunscreen.menu.element.impl;

import me.combimagnetron.passport.event.Event;
import me.combimagnetron.sunscreen.event.ClickElementEvent;
import me.combimagnetron.sunscreen.event.HoverElementEvent;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.menu.element.Interactable;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.SimpleBufferedElement;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ButtonElement extends SimpleBufferedElement {
    private final Map<State, Canvas> icons = new HashMap<>();
    private Canvas selected;

    protected ButtonElement(Vec2d size, Position position, Identifier identifier, Map<State, Canvas> icon) {
        super(size, identifier, position);
        icons.putAll(icon);
    }

    public static Builder buttonElement(Vec2d size, Identifier identifier, Position position) {
        return new Builder(size, identifier, position);
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

    @Override
    public Canvas canvas() {
        return selected;
    }

    public Map<State, Canvas> icons() {
        return icons;
    }

    public static class Builder {
        private final Vec2d size;
        private final Identifier identifier;
        private final Position position;
        private final HashMap<State, Canvas> icons = new HashMap<>();

        public Builder(Vec2d size, Identifier identifier, Position position) {
            this.size = size;
            this.identifier = identifier;
            this.position = position;
        }

        public Builder icon(State state, Canvas icon) {
            icons.put(state, icon);
            return this;
        }

        public Builder standard(Canvas icon) {
            return icon(State.DEFAULT, icon);
        }

        public Builder hover(Canvas icon) {
            return icon(State.HOVER, icon);
        }

        public Builder click(Canvas icon) {
            return icon(State.CLICK, icon);
        }

        public ButtonElement build() {
            return new ButtonElement(size, position, identifier, icons);
        }
    }

    public enum State {
        DEFAULT,
        HOVER,
        CLICK
    }

}
