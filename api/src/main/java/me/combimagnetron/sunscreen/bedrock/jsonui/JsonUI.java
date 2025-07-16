package me.combimagnetron.sunscreen.bedrock.jsonui;

import me.combimagnetron.sunscreen.menu.OpenedMenu;
import org.jetbrains.annotations.NotNull;

public interface JsonUI {

    JUIScreen transform(@NotNull OpenedMenu openedMenu);

}
