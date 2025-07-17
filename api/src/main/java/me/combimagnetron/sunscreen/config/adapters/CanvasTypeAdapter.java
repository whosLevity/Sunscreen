package me.combimagnetron.sunscreen.config.adapters;

import me.combimagnetron.passport.config.element.ConfigElement;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.config.ConfigTypeAdapter;
import me.combimagnetron.sunscreen.config.MenuConfigTransformer;
import me.combimagnetron.sunscreen.image.Canvas;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class CanvasTypeAdapter implements ConfigTypeAdapter<Map<String, Canvas>> {
    @Override
    public @Nullable Map<String, Canvas> direct(@NotNull ConfigElement element) {
        return MenuConfigTransformer.ElementTransformer.get((Section) element);
    }

    @Override
    public @Nullable Map<String, Canvas> find(@NotNull Section section) {
        Section section1 = ConfigTypeAdapter.find(section, "canvas");
        if (section1 == null) {
            return null;
        }
        return MenuConfigTransformer.ElementTransformer.get(section1);
    }
}
