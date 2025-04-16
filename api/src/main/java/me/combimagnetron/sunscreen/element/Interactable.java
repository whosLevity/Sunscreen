package me.combimagnetron.sunscreen.element;

import me.combimagnetron.sunscreen.util.Vec2d;

import java.util.function.Consumer;

public interface Interactable {

    /**
     * Whether the element is reactive to hover.
     * @return true if the element is reactive to hover.
     */
    boolean reactiveToHover();

    /**
     * Whether the element is reactive to click.
     * @return true if the element is reactive to click.
     */
    boolean reactiveToClick();

    /**
     * Indicates to the element to do hover calculations with the cursor at the given Vec2d position.
     * @param pos position of the cursor.
     * @return true if the element should be rendered again.
     */
    boolean hover(Vec2d pos);

    /**
     * Indicates to the element to do click calculations with the cursor at the given Vec2d position.
     * @param pos position of the cursor.
     * @return true if the element should be rendered again.
     */
    boolean click(Vec2d pos);

}
