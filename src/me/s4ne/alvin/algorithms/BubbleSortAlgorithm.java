package me.s4ne.alvin.algorithms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import me.s4ne.alvin.util.IntegerArray;

public class BubbleSortAlgorithm extends Algorithm {

    private IntegerArray array;

    public BubbleSortAlgorithm() {
        super("BubbleSort");
    }

    @Override
    void init() {
        array = new IntegerArray(getProblemSize());
        for (int i = 0; i < getProblemSize(); i++) {
            array.set(i, (int) (Math.random() * 100) + 1);
        }
    }

    @Override
    public void execute() {
        for (int i = 1; i < array.size(); i++) {
            array.setPaint(array.size() - i, Color.DARKCYAN);
            for (int j = 0; j < array.size() - i; j++) {
                array.setPaint(j, Color.RED);
                array.setPaint(j+1, Color.RED);
                if (array.get(j) > array.get(j+1)) {
                    breakpoint("array[" + j + "] > array[" + (j+1) + "]. Swap.\n");
                    int temp = array.get(j);
                    array.set(j, array.get(j+1));
                    array.set(j+1, temp);
                } else {
                    breakpoint("array[" + j + "] <= array[" + (j+1) + "]. Don't swap.\n");
                }
                if (shouldStop()) {
                    break;
                }

                array.setPaint(j, Color.BLACK);
                array.setPaint(j, Color.BLACK);
            }
            if (shouldStop()) {
                break;
            }
            array.setPaint(array.size() - i, Color.BLACK);
        }
    }

    @Override
    public void draw(GraphicsContext context) {
        array.draw(64, 64, 800-128, 600-128, context);
    }
}
