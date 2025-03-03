package me.combimagnetron.sunscreen.config;

import me.combimagnetron.sunscreen.menu.element.Element;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.util.Identifier;

import java.util.Collection;

public interface MenuConfig {

    Identifier identifier();

    Collection<DivConfig> divs();

    interface DivConfig {

        Identifier identifier();

        Collection<ElementConfig> elements();

        Position position();

    }

    interface ElementConfig {

        Identifier identifier();

        Position position();

        Class<? extends Element> type();

    }

}
