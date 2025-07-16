package me.combimagnetron.sunscreen.menu.cursor;

import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.passport.internal.entity.impl.display.Display;
import me.combimagnetron.passport.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Vec2d;
import me.combimagnetron.sunscreen.util.Vec2i;

public interface EntityCursor extends Cursor {

    Entity entity();

    class TextDisplayCursor implements EntityCursor {
        private final TextDisplay textDisplay;

        protected TextDisplayCursor(SunscreenUser<?> user) {
            textDisplay = TextDisplay.textDisplay(user.position());
        }

        @Override
        public Entity entity() {
            return null;
        }

        @Override
        public void show(SunscreenUser<?> user) {

        }

        @Override
        public void move(Vec2i vec2i) {
            Display.Transformation transformation = Display.Transformation.transformation().translation(Vector3d.vec3(vec2i.x(), vec2i.y(), -0.24999)).scale(Vector3d.vec3((double) 1/24, (double) 1/24, (double) 1/24));
            textDisplay.transformation(transformation);
        }

        @Override
        public void hide() {

        }

        @Override
        public void show() {

        }

        @Override
        public void remove() {

        }

        @Override
        public Vec2d startPos(SunscreenUser<?> user) {
            return null;
        }
    }

}
