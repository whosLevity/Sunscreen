package me.combimagnetron.sunscreen.command.condition;

import me.combimagnetron.passport.util.condition.Condition;
import me.combimagnetron.passport.util.condition.Supplier;
import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.process.CommandCondition;
import revxrsal.commands.stream.StringStream;

public enum CustomCondition implements CommandCondition<CommandActor> {
    INSTANCE;

    @Override
    public void test(@NotNull ExecutionContext<CommandActor> executionContext) {
        boolean annotationPresent = executionContext.command().annotations().contains(me.combimagnetron.sunscreen.command.condition.Condition.class);
        if (!annotationPresent) {
            return;
        }
        String input = executionContext.command().annotations().get(me.combimagnetron.sunscreen.command.condition.Condition.class).condition();
        Condition condition = Condition.of(input);
        SunscreenUser<Audience> user = SunscreenLibrary.library().users().user(executionContext.actor().uniqueId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!condition.eval(Supplier.of(user.platformSpecificPlayer(), user)).value()) {
            throw new IllegalArgumentException("Condition failed: " + condition);
        }
    }
}
