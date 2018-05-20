package me.s4ne.alvin.algorithms;

import javafx.scene.canvas.GraphicsContext;

public abstract class Algorithm implements Runnable {

    private String name;
    private boolean step;
    final Object BREAKPOINT_MONITOR;

    private AlgorithmExecutor executor;


    Algorithm(String name) {
        this.name = name;
        BREAKPOINT_MONITOR = new Object();
        step = true;
    }

    @Override
    public void run() {
        execute();
        executor.handleTermination();
    }

    @Override
    public String toString() {
        return name;
    }

    abstract void init();
    abstract void execute();
    abstract void draw(GraphicsContext context);

    void breakpoint(String message) {
        if (executor != null) {
            executor.handleBreakpoint(message);
        }
        synchronized (BREAKPOINT_MONITOR) {
            try {
                BREAKPOINT_MONITOR.wait();
            } catch (InterruptedException ex) {
                /* ignore */
            }
        }
    }

    int getProblemSize() {
        if (executor != null) {
            return executor.getProblemSize();
        } else {
            return 0;
        }
    }

    boolean shouldStop() {
        return !step;
    }

    void setStep(boolean step) {
        this.step = step;
    }

    void setExecutor(AlgorithmExecutor executor) {
        this.executor = executor;
    }
}
