package me.s4ne.alvin.algorithms;

public interface BreakpointListener {

    void onBreakpointEntered(String logMessage);
    void onTerminated();
}
