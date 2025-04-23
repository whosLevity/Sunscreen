package me.combimagnetron.sunscreen.logic.action;

import me.combimagnetron.passport.util.condition.Condition;
import me.combimagnetron.sunscreen.logic.action.impl.*;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface Action {
    Map<Identifier, Action> ACTION_MAP = new HashMap<>();

    Identifier identifier();

    Condition condition();

    void execute(SunscreenUser<?> user, Argument<?>... arguments);

    boolean validate(Argument<?>... arguments);

    Collection<ArgumentType> argumentType();

    abstract class AbstractAction implements Action {

        static {
            ACTION_MAP.putAll(Map.of(
                    EditElementAction.ActionIdentifier, new EditElementAction(),
                    ShowElementAction.ActionIdentifier, new ShowElementAction(),
                    HideElementAction.ActionIdentifier, new HideElementAction(),
                    ShowDivAction.ActionIdentifier, new ShowDivAction(),
                    HideDivAction.ActionIdentifier, new HideDivAction()
            ));
        }

        private final Identifier identifier;
        private Condition condition;

        public AbstractAction(Identifier identifier) {
            this.identifier = identifier;
        }

        public void condition(Condition condition) {
            this.condition = condition;
        }

        @Override
        public Condition condition() {
            return condition;
        }

        @Override
        public Identifier identifier() {
            return identifier;
        }

        protected boolean containsType(Class<?> type, Argument<?>... arguments) {
            for (Argument<?> argument : arguments) {
                if (argument.type() == type) {
                    return true;
                }
            }
            return false;
        }

        protected static boolean containsType(Class<?> type, int amount, Argument<?>... arguments) {
            int i = 0;
            for (Argument<?> argument : arguments) {
                if (argument.type() == type) {
                    i++;
                }
                if (i == amount) {
                    return true;
                }
            }
            return false;
        }

    }

}
