package me.combimagnetron.sunscreen.menu.builtin.editor.element.toolbar.widget;

import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.menu.builtin.editor.EditorMenu;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.toolbar.ToolbarWidgetElement;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;

import java.util.Map;

public class DesignToolbarWidgetElement extends ToolbarWidgetElement {

    public DesignToolbarWidgetElement(Size size, Identifier identifier, Position position) {
        super(size, identifier, position);
    }

    @Override
    public Canvas canvas() {
        Canvas canvas = Canvas.image(size());
        canvas = canvas.fill(Vec2i.of(0, 0), size().vec2i(), EditorMenu.Colors.Secondary);
        canvas = canvas.fill(Vec2i.of(0, 0), Vec2i.of(size().vec2i().x(), 10), EditorMenu.Colors.Tertiary);
        return canvas;
    }

    @Override
    public <S> Element<Canvas> style(Style<S> style, Position pos2D, S s) {
        return null;
    }

    @Override
    public <S> Element<Canvas> style(Style<S> style, S s) {
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
