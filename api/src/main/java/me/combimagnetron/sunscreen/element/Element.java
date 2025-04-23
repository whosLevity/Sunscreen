package me.combimagnetron.sunscreen.element;

import me.combimagnetron.sunscreen.menu.*;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.util.RuntimeDefinable;
import me.combimagnetron.sunscreen.util.Vec2d;

import java.util.Collection;

public interface Element<T> extends Editable, RuntimeDefinable.Holder {

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

    Collection<RuntimeDefinable.Type<?, ?>> definables();

    Element<T> geometry(RuntimeDefinableGeometry.GeometryBuilder<?> geometry);

    Element<T> geometry(Geometry geometry);

    /**
     * The size of this element.
     * @return a Vec2d containing the size of the element
     */

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
     * @param s the value to apply the style with
     * @return this element, updated with the given style
     */
    <S> Element<T> style(Style<S> style, Position pos2D, S s);

    /**
     * Applies a style to this element.
     * @param style the style to apply
     * @param s the value to apply the style with
     * @return this element, updated with the given style
     */
    <S> Element<T> style(Style<S> style, S s);

    Size size();

    /**
     * Sets the size of this element.
     * @param size the size to set
     * @return this element
     */
    Element<T> size(Size size);

}
