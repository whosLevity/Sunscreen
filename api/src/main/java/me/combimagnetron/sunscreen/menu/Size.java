package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.util.Pair;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;

public non-sealed interface Size extends RuntimeDefinableGeometry, Geometry {

    static SizeBuilder size() {
        return new SizeBuilder();
    }

    static Size pixel(int x, int y) {
        return new Impl(RuntimeDefinableGeometry.CoordType.pixel(x), RuntimeDefinableGeometry.CoordType.pixel(y));
    }

    static Size pixel(Vec2i size) {
        return new Impl(RuntimeDefinableGeometry.CoordType.pixel(size.x()), RuntimeDefinableGeometry.CoordType.pixel(size.y()));
    }

    static SizeBuilder config(Section section) {
        String x = ((Node<String>)section.find("width")).value();
        String y = ((Node<String>)section.find("height")).value();
        GeometryBuilder.Section<Size> xSection = RuntimeDefinableGeometry.GeometryBuilder.parse(x);
        GeometryBuilder.Section<Size> ySection = RuntimeDefinableGeometry.GeometryBuilder.parse(y);
        SizeBuilder builder = new SizeBuilder(xSection, ySection);
        xSection.parent(builder);
        ySection.parent(builder);
        return builder;
    }

    Vec2i vec2i();

    final class SizeBuilder extends RuntimeDefinableGeometry.GeometryBuilder<Size> {

        protected SizeBuilder(Section<Size> x, Section<Size> y) {
            super(x, y);
        }

        private SizeBuilder() {
            super();
        }

        @Override
        public Size finish(Vec2i size, Vec2i div) {
            return new Impl(finalise(x(), true, size, div), finalise(y(), false, size, size));

        }

        @Override
        public int priority() {
            return 0;
        }

        @Override
        public Class<?> type() {
            return Size.class;
        }

        @Override
        public Size finish(Pair<Vec2i, Vec2i> vec2dVec2dPair) {
            return finish(vec2dVec2dPair.k(), vec2dVec2dPair.v());
        }

    }

    final class Impl implements Size {
        private final CoordType<Integer> x;
        private final CoordType<Integer> y;
        private SizeBuilder builder;

        public Impl(CoordType<Integer> x, CoordType<Integer> y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public Geometry build(Pair<Vec2i, Vec2i> var) {
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

        public CoordType<Integer> x() {
            return x;
        }

        public CoordType<Integer> y() {
            return y;
        }

        @Override
        public Vec2i vec2i() {
            return Vec2i.of((int) x.pixel(), (int) y.pixel());
        }

    }

}
