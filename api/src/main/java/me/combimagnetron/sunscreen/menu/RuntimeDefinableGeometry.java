package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.passport.util.matcher.*;
import me.combimagnetron.sunscreen.element.Operator;
import me.combimagnetron.sunscreen.util.RuntimeDefinable;
import me.combimagnetron.sunscreen.util.Vec2d;

import java.util.ArrayList;
import java.util.List;

public interface RuntimeDefinableGeometry extends RuntimeDefinable<Geometry, RuntimeDefinableGeometry.GeometryBuilder<? extends Geometry>, Vec2d> {

    CoordType x();

    CoordType y();

    abstract class GeometryBuilder<G extends Geometry> implements RuntimeDefinable.Type {
        private final Section<G> x;
        private final Section<G> y;

        protected GeometryBuilder(Section<G> x, Section<G> y) {
            this.x = x;
            this.y = y;
        }

        protected GeometryBuilder() {
            this.x = new Section<>(this);
            this.y = new Section<>(this);
        }

        public Section<G> x() {
            return x;
        }

        public Section<G> y() {
            return y;
        }

        public abstract G finish(Vec2d size);

        protected CoordType finalise(Section<G> section, boolean x, Vec2d input) {
            CoordType finalCoord = CoordType.pixel(0);
            for (CoordType coord : section.coords) {
                if (coord instanceof CoordType.PixelCoordType) {
                    finalCoord = CoordType.pixel(finalCoord.pixel() + coord.pixel());
                } else if (coord instanceof CoordType.PercentageCoordType) {
                    double size = x ? input.x() : input.y();
                    finalCoord = CoordType.pixel(finalCoord.pixel() + (coord.pixel() / 100 * size));
                }
            }
            return finalCoord;
        }

        public static final class Section<T extends Geometry> {
            private GeometryBuilder<T> parent;
            private final List<CoordType> coords = new ArrayList<>();

            private Section(GeometryBuilder<T> parent) {
                this.parent = parent;
            }

            protected void parent(GeometryBuilder<T> parent) {
                this.parent = parent;
            }

            public Section<T> pixel(double pixel) {
                coords.add(CoordType.pixel(pixel));
                return this;
            }

            public Section<T> percentage(double percentage) {
                coords.add(CoordType.percentage(percentage));
                return this;
            }

            public GeometryBuilder<T> back() {
                return parent;
            }
        }

        protected static <T extends Geometry> Section<T> parse(String position) {
            Token.Type token = Token.Type.of("\\+|\\*|\\/|\\-");
            TokenizedResult result = TokenMatcher.matcher(position).section(MatcherSection.section().token(MatcherToken.optional(token))).validate();
            position = position.replaceAll("center", "50%").replaceAll("left", "0px").replaceAll("right", "100%").replaceAll("top", "0px").replaceAll("bottom", "100%");
            Operator operator = null;
            String[] split = new String[]{position};
            if (!result.empty()) {
                operator = Operator.get(result.ordered().next().token().captured());
                split = position.split(operator.operator());
            }
            Section<T> section = new Section<>(null);
            boolean first = true;
            for (String string : split) {
                if (string.contains("%")) {
                    section.percentage(Double.parseDouble(string.replace("%", "")));
                } else {
                    int times = 1;
                    if (!first && operator != null) {
                        times = operator.operator().equals("+") ? -1 : 1;
                    }
                    section.pixel(times * (Double.parseDouble(string.replace("px", ""))));
                }
                first = false;
            }
            return section;
        }

    }

    interface CoordType {

        double pixel();

        static CoordType pixel(double pixel) {
            return new PixelCoordType(pixel);
        }

        static CoordType percentage(double percentage) {
            return new PercentageCoordType(percentage);
        }

        record PixelCoordType(double pixel) implements CoordType {

        }

        record PercentageCoordType(double pixel) implements CoordType {

        }

    }

}
