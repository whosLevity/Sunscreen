package me.combimagnetron.sunscreen.logic.action;

public record ArgumentType(String name, Class<?> type) {

    public static ArgumentType of(String name, Class<?> type) {
        return new ArgumentType(name, type);
    }

}
