package me.s4ne.alvin.algorithms;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import me.s4ne.alvin.util.IntegerArray;

public class QuickSortAlgorithm extends Algorithm {

    private IntegerArray array;

    public QuickSortAlgorithm() {
        super("QuickSort");
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
        quickSort(0, array.size()-1);
    }

    private void quickSort(int l, int r) {
        if (shouldStop()) return;

        if (r > l) {
            int k = array.get(r);

            array.setPaint(r, Color.DARKCYAN);

            int i = l;
            int j = r - 1;

            breakpoint("Sorting sub-array from " + l + " to " + r + ". (k=" + k + ")\n");
            if (shouldStop()) return;

            while (true) {
                array.setPaint(i, Color.RED);
                breakpoint("Looking for element > " + k + " left to right.\n");
                if (shouldStop()) return;
                while (i < r && array.get(i) <= k) {
                    array.setPaint(i, Color.BLACK);
                    array.setPaint(i+1, Color.RED);
                    breakpoint("");
                    if (shouldStop()) return;
                    i++;
                }
                array.setPaint(j, Color.RED);
                breakpoint("Looking for element < " + k + " right to left.\n");
                if (shouldStop()) return;
                while (j > i && array.get(j) >= k) {
                    array.setPaint(j, Color.BLACK);
                    array.setPaint(j-1, Color.RED);
                    breakpoint("");
                    if (shouldStop()) return;
                    j--;
                }
                if (i >= j) {
                    break;
                }
                breakpoint("Swapping " + i + " and " + j + ".\n");
                if (shouldStop()) return;
                swap(i, j);
            }
            breakpoint("Swapping " + i + " and " + r + ".\n");
            if (shouldStop()) return;
            swap(i, r);

            array.setPaint(r, Color.BLACK);
            array.setPaint(j, Color.BLACK);

            quickSort(l, i-1);
            quickSort(i+1, r);
        }
    }

    private void swap(int i, int j) {
        int temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j, temp);
    }

    @Override
    public void draw(GraphicsContext context) {
        array.draw(64, 64, 800-128, 600-128, context);
    }
}
