package me.combimagnetron.sunscreen.util;

import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.menu.Size;

public interface RuntimeDefinable<T, B extends RuntimeDefinable.Type, V> {
    Values<Class<? extends RuntimeDefinableGeometry>> Types = Values.of(Position.class, Size.class);

    T build(V var);

    B builder();

    void builder(B builder);

    interface Type {

        Class<?> type();

    }

}
