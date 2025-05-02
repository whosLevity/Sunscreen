package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.passport.util.matcher.*;
import me.combimagnetron.sunscreen.element.Operator;
import me.combimagnetron.sunscreen.util.Pair;
import me.combimagnetron.sunscreen.util.RuntimeDefinable;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;

import java.util.ArrayList;
import java.util.List;

public sealed interface RuntimeDefinableGeometry extends RuntimeDefinable<Geometry, RuntimeDefinableGeometry.GeometryBuilder<? extends Geometry>, Pair<Vec2i, Vec2i>> permits Position, Size {

    CoordType<Integer> x();

    CoordType<Integer> y();

    abstract class GeometryBuilder<G extends Geometry> implements RuntimeDefinable.Type<G, Pair<Vec2i, Vec2i>> {
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

        public abstract G finish(Vec2i size, Vec2i div);

        protected CoordType<Integer> finalise(Section<G> section, boolean x, Vec2i input, Vec2i div) {
            CoordType<Integer> finalCoord = CoordType.pixel(0);
            for (CoordType coord : section.coords) {
                if (coord instanceof CoordType.PixelCoordType pixelCoordType) {
                    finalCoord = CoordType.pixel(finalCoord.pixel() + pixelCoordType.pixel());
                } else if (coord instanceof CoordType.PercentageCoordType percentageCoordType) {
                    double size = x ? input.x() : input.y();
                    finalCoord = CoordType.pixel((int) (finalCoord.pixel() + (percentageCoordType.pixel() / 100 * size)));
                } else if (coord instanceof CoordType.SizePercentage sizePercentage) {
                    double size = x ? div.x() : div.y();
                    finalCoord = CoordType.pixel((int) (finalCoord.pixel() + (sizePercentage.pixel() / 100 * size)));
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

            public Section<T> pixel(int pixel) {
                coords.add(CoordType.pixel(pixel));
                return this;
            }

            public Section<T> percentage(double percentage) {
                coords.add(CoordType.percentage(percentage));
                return this;
            }

            public Section<T> sizePercentage(double percentage) {
                coords.add(CoordType.sizePercentage(percentage));
                return this;
            }

            public GeometryBuilder<T> back() {
                return parent;
            }
        }

        protected static <T extends Geometry> Section<T> parse(String position) {
            Token.Type token = Token.Type.of("\\s\\+\\s|\\s\\*\\s|\\s\\/\\s|\\s\\-\\s");
            position = position.replaceAll("center", "50% - 50&").replaceAll("left", "0px").replaceAll("right", "100%").replaceAll("top", "0px").replaceAll("bottom", "100%");
            TokenizedResult result = TokenMatcher.matcher(position).section(MatcherSection.section().token(MatcherToken.optional(token))).validate();
            Operator operator = Operator.find(position);
            String[] split = new String[]{position};
            if (!result.empty()) {
                operator = Operator.get(result.ordered().next().token().captured());
                split = position.split(operator.operator());
            }
            Section<T> section = new Section<>(null);
            for (String string : split) {
                if (string.contains("%")) {
                    section.percentage(Double.parseDouble(string.replace("%", "")));
                } else if (string.contains("&")) {
                    double x = operator.calc(0, Double.parseDouble(string.replace("&", "")));
                    section.sizePercentage(x);
                } else {
                    double x = operator.calc(0, Double.parseDouble(string.replace("px", "")));
                    section.pixel((int) x);
                }
            }
            return section;
        }
    }

    interface CoordType<T extends Number> {

        T pixel();

        static CoordType<Integer> pixel(int pixel) {
            return new PixelCoordType(pixel);
        }

        static CoordType<Double> percentage(double percentage) {
            return new PercentageCoordType(percentage);
        }

        static CoordType<Double> sizePercentage(double percentage) {
            return new SizePercentage(percentage);
        }

        record PixelCoordType(Integer pixel) implements CoordType<Integer> {

        }

        record PercentageCoordType(Double pixel) implements CoordType<Double> {

        }

        record SizePercentage(Double pixel) implements CoordType<Double> {

        }

    }

}
