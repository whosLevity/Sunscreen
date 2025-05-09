package me.combimagnetron.sunscreen.menu.builtin.setup;

import me.combimagnetron.sunscreen.element.animated.AnimatedElement;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.MenuTemplate;
import me.combimagnetron.sunscreen.menu.OpenedMenu;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.menu.Size;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2i;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class UserSetupMenu extends OpenedMenu.Float {

    public UserSetupMenu(SunscreenUser<?> viewer) {
        super(viewer, MenuTemplate.simple(MenuTemplate.Type.FLOAT, null));
        divHashMap.putAll(build());
        forceDivGeometry(viewer);
        open(viewer);
    }

    private Map<Identifier, Div<Canvas>> build() {
        Map<Identifier, Div<Canvas>> divMap = new HashMap<>();
        Div<Canvas> manual = Div.div(Identifier.of("setup", "manual-button"))
                .size(Size.pixel(154, 80))
                .add(AnimatedElement.of(
                        Canvas.image(Canvas.ImageProvider.file(Path.of("assets/sunscreen/setup/manual.png"))),
                        Vec2i.of(2, 1),
                        60,
                        Position.pixel(0, 0),
                        Size.pixel(154, 80),
                        Identifier.of("setup", "manual-button")));
        divMap.put(manual.identifier(), manual);
        return divMap;
    }

}
