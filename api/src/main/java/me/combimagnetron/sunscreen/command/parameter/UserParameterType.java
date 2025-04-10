package me.combimagnetron.sunscreen.command.parameter;

import me.combimagnetron.sunscreen.SunscreenLibrary;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;

public class UserParameterType implements ParameterType<CommandActor, SunscreenUser> {
    @Override
    public SunscreenUser<Audience> parse(@NotNull MutableStringStream mutableStringStream, @NotNull ExecutionContext<@NotNull CommandActor> executionContext) {
        return SunscreenLibrary.library().users().user(mutableStringStream.readString()).orElse(null);
    }
}
