package me.combimagnetron.sunscreen.menu.element;

import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.Editable;
import me.combimagnetron.sunscreen.util.Vec2d;

public interface Element<T> extends Editable {

    /**
     * The identifier of this element.
     * @return the identifier
     */
    Identifier identifier();

    /**
     * The canvas of this element.
     * @return the canvas
     */
    T canvas();

    /**
     * The position of this element.
     * @return the position
     */
    Position position();

    /**
     * The size of this element.
     * @return a Vec2d containing the size of the element
     */
    default Vec2d size() {
        if (canvas() instanceof Canvas canvas) {
            return canvas.size();
        }
        return Vec2d.of(0, 0);
    }

    /**
     * Sets the position of this element.
     * @param pos the position to set
     * @return this element
     */
    Element<T> position(Position pos);

    /**
     * Applies a style to this element.
     * @param style the style to apply
     * @param pos2D the position to apply the style to
     * @param t the value to apply the style with
     * @return this element, updated with the given style
     */
    <S> Element<T> style(Style<S> style, Position pos2D, S s);

    /**
     * Applies a style to this element.
     * @param style the style to apply
     * @param t the value to apply the style with
     * @return this element, updated with the given style
     */
    <S> Element<T> style(Style<S> style, S S);

    /**
     * Sets the size of this element.
     * @param size the size to set
     * @return this element
     */
    Element<T> size(Vec2d size);

}
