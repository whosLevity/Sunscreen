package me.combimagnetron.sunscreen.util;

import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.menu.ScreenSize;
import me.combimagnetron.sunscreen.menu.element.Position;

public class ViewportHelper {

    public static Vec2d toScreen(Vector3d translation, ScreenSize screenSize) {
        return Vec2d.of((screenSize.pixel().x() * (translation.x() + screenSize.coordinates().v().x()))/(screenSize.coordinates().v().x()*2), (screenSize.pixel().y() * (translation.y() + screenSize.coordinates().v().y()))/Math.abs((screenSize.coordinates().v().y()*2)));
    }

    public static Vector3d toTranslation(Vec2d screen, ScreenSize screenSize) {
        return Vector3d.vec3((screenSize.coordinates().k().x() + ((screen.x()*(screenSize.coordinates().v().x() - screenSize.coordinates().k().x()))/screenSize.pixel().x())), screenSize.coordinates().k().y() - ((screen.y()*(screenSize.coordinates().v().y() - screenSize.coordinates().k().y()))/screenSize.pixel().y()), 0);
    }

    public static Vec2d fromVector3d(Vector3d vector3d) {
        return Vec2d.of(vector3d.x(), vector3d.y());
    }

    public static Vec2d fromPosition(Position position) {
        return Vec2d.of(position.x().pixel(), position.y().pixel());
    }

}
