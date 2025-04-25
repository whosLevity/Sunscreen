package me.combimagnetron.sunscreen.menu.timing;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MenuTicker {
    private final static ScheduledExecutorService Executor = Executors.newScheduledThreadPool(3, f -> {Thread thread = new Thread(f, "Sunscreen/MenuTicker"); thread.setDaemon(true); return thread;});
    private final Map<Integer, ExecutingTickable> tickables = new java.util.HashMap<>();

    public ExecutingTickable start(Tickable tickable) {
        ExecutingTickable executingTickable = new ExecutingTickable(tickable);
        Future<?> future = Executor.scheduleAtFixedRate(executingTickable::tick, 0L, 50L, TimeUnit.MILLISECONDS);
        executingTickable.future(future);
        tickables.put(tickable.hashCode(), executingTickable);
        return executingTickable;
    }

    public void stop(Tickable tickable) {
        ExecutingTickable executingTickable = tickables.remove(tickable.hashCode());
        if (executingTickable != null) {
            executingTickable.cancel();
        }
    }

    public ExecutingTickable get(Tickable tickable) {
        return tickables.get(tickable.hashCode());
    }

    public static class ExecutingTickable {
        private Future<?> future;
        private final Tickable tickable;
        private final UUID uuid = UUID.randomUUID();
        private long lastTickTime;
        private long timeSinceLastTick;
        private long time;

        protected ExecutingTickable(Tickable tickable) {
            this.tickable = tickable;
            this.lastTickTime = System.currentTimeMillis();
            this.timeSinceLastTick = 0;
            this.time = 0;
        }

        public UUID uuid() {
            return uuid;
        }

        void future(Future<?> future) {
            this.future = future;
        }

        public void cancel() {
            future.cancel(true);
        }

        private void tick() {
            long currentTime = System.currentTimeMillis();
            time += currentTime - lastTickTime;
            timeSinceLastTick = currentTime - lastTickTime;
            lastTickTime = currentTime;
            try {
                tickable.tick(Tick.traceable(time, timeSinceLastTick));
            } catch (TickFailException e) {
                throw new RuntimeException(e);
            }
        }

        public long time() {
            return time;
        }

        public long timeSinceLastTick() {
            return timeSinceLastTick;
        }

    }

}
