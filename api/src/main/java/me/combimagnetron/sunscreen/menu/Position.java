package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.util.Vec2d;

public non-sealed interface Position extends RuntimeDefinableGeometry, Geometry {

    static PositionBuilder position() {
        return new PositionBuilder();
    }

    static Position pixel(double x, double y) {
        return new Impl(RuntimeDefinableGeometry.CoordType.pixel(x), RuntimeDefinableGeometry.CoordType.pixel(y));
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
        public Position finish(Vec2d size) {
            return new Impl(finalise(x(), true, size), finalise(y(), false, size));
        }

        @Override
        public Class<?> type() {
            return Position.class;
        }

    }

    final class Impl implements Position {
        private final RuntimeDefinableGeometry.CoordType x;
        private final RuntimeDefinableGeometry.CoordType y;
        private PositionBuilder builder;

        public Impl(RuntimeDefinableGeometry.CoordType x, RuntimeDefinableGeometry.CoordType y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public Geometry build(Vec2d var) {
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

        public RuntimeDefinableGeometry.CoordType x() {
            return x;
        }

        public RuntimeDefinableGeometry.CoordType y() {
            return y;
        }

    }

}