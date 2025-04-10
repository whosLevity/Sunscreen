package me.combimagnetron.sunscreen.menu.element.impl;

import me.combimagnetron.passport.event.Event;
import me.combimagnetron.sunscreen.event.ClickElementEvent;
import me.combimagnetron.sunscreen.event.HoverElementEvent;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.input.Input;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.menu.element.Interactable;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.SimpleBufferedElement;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ButtonElement extends SimpleBufferedElement implements Interactable {
    private final Map<State, Canvas> icons = new HashMap<>();
    private final Consumer<ClickElementEvent<ButtonElement>> click;
    private Canvas selected;

    protected ButtonElement(Vec2d size, Position position, Identifier identifier, Map<State, Canvas> icon, Consumer<ClickElementEvent<ButtonElement>> click) {
        super(size, identifier, position);
        this.click = click;
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
        return selected != null ? selected : icons.get(State.DEFAULT);
    }

    public Map<State, Canvas> icons() {
        return icons;
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
    public boolean hover(Vec2d pos) {
        return false;
    }

    @Override
    public boolean click(Vec2d pos) {
        if (click != null) {
            click.accept(ClickElementEvent.create(this, pos, new Input.Type.MouseClick(false)));
        }
        return true;
    }

    public static class Builder {
        private final Vec2d size;
        private final Identifier identifier;
        private final Position position;
        private final HashMap<State, Canvas> icons = new HashMap<>();
        private Consumer<ClickElementEvent<ButtonElement>> click = (t) -> {};

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

        public Builder click(Consumer<ClickElementEvent<ButtonElement>> click) {
            this.click = click;
            return this;
        }

        public Builder hover(Canvas icon) {
            return icon(State.HOVER, icon);
        }

        public Builder click(Canvas icon) {
            return icon(State.CLICK, icon);
        }

        public ButtonElement build() {
            return new ButtonElement(size, position, identifier, icons, click);
        }
    }

    public enum State {
        DEFAULT,
        HOVER,
        CLICK
    }

}
