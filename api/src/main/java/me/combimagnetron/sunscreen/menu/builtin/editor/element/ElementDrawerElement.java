package me.combimagnetron.sunscreen.menu.builtin.editor.element;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;

import java.util.Map;

public class ElementDrawerElement extends SimpleBufferedElement implements Interactable {

    public ElementDrawerElement(Size size, Identifier identifier, Position position) {
        super(size, identifier, position);
    }

    public static ElementDrawerElement elementDrawerElement(Size size, Identifier identifier, Position position) {
        return new ElementDrawerElement(size, identifier, position);
    }

    private void build() {

    }

    @Override
    public Canvas canvas() {
        return null;
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
        return false;
    }

    @Override
    public boolean reactiveToClick() {
        return false;
    }

    @Override
    public boolean hover(Vec2i pos) {
        return false;
    }

    @Override
    public boolean click(Vec2i pos) {
        return false;
    }

    @Override
    public Map<ActionType, ActionWrapper> actions() {
        return Map.of();
    }
}
