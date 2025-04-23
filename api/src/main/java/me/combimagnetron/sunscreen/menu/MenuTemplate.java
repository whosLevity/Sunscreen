package me.combimagnetron.sunscreen.menu;

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

    MenuTemplate apply(Draft draft);

    MenuTemplate div(Div div);

    Identifier identifier();

    static MenuTemplate simple(Type type, Identifier identifier) {
        return new Simple(type, identifier);
    }

    class Simple implements MenuTemplate {
        private final Type type;
        private final Identifier identifier;
        protected final HashMap<Identifier, Div<?>> divHashMap = new HashMap<>();
        protected final HashMap<Identifier, List<ActionWrapper>> actionHashMap = new HashMap<>();

        protected Simple(Type type, Identifier identifier) {
            this.type = type;
            this.identifier = identifier;
        }

        public HashMap<Identifier, Div<?>> divHashMap() {
            return divHashMap;
        }

        public HashMap<Identifier, List<ActionWrapper>> actionHashMap() {
            return actionHashMap;
        }

        @Override
        public Identifier identifier() {
            return identifier;
        }

        @Override
        public MenuTemplate apply(Draft draft) {
            Draft.Impl draftImpl = (Draft.Impl) draft;
            for (Edit<?> edit : draftImpl.edits()) {
                if (edit.type() == Div.class) {
                    Edit<Div> divEdit = (Edit<Div>) edit;
                    Div div = divHashMap.get(edit.identifier());
                    for (Function<Div, Div> draftEdit : divEdit.edits()) {
                        div = draftEdit.apply(div);
                    }
                    divHashMap.put(edit.identifier(), div);
                } else if (edit.type() == Element.class) {
                    Edit<Element<?>> elementEdit = (Edit<Element<?>>) edit;
                    Element<?> element = divHashMap.get(edit.identifier()).elements().stream().filter(e -> e.identifier().equals(edit.identifier())).findFirst().orElse(null);
                    for (Function<Element<?>, Element<?>> draftEdit : elementEdit.edits()) {
                        element = draftEdit.apply(element);
                    }
                    Div div = divHashMap.get(edit.identifier());
                    div.remove(edit.identifier());
                    div.add(element);
                    divHashMap.put(edit.identifier(), div);
                }
            }
            return this;
        }

        @Override
        public MenuTemplate div(Div div) {
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
