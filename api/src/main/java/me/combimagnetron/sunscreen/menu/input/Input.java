package me.combimagnetron.sunscreen.menu.input;

import org.jetbrains.annotations.Nullable;

public interface Input<T extends Input.Type> {

    @Nullable T value();

    Type type();

    record Impl<T extends Type>(T value, Type type) implements Input<T> {

    }

    static <T extends Type> Input<T> of(T value, Type type) {
        return new Impl<>(value, type);
    }

    interface Type {
        class MouseClick implements Type {
            public final boolean right;
            public MouseClick(boolean right) {
                this.right = right;
            }
        }

        class KeyPress implements Type {
            public final int key;
            public KeyPress(int key) {
                this.key = key;
            }
        }

        class CursorMove implements Type {
            public final int x;
            public final int y;
            public CursorMove(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

        class Scroll implements Type {
            public final double scroll;
            public final Direction direction;
            public Scroll(double scroll, Direction direction) {
                this.scroll = scroll;
                this.direction = direction;
            }
            public enum Direction {
                UP, DOWN
            }
        }

        class Sneak implements Type {
            public final boolean sneak;
            public Sneak(boolean sneak) {
                this.sneak = sneak;
            }
        }

        class Jump implements Type {
            public final boolean jump;
            public Jump(boolean jump) {
                this.jump = jump;
            }
        }
    }

}
