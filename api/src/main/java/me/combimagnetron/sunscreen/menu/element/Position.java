package me.combimagnetron.sunscreen.menu.element;

import me.combimagnetron.passport.user.User;
import me.combimagnetron.passport.util.matcher.*;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import org.apache.commons.lang3.RegExUtils;

import java.util.ArrayList;
import java.util.List;

public interface Position {

    CoordType x();

    CoordType y();

    static Position pixel(double x, double y) {
        return new Impl(CoordType.pixel(x), CoordType.pixel(y));
    }

    static Position center(SunscreenUser<?> user) {
        return Position.position(user).x.percentage(50).back().y.percentage(50).back().finish();
    }

    static PositionBuilder position(SunscreenUser<?> user) {
        return new PositionBuilder(user);
    }

    final class PositionBuilder {
        private final Section x = new Section(this);
        private final Section y = new Section(this);
        private final SunscreenUser<?> user;

        public PositionBuilder(SunscreenUser<?> user) {
            this.user = user;
        }

        public Section x() {
            return x;
        }

        public Section y() {
            return y;
        }

        public static class Section {
            private final PositionBuilder parent;
            private final List<CoordType> coords = new ArrayList<>();

            protected Section(PositionBuilder parent) {
                this.parent = parent;
            }

            public PositionBuilder back() {
                return parent;
            }

            public Section percentage(double percentage) {
                coords.add(CoordType.percentage(percentage));
                return this;
            }

            public Section pixel(double pixel) {
                coords.add(CoordType.pixel(pixel));
                return this;
            }

            public Section add(CoordType coord) {
                coords.add(coord);
                return this;
            }

        }

        public Position finish() {
            return new Impl(finalise(x.coords, true), finalise(y.coords, false));
        }

        private CoordType finalise(List<CoordType> coords, boolean x) {
            CoordType finalCoord = CoordType.pixel(0);
            for (CoordType coord : coords) {
                if (coord instanceof CoordType.PixelCoordType) {
                    finalCoord = CoordType.pixel(finalCoord.pixel() + coord.pixel());
                } else if (coord instanceof CoordType.PercentageCoordType) {
                    double size = x ? user.screenSize().pixel().x() : user.screenSize().pixel().y();
                    finalCoord = CoordType.pixel(finalCoord.pixel() + (coord.pixel() / 100 * size));
                }
            }
            return finalCoord;
        }

    }

    final class RegexPositionParser {

        public static Position parse(String x, String y, SunscreenUser<?> user) {
            return new Impl(parse(x, user, true), parse(y, user, false));
        }

        public static CoordType parse(String position, SunscreenUser<?> user, boolean x) {
            Token.Type token = Token.Type.of("\\+|\\*|\\/|\\-");
            TokenizedResult result = TokenMatcher.matcher(position).section(MatcherSection.section().token(MatcherToken.optional(token))).validate();
            position = position.replaceAll("center", "50%").replaceAll("left", "0").replaceAll("right", "100%").replaceAll("top", "0").replaceAll("bottom", "100%");
            if (position.contains(" ") || !result.empty()) {
                Operator operator = Operator.get(result.ordered().next().token().captured());
                String[] split = position.split(operator.operator());
                double first = find(split[0], user, x);
                double second = find(split[1], user, x);
                return CoordType.pixel(operator.calc(first, second));
            }
            return null;
        }

    }

    static double find(String input, SunscreenUser<?> user, boolean x) {
        if (input.contains("%")) {
            double size = x ? user.screenSize().pixel().x() : user.screenSize().pixel().y();
            return Double.parseDouble(input.replace("%", "")) / 100 * size;
        } else {
            return Double.parseDouble(input.replace("px", ""));
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

    record Impl(CoordType x, CoordType y) implements Position {

    }

}
