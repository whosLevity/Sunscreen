package me.combimagnetron.sunscreen.element;

import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

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
    boolean hover(Vec2i pos);

    /**
     * Indicates to the element to do click calculations with the cursor at the given Vec2d position.
     * @param pos position of the cursor.
     * @return true if the element should be rendered again.
     */
    boolean click(Vec2i pos);

    @NotNull Map<ActionType, ActionWrapper> actions();

    default void action(ActionType type, ActionWrapper action) {
        actions().put(type, action);
    }

    enum ActionType {
        HOVER,
        CLICK
    }

}
