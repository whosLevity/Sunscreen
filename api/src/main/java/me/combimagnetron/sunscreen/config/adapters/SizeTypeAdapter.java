package me.combimagnetron.sunscreen.config.adapters;

import me.combimagnetron.passport.config.element.ConfigElement;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.config.ConfigTypeAdapter;
import me.combimagnetron.sunscreen.menu.Size;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SizeTypeAdapter implements ConfigTypeAdapter<Size.SizeBuilder> {
    @Override
    public Size.@Nullable SizeBuilder direct(@NotNull ConfigElement element) {
        if (!ConfigTypeAdapter.isSection(element)) {
            return null;
        }
        Section section = (Section) element;
        return Size.config(section);
    }

    @Override
    public Size.@Nullable SizeBuilder find(@NotNull Section section) {
        Section section1 = ConfigTypeAdapter.find(section, "size");
        if (section1 == null) {
            return null;
        }
        return Size.config(section1);
    }
}
