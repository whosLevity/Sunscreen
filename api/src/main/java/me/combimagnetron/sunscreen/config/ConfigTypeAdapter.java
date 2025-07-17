package me.combimagnetron.sunscreen.config;

import me.combimagnetron.passport.config.element.ConfigElement;
import me.combimagnetron.passport.config.element.Node;
import me.combimagnetron.passport.config.element.Section;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConfigTypeAdapter<T> {

    @Nullable T direct(@NotNull ConfigElement element);

    @Nullable T find(@NotNull Section section);

    static boolean isSection(ConfigElement element) {
        return element instanceof Section;
    }

    static boolean isNode(ConfigElement element) {
        return element instanceof Node;
    }

    static @Nullable Section find(ConfigElement element, String name) {
        if (isSection(element) && element.name().equals(name)) {
            return (Section) element;
        }
        Section section = (Section) element;
        for (ConfigElement child : section.elements()) {
            if (!child.name().equals(name)) {
               continue;
            }
            return (Section) child;
        }
        return null;
    }

    static <A> @Nullable Node<A> find(ConfigElement element, String name, Class<A> type) {
        if (!isNode(element) && ((Section) element).elements().stream().anyMatch(element1 -> element1.name().equals(name))) {
            return ((Section) element).elements().stream()
                    .filter(element1 -> element1.name().equals(name))
                    .map(element1 -> (Node<A>) element1)
                    .findFirst()
                    .orElse(null);
        }
        Node<?> node = (Node<?>) element;
        if (!node.name().equals(name)) {
            return null;
        }
        if (!(node.type().equals(type))) {
            return null;
        }
        return (Node<A>) node;
    }

}
