package me.combimagnetron.sunscreen.menu.editor.menu.element;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.element.SimpleBufferedElement;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;
import org.jetbrains.annotations.NotNull;

public class TreeElement extends SimpleBufferedElement {
    public TreeElement(Size size, Identifier identifier, Position position) {
        super(size, identifier, position);
    }

    @Override
    public @NotNull Canvas canvas() {
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
}
