package me.combimagnetron.sunscreen.logic.action.impl;

import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.logic.action.Argument;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;
import org.checkerframework.checker.units.qual.A;

public class HideDivAction extends Action.AbstractAction {
    private static final Identifier ActionIdentifier = Identifier.of("sunscreen_action", "hide_div");

    public HideDivAction() {
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
        Identifier id = ((Identifier) arguments[0].value());
        OpenedMenu menu = user.session().menu();
        if (!(menu instanceof OpenedMenu.Float floatMenu)) {
            return;
        }
        floatMenu.hide(id);
    }

    @Override
    public boolean validate(Argument<?>... arguments) {
        if (arguments.length != 1) {
            return false;
        }
        return arguments[0].type() == Identifier.class;
    }
}
