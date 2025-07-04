package me.combimagnetron.sunscreen.menu;

import me.combimagnetron.passport.config.element.Section;
import me.combimagnetron.sunscreen.config.CachedConfigDiv;
import me.combimagnetron.sunscreen.config.MenuConfigTransformer;
import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.logic.action.ActionWrapper;
import me.combimagnetron.sunscreen.logic.action.Argument;
import me.combimagnetron.sunscreen.menu.draft.Draft;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.element.div.Div;
import me.combimagnetron.sunscreen.element.div.Edit;
import me.combimagnetron.sunscreen.util.Identifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public interface MenuTemplate {

    MenuTemplate div(CachedConfigDiv div);

    Identifier identifier();

    static MenuTemplate simple(Type type, Identifier identifier) {
        return new Simple(type, identifier);
    }

    class Simple implements MenuTemplate {
        private final Type type;
        private final Identifier identifier;
        protected final HashMap<Identifier, CachedConfigDiv> divHashMap = new HashMap<>();
        protected final HashMap<Identifier, List<ActionWrapper>> actionHashMap = new HashMap<>();

        protected Simple(Type type, Identifier identifier) {
            this.type = type;
            this.identifier = identifier;
        }

        public HashMap<Identifier, CachedConfigDiv> divHashMap() {
            return divHashMap;
        }

        public HashMap<Identifier, List<ActionWrapper>> actionHashMap() {
            return actionHashMap;
        }

        @Override
        public Identifier identifier() {
            return identifier;
        }

       public HashMap<Identifier, Div<?>> create() {
            HashMap<Identifier, Div<?>> divs = new HashMap<>();
            for (CachedConfigDiv div : divHashMap.values()) {
                Div<?> d = Div.div(div.identifier());
                d.order(div.order());
                for (Section element : div.elements()) {
                    Element e = MenuConfigTransformer.ElementTransformer.transform(element, identifier.string());
                    d.add(e);
                }
                if (div.condition() != null) {
                    d.condition(div.condition());
                }
                for (RuntimeDefinableGeometry.GeometryBuilder<?> geometry : div.geometry()) {
                    d.geometry(geometry);
                }
                divs.put(div.identifier(), d);
            }
            return divs;
       }

        @Override
        public MenuTemplate div(CachedConfigDiv div) {
            divHashMap.put(div.identifier(), div);
            return this;
        }

    }

    enum Type {
        FLOAT,
        SINGLE,
        MAP,
        HYBRID
    }

}
