package me.combimagnetron.sunscreen.renderer.div;

import me.combimagnetron.passport.internal.entity.impl.display.Display;
import me.combimagnetron.passport.internal.entity.impl.display.ItemDisplay;
import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.passport.internal.item.Item;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.ViewportHelper;

public final class ItemDivRenderer implements DivRenderer<ItemDisplay> {
    private final ReferenceHolder<ItemDisplay> referenceHolder = ReferenceHolder.item();

    @Override
    public Reference<ItemDisplay> render(Div<ItemDisplay> div, SunscreenUser<?> user) {
        if (div.canvas() instanceof Item item) {
            ItemDisplay itemDisplay = ItemDisplay.itemDisplay(user.position());
            itemDisplay.item(item);
            itemDisplay.billboard(ItemDisplay.Billboard.CENTER);
            itemDisplay.brightness(15, 15);
            itemDisplay.displayType(ItemDisplay.DisplayType.GUI);
            Display.Transformation transformation = Display.Transformation.transformation();
            transformation = transformation.translation(ViewportHelper.toTranslation(ViewportHelper.fromPosition(div.position()).add((int) (div.size().x() * 0.5 * div.scale().x()), (int) (div.size().y() * div.scale().y())), user.screenSize()).add(Vector3d.vec3(0, 0, -0.25 + Integer.MIN_VALUE * div.order())));
            transformation = transformation.scale(Vector3d.vec3((double) 1 / 24).mul(div.scale()));
            itemDisplay.transformation(transformation);
            return referenceHolder.reference(itemDisplay, div);
        }
        return null;
    }

    @Override
    public ReferenceHolder<ItemDisplay> referenceHolder() {
        return referenceHolder;
    }
}
