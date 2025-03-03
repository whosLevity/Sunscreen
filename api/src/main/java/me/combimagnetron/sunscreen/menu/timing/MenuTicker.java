package me.combimagnetron.sunscreen.menu.timing;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MenuTicker {
    private final static ScheduledExecutorService Executor = Executors.newScheduledThreadPool(3, f -> {Thread thread = new Thread(f, "Sunscreen/MenuTicker"); thread.setDaemon(true); return thread;});

    public ExecutingTickable start(Tickable tickable) {
        ExecutingTickable executingTickable = new ExecutingTickable(tickable);
        Future<?> future = Executor.scheduleAtFixedRate(executingTickable::tick, 0L, 50L, TimeUnit.MILLISECONDS);
        executingTickable.future(future);
        return executingTickable;
    }



    public static class ExecutingTickable {
        private Future<?> future;
        private final Tickable tickable;
        private long lastTickTime;
        private long timeSinceLastTick;
        private long time;

        protected ExecutingTickable(Tickable tickable) {
            this.tickable = tickable;
            this.lastTickTime = System.currentTimeMillis();
            this.timeSinceLastTick = 0;
            this.time = 0;
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
