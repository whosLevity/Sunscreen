package me.combimagnetron.sunscreen.menu.element;

import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.Editable;
import me.combimagnetron.sunscreen.util.Vec2d;

public interface Element extends Editable {

    Identifier identifier();

    Canvas canvas();

    Position position();

    default Vec2d size() {
        return canvas().size();
    }

    Element position(Position pos);

    <T> Element style(Style<T> style, Position pos2D, T t);

    <T> Element style(Style<T> style, T t);

    Element size(Vec2d size);

}
