package me.combimagnetron.sunscreen.style;

import me.combimagnetron.sunscreen.menu.Editable;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.image.Canvas;

public interface Style<T> extends Editable {

    Canvas edit(Canvas canvas, Position position, T t);

    static ColorStyle color() {
        return new ColorStyle();
    }

}
