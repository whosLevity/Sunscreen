package me.combimagnetron.sunscreen.logic.action;

import me.combimagnetron.sunscreen.logic.variable.Variable;

public interface Argument<T> {

    Variable<T> reference();

    T value();

    Class<T> type();

}
