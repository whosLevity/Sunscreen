package me.combimagnetron.sunscreen.menu.element.impl;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.builtin.editor.EditorMenu;
import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Interactable;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.menu.input.InputHandler;
import me.combimagnetron.sunscreen.menu.input.TextInput;
import me.combimagnetron.sunscreen.menu.timing.Tick;
import me.combimagnetron.sunscreen.menu.timing.TickFailException;
import me.combimagnetron.sunscreen.menu.timing.Tickable;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.style.Text;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;

public class TextInputElement extends SimpleBufferedElement implements Tickable, Interactable {
    private final InputHandler inputHandler;

    protected TextInputElement(Vec2d size, Identifier identifier, Position position, InputHandler inputHandler) {
        super(size, identifier, position);
        this.inputHandler = inputHandler;
    }

    public static TextInputElement of(Vec2d size, Identifier identifier, Position position, InputHandler inputHandler) {
        return new TextInputElement(size, identifier, position, inputHandler);
    }

    @Override
    public Canvas canvas() {
        Canvas canvas = Canvas.image(size());
        canvas.fill(Vec2d.of(0, 0), size(), EditorMenu.Colors.Background);
        canvas.fill(Vec2d.of(1, 1), size().sub(Vec2d.of(2, 2)), EditorMenu.Colors.Secondary);
        canvas.fill(Vec2d.of(2, 2), size().sub(Vec2d.of(4, 4)), EditorMenu.Colors.Background);
        if (inputHandler.active()) {
            canvas.text(Text.text(inputHandler.textInput().input()), Vec2d.of(3, 10), EditorMenu.Colors.PrimaryText);
        }
        return canvas;
    }

    @Override
    public Element<Canvas> position(Position pos) {
        this.position = pos;
        return this;
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
    public boolean tick(Tick tick) throws TickFailException {
        if (inputHandler.active()) {
            if (inputHandler.textInput().changed()) {
                this.canvas();
                return true;
            }
        }
        return false;
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
    public boolean hover(Vec2d pos) {
        return false;
    }

    @Override
    public boolean click(Vec2d pos) {
        if (pos == null) {
            return false;
        }
        if (inputHandler.active()) {
            inputHandler.quit();
        }
        TextInput input = inputHandler.open();
        input.position(Position.pixel(10, 20));
        return true;
    }
}
