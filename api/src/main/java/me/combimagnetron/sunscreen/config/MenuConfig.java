package me.combimagnetron.sunscreen.config;

import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface MenuConfig {

    @NotNull Identifier identifier();

    @NotNull Collection<DivConfig> divs();

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
