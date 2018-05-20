package me.s4ne.alvin.algorithms;

import javafx.scene.canvas.GraphicsContext;

public class AlgorithmExecutor {

    private Thread executorThread;
    private int problemSize;

    private Algorithm algorithm;
    private BreakpointListener listener;

    public AlgorithmExecutor(Algorithm algorithm, BreakpointListener listener, int problemSize) {
        this.algorithm = algorithm;
        this.listener = listener;
        this.problemSize = problemSize;
        reset();
    }

    public void step() {
        if (executorThread != null && !executorThread.isAlive() && executorThread.getState() == Thread.State.NEW) {
            executorThread.start();
        } else {
            notifyBreakpointMonitor();
        }
    }

    public void draw(GraphicsContext context) {
        algorithm.draw(context);

    }

    public void reset() {
        stopExecution();

        algorithm.setExecutor(this);
        algorithm.setStep(true);
        algorithm.init();
        executorThread = new Thread(algorithm);
    }

    public void stopExecution() {
        if (executorThread != null && executorThread.isAlive()) {
            algorithm.setStep(false);
            notifyBreakpointMonitor();
            try {
                executorThread.join();
            } catch (InterruptedException e) {
                /* ignore */
            }
        }
    }

    public void setAlgorithm(Algorithm algorithm) {
        stopExecution();
        this.algorithm = algorithm;
        reset();
    }

    public void setProblemSize(int size) {
        stopExecution();
        this.problemSize = size;
        reset();
    }

    int getProblemSize() {
        return problemSize;
    }

    void handleBreakpoint(String message) {
        listener.onBreakpointEntered(message);
    }
    void handleTermination() {
        listener.onTerminated();
    }

    private void notifyBreakpointMonitor() {
        synchronized (algorithm.BREAKPOINT_MONITOR) {
            algorithm.BREAKPOINT_MONITOR.notify();
        }
    }
}
