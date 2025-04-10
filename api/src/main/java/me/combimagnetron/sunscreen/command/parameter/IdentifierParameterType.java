package me.combimagnetron.sunscreen.command.parameter;

import me.combimagnetron.sunscreen.util.Identifier;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;

public class IdentifierParameterType implements ParameterType<CommandActor, Identifier> {
    @Override
    public Identifier parse(@NotNull MutableStringStream mutableStringStream, @NotNull ExecutionContext<@NotNull CommandActor> executionContext) {
        if (!mutableStringStream.readString().contains(":")) {
            return Identifier.of(mutableStringStream.readString());
        }
        return Identifier.split(mutableStringStream.readString());
    }
}
