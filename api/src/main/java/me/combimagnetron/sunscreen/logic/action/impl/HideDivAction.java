package me.combimagnetron.sunscreen.logic.action.impl;

import me.combimagnetron.passport.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.logic.action.Argument;
import me.combimagnetron.sunscreen.logic.action.ArgumentType;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;
import net.kyori.adventure.text.Component;

import java.util.Collection;
import java.util.List;

public class HideDivAction extends Action.AbstractAction {
    public static final Identifier ActionIdentifier = Identifier.of("sunscreen", "hide_div");

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
        TextDisplay display = floatMenu.renderer().referenceHolder().reference(id).t();
        display.text(Component.empty());
        floatMenu.hide(id);
        OpenedMenu.FloatImpl.MenuHelper.send(user, display);
    }

    @Override
    public boolean validate(Argument<?>... arguments) {
        if (arguments.length != 1) {
            return false;
        }
        return arguments[0].type() == Identifier.class;
    }

    @Override
    public Collection<ArgumentType> argumentType() {
        return List.of(ArgumentType.of("div", Identifier.class));
    }

}
