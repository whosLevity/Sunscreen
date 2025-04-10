package me.combimagnetron.sunscreen.menu.element.impl;

import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.menu.element.Interactable;
import me.combimagnetron.sunscreen.menu.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.HoverHelper;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.util.Pair;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class SelectorElement extends SimpleBufferedElement implements Interactable {
    private final LinkedHashMap<ButtonElement, ButtonElement.State> buttons = new LinkedHashMap<>();
    private final LinkedHashMap<ButtonElement, Pair<Vec2d, Vec2d>> bounds = new LinkedHashMap<>();
    private boolean vertical;
    private Canvas canvas;

    protected SelectorElement(Vec2d size, Identifier identifier, Position position, LinkedList<ButtonElement> buttons, boolean vertical) {
        super(size, identifier, position);
        int i = 0;
        int x = 0, y = 0;
        for (ButtonElement button : buttons) {
            this.buttons.put(button, ButtonElement.State.DEFAULT);
            if (vertical) {
                int pos = i * buttons.getFirst().size().yi() + i * 2;
                this.bounds.put(button, Pair.of(Vec2d.of(0, pos), button.size()));
                i++;
            } else {
                if (button.size().xi() + x > size().x()) {
                    x = button.size().xi() + 2;
                    y += button.size().yi() + 2;
                } else {
                    x += button.size().xi() + 2;
                }
                Vec2d pos = Vec2d.of(x, y).sub(button.size().xi(), 0);
                this.bounds.put(button, Pair.of(pos, button.size()));
            }
        }
        this.vertical = vertical;
        this.canvas = render();
    }

    public static SelectorElement selectorElement(Vec2d size, Identifier identifier, Position position, LinkedList<ButtonElement> buttons) {
        return new SelectorElement(size, identifier, position, buttons, true);
    }

    public static Builder selectorElement(Vec2d size, Identifier identifier, Position position) {
        return new Builder(size, identifier, position);
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

    protected Canvas render() {
        return vertical ? renderVert() : renderHor();
    }

    protected Canvas renderHor() {
        ButtonElement first = buttons.keySet().stream().findFirst().get();
        int y = first.size().yi();
        int x = 0;
        Canvas canvas = Canvas.image(Vec2d.of(256, 256));
        for (Map.Entry<ButtonElement, ButtonElement.State> entry : buttons.entrySet()) {
            if (entry.getKey().size().xi() + x > size().x()) {
                x = entry.getKey().size().xi() + 2;
                y += entry.getKey().size().yi() + 2;
            } else {
                x += entry.getKey().size().xi() + 2;
            }
            Canvas buttonImage = entry.getKey().icons().get(entry.getValue());
            canvas.place(buttonImage, Vec2d.of(x, y).sub(entry.getKey().size()));
        }
        canvas = canvas.trim();
        size(canvas.size());
        return canvas;
    }

    protected Canvas renderVert() {
        ButtonElement first = buttons.keySet().stream().findFirst().get();
        int x = first.size().xi();
        int y = buttons.size() * first.size().yi() + (buttons.size() + 1) * 2 + 2;
        BufferedImage image = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        int i = 0;
        for (Map.Entry<ButtonElement, ButtonElement.State> entry : buttons.entrySet()) {
            BufferedImage buttonImage = ((Canvas.StaticImpl) entry.getKey().icons().get(entry.getValue())).image();
            image.getGraphics().drawImage(buttonImage, 0, i * first.size().yi() + i * 2, null);
            i++;
        }
        this.size(Vec2d.of(size().x(), y + 4));
        return Canvas.image(image);
    }

    @Override
    public Canvas canvas() {
        return canvas;
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
    public boolean reactiveToHover() {
        return true;
    }

    @Override
    public boolean reactiveToClick() {
        return true;
    }

    @Override
    public boolean hover(Vec2d pos) {
        return vertical ? hoverVertical(pos) : hoverHorizontal(pos);
    }

    private boolean hoverHorizontal(Vec2d pos) {
        if (pos == null) {
            return outOfBounds();
        }
        reset();
        for (Map.Entry<ButtonElement, Pair<Vec2d, Vec2d>> entry : bounds.entrySet()) {
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
        for (Map.Entry<ButtonElement, ButtonElement.State> button : buttons.entrySet()) {
            if (button.getValue().equals(ButtonElement.State.HOVER)) buttons.replace(button.getKey(), ButtonElement.State.DEFAULT);
        }
        this.canvas = render();
        return true;
    }

    private boolean hoverVertical(Vec2d pos) {
        if (pos == null) {
            return outOfBounds();
        }
        reset();
        Pair<ButtonElement, Vec2d> entry = bounds.entrySet().stream().filter(e -> HoverHelper.isHovered(pos, e.getValue().k(), e.getValue().v())).map(e -> Pair.of(e.getKey(), pos)).findFirst().orElse(null);
        if (entry != null) {
            if (!buttons.get(entry.k()).equals(ButtonElement.State.HOVER)) {
                buttons.replace(entry.k(), ButtonElement.State.HOVER);
            }
        } else {
            buttons.forEach((button, state) -> {
                if (!state.equals(ButtonElement.State.DEFAULT)) {
                    buttons.replace(button, ButtonElement.State.DEFAULT);
                }
            });
        }
        this.canvas = render();
        return true;
    }

    @Override
    public boolean click(Vec2d pos) {
        if (pos == null) {
            return false;
        }
        for (Map.Entry<ButtonElement, ButtonElement.State> button : buttons.entrySet()) {
            if (button.getValue() == null || button.getKey() == null) {
                continue;
            }
            buttons.replace(button.getKey(), ButtonElement.State.DEFAULT);
        }
        for (Map.Entry<ButtonElement, Pair<Vec2d, Vec2d>> entry : bounds.entrySet()) {
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
        private final Vec2d size;
        private final Identifier identifier;
        private final Position position;
        private boolean vertical = true;

        public Builder(Vec2d size, Identifier identifier, Position position) {
            this.size = size;
            this.identifier = identifier;
            this.position = position;
        }

        public Builder button(ButtonElement button) {
            buttons.add(button);
            return this;
        }

        public Builder horizontal() {
            this.vertical = false;
            return this;
        }

        public SelectorElement build() {
            return new SelectorElement(size, identifier, position, buttons, vertical);
        }
    }

}
