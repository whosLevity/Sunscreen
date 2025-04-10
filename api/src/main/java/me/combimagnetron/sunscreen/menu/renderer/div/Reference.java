package me.combimagnetron.sunscreen.menu.renderer.div;

import me.combimagnetron.passport.internal.entity.Entity;
import me.combimagnetron.sunscreen.menu.element.div.Div;
import me.combimagnetron.sunscreen.util.Identifier;

public interface Reference<T> {

    Div div();

    T t();

    Identifier identifier();

    static <T> Reference<T> of(T t, Div div) {
        return new ReferenceImpl<>(t, div.identifier(), div);
    }

    static Reference<me.combimagnetron.passport.internal.entity.Entity> entity(Entity t, Div div) {
        return new EntityReference(t, div.identifier(), div);
    }

    record ReferenceImpl<T>(T t, Identifier identifier, Div div) implements Reference<T> {

    }

    record EntityReference(Entity t, Identifier identifier, Div div) implements Reference<Entity> {

    }

}
