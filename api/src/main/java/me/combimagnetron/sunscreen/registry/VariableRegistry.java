package me.combimagnetron.sunscreen.registry;

import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.internal.registry.Registry;
import me.combimagnetron.sunscreen.logic.variable.Variable;
import me.combimagnetron.sunscreen.user.SunscreenUser;

public interface VariableRegistry extends Registry<Variable<?>> {

    default Variable<?> register(Variable<?> variable, SunscreenUser<?> holder) {
        return register(Identifier.of(variable.identifier().namespace() + "&" + holder.uniqueIdentifier().toString(), variable.identifier().key().string()), variable);
    }

}
