package me.combimagnetron.sunscreen.renderer.div;

import me.combimagnetron.passport.internal.entity.impl.display.ItemDisplay;
import me.combimagnetron.passport.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.user.SunscreenUser;

public sealed interface DivRenderer<T> permits CombinedDivRenderer, FontDivRenderer, ItemDivRenderer {

    static DivRenderer<TextDisplay> font() {
        return new FontDivRenderer();
    }

    static DivRenderer<ItemDisplay> item() {
        return new ItemDivRenderer();
    }

    Reference<T> render(Div<T> div, SunscreenUser<?> user);

    ReferenceHolder<T> referenceHolder();

}
