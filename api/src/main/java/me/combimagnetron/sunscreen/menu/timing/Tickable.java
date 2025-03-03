package me.combimagnetron.sunscreen.menu.timing;

public interface Tickable {

    void tick(Tick tick) throws TickFailException;

}
