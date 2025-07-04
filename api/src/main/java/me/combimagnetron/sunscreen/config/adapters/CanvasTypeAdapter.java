package me.combimagnetron.sunscreen.config.adapters;

import me.combimagnetron.passport.config.element.ConfigElement;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.config.ConfigTypeAdapter;
import me.combimagnetron.sunscreen.config.MenuConfigTransformer;
import me.combimagnetron.sunscreen.image.Canvas;

import java.util.Map;

public class CanvasTypeAdapter implements ConfigTypeAdapter<Map<String, Canvas>> {
    @Override
    public Map<String, Canvas> direct(ConfigElement element) {
        return MenuConfigTransformer.ElementTransformer.get((Section) element);
    }

    @Override
    public Map<String, Canvas> find(Section section) {
        Section section1 = ConfigTypeAdapter.find(section, "canvas");
        if (section1 == null) {
            return null;
        }
        return MenuConfigTransformer.ElementTransformer.get(section1);
    }
}
