package me.combimagnetron.sunscreen.element.animated;

import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.menu.Geometry;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.menu.RuntimeDefinableGeometry;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.menu.timing.Tick;
import me.combimagnetron.sunscreen.menu.timing.TickFailException;
import me.combimagnetron.sunscreen.menu.timing.Tickable;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.RuntimeDefinable;
import me.combimagnetron.sunscreen.util.Vec2i;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface AnimatedElement extends Element<Canvas>, Tickable {

    Collection<Keyframe> keyframes();

    void next();

    Keyframe current();

    Keyframe keyframe(int index);

    void loopMode(LoopMode mode);

    LoopMode loopMode();

    AnimatedElement keyframe(Keyframe keyframe);

    AnimatedElement keyframes(Collection<Keyframe> keyframes);

    AnimatedElement keyframes(Keyframe... keyframes);

    enum LoopMode {
        NONE,
        HOLD,
        LOOP,
        REVERSE
    }

    static AnimatedElement of(List<Keyframe> keyframes, Position position, Size size, Identifier identifier) {
        return new Impl(keyframes, position, size, identifier);
    }

    static AnimatedElement of(Canvas canvas, Vec2i grid, long holdTime, Position position, Size size, Identifier identifier) {
        List<Keyframe> keyframes = new ArrayList<>();
        for (int x = 0; x < grid.x(); x++) {
            Vec2i section = canvas.size().div(grid.x(), grid.y());
            for (int y = 0; y < grid.y(); y++) {
                keyframes.add(Keyframe.of(canvas.sub(section, Vec2i.of(x * section.x(), y * section.y())), holdTime));
            }
        }
        return new Impl(keyframes, position, size, identifier);
    }

    static AnimatedElement of(Canvas canvas, Vec2i grid, Position position, Size size, Identifier identifier) {
        return of(canvas, grid, 1, position, size, identifier);
    }

    class Impl implements AnimatedElement {
        private final List<Keyframe> keyframes;
        private final Position position;
        private final Size size;
        private final Identifier identifier;
        private Keyframe currentKeyframe;
        private long time = 0;
        private int index = 0;
        private long totalTime = 0;
        private LoopMode loopMode = LoopMode.LOOP;

        public Impl(List<Keyframe> keyframes, Position position, Size size, Identifier identifier) {
            this.keyframes = keyframes;
            for (Keyframe keyframe : keyframes) {
                totalTime += keyframe.holdTime();
            }
            this.currentKeyframe = keyframes.getFirst();
            this.position = position;
            this.size = size;
            this.identifier = identifier;
        }

        @Override
        public Collection<Keyframe> keyframes() {
            return keyframes;
        }

        @Override
        public void next() {
            index++;
            if (index > keyframes.size() - 1) {
                index = 0;
            }
            currentKeyframe = keyframe(index);
        }

        @Override
        public Keyframe current() {
            return currentKeyframe;
        }

        @Override
        public Keyframe keyframe(int index) {
            return keyframes.get(index);
        }

        @Override
        public void loopMode(LoopMode mode) {
            this.loopMode = mode;
        }

        @Override
        public LoopMode loopMode() {
            return loopMode;
        }

        @Override
        public AnimatedElement keyframe(Keyframe keyframe) {
            this.currentKeyframe = keyframe;
            this.totalTime += keyframe.holdTime();
            return this;
        }

        @Override
        public AnimatedElement keyframes(Collection<Keyframe> keyframes) {
            this.keyframes.clear();
            this.keyframes.addAll(keyframes);
            for (Keyframe keyframe : keyframes) {
                totalTime += keyframe.holdTime();
            }
            return this;
        }

        @Override
        public AnimatedElement keyframes(Keyframe... keyframes) {
            this.keyframes.addAll(Arrays.asList(keyframes));
            for (Keyframe keyframe : keyframes) {
                totalTime += keyframe.holdTime();
            }
            return this;
        }

        @Override
        public @NotNull Identifier identifier() {
            return identifier;
        }

        @Override
        public @NotNull Canvas canvas() {
            return render();
        }

        private Canvas render() {
            Keyframe currentKeyframe = current();
            return currentKeyframe.canvas();
        }

        @Override
        public @NotNull Position position() {
            return position;
        }

        @Override
        public @NotNull Collection<RuntimeDefinable.Type<?, ?>> definables() {
            return List.of();
        }

        @Override
        public void add(RuntimeDefinable.Type<?, ?> definable) {

        }

        @Override
        public @NotNull Element<Canvas> geometry(RuntimeDefinableGeometry.GeometryBuilder<?> geometry) {
            return this;
        }

        @Override
        public @NotNull Element<Canvas> geometry(Geometry geometry) {
            return this;
        }

        @Override
        public @NotNull Element<Canvas> position(Position pos) {
            return this;
        }

        @Override
        public <S> Element<Canvas> style(Style<S> style, Position pos2D, S s) {
            return null;
        }

        @Override
        public <S> Element<Canvas> style(Style<S> style, S s) {
            return null;
        }

        @Override
        public @NotNull Size size() {
            return size;
        }

        @Override
        public @NotNull Element<Canvas> size(Size size) {
            return this;
        }

        @Override
        public boolean tick(Tick tick) throws TickFailException {
            time++;
            if (time + currentKeyframe.holdTime() > totalTime) {
                switch (loopMode) {
                    case NONE:
                        return false;
                    case HOLD, REVERSE, LOOP:
                        time = 0;
                        break;
                }
            }
            if (time >= currentKeyframe.holdTime()) {
                next();
                time = 0;
            }
            return true;
        }
    }

}
