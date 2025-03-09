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
    private Canvas canvas;

    protected SelectorElement(Vec2d size, Identifier identifier, Position position, LinkedList<ButtonElement> buttons) {
        super(size, identifier, position);
        int i = 0;
        for (ButtonElement button : buttons) {
            this.buttons.put(button, ButtonElement.State.DEFAULT);
            int pos = i * buttons.getFirst().size().yi() + i * 2;
            this.bounds.put(button, Pair.of(Vec2d.of(0, pos), button.size()));
            i++;
        }
        this.canvas = render();
    }

    public static SelectorElement selectorElement(Vec2d vec2d, Identifier identifier, Position position, LinkedList<ButtonElement> buttons) {
        return new SelectorElement(vec2d, identifier, position, buttons);
    }

    public static Builder selectorElement(Vec2d vec2d, Identifier identifier, Position position) {
        return new Builder(vec2d, identifier, position);
    }

    protected Canvas render() {
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
        /*for (int i = 0; i < buttons.size(); i++) {
            ButtonElement button = buttons.get(i);
            BufferedImage buttonImage = ((Canvas.StaticImpl) button.icons().get(ButtonElement.State.DEFAULT)).image();
            image.getGraphics().drawImage(buttonImage, 0, i * first.size().yi() + i * 2, null);
        }*/
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
    public void hover(Vec2d pos) {
        //System.out.println(pos);
        for (Map.Entry<ButtonElement, ButtonElement.State> button : buttons.entrySet()) {
            buttons.replace(button.getKey(), ButtonElement.State.DEFAULT);
        }
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
    }

    @Override
    public void click(Vec2d pos) {

    }

    public static class Builder {
        private final LinkedList<ButtonElement> buttons = new LinkedList<>();
        private final Vec2d size;
        private final Identifier identifier;
        private final Position position;

        public Builder(Vec2d size, Identifier identifier, Position position) {
            this.size = size;
            this.identifier = identifier;
            this.position = position;
        }

        public Builder button(ButtonElement button) {
            buttons.add(button);
            return this;
        }

        public SelectorElement build() {
            return new SelectorElement(size, identifier, position, buttons);
        }
    }

}
