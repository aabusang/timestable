import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Main application class for the Modulo Times Table Visualization.
 * Enhanced with modern UI/UX, preset patterns, keyboard shortcuts, and more.
 * 
 * @author Adam Abusang
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static final double WIDTH = 1400;
    public static final double HEIGHT = 900;
    public static final double OFFSET_W = WIDTH / 2;
    public static final double OFFSET_H = HEIGHT / 2;
    public static final int RADIUS = 300;

    // Animation state
    private enum AnimationState {
        STOPPED, RUNNING, PAUSED
    }

    private AnimationState animationState = AnimationState.STOPPED;

    // Theme manager
    private ThemeManager themeManager = new ThemeManager();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Modulo Times Table Visualization");

        // Initial values
        double ittn = 2;
        final Circle circle = new Circle(WIDTH / 2, HEIGHT / 2, RADIUS);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(themeManager.getCircleColor());
        circle.setStrokeWidth(2);

        // Main layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + themeManager.getBackgroundColor());

        // Canvas for visualization
        Pane canvasPane = new Pane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        canvasPane.getChildren().addAll(canvas, circle);
        root.setCenter(canvasPane);

        // Control panel (right sidebar) - wrapped in ScrollPane
        VBox controlPanel = createControlPanel();
        ScrollPane scrollPane = new ScrollPane(controlPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: " + themeManager.getPanelBackgroundColor() + ";");
        root.setRight(scrollPane);

        // Create scene
        Scene scene = new Scene(root, WIDTH + 350, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Visualization
        Visualization visualization = new Visualization(ittn, RADIUS);

        // UI Components (need to be accessible in event handlers)
        Label ttnValueLabel = new Label(String.format("%.1f", ittn));
        ttnValueLabel.setStyle(themeManager.getTitleStyle());

        Label patternNameLabel = new Label("Cardioid");
        patternNameLabel.setStyle(themeManager.getLabelStyle() + " -fx-font-style: italic;");

        Label formulaLabel = new Label("n × 2.0 mod 360");
        formulaLabel.setStyle(themeManager.getLabelStyle() + " -fx-font-family: monospace;");

        Label statusLabel = new Label("● Stopped");
        statusLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px; -fx-font-weight: bold;");

        // Sliders with value labels
        Slider stepNumSlider = new Slider(0.01, 5, 0.1);
        Label stepValueLabel = new Label("0.1");

        Slider delaySlider = new Slider(0, 2, 0.1);
        Label delayValueLabel = new Label("0.1s");

        TextField ttnJTF = new TextField("2");
        ttnJTF.setTextFormatter(DecimalTextVerifier.getFormatter());
        ttnJTF.setPrefWidth(100);

        TextField numPointsTF = new TextField("360");
        numPointsTF.setTextFormatter(DecimalTextVerifier.getFormatter());
        numPointsTF.setPrefWidth(100);

        ColorPicker colorPicker = new ColorPicker(Color.RED);
        colorPicker.setPrefWidth(150);

        CheckBox showCircleCheckBox = new CheckBox("Show Circle");
        showCircleCheckBox.setSelected(true);
        showCircleCheckBox.setStyle(themeManager.getLabelStyle());

        // Buttons
        Button playBtn = new Button("▶ Play");
        Button pauseBtn = new Button("⏸ Pause");
        Button stopBtn = new Button("⏹ Stop");
        Button resetBtn = new Button("↻ Reset");
        Button saveImageBtn = new Button("💾 Save Image");
        Button darkModeBtn = new Button("🌙 Dark Mode");
        Button jumpToBtn = new Button("Jump");

        // Apply button styles
        applyButtonStyle(playBtn, "success");
        applyButtonStyle(pauseBtn, "warning");
        applyButtonStyle(stopBtn, "danger");
        applyButtonStyle(resetBtn, "default");
        applyButtonStyle(saveImageBtn, "primary");
        applyButtonStyle(darkModeBtn, "default");
        applyButtonStyle(jumpToBtn, "primary");

        // Preset buttons
        VBox presetsBox = new VBox(8);
        Label presetsTitle = new Label("Preset Patterns");
        presetsTitle.setStyle(themeManager.getTitleStyle());
        presetsBox.getChildren().add(presetsTitle);

        for (PresetPatterns.Pattern preset : PresetPatterns.getAllPresets()) {
            Button presetBtn = new Button(preset.getName());
            presetBtn.setPrefWidth(200);
            applyButtonStyle(presetBtn, "default");
            Tooltip.install(presetBtn, new Tooltip(preset.getDescription()));

            presetBtn.setOnAction(e -> {
                visualization.setTimesTableNum(preset.getTimesTableNumber());
                numPointsTF.setText(String.valueOf(preset.getNumPoints()));
                ttnJTF.setText(String.format("%.1f", preset.getTimesTableNumber()));
                updateVisualization(canvasPane, visualization, numPointsTF, colorPicker,
                        ttnValueLabel, patternNameLabel, formulaLabel, true);
            });

            presetsBox.getChildren().add(presetBtn);
        }

        // Build control panel
        controlPanel.getChildren().clear();

        // Title section
        VBox titleBox = new VBox(5);
        Label title = new Label("Controls");
        title.setStyle(themeManager.getTitleStyle() + " -fx-font-size: 22px;");
        titleBox.getChildren().addAll(title, new Separator(), statusLabel);

        // Current TTN display
        VBox ttnDisplayBox = new VBox(5);
        Label ttnLabel = new Label("Times Table Number:");
        ttnLabel.setStyle(themeManager.getLabelStyle());
        HBox ttnValueBox = new HBox(10);
        ttnValueBox.setAlignment(Pos.CENTER_LEFT);
        ttnValueBox.getChildren().addAll(ttnValueLabel, patternNameLabel);
        ttnDisplayBox.getChildren().addAll(ttnLabel, ttnValueBox, formulaLabel);

        // Playback controls
        VBox playbackBox = new VBox(8);
        Label playbackLabel = new Label("Playback");
        playbackLabel.setStyle(themeManager.getTitleStyle());
        HBox buttonRow1 = new HBox(8);
        buttonRow1.getChildren().addAll(playBtn, pauseBtn);
        HBox buttonRow2 = new HBox(8);
        buttonRow2.getChildren().addAll(stopBtn, resetBtn);
        playbackBox.getChildren().addAll(playbackLabel, buttonRow1, buttonRow2);

        // Speed controls
        VBox speedBox = new VBox(8);
        Label speedLabel = new Label("Animation Speed");
        speedLabel.setStyle(themeManager.getTitleStyle());

        HBox stepBox = new HBox(10);
        stepBox.setAlignment(Pos.CENTER_LEFT);
        Label stepLabel = new Label("Increment:");
        stepLabel.setStyle(themeManager.getLabelStyle());
        stepNumSlider.setPrefWidth(120);
        stepValueLabel.setStyle(themeManager.getLabelStyle());
        stepBox.getChildren().addAll(stepLabel, stepNumSlider, stepValueLabel);

        HBox delayBox = new HBox(10);
        delayBox.setAlignment(Pos.CENTER_LEFT);
        Label delayLabel = new Label("Delay:");
        delayLabel.setStyle(themeManager.getLabelStyle());
        delaySlider.setPrefWidth(120);
        delayValueLabel.setStyle(themeManager.getLabelStyle());
        delayBox.getChildren().addAll(delayLabel, delaySlider, delayValueLabel);

        speedBox.getChildren().addAll(speedLabel, stepBox, delayBox);

        // Jump controls
        VBox jumpBox = new VBox(8);
        Label jumpLabel = new Label("Jump To");
        jumpLabel.setStyle(themeManager.getTitleStyle());

        HBox ttnJumpBox = new HBox(8);
        ttnJumpBox.setAlignment(Pos.CENTER_LEFT);
        Label ttnJLabel = new Label("TTN:");
        ttnJLabel.setStyle(themeManager.getLabelStyle());
        ttnJumpBox.getChildren().addAll(ttnJLabel, ttnJTF);

        HBox pointsJumpBox = new HBox(8);
        pointsJumpBox.setAlignment(Pos.CENTER_LEFT);
        Label pointsJLabel = new Label("Points:");
        pointsJLabel.setStyle(themeManager.getLabelStyle());
        pointsJumpBox.getChildren().addAll(pointsJLabel, numPointsTF);

        jumpBox.getChildren().addAll(jumpLabel, ttnJumpBox, pointsJumpBox, jumpToBtn);

        // Appearance controls
        VBox appearanceBox = new VBox(8);
        Label appearanceLabel = new Label("Appearance");
        appearanceLabel.setStyle(themeManager.getTitleStyle());

        HBox colorBox = new HBox(8);
        colorBox.setAlignment(Pos.CENTER_LEFT);
        Label colorLabel = new Label("Line Color:");
        colorLabel.setStyle(themeManager.getLabelStyle());
        colorBox.getChildren().addAll(colorLabel, colorPicker);

        appearanceBox.getChildren().addAll(appearanceLabel, colorBox, showCircleCheckBox, darkModeBtn);

        // Actions
        VBox actionsBox = new VBox(8);
        Label actionsLabel = new Label("Actions");
        actionsLabel.setStyle(themeManager.getTitleStyle());
        actionsBox.getChildren().addAll(actionsLabel, saveImageBtn);

        // Add all sections to control panel
        controlPanel.getChildren().addAll(
                titleBox,
                new Separator(),
                ttnDisplayBox,
                new Separator(),
                playbackBox,
                new Separator(),
                speedBox,
                new Separator(),
                jumpBox,
                new Separator(),
                appearanceBox,
                new Separator(),
                presetsBox,
                new Separator(),
                actionsBox);

        // Slider value updates
        stepNumSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            stepValueLabel.setText(String.format("%.2f", newVal.doubleValue()));
        });

        delaySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            delayValueLabel.setText(String.format("%.2fs", newVal.doubleValue()));
        });

        // Color picker updates
        colorPicker.setOnAction(e -> {
            if (animationState == AnimationState.STOPPED || animationState == AnimationState.PAUSED) {
                updateVisualization(canvasPane, visualization, numPointsTF, colorPicker,
                        ttnValueLabel, patternNameLabel, formulaLabel, true);
            }
        });

        // Show/hide circle
        showCircleCheckBox.setOnAction(e -> {
            circle.setVisible(showCircleCheckBox.isSelected());
        });

        // Animation Timer
        class TTAnimationTimer extends AnimationTimer {
            private long lastUpdate = 0;

            public void run(boolean jumpTo) {
                updateVisualization(canvasPane, visualization, numPointsTF, colorPicker,
                        ttnValueLabel, patternNameLabel, formulaLabel, jumpTo);

                if (!jumpTo) {
                    visualization.incrementTTN(stepNumSlider.getValue());
                }
            }

            @Override
            public void handle(long current) {
                if (animationState == AnimationState.RUNNING) {
                    long delay = (long) (delaySlider.getValue() * 1_000_000_000);
                    if (current - lastUpdate >= delay) {
                        if (!numPointsTF.getText().isEmpty()) {
                            run(false);
                            lastUpdate = current;
                        }
                    }
                }
            }
        }

        TTAnimationTimer timer = new TTAnimationTimer();

        // Button actions
        playBtn.setOnAction(e -> {
            animationState = AnimationState.RUNNING;
            timer.start();
            updateStatusLabel(statusLabel, animationState);
        });

        pauseBtn.setOnAction(e -> {
            animationState = AnimationState.PAUSED;
            updateStatusLabel(statusLabel, animationState);
        });

        stopBtn.setOnAction(e -> {
            animationState = AnimationState.STOPPED;
            timer.stop();
            updateStatusLabel(statusLabel, animationState);
        });

        resetBtn.setOnAction(e -> {
            animationState = AnimationState.STOPPED;
            timer.stop();
            visualization.setTimesTableNum(2.0);
            ttnJTF.setText("2");
            numPointsTF.setText("360");
            stepNumSlider.setValue(0.1);
            delaySlider.setValue(0.1);
            colorPicker.setValue(Color.RED);
            showCircleCheckBox.setSelected(true);
            updateVisualization(canvasPane, visualization, numPointsTF, colorPicker,
                    ttnValueLabel, patternNameLabel, formulaLabel, true);
            updateStatusLabel(statusLabel, animationState);
        });

        jumpToBtn.setOnAction(e -> {
            try {
                double newTTN = Double.parseDouble(ttnJTF.getText());
                visualization.setTimesTableNum(newTTN);
                timer.run(true);
            } catch (NumberFormatException ex) {
                showError("Invalid number format for Times Table Number");
            }
        });

        saveImageBtn.setOnAction(e -> {
            saveVisualizationImage(primaryStage, canvasPane);
        });

        darkModeBtn.setOnAction(e -> {
            themeManager.toggleTheme();
            applyTheme(root, controlPanel, circle, ttnValueLabel, patternNameLabel, formulaLabel,
                    ttnLabel, playbackLabel, speedLabel, jumpLabel, appearanceLabel, actionsLabel,
                    presetsTitle, stepLabel, delayLabel, ttnJLabel, pointsJLabel, colorLabel, title);
            showCircleCheckBox.setStyle(themeManager.getLabelStyle());
            scrollPane.setStyle("-fx-background: " + themeManager.getPanelBackgroundColor() + ";");
            darkModeBtn.setText(
                    themeManager.getCurrentTheme() == ThemeManager.Theme.LIGHT ? "🌙 Dark Mode" : "☀ Light Mode");
        });

        // Keyboard shortcuts
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                if (animationState == AnimationState.RUNNING) {
                    pauseBtn.fire();
                } else {
                    playBtn.fire();
                }
                e.consume();
            } else if (e.getCode() == KeyCode.R) {
                resetBtn.fire();
                e.consume();
            } else if (e.getCode() == KeyCode.S) {
                saveImageBtn.fire();
                e.consume();
            } else if (e.getCode() == KeyCode.H) {
                showCircleCheckBox.setSelected(!showCircleCheckBox.isSelected());
                circle.setVisible(showCircleCheckBox.isSelected());
                e.consume();
            } else if (e.getCode() == KeyCode.D) {
                darkModeBtn.fire();
                e.consume();
            } else if (e.getCode() == KeyCode.UP) {
                double newTTN = visualization.getTimesTableNumber() + 0.1;
                visualization.setTimesTableNum(newTTN);
                ttnJTF.setText(String.format("%.1f", newTTN));
                timer.run(true);
                e.consume();
            } else if (e.getCode() == KeyCode.DOWN) {
                double newTTN = Math.max(0, visualization.getTimesTableNumber() - 0.1);
                visualization.setTimesTableNum(newTTN);
                ttnJTF.setText(String.format("%.1f", newTTN));
                timer.run(true);
                e.consume();
            } else if (e.getCode() == KeyCode.LEFT) {
                double newTTN = Math.max(0, visualization.getTimesTableNumber() - 1.0);
                visualization.setTimesTableNum(newTTN);
                ttnJTF.setText(String.format("%.1f", newTTN));
                timer.run(true);
                e.consume();
            } else if (e.getCode() == KeyCode.RIGHT) {
                double newTTN = visualization.getTimesTableNumber() + 1.0;
                visualization.setTimesTableNum(newTTN);
                ttnJTF.setText(String.format("%.1f", newTTN));
                timer.run(true);
                e.consume();
            } else if (e.getCode().isDigitKey()) {
                int digit = Integer.parseInt(e.getText());
                PresetPatterns.Pattern[] presets = PresetPatterns.getAllPresets();
                // 0 = preset 10, 1-9 = presets 1-9
                int presetIndex = (digit == 0) ? 9 : digit - 1;
                if (presetIndex >= 0 && presetIndex < presets.length) {
                    PresetPatterns.Pattern preset = presets[presetIndex];
                    visualization.setTimesTableNum(preset.getTimesTableNumber());
                    numPointsTF.setText(String.valueOf(preset.getNumPoints()));
                    ttnJTF.setText(String.format("%.5f", preset.getTimesTableNumber()));
                    timer.run(true);
                    e.consume();
                }
            }
        });

        // Initial render
        timer.run(true);
    }

    private VBox createControlPanel() {
        VBox panel = new VBox(15);
        panel.setPrefWidth(350);
        panel.setStyle(themeManager.getControlPanelStyle());
        return panel;
    }

    private void updateVisualization(Pane canvasPane, Visualization visualization,
            TextField numPointsTF, ColorPicker colorPicker,
            Label ttnValueLabel, Label patternNameLabel,
            Label formulaLabel, boolean jumpTo) {
        // Remove old lines
        canvasPane.getChildren().removeIf(node -> node instanceof Group);

        // Generate new lines
        try {
            double numPoints = Double.parseDouble(numPointsTF.getText());
            Color selectedColor = colorPicker.getValue();
            Group lines = visualization.generateLines(numPoints, selectedColor);

            // Add fade-in effect
            FadeTransition fade = new FadeTransition(Duration.millis(200), lines);
            fade.setFromValue(0.0);
            fade.setToValue(1.0);

            canvasPane.getChildren().add(0, lines);
            fade.play();

            // Update labels
            DecimalFormat df = new DecimalFormat("#.0");
            ttnValueLabel.setText(df.format(visualization.getTimesTableNumber()));
            patternNameLabel.setText(PresetPatterns.identifyPattern(visualization.getTimesTableNumber()));
            formulaLabel.setText(String.format("n × %.1f mod %.0f",
                    visualization.getTimesTableNumber(), numPoints));
        } catch (NumberFormatException ex) {
            // Invalid input, skip update
        }
    }

    private void updateStatusLabel(Label statusLabel, AnimationState state) {
        switch (state) {
            case RUNNING:
                statusLabel.setText("● Running");
                statusLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-size: 14px; -fx-font-weight: bold;");
                break;
            case PAUSED:
                statusLabel.setText("● Paused");
                statusLabel.setStyle("-fx-text-fill: #f39c12; -fx-font-size: 14px; -fx-font-weight: bold;");
                break;
            case STOPPED:
                statusLabel.setText("● Stopped");
                statusLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px; -fx-font-weight: bold;");
                break;
        }
    }

    private void applyButtonStyle(Button button, String type) {
        button.setStyle(themeManager.getButtonStyle(type));
        button.setOnMouseEntered(e -> button.setStyle(themeManager.getButtonHoverStyle(type)));
        button.setOnMouseExited(e -> button.setStyle(themeManager.getButtonStyle(type)));
    }

    private void applyTheme(BorderPane root, VBox controlPanel, Circle circle, Label... labels) {
        root.setStyle("-fx-background-color: " + themeManager.getBackgroundColor());
        controlPanel.setStyle(themeManager.getControlPanelStyle());
        circle.setStroke(themeManager.getCircleColor());

        for (Label label : labels) {
            if (label.getStyle().contains("font-size: 22px")) {
                label.setStyle(themeManager.getTitleStyle() + " -fx-font-size: 22px;");
            } else if (label.getStyle().contains("font-size: 18px")) {
                label.setStyle(themeManager.getTitleStyle());
            } else if (label.getStyle().contains("italic")) {
                label.setStyle(themeManager.getLabelStyle() + " -fx-font-style: italic;");
            } else if (label.getStyle().contains("monospace")) {
                label.setStyle(themeManager.getLabelStyle() + " -fx-font-family: monospace;");
            } else {
                label.setStyle(themeManager.getLabelStyle());
            }
        }
    }

    private void saveVisualizationImage(Stage stage, Pane canvasPane) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Visualization");
        fileChooser.setInitialFileName("timestable_visualization.png");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Image", "*.png"));

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                WritableImage image = canvasPane.snapshot(null, null);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                showInfo("Image saved successfully to: " + file.getAbsolutePath());
            } catch (IOException e) {
                showError("Failed to save image: " + e.getMessage());
            }
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
