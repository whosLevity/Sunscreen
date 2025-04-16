package me.combimagnetron.sunscreen.util;

import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.user.SunscreenUser;

public class HoverHelper {

    public static boolean isHovered(Vector3d cursorTranslation, SunscreenUser<?> viewer, Vec2d position, Vec2d size) {
        Vec2d cursor = ViewportHelper.toScreen(cursorTranslation, viewer.screenSize());
        return isHovered(cursor, position, size);
    }

    public static boolean isHovered(Vec2d cursor, Vec2d position, Vec2d size) {
        return cursor.x() > position.x() && cursor.x() < position.x() + size.x() && cursor.y() > position.y() && cursor.y() < position.y() + size.y();
    }

    public static boolean isHovered(Vec2d cursor, Vec2d position, Size size) {
        return cursor.x() > position.x() && cursor.x() < position.x() + size.x().pixel() && cursor.y() > position.y() && cursor.y() < position.y() + size.y().pixel();
    }

}
