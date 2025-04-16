package me.combimagnetron.sunscreen.renderer.div;

import me.combimagnetron.passport.internal.entity.impl.display.ItemDisplay;
import me.combimagnetron.passport.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.user.SunscreenUser;

public final class CombinedDivRenderer implements DivRenderer<Object> {
    private final ReferenceHolder<Object> referenceHolder = ReferenceHolder.object();
    private final DivRenderer<TextDisplay> fontDivRenderer = DivRenderer.font();
    private final DivRenderer<ItemDisplay> itemDivRenderer = DivRenderer.item();

    @Override
    public Reference<Object> render(Div<Object> div, SunscreenUser<?> user) {
        if (div.canvas() instanceof TextDisplay) {
            //return fontDivRenderer.render((Div<TextDisplay>) div, user);
        } else if (div.canvas() instanceof ItemDisplay) {
            ///return itemDivRenderer.render((Div<ItemDisplay>) div, user);
        }
        return null;
    }

    @Override
    public ReferenceHolder<Object> referenceHolder() {
        return referenceHolder;
    }
}
