package me.combimagnetron.sunscreen.renderer.div;

import me.combimagnetron.passport.internal.entity.impl.display.Display;
import me.combimagnetron.passport.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.image.CanvasRenderer;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.menu.ScreenSize;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.ViewportHelper;
import net.kyori.adventure.text.Component;

public final class FontDivRenderer implements DivRenderer<TextDisplay> {
    private final ReferenceHolder<TextDisplay> referenceHolder = ReferenceHolder.font();

    @Override
    public Reference<TextDisplay> render(Div<TextDisplay> div, SunscreenUser<?> user) {
        return referenceHolder.reference(subrender(div, user), div);
    }

    @Override
    public ReferenceHolder<TextDisplay> referenceHolder() {
        return referenceHolder;
    }

    private TextDisplay subrender(Div<TextDisplay> div, SunscreenUser<?> user) {
        TextDisplay textDisplay = TextDisplay.textDisplay(user.position());
        Canvas canvas = div.render(user);
        Component text = CanvasRenderer.optimized().render(canvas).component();
        textDisplay.text(text);
        textDisplay.billboard(Display.Billboard.CENTER);
        textDisplay.brightness(15, 15);
        textDisplay.lineWidth(200000);
        //textDisplay.backgroundColor(0);
        Display.Transformation transformation = Display.Transformation.transformation();
        ScreenSize screenSize = user.screenSize();
        System.out.println(((screenSize.coordinates().v().x() - screenSize.coordinates().k().x())/screenSize.pixel().x()) * div.size().x() * 0.5 + ((40.75*0.5)/screenSize.pixel().x()) + " " + (screenSize.coordinates().v().x() - screenSize.coordinates().k().x()) + " " + (40.75*0.5)/screenSize.pixel().x());
        transformation = transformation.translation(ViewportHelper.toTranslation(ViewportHelper.fromPosition(div.position()), user.screenSize()).add(Vector3d.vec3(((screenSize.coordinates().v().x() - screenSize.coordinates().k().x() + 0.027)/screenSize.pixel().x()) * div.size().x() * 0.5, -((screenSize.coordinates().v().y() - screenSize.coordinates().k().y())/screenSize.pixel().y()) * div.size().y(), -0.25 + ((double) 1 /1_000_000) * div.order())));
        transformation = transformation.scale(Vector3d.vec3((double) 1 /24).mul(div.scale()));
        textDisplay.transformation(transformation);
        user.show(textDisplay);
        return textDisplay;
    }

}
