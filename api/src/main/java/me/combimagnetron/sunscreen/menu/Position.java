package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.util.Pair;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;

public non-sealed interface Position extends RuntimeDefinableGeometry, Geometry {

    static PositionBuilder position() {
        return new PositionBuilder();
    }

    static Position pixel(int x, int y) {
        return new Impl(CoordType.pixel(x), CoordType.pixel(y));
    }

    static PositionBuilder config(Section section) {
        String x = ((Node<String>)section.find("x")).value();
        String y = ((Node<String>)section.find("y")).value();
        GeometryBuilder.Section<Position> xSection = RuntimeDefinableGeometry.GeometryBuilder.parse(x);
        GeometryBuilder.Section<Position> ySection = RuntimeDefinableGeometry.GeometryBuilder.parse(y);
        PositionBuilder builder = new PositionBuilder(xSection, ySection);
        xSection.parent(builder);
        ySection.parent(builder);
        return builder;
    }

    final class PositionBuilder extends RuntimeDefinableGeometry.GeometryBuilder<Position> {

        protected PositionBuilder(Section<Position> x, Section<Position> y) {
            super(x, y);
        }

        protected PositionBuilder() {
            super();
        }

        @Override
        public Position finish(Vec2i size, Vec2i divSize) {
            return new Impl(finalise(x(), true, size, divSize), finalise(y(), false, size, divSize));
        }

        @Override
        public int priority() {
            return 1;
        }

        @Override
        public Class<?> type() {
            return Position.class;
        }

        @Override
        public Position finish(Pair<Vec2i, Vec2i> vec2dVec2dPair) {
            return finish(vec2dVec2dPair.k(), vec2dVec2dPair.v());
        }

    }

    final class Impl implements Position {
        private final CoordType<Integer> x;
        private final CoordType<Integer> y;
        private PositionBuilder builder;

        public Impl(CoordType<Integer> x, CoordType<Integer> y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public Geometry build(Pair<Vec2i, Vec2i> var) {
            return builder.finish(var);
        }

        @Override
        public PositionBuilder builder() {
            return builder;
        }

        @Override
        public void builder(GeometryBuilder<? extends Geometry> builder) {
            if (builder instanceof PositionBuilder) {
                this.builder = (PositionBuilder) builder;
            } else {
                throw new IllegalArgumentException("Builder must be of type PositionBuilder");
            }
        }

        public CoordType<Integer> x() {
            return x;
        }

        public CoordType<Integer> y() {
            return y;
        }

    }

}