package me.combimagnetron.sunscreen.logic.variable;

import me.combimagnetron.sunscreen.util.Identifier;

public record SimpleVariable<T>(T value, Identifier identifier) implements Variable<T> {

    @Override
    public void update() {
        // No-op
    }

    @Override
    public Class<T> type() {
        return (Class<T>) value.getClass();
    }

}
