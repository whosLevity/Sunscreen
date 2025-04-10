package me.combimagnetron.sunscreen.menu.builtin.editor.element;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Interactable;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;

public class ElementDrawerElement extends SimpleBufferedElement implements Interactable {

    public ElementDrawerElement(Vec2d size, Identifier identifier, Position position) {
        super(size, identifier, position);
    }

    public static ElementDrawerElement elementDrawerElement(Vec2d size, Identifier identifier, Position position) {
        return new ElementDrawerElement(size, identifier, position);
    }

    private void build() {

    }

    @Override
    public Canvas canvas() {
        return null;
    }

    @Override
    public Element<Canvas> position(Position pos) {
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
    public boolean hover(Vec2d pos) {
        return false;
    }

    @Override
    public boolean click(Vec2d pos) {
        return false;
    }
}
