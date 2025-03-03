package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.sunscreen.util.Pair;
import me.combimagnetron.sunscreen.util.Vec2d;

public record ScreenSize(Vec2d pixel, Pair<Vec2d, Vec2d> coordinates) {

    public static ScreenSize of(Vec2d pixel, Pair<Vec2d, Vec2d> coordinates) {
        return new ScreenSize(pixel, coordinates);
    }

}
