package me.combimagnetron.sunscreen.logic.action;

import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;

public interface Action {

    Identifier identifier();

    void execute(SunscreenUser<?> user, Argument<?>... arguments);

    boolean validate(Argument<?>... arguments);

    abstract class AbstractAction implements Action {

        private final Identifier identifier;

        public AbstractAction(Identifier identifier) {
            this.identifier = identifier;
        }

        @Override
        public Identifier identifier() {
            return identifier;
        }

    }

}
