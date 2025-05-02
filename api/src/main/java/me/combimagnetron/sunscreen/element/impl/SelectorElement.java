package me.combimagnetron.sunscreen.element.impl;

import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.*;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.menu.Position;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class SelectorElement extends SimpleBufferedElement implements Interactable {
    private final Map<ActionType, ActionWrapper> actions = new LinkedHashMap<>();
    private final LinkedHashMap<ButtonElement, ButtonElement.State> buttons = new LinkedHashMap<>();
    private final LinkedHashMap<ButtonElement, Pair<Vec2i, Vec2i>> bounds = new LinkedHashMap<>();
    private boolean vertical;
    private boolean unselectable = true;
    private int spacing;
    private Canvas canvas;

    protected SelectorElement(Size size, Identifier identifier, Position position, LinkedList<ButtonElement> buttons, boolean vertical, int spacing) {
        super(size, identifier, position);
        int i = 0;
        int x = 0, y = 0;
        for (ButtonElement button : buttons) {
            this.buttons.put(button, ButtonElement.State.DEFAULT);
            if (vertical) {
                int pos = i * buttons.getFirst().size().y().pixel() + i * 2;
                this.bounds.put(button, Pair.of(Vec2i.of(0, pos), button.size().vec2i()));
                i++;
            } else {
                if (button.size().x().pixel() + x > size().x().pixel()) {
                    x = button.size().x().pixel() + spacing;
                    y += button.size().y().pixel() + spacing;
                } else {
                    x += button.size().x().pixel() + spacing;
                }
                Vec2i pos = Vec2i.of(x, y).sub(button.size().x().pixel(), 0);
                this.bounds.put(button, Pair.of(pos, button.size().vec2i()));
            }
        }
        this.vertical = vertical;
        this.canvas = render();
        this.spacing = spacing;
    }

    public static SelectorElement selectorElement(Size size, Identifier identifier, Position position, LinkedList<ButtonElement> buttons) {
        return new SelectorElement(size, identifier, position, buttons, true, 2);
    }

    public static Builder selectorElement(Size size, Identifier identifier, Position position) {
        return new Builder(size, identifier, position);
    }

    public SelectorElement unselectable(boolean unselectable) {
        this.unselectable = unselectable;
        return this;
    }

    public void select(Identifier identifier) {
        for (Map.Entry<ButtonElement, ButtonElement.State> entry : buttons.entrySet()) {
            if (entry.getKey().identifier().equals(identifier)) {
                buttons.replace(entry.getKey(), ButtonElement.State.CLICK);
            } else {
                buttons.replace(entry.getKey(), ButtonElement.State.DEFAULT);
            }
        }
        this.canvas = render();
    }

    public SelectorElement vertical(boolean vertical) {
        this.vertical = vertical;
        return this;
    }

    public SelectorElement horizontal() {
        this.vertical = false;
        return this;
    }

    public ButtonElement selected() {
        for (Map.Entry<ButtonElement, ButtonElement.State> entry : buttons.entrySet()) {
            if (entry.getValue().equals(ButtonElement.State.CLICK)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public Map<ActionType, ActionWrapper> actions() {
        return actions;
    }

    protected Canvas render() {
        return vertical ? renderVert() : renderHor();
    }

    protected Canvas renderHor() {
        ButtonElement first = buttons.keySet().stream().findFirst().get();
        int y = first.size().y().pixel();
        int x = 0;
        Canvas canvas = Canvas.image(Vec2i.of(256, 256));
        for (Map.Entry<ButtonElement, ButtonElement.State> entry : buttons.entrySet()) {
            if (entry.getKey().size().x().pixel() + x > size().x().pixel()) {
                x = entry.getKey().size().x().pixel() + spacing;
                y += entry.getKey().size().y().pixel() + spacing;
            } else {
                x += entry.getKey().size().x().pixel() + spacing;
            }
            Canvas buttonImage = entry.getKey().icons().get(entry.getValue());
            canvas.place(buttonImage, Vec2i.of(x, y).sub(entry.getKey().size().vec2i()));
        }
        canvas = canvas.trim();
        size(Size.pixel(canvas.size()));
        return canvas;
    }

    protected Canvas renderVert() {
        ButtonElement first = buttons.keySet().stream().findFirst().get();
        int x = first.size().x().pixel();
        int y = buttons.size() * first.size().y().pixel() + (buttons.size() + 1) * spacing + 2;
        Canvas image = Canvas.image(Vec2i.of(x, y));
        int i = 0;
        for (Map.Entry<ButtonElement, ButtonElement.State> entry : buttons.entrySet()) {
            image.place(entry.getKey().icons().get(entry.getValue()), Vec2i.of(0, i * first.size().y().pixel() + i * spacing));
            i++;
        }
        this.size(Size.pixel(size().x().pixel(), y +4));
        return image;
    }

    @Override
    public Canvas canvas() {
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

    @Override
    public boolean reactiveToHover() {
        return true;
    }

    @Override
    public boolean reactiveToClick() {
        return true;
    }

    @Override
    public boolean hover(Vec2i pos) {
        return vertical ? hoverVertical(pos) : hoverHorizontal(pos);
    }

    private boolean hoverHorizontal(Vec2i pos) {
        if (pos == null) {
            return outOfBounds();
        }
        reset();
        for (Map.Entry<ButtonElement, Pair<Vec2i, Vec2i>> entry : bounds.entrySet()) {
            if (buttons.get(entry.getKey()).equals(ButtonElement.State.CLICK)) continue;
            if (HoverHelper.isHovered(pos, entry.getValue().k(), entry.getValue().v())) {
                buttons.replace(entry.getKey(), ButtonElement.State.HOVER);
            }
        }
        this.canvas = render();
        return true;
    }

    private void reset() {
        for (Map.Entry<ButtonElement, ButtonElement.State> button : buttons.entrySet()) {
            if (button.getValue().equals(ButtonElement.State.HOVER)) buttons.replace(button.getKey(), ButtonElement.State.DEFAULT);
        }
    }

    private boolean outOfBounds() {
        boolean update = false;
        for (Map.Entry<ButtonElement, ButtonElement.State> button : buttons.entrySet()) {
            if (button.getValue().equals(ButtonElement.State.HOVER)) {
                buttons.replace(button.getKey(), ButtonElement.State.DEFAULT);
                update = true;
            }
        }
        if (update) {
            this.canvas = render();
        }
        return update;
    }

    private boolean hoverVertical(Vec2i pos) {
        if (pos == null) {
            return outOfBounds();
        }
        pos = pos.sub(0, 8);
        reset();
        for (Map.Entry<ButtonElement, Pair<Vec2i, Vec2i>> entry : bounds.entrySet()) {
            if (buttons.get(entry.getKey()).equals(ButtonElement.State.CLICK)) continue;
            if (HoverHelper.isHovered(pos, entry.getValue().k(), entry.getValue().v())) {
                buttons.replace(entry.getKey(), ButtonElement.State.HOVER);
            }
        }
        this.canvas = render();
        return true;
    }

    @Override
    public boolean click(Vec2i pos) {
        if (pos == null) {
            return false;
        }
        pos = pos.sub(0, 8);
        for (Map.Entry<ButtonElement, ButtonElement.State> button : buttons.entrySet()) {
            if ((button.getValue() == null || button.getKey() == null) && !unselectable) {
                continue;
            }
            buttons.replace(button.getKey(), ButtonElement.State.DEFAULT);
        }
        for (Map.Entry<ButtonElement, Pair<Vec2i, Vec2i>> entry : bounds.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            if (HoverHelper.isHovered(pos, entry.getValue().k(), entry.getValue().v())) {
                buttons.replace(entry.getKey(), ButtonElement.State.CLICK);
                entry.getKey().click(pos);
            }
        }
        this.canvas = render();
        return true;
    }

    public static class Builder {
        private final LinkedList<ButtonElement> buttons = new LinkedList<>();
        private Size size;
        private final Identifier identifier;
        private final Position position;
        private boolean vertical = true;
        private int spacing = 2;

        public Builder(Size size, Identifier identifier, Position position) {
            this.size = size;
            this.identifier = identifier;
            this.position = position;
        }

        public Builder size(Size size) {
            this.size = size;
            return this;
        }

        public Builder button(ButtonElement button) {
            buttons.add(button);
            return this;
        }

        public Builder horizontal() {
            this.vertical = false;
            return this;
        }

        public Builder vertical() {
            this.vertical = true;
            return this;
        }

        public Builder spacing(int spacing) {
            this.spacing = spacing;
            return this;
        }

        public SelectorElement build() {
            return new SelectorElement(size, identifier, position, buttons, vertical, spacing);
        }
    }

}
