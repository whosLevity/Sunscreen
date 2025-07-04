package me.combimagnetron.sunscreen.logic.variable;

import me.combimagnetron.sunscreen.util.Identifier;

public interface Variable<T> {

    Identifier identifier();

    T value();

    void update();

    Class<T> type();

}
