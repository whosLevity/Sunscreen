package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.sunscreen.util.Vec2d;

import java.util.Objects;

public interface Size extends RuntimeDefinableGeometry, Geometry {

    static SizeBuilder size() {
        return new SizeBuilder();
    }

    static Size pixel(double x, double y) {
        return new Impl(RuntimeDefinableGeometry.CoordType.pixel(x), RuntimeDefinableGeometry.CoordType.pixel(y));
    }

    static Size pixel(Vec2d size) {
        return new Impl(RuntimeDefinableGeometry.CoordType.pixel(size.x()), RuntimeDefinableGeometry.CoordType.pixel(size.y()));
    }

    Vec2d vec2d();

    final class SizeBuilder extends RuntimeDefinableGeometry.GeometryBuilder<Size> {

        protected SizeBuilder(Section<Size> x, Section<Size> y) {
            super(x, y);
        }

        private SizeBuilder() {
            super();
        }

        @Override
        public Class<?> type() {
            return Size.class;
        }

        @Override
        public Size finish(Vec2d size) {
            return new Impl(finalise(x(), true, size), finalise(y(), false, size));
        }

    }

    final class Impl implements Size {
        private final CoordType x;
        private final CoordType y;
        private SizeBuilder builder;

        public Impl(CoordType x, CoordType y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public Geometry build(Vec2d var) {
            return builder.finish(var);
        }

        @Override
        public GeometryBuilder<? extends Geometry> builder() {
            return builder;
        }

        @Override
        public void builder(GeometryBuilder<? extends Geometry> builder) {
            if (builder instanceof SizeBuilder) {
                this.builder = (SizeBuilder) builder;
            } else {
                throw new IllegalArgumentException("Builder must be of type SizeBuilder");
            }
        }

        public CoordType x() {
            return x;
        }

        public CoordType y() {
            return y;
        }

        @Override
        public Vec2d vec2d() {
            return Vec2d.of(x.pixel(), y.pixel());
        }
    }

}
