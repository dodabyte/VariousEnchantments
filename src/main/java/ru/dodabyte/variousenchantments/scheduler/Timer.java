package ru.dodabyte.variousenchantments.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Timer {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private int cooldown;
    private int currentTimer;

    public Timer(int cooldown) {
        this.cooldown = cooldown;
        this.currentTimer = 0;
    }

    public void start() {
        Runnable timer = new Runnable() {
            public void run() {
                currentTimer++;

                if (currentTimer > cooldown) {
                    scheduler.shutdown();
                }
            }
        };
        scheduler.scheduleAtFixedRate(timer, 0, 1, SECONDS);
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getCurrentTimer() {
        return currentTimer;
    }

    public ScheduledExecutorService getScheduler() { return scheduler; }
}
