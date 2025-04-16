package me.combimagnetron.sunscreen.registry;

import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.internal.registry.Registry;
import me.combimagnetron.passport.util.Pair;
import me.combimagnetron.sunscreen.menu.MenuTemplate;

import java.util.Collection;

public interface MenuRegistry extends Registry<MenuTemplate> {

    default void register(MenuTemplate menuTemplate) {
        register(Identifier.split(menuTemplate.identifier().string()), menuTemplate);
    }

    static MenuRegistry create() {
        return new Impl();
    }

    default MenuTemplate get(me.combimagnetron.sunscreen.util.Identifier identifier) {
        return get(Identifier.of(identifier.namespace().string(), identifier.key().string()));
    }

    Collection<MenuTemplate> all();

    void clear();

    class Impl implements MenuRegistry {
        private final Registry<MenuTemplate> registry = Registry.empty();

        public Impl() {
        }

        @Override
        public MenuTemplate register(Identifier identifier, MenuTemplate menuTemplate) {
            return registry.register(identifier, menuTemplate);
        }

        @Override
        public MenuTemplate get(Identifier identifier) {
            return registry.get(identifier);
        }

        @Override
        public MenuTemplate unregister(Identifier identifier) {
            return registry.unregister(identifier);
        }

        @Override
        public boolean contains(Identifier identifier) {
            return registry.contains(identifier);
        }

        @Override
        public Pair<Identifier, MenuTemplate> find(MenuTemplate menuTemplate) {
            return registry.find(menuTemplate);
        }


        @Override
        public Collection<MenuTemplate> all() {
            return ((Registry.Impl) registry).registry().values();
        }

        @Override
        public void clear() {
            for (Identifier identifier : ((Registry.Impl<MenuTemplate>) registry).registry().keySet()) {
                registry.unregister(identifier);
            }
        }
    }

}
