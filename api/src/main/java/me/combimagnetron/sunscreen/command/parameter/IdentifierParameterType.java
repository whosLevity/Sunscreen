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
        String input = mutableStringStream.readString();
        if (!input.contains(":")) {
            return Identifier.of(input);
        }
        return Identifier.split(input);
    }
}
