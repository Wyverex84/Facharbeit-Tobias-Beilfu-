package com.github.rccookie.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Time {
    public static double MAX_DELTA_TIME = 0.08;
    public static double AVERAGE_DELTA_TIME = 0.01;
    long lastNanos;
    double deltaTime = AVERAGE_DELTA_TIME;
    public double timeScale = 1;
    public boolean useStaticFramelength = false;
    public double staticFramelength = 0.001;
    public double maxDeltaTime = MAX_DELTA_TIME;

    private double time = 0;
    long frameIndex = 0;

    long lastFpsUpdateNanos = 0;
    int frameCount;
    int stableFps;

    private final List<RepeatingTask> repeatingTasks = new ArrayList<>();
    private final List<DelayedTask> delayedTasks = new ArrayList<>();


    public Time() {
        lastFpsUpdateNanos = System.nanoTime();
    }

    public void update() {
        long currentNanos = System.nanoTime();
        deltaTime = (currentNanos - lastNanos) / 1000000000d;
        lastNanos = currentNanos;
        deltaTime %= 1;


        frameCount++;
        if(currentNanos - lastFpsUpdateNanos >= 1000000000l) {
            lastFpsUpdateNanos += 1000000000l;
            stableFps = frameCount;
            frameCount = 0;
        }
        frameIndex++;
        time += deltaTime();

        runTasks();
    }

    private void runTasks() {
        for(DelayedTask task : delayedTasks.toArray(new DelayedTask[0])) {
            if(task.executionTime <= time()) {
                task.execute();
                delayedTasks.remove(task);
            }
        }
        for(RepeatingTask task : repeatingTasks.toArray(new RepeatingTask[0])) {
            if(task.nextExecutionTime <= time()) {
                task.execute();
            }
        }
    }

    /**
     * Fraction of time since the last frame
     */
    public double deltaTime() {
        if(useStaticFramelength) return staticFramelength;
        return Math.min(maxDeltaTime, deltaTime) * timeScale;
    }

    public void setTimeScale(double scale) {
        timeScale = scale;
    }

    /**
     * Returns the time that has passed since the initialization, in seconds. The time
     * may be incorrect to the real time if the update method was not called often enough,
     * but it will be exactly the sum of all returned deltaTimes.
     * 
     * @return The current time
     */
    public double time() {
        return time;
    }

    /**
     * Updated once per frame
     */
    public int fps() {
        if(deltaTime == 0) return 2000;
        return (int)(1 / deltaTime);
    }

    /**
     * Updated once per second
     */
    public int stableFps() {
        return stableFps;
    }

    public long frameIndex() {
        return frameIndex;
    }

    public void resetFrameIndex() {
        frameIndex = 0;
    }



    public void repeat(Runnable task, double rate, double initialDelay) {
        repeatingTasks.add(new RepeatingTask(task, rate, initialDelay));
    }

    public void repeat(Runnable task, double rate) {
        repeat(task, rate, 0);
    }

    public void delay(Runnable task, double delay) {
        delayedTasks.add(new DelayedTask(task, delay));
    }



    private class RepeatingTask {

        private final Runnable task;
        private final double rate;
        private double nextExecutionTime;

        public RepeatingTask(Runnable task, double rate, double initialDelay) {
            this.task = Objects.requireNonNull(task);
            if(rate < 0) throw new IllegalArgumentException("The delay must be a positive number");
            this.rate = rate;
            if(initialDelay < 0) throw new IllegalArgumentException("The initial delay must be a positive number");
            nextExecutionTime = time() + initialDelay;
        }

        public void execute() {
            task.run();
            nextExecutionTime += rate;
        }
    }

    private class DelayedTask {

        private final Runnable task;
        private final double executionTime;

        public DelayedTask(Runnable task, double delay) {
            this.task = task;
            if(delay < 0) throw new IllegalArgumentException("The delay must be a positive number");
            executionTime = time() + delay;
        }

        public void execute() {
            task.run();
        }
    }
}
