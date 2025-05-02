package me.combimagnetron.sunscreen.config.adapters;

import me.combimagnetron.passport.config.element.ConfigElement;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.config.ConfigTypeAdapter;
import me.combimagnetron.sunscreen.menu.Position;

public class PositionTypeAdapter implements ConfigTypeAdapter<Position.PositionBuilder> {
    @Override
    public Position.PositionBuilder direct(ConfigElement element) {
        if (!ConfigTypeAdapter.isSection(element)) {
            return null;
        }
        Section section = (Section) element;
        return Position.config(section);
    }

    @Override
    public Position.PositionBuilder find(Section section) {
        Section section1 = ConfigTypeAdapter.find(section, "position");
        if (section1 == null) {
            return null;
        }
        return Position.config(section1);
    }
}
