package me.combimagnetron.sunscreen.element.impl;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.menu.editor.menu.EditorMenu;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.menu.input.InputHandler;
import me.combimagnetron.sunscreen.menu.input.TextInput;
import me.combimagnetron.sunscreen.menu.timing.Tick;
import me.combimagnetron.sunscreen.menu.timing.TickFailException;
import me.combimagnetron.sunscreen.style.Text;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2i;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TextInputElement extends SimpleBufferedElement implements /*Tickable,*/ Interactable {
    private final Map<ActionType, ActionWrapper> actions = new HashMap<>();
    private InputHandler inputHandler;
    private String input = "";
    private Style style = Style.SIMPLE;
    private String result;

    protected TextInputElement(Size size, Identifier identifier, Position position, InputHandler inputHandler) {
        super(size, identifier, position);
        this.inputHandler = inputHandler;
    }

    public static TextInputElement of(Size size, Identifier identifier, Position position, InputHandler inputHandler) {
        return new TextInputElement(size, identifier, position, inputHandler);
    }

    public void handle(String text) {
        this.input = text;
        this.canvas();
    }

    public static TextInputElement of(Size size, Identifier identifier, RuntimeDefinableGeometry.GeometryBuilder<?> position, InputHandler inputHandler) {
        TextInputElement element = new TextInputElement(size, identifier, null, inputHandler);
        element.geometry(position);
        return element;
    }

    public String result() {
        return result == null ? "" : result;
    }

    public TextInputElement style(Style style) {
        this.style = style;
        return this;
    }

    @Override
    public @NotNull Canvas canvas() {
        return style == Style.SIMPLE ? simple() : bordered();
    }

    private Canvas simple() {
        Canvas canvas = Canvas.image(size());
        canvas.fill(Vec2i.of(0, 0), size().vec2i(), EditorMenu.Colors.Tertiary);
        if (inputHandler.active()) {
            canvas.text(Text.text(this.input), Vec2i.of(1, 8), EditorMenu.Colors.PrimaryText);
        } else if (result != null) {
            canvas.text(Text.text(result), Vec2i.of(1, 8), EditorMenu.Colors.PrimaryText);
        } else {
            canvas.text(Text.text("Click to edit"), Vec2i.of(1, 8), EditorMenu.Colors.PrimaryText);
        }
        return canvas;
    }

    @Override
    public @NotNull Map<ActionType, ActionWrapper> actions() {
        return actions;
    }

    private Canvas bordered() {
        Canvas canvas = Canvas.image(size());
        canvas.fill(Vec2i.of(0, 0), size().vec2i(), EditorMenu.Colors.Background);
        canvas.fill(Vec2i.of(1, 1), size().vec2i().sub(Vec2i.of(2, 2)), EditorMenu.Colors.Secondary);
        canvas.fill(Vec2i.of(2, 2), size().vec2i().sub(Vec2i.of(4, 4)), EditorMenu.Colors.Background);
        if (inputHandler.active()) {
            canvas.text(Text.text(input), Vec2i.of(3, 10), EditorMenu.Colors.PrimaryText);
        } else if (result != null) {
            canvas.text(Text.text(result), Vec2i.of(3, 10), EditorMenu.Colors.PrimaryText);
        }
        return canvas;
    }

    @Override
    public <S> Element<Canvas> style(me.combimagnetron.sunscreen.style.Style<S> style, Position pos2D, S s) {
        return null;
    }

    @Override
    public <S> Element<Canvas> style(me.combimagnetron.sunscreen.style.Style<S> style, S S) {
        return null;
    }

    //@Override
    public boolean tick(Tick tick) throws TickFailException {
        if (inputHandler.active()) {
            if (inputHandler.textInput().changed()) {
                this.canvas();
                this.result = inputHandler.textInput().input();
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
    public boolean hover(Vec2i pos) {
        return false;
    }

    @Override
    public boolean click(Vec2i pos) {
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

    public TextInputElement inputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        return this;
    }

    public enum Style {
        SIMPLE, BORDERED
    }

}
