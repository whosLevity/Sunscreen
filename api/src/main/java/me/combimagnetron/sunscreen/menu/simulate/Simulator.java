package me.combimagnetron.sunscreen.menu.simulate;

import java.util.Collection;

public interface Simulator {

    Collection<String> command();

    boolean active();

    ChestMenuEmulator chestMenuEmulator();

}
