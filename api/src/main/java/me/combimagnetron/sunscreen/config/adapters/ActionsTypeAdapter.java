package me.combimagnetron.sunscreen.config.adapters;

import me.combimagnetron.passport.config.element.ConfigElement;
import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.config.ConfigTypeAdapter;
import me.combimagnetron.sunscreen.element.Interactable;
import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.logic.action.Argument;
import me.combimagnetron.sunscreen.logic.action.ArgumentType;
import me.combimagnetron.sunscreen.logic.action.adapter.TypeAdapter;
import me.combimagnetron.sunscreen.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ActionsTypeAdapter implements ConfigTypeAdapter<Map<Interactable.ActionType, ActionWrapper>> {
    @Override
    public @Nullable Map<Interactable.ActionType, ActionWrapper> direct(@NotNull ConfigElement element) {
        Section section = Section.required("temp");
        section.section((Section) element);
        return action(section);
    }

    @Override
    public @Nullable Map<Interactable.ActionType, ActionWrapper> find(@NotNull Section section) {
        return action(section);
    }

    protected static Map<Interactable.ActionType, ActionWrapper> action(@NotNull Section elementConfigElement) {
        Section actionsSection = elementConfigElement.find("actions");
        Map<Interactable.ActionType, ActionWrapper> actionWrappers = new HashMap<>();
        for (ConfigElement element : actionsSection.elements()) {
            Section elementSection = (Section) element;
            Node<String> listen = ConfigTypeAdapter.find(elementSection, "listen", String.class);
            Section executeSection = ConfigTypeAdapter.find(element, "execute");
            Node<String> actionType = ConfigTypeAdapter.find(executeSection, "type", String.class);
            if (actionType == null || listen == null) {
                return actionWrappers;
            }
            if (actionType.value() == null || listen.value() == null) {
                return actionWrappers;
            }
            Action action = Action.ACTION_MAP.get(Identifier.split(actionType.value()));
            if (action == null) {
                return actionWrappers;
            }
            Collection<Argument<?>> arguments = get(action, executeSection);
            ActionWrapper actionWrapper = ActionWrapper.of(element.name(), null, action, arguments);
            actionWrappers.put(Interactable.ActionType.valueOf(listen.value().toUpperCase()), actionWrapper);
        }
        return actionWrappers;
    }

    private static Collection<Argument<?>> get(Action action, Section executeSection) {
        List<Argument<?>> arguments = new ArrayList<>();
        for (ArgumentType argumentType : action.argumentType()) {
            String key = argumentType.name();
            if (executeSection.elements().stream().noneMatch(configElement -> configElement.name().equals(key))) {
                return arguments;
            }
            TypeAdapter<?> typeAdapter = TypeAdapter.VALUES.stream().filter(typeAdapter1 -> typeAdapter1.type().equals(argumentType.type())).findFirst().orElse(null);
            if (typeAdapter == null) {
                return arguments;
            }
            Object value = ((Node<?>)executeSection.find(key)).value();
            Argument<?> argument;
            if (value instanceof String stringVal) {
                argument = Argument.of(null, key, typeAdapter.adapt(stringVal));
            } else if (value.getClass() == argumentType.type()) {
                argument = Argument.of(null, key, value);
            } else {
                return arguments;
            }
            arguments.add(argument);
        }
        return arguments;
    }

}
