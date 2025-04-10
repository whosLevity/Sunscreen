package me.combimagnetron.sunscreen.menu.renderer.div;

import me.combimagnetron.passport.internal.entity.impl.display.ItemDisplay;
import me.combimagnetron.passport.internal.entity.impl.display.TextDisplay;
import me.combimagnetron.sunscreen.menu.element.div.Div;
import me.combimagnetron.sunscreen.util.Identifier;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface ReferenceHolder<T> {

    Reference<T> reference(T t, Div div);

    Reference<T> reference(Identifier identifier);

    Collection<Reference<T>> references();

    static ReferenceHolder<TextDisplay> font() {
        return new FontImpl();
    }

    static ReferenceHolder<ItemDisplay> item() {
        return new ItemImpl();
    }

    static ReferenceHolder<Object> object() {
        return new ObjectImpl();
    }

    class FontImpl implements ReferenceHolder<TextDisplay> {
        private final ConcurrentHashMap<Div, Reference<TextDisplay>> references = new ConcurrentHashMap<>();

        @Override
        public Reference<TextDisplay> reference(TextDisplay t, Div div) {
            Reference<TextDisplay> reference = Reference.of(t, div);
            references.put(div, reference);
            return reference;
        }

        @Override
        public Reference<TextDisplay> reference(Identifier identifier) {
            return references.values().stream()
                    .filter(reference -> reference.identifier().equals(identifier))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public Collection<Reference<TextDisplay>> references() {
            return references.values();
        }
    }

    class ItemImpl implements ReferenceHolder<ItemDisplay> {
        private final ConcurrentHashMap<Div, Reference<ItemDisplay>> references = new ConcurrentHashMap<>();

        @Override
        public Reference<ItemDisplay> reference(ItemDisplay t, Div div) {
            Reference<ItemDisplay> reference = Reference.of(t, div);
            references.put(div, reference);
            return reference;
        }

        @Override
        public Reference<ItemDisplay> reference(Identifier identifier) {
            return references.values().stream()
                    .filter(reference -> reference.identifier().equals(identifier))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public Collection<Reference<ItemDisplay>> references() {
            return references.values();
        }
    }

    class ObjectImpl implements ReferenceHolder<Object> {
        private final ConcurrentHashMap<Div, Reference<Object>> references = new ConcurrentHashMap<>();

        @Override
        public Reference<Object> reference(Object t, Div div) {
            Reference<Object> reference = Reference.of(t, div);
            references.put(div, reference);
            return reference;
        }

        @Override
        public Reference<Object> reference(Identifier identifier) {
            return references.values().stream()
                    .filter(reference -> reference.identifier().equals(identifier))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public Collection<Reference<Object>> references() {
            return references.values();
        }
    }

}
