package me.combimagnetron.sunscreen.logic.action.impl;

import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.div.Edit;
import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.logic.action.Argument;
import me.combimagnetron.sunscreen.logic.action.ArgumentType;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.menu.draft.Draft;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class EditElementAction extends Action.AbstractAction {
    public static final Identifier ActionIdentifier = Identifier.of("sunscreen", "edit_element");

    public EditElementAction() {
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
        if (!(user.session().menu() instanceof OpenedMenu.Float floatMenu)) {
            return;
        }
        Identifier divId = ((Identifier) arguments[0].value());
        Identifier elementId = ((Identifier) arguments[1].value());
        String property = ((String) arguments[2].value());
        Object value = arguments[3].value();
        Element element = floatMenu.div(divId).element(elementId);
        Edit<?> edit = Edit.element().identifier(elementId).section().edit(element.getClass(), e -> {
            Method method;
            try {
                method = e.getClass().getDeclaredMethod(property, value.getClass());
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }
            try {
                return (Element) method.invoke(e, value);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        }).back().done();
        Draft draft = Draft.draft().edit(edit);
        floatMenu.apply(draft);
    }

    @Override
    public boolean validate(Argument<?>... arguments) {
        return arguments.length == 4
                && containsType(Identifier.class, 2, arguments)
                && containsType(String.class, 2, arguments);
    }

    @Override
    public Collection<ArgumentType> argumentType() {
        return List.of(
                ArgumentType.of("div", Identifier.class),
                ArgumentType.of("element", Identifier.class),
                ArgumentType.of("property", String.class),
                ArgumentType.of("value", String.class)
        );
    }

}
