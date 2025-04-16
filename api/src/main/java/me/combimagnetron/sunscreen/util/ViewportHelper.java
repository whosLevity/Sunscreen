package me.combimagnetron.sunscreen.util;

import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.menu.ScreenSize;
import me.combimagnetron.sunscreen.menu.Position;

public class ViewportHelper {

    /**
     * Converts a translation to a screen position.
     * @param translation The translation to convert.
     * @param screenSize The screen size of the user, to calculate the viewport position.
     * @return The screen position.
     */
    public static Vec2d toScreen(Vector3d translation, ScreenSize screenSize) {
        return Vec2d.of((screenSize.pixel().x() * (translation.x() + screenSize.coordinates().v().x()))/(screenSize.coordinates().v().x()*2),
                (screenSize.pixel().y() * (translation.y() - screenSize.coordinates().v().y()))/ (screenSize.coordinates().k().y()*2));
    }

    /**
     * Converts a screen position to a translation.
     * @param screen The screen position to convert.
     * @param screenSize The screen size of the user, to calculate the viewport position.
     * @return The translation.
     */
    public static Vector3d toTranslation(Vec2d screen, ScreenSize screenSize) {
        return Vector3d.vec3(
                (screenSize.coordinates().k().x() + ((screen.x()*(screenSize.coordinates().v().x() - screenSize.coordinates().k().x()))/screenSize.pixel().x())),
                (screenSize.coordinates().v().y() - ((screen.y())*(screenSize.coordinates().v().y() - screenSize.coordinates().k().y()))/screenSize.pixel().y()),
                0);
    }

    /**
     * Converts a Vector3d to a Vec2d by supplying a Vec2d with the x and y values.
     * @param vector3d The Vector3d to convert.
     * @return A Vec2d with the x and y values of the Vector3d.
     */
    public static Vec2d fromVector3d(Vector3d vector3d) {
        return Vec2d.of(vector3d.x(), vector3d.y());
    }

    public static Vec2d fromPosition(Position position) {
        return Vec2d.of(position.x().pixel(), position.y().pixel());
    }

}
