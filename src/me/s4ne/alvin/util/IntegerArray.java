package me.s4ne.alvin.util;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

public class IntegerArray {

    private int[] array;
    private Paint[] color;
    private double max;
    private boolean maxUpToDate;

    public IntegerArray(int size) {
        array = new int[size];
        color = new Paint[size];

        for (int i = 0; i < size; i++) {
            array[i] = 0;
            color[i] = Color.BLACK;
        }

        max = 1;
        maxUpToDate = true;
    }

    public void set(int pos, int value) {
        array[pos] = value;
        maxUpToDate = false;
    }

    public void draw(double xOffset, double yOffset, double width, double height, GraphicsContext gc) {
        if (!maxUpToDate) {
            calculateMax();
        }

        double barWidth = (width-(array.length))/array.length;
        double barMaxHeight = height - 16;
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.BOTTOM);

        for (int i = 0; i < array.length; i++) {
            double barHeight = barMaxHeight * (array[i]/max);
            double barX = xOffset + i*barWidth + i;
            double barY = yOffset + (barMaxHeight - barHeight);
            gc.setFill(color[i]);
            gc.fillRect(barX, barY, barWidth, barHeight);
            gc.setFill(Color.BLACK);
            gc.fillText(array[i] + "", barX + (barWidth/2), barY);
            gc.fillText(i + "", barX + (barWidth/2), yOffset + height);
        }
    }

    private void calculateMax() {
        int m = 1;

        for (int elem : array) {
            m = m < elem ? elem : m;
        }

        max = m;
        maxUpToDate = true;
    }

    public int size() {
        return array.length;
    }

    public int get(int i) {
        return array[i];
    }

    public void setPaint(int pos, Paint paint) {
        color[pos] = paint;
    }
}
