package me.combimagnetron.sunscreen.config.adapters;

import me.combimagnetron.passport.config.element.ConfigElement;
import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.config.ConfigTypeAdapter;
import me.combimagnetron.sunscreen.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IdentifierTypeAdapter implements ConfigTypeAdapter<Identifier> {
    @Override
    public @Nullable Identifier direct(@NotNull ConfigElement element) {
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
    public @Nullable Identifier find(@NotNull Section section) {
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
