package me.combimagnetron.sunscreen.logic.action.impl;

import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.logic.action.Argument;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;

public class HideElementAction extends Action.AbstractAction {
    private final static Identifier ActionIdentifier = Identifier.of("sunscreen_action", "hide_element");

    public HideElementAction() {
        super(ActionIdentifier);
    }

    @Override
    public void execute(SunscreenUser<?> user, Argument<?>... arguments) {
        if (!validate(arguments)) {
            throw new IllegalArgumentException("Invalid arguments provided to action " + ActionIdentifier);
        }
        if (user == null || user.session().menu() == null) {
            throw new IllegalArgumentException("User and its menu cannot be null");
        }
        Identifier divId = ((Identifier) arguments[0].value());
        Identifier elementId = ((Identifier) arguments[1].value());
        OpenedMenu menu = user.session().menu();
        if (!(menu instanceof OpenedMenu.Float floatMenu)) {
            return;
        }
        Div<Canvas> div = floatMenu.div(divId);
        div.hide(elementId);
    }

    @Override
    public boolean validate(Argument<?>... arguments) {
        if (arguments.length != 2) {
            return false;
        }
        return arguments[0].type() == Identifier.class && arguments[1].type() == Identifier.class;
    }
}
