package me.combimagnetron.sunscreen.menu.simulate;

import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.logic.action.Argument;
import me.combimagnetron.sunscreen.logic.action.ArgumentType;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;

import java.util.Collection;
import java.util.List;

public class ForwardClickAction extends Action.AbstractAction {
    public static final Identifier ActionIdentifier = Identifier.of("sunscreen", "forward_click");

    public ForwardClickAction() {
        super(ActionIdentifier);
    }

    static {
        ACTION_MAP.put(ActionIdentifier, new ForwardClickAction());
    }

    @Override
    public void execute(SunscreenUser<?> user, Argument<?>... arguments) {
        if (!validate(arguments)) {
            throw new IllegalArgumentException("Invalid arguments provided to action " + ActionIdentifier);
        }
        if (user == null || user.session().menu() == null) {
            throw new IllegalArgumentException("User and its menu cannot be null");
        }
        int slot = ((int) arguments[0].value());
        OpenedMenu menu = user.session().menu();
        if (!(menu instanceof OpenedMenu.Base base)) {
            return;
        }
        if (!base.simulator().active()) {
            return;
        }
        base.simulator().chestMenuEmulator().click(slot);
    }

    @Override
    public boolean validate(Argument<?>... arguments) {
        return arguments.length == 1 && arguments[0].type() == int.class;
    }

    @Override
    public Collection<ArgumentType> argumentType() {
        return List.of(ArgumentType.of("slot", int.class));
    }

}
