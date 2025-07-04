package me.combimagnetron.sunscreen.config.adapters;

import me.combimagnetron.passport.config.element.ConfigElement;
import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.config.ConfigTypeAdapter;
import me.combimagnetron.sunscreen.util.Identifier;

public class IdentifierTypeAdapter implements ConfigTypeAdapter<Identifier> {
    @Override
    public Identifier direct(ConfigElement element) {
        if (!ConfigTypeAdapter.isNode(element)) {
            return null;
        }
        String string = ConfigTypeAdapter.find(element, "identifier", String.class).value();
        if (string == null) {
            return null;
        }
        return Identifier.split(string);
    }

    @Override
    public Identifier find(Section section) {
        Node<String> node = ConfigTypeAdapter.find(section, "identifier", String.class);
        if (node == null) {
            return null;
        }
        String string = node.value();
        if (string == null) {
            return null;
        }
        return Identifier.split(string);
    }
}
