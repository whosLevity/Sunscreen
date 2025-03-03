package me.combimagnetron.sunscreen.menu.timing;

public interface Tick {

    long time();

    long timeSinceLastTick();

    int id();

    static Tick traceable(long time, long timeSinceLastTick) {
        return new TraceableTick(time, timeSinceLastTick);
    }

    record TraceableTick(long time, long timeSinceLastTick) implements Tick {

        @Override
        public int id() {
            return hashCode();
        }

    }

}
