import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.stage.Stage;

import java.text.DecimalFormat;

/**
 * Main application class for the Modulo Times Table Visualization.
 * <p>
 * This application provides a visualization of the modulo times table,
 * allowing users to control various parameters of the visualization.
 * </p>
 * @author Adam Abusang
 *
 */
public class Main extends Application {

    /**
     * Entry point for the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static final double WIDTH = 1800;
    public static final double HEIGHT = 900;
    public static final double OFFSET_W = WIDTH / 2;
    public static final double OFFSET_H = HEIGHT / 2;
    public static final int RADIUS = 250;

    /**
     * Starts the JavaFX application, setting up the stage, UI components,
     * and visualization.
     *
     * @param primaryStage the primary stage for the application
     * @throws Exception if there's an issue during startup
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

//        Set Up The Window
        primaryStage.setTitle("Project 1: Modulo Times Table Visualization");

//        Initial times table numbers ittn
        double ittn = 2;
//        Circle that surrounds the line
        final Circle circle = new Circle(WIDTH / 2, HEIGHT / 2, RADIUS);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);

//        Space between control button
        double spacing = 10;

//        Vertical box to hold controls
        VBox controls = new VBox(spacing);
        controls.setLayoutX(10);
        controls.setLayoutY(100);

//        Run and Stop Buttons
        Button runBtn = new Button("Start");
        Button stopBtn = new Button("Stop");
        HBox SRBTNBox = new HBox(spacing*2);
        SRBTNBox.getChildren().addAll(runBtn, stopBtn);

//        Times table number display and corresponding HBox
        HBox ttnBox = new HBox(spacing);
        Label ttnLabel = new Label("Currently at Times Table Number: ");
        DecimalFormat dfTTN = new DecimalFormat("#.0");
        Label ttnValueLabel = new Label(Double.toString(ittn));
        ttnBox.getChildren().addAll(ttnLabel, ttnValueLabel);

//        Increment sliding and corresponding HBox
        HBox stepNumBox = new HBox(spacing);
        Label stepNumLabel = new Label("Increment By: ");
        Slider stepNumSlider = new Slider(0, 5, 1);
        stepNumSlider.setShowTickLabels(true);
        stepNumSlider.setShowTickMarks(true);
        stepNumSlider.setMajorTickUnit(0.25f);
        stepNumSlider.setBlockIncrement(0.1f);
        stepNumBox.getChildren().addAll(stepNumLabel, stepNumSlider);


//        Delay Slider and corresponding HBOX
        HBox delayBox = new HBox(spacing);
        Label delayLabel = new Label("Delay By (seconds): ");
        Slider delaySlider = new Slider(0, 5, 0.5);
        delaySlider.setShowTickLabels(true);
        delaySlider.setShowTickMarks(true);
        delaySlider.setMajorTickUnit(0.25f);
        delaySlider.setBlockIncrement(0.1f);
        delayBox.getChildren().addAll(delayLabel, delaySlider);

//        Jump to specific parameters
        Label jumpToLabel = new Label("Jump to specific TTN and Points: ");

//        Times Table Number to jump to corresponding HBbox
        HBox ttnJBox = new HBox(spacing);
        Label ttnJLabel = new Label("Times Table Number: ");
        TextField ttnJTF = new TextField("2");
        ttnJTF.setTextFormatter(DecimalTextVerifier.getFormatter());
        ttnJBox.getChildren().addAll(ttnJLabel, ttnJTF);


//        Number of points around the circle input box and corresponding HBox
        HBox numPointsBox = new HBox(spacing);
        Label numPointsLabel = new Label("Number of Points: ");
        TextField numPointsTF = new TextField("360");
        numPointsTF.setTextFormatter(DecimalTextVerifier.getFormatter());
        numPointsBox.getChildren().addAll(numPointsLabel, numPointsTF);

//        Colors
        ComboBox<String> colorComboBox = new ComboBox<>();
        colorComboBox.getItems().addAll("Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Cyan", "Magenta", "Brown", "Pink");
//        Color color = new Color((double)r, 0.8, 0.5, 1);
        colorComboBox.setValue("Red"); // default value

        HBox colorBox = new HBox(spacing);
        Label colorLabel = new Label("Choose Color: ");
        colorBox.getChildren().addAll(colorLabel, colorComboBox);


        Button jumpToBtn = new Button("Jump");

        controls.getChildren().addAll(
                ttnBox, stepNumBox, delayBox, SRBTNBox,
                jumpToLabel, ttnJBox, numPointsBox, jumpToBtn
        );
        controls.getChildren().add(colorBox);



//        Styles
        runBtn.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white;");
        stopBtn.setStyle("-fx-background-color: #FF3805; -fx-text-fill: white;");

        runBtn.setOnMouseEntered(e -> runBtn.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white;"));
        runBtn.setOnMouseExited(e -> stopBtn.setStyle("-fx-background-color: #FF3805; -fx-text-fill: white;"));

        ttnBox.setPadding(new Insets(30, 30, 30, 0));
        SRBTNBox.setPadding(new Insets(0, 0, 32, 0));

        controls.setPadding(new Insets(15, 12, 15, 12));
        controls.setStyle("-fx-font-size: 24px;");


//        Create Canvas
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        Pane root = new Pane(canvas, circle, controls);
        root.setStyle("-fx-background-color:  #eee");

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();


        Visualization visualization = new Visualization(ittn, RADIUS);


        /**
         * Inner class that handles the animation and updating of the times table visualization.
         * <p>
         * This animation timer continually updates the visualization based on the
         * current times table number, delay, and other parameters.
         * </p>
         */
        class TTAnimationTime extends AnimationTimer {

            private long lastUpdate = 0;

            /**
             * Updates the visualization.
             * @param jumpTo boolean indicating if the animation should jump to a specific times table number
             */
            public void run(boolean jumpTo) {

//              remove old lines
                root.getChildren().removeIf(node -> node instanceof Group);
//              Generate new lines
                Color selectedColor;
                switch(colorComboBox.getValue()) {
                    case "Green":
                        selectedColor = Color.GREEN;
                        break;
                    case "Blue":
                        selectedColor = Color.BLUE;
                        break;
                    case "Yellow":
                        selectedColor = Color.YELLOW;
                        break;
                    case "Orange":
                        selectedColor = Color.ORANGE;
                        break;
                    case "Purple":
                        selectedColor = Color.PURPLE;
                        break;
                    case "Cyan":
                        selectedColor = Color.CYAN;
                        break;
                    case "Magenta":
                        selectedColor = Color.MAGENTA;
                        break;
                    case "Brown":
                        selectedColor = Color.BROWN;
                        break;
                    case "Pink":
                        selectedColor = Color.PINK;
                        break;

                    default:
                        selectedColor = Color.RED;
                }

                Group lines = visualization.generateLines(
                        Double.parseDouble(numPointsTF.getText()),
                        selectedColor);

//                Add new lines
                root.getChildren().add(lines);
//                Update controls
                ttnValueLabel.setText(dfTTN.format(visualization.getTimesTableNumber()));

                if (!jumpTo) {
//                        increment the current times table numbers (ttn)
                    visualization.incrementTTN(stepNumSlider.getValue());
                }

            }

            /**
             * Handles the animation update.
             * @param current current timestamp of the animation frame in nanoseconds
             */
            @Override
            public void handle(long current) {
                if (current - lastUpdate >= delaySlider.getValue() * 1_000_000_000) {
                    if (!numPointsTF.getText().equals("")) {
                        run(false);
                        lastUpdate = current;

                    }
                }
            }

        }

        TTAnimationTime timer = new TTAnimationTime();
        runBtn.setOnAction(e -> {timer.start();});
        stopBtn.setOnAction(e -> {timer.stop();});

        jumpToBtn.setOnAction(event -> {
            timer.stop();
            visualization.setTimesTableNum(Double.parseDouble(ttnJTF.getText()));
            timer.run(true);
        });



    }

}
