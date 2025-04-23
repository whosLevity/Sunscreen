package me.combimagnetron.sunscreen.util;

import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.menu.Size;

import java.util.Collection;
import java.util.function.Function;

public interface RuntimeDefinable<T, B extends RuntimeDefinable.Type, V> {
    Values<Class<? extends RuntimeDefinableGeometry>> Types = Values.of(Position.class, Size.class);

    T build(V var);

    B builder();

    void builder(B builder);

    interface Type<T, V> {

        Class<?> type();

        T finish(V v);

    }

    interface Holder {

        Collection<RuntimeDefinable.Type<?, ?>> definables();

        void add(RuntimeDefinable.Type<?, ?> definable);

    }

    class Impl<T, B extends RuntimeDefinable.Type, V> implements RuntimeDefinable<T, B, V> {
        private final Class<? extends RuntimeDefinableGeometry> type;
        private B builder;
        private final T instance;

        public Impl(Class<? extends RuntimeDefinableGeometry> type, B builder, T instance) {
            this.type = type;
            this.builder = builder;
            this.instance = instance;
        }

        @Override
        public T build(V var) {
            return instance;
        }

        @Override
        public B builder() {
            return builder;
        }

        @Override
        public void builder(B builder) {
            this.builder = builder;
        }

        public static class Type<T, V> implements RuntimeDefinable.Type<T, V> {
            private final Class<?> type;
            private final Function<V, T> function;

            public Type(Class<?> type, Function<V, T> function) {
                this.type = type;
                this.function = function;
            }

            @Override
            public Class<?> type() {
                return type;
            }

            @Override
            public T finish(V o) {
                return function.apply(o);
            }
        }

    }

}
