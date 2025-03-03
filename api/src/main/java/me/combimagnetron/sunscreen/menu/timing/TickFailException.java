package me.combimagnetron.sunscreen.menu.timing;

public class TickFailException extends RuntimeException {
    public TickFailException(Tick tick, String message) {
        super(String.format("Tick %s failed: %s", tick.id(), message));
    }
}
