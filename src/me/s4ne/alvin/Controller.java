package me.s4ne.alvin;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import me.s4ne.alvin.algorithms.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, BreakpointListener {

    @FXML
    private Canvas canvas;
    @FXML
    private ComboBox<Algorithm> comboBox;
    @FXML
    private Button stepButton;
    @FXML
    private TextArea textArea;
    @FXML
    private Spinner<Integer> spinner;

    private GraphicsContext context;

    private AlgorithmExecutor executor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        context = canvas.getGraphicsContext2D();

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 20);
        spinner.setValueFactory(valueFactory);
        spinner.valueProperty().addListener(observable -> onSpinnerChanged());

        ObservableList<Algorithm> algorithms = FXCollections.observableArrayList(
                new QuickSortAlgorithm(),
                new BubbleSortAlgorithm()
        );
        comboBox.setItems(algorithms);
        comboBox.setValue(algorithms.get(0));

        executor = new AlgorithmExecutor(comboBox.getValue(), this, 20);
        draw();
    }

    public void onComboBoxChanged() {
        executor.setAlgorithm(comboBox.getValue());
        resetGUI();
    }

    public void onButtonStep() {
        stepButton.setDisable(true);
        executor.step();
    }

    public void onButtonReset() {
        executor.reset();
        resetGUI();
    }

    @Override
    public void onBreakpointEntered(String message) {
        Platform.runLater(() -> {
            textArea.appendText(message);
            draw();
            stepButton.setDisable(false);
        });
    }

    @Override
    public void onTerminated() {
        Platform.runLater(this::draw);
    }

    void onClose() {
        executor.stopExecution();
    }

    private void onSpinnerChanged() {
        executor.setProblemSize(spinner.getValue());
        resetGUI();
    }

    private void draw() {
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        executor.draw(context);
    }

    private void resetGUI() {
        draw();
        textArea.clear();
        stepButton.setDisable(false);
    }
}
