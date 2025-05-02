package me.combimagnetron.sunscreen.element.impl;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.menu.builtin.editor.EditorMenu;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.style.Text;
import me.combimagnetron.sunscreen.util.HoverHelper;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DropdownElement extends SimpleBufferedElement implements Interactable {
    private final Map<ActionType, ActionWrapper> actions = new HashMap<>();
    private final LinkedList<ButtonElement> buttons;
    private final SelectorElement selector;
    private String defaultLabel = "Select";
    private boolean folded = true;

    public DropdownElement(Size size, Identifier identifier, Position position, LinkedList<ButtonElement> buttons) {
        super(size, identifier, position);
        this.buttons = buttons;
        this.selector = SelectorElement.selectorElement(Size.pixel(size.vec2i().sub(0, 11)), identifier, position, buttons).vertical(true).unselectable(false);
    }

    public static DropdownElement dropdownElement(Size size, Identifier identifier, Position position, LinkedList<ButtonElement> buttons) {
        return new DropdownElement(size, identifier, position, buttons);
    }

    public static DropdownElement dropdownElement(Size size, Identifier identifier, RuntimeDefinableGeometry.GeometryBuilder<?> position, LinkedList<ButtonElement> buttons) {
        DropdownElement element = new DropdownElement(size, identifier, null, buttons);
        element.geometry(position);
        return element;
    }

    public static DropdownElement.Builder dropdownElement(Size size, Identifier identifier, Position position) {
        return new DropdownElement.Builder(size, identifier, position);
    }

    public DropdownElement defaultLabel(String label) {
        this.defaultLabel = label;
        return this;
    }

    @Override
    public Map<ActionType, ActionWrapper> actions() {
        return actions;
    }

    @Override
    public Canvas canvas() {
        Vec2i size = !folded ? this.size().vec2i() : Vec2i.of(size().x().pixel(), 11);
        Canvas canvas = Canvas.image(size);
        canvas.fill(Vec2i.of(0, 0), size, EditorMenu.Colors.Background);
        canvas.fill(Vec2i.of(1, 1), size.sub(Vec2i.of(2, 2)), EditorMenu.Colors.Secondary);
        canvas.fill(Vec2i.of(1, 1), Vec2i.of(size().x().pixel() - 2, 9), EditorMenu.Colors.Background);
        if (selector.selected() == null) {
            canvas.text(Text.text(defaultLabel), Vec2i.of(1, 8), EditorMenu.Colors.PrimaryText);
        } else {
            canvas.place(selector.selected().icons().get(ButtonElement.State.CLICK), Vec2i.of(0, 0));
        }
        if (folded) {
            return canvas;
        }
        canvas.place(selector.canvas(), Vec2i.of(0, 11));
        return canvas;
    }

    @Override
    public <S> Element<Canvas> style(Style<S> style, Position pos2D, S s) {
        return null;
    }

    @Override
    public <S> Element<Canvas> style(Style<S> style, S S) {
        return null;
    }

    @Override
    public boolean reactiveToHover() {
        return false;
    }

    @Override
    public boolean reactiveToClick() {
        return true;
    }

    @Override
    public boolean hover(Vec2i pos) {
        if (HoverHelper.isHovered(pos, Vec2i.of(0, 0), Vec2i.of(size().x().pixel(), 11))) {
            return true;
        }
        return selector.hover(pos.sub(0, 11));
    }

    @Override
    public boolean click(Vec2i pos) {
        if (pos == null) {
            if (!folded) {
                folded = true;
                return true;
            }
            return false;
        }
        if (HoverHelper.isHovered(pos, Vec2i.of(0, 0), Vec2i.of(size().x().pixel(), 11)))  {
            folded = !folded;
            return true;
        }
        return selector.click(pos.sub(0, 11));
    }

    public static class Builder {
        private final Size size;
        private final Identifier identifier;
        private final Position position;
        private final LinkedList<ButtonElement> buttons = new LinkedList<>();

        public Builder(Size size, Identifier identifier, Position position) {
            this.size = size;
            this.identifier = identifier;
            this.position = position;
        }

        public Builder button(ButtonElement button) {
            this.buttons.add(button);
            return this;
        }

        public DropdownElement build() {
            return new DropdownElement(size, identifier, position, buttons);
        }
    }

}
