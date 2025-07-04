package me.combimagnetron.sunscreen.menu.timing;

public interface Tickable {

    boolean tick(Tick tick) throws TickFailException;

}
