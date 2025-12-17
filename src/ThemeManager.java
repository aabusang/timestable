import javafx.scene.paint.Color;

/**
 * Manages light and dark themes for the application.
 * Provides color schemes and styling for UI components.
 * 
 * @author Adam Abusang
 */
public class ThemeManager {

    public enum Theme {
        LIGHT, DARK
    }

    private Theme currentTheme = Theme.LIGHT;

    // Light theme colors
    private static final String LIGHT_BG = "#f5f5f5";
    private static final String LIGHT_PANEL_BG = "#ffffff";
    private static final String LIGHT_TEXT = "#333333";
    private static final String LIGHT_BORDER = "#cccccc";

    // Dark theme colors
    private static final String DARK_BG = "#1e1e1e";
    private static final String DARK_PANEL_BG = "#2d2d2d";
    private static final String DARK_TEXT = "#e0e0e0";
    private static final String DARK_BORDER = "#404040";

    // Accent colors (same for both themes)
    private static final String ACCENT_PRIMARY = "#3498db";
    private static final String ACCENT_SUCCESS = "#2ecc71";
    private static final String ACCENT_DANGER = "#e74c3c";
    private static final String ACCENT_WARNING = "#f39c12";

    /**
     * Toggle between light and dark themes.
     */
    public void toggleTheme() {
        currentTheme = (currentTheme == Theme.LIGHT) ? Theme.DARK : Theme.LIGHT;
    }

    /**
     * Get current theme.
     * 
     * @return current theme
     */
    public Theme getCurrentTheme() {
        return currentTheme;
    }

    /**
     * Set specific theme.
     * 
     * @param theme theme to set
     */
    public void setTheme(Theme theme) {
        this.currentTheme = theme;
    }

    /**
     * Get background color for main canvas.
     * 
     * @return background color
     */
    public String getBackgroundColor() {
        return (currentTheme == Theme.LIGHT) ? LIGHT_BG : DARK_BG;
    }

    /**
     * Get background color for control panels.
     * 
     * @return panel background color
     */
    public String getPanelBackgroundColor() {
        return (currentTheme == Theme.LIGHT) ? LIGHT_PANEL_BG : DARK_PANEL_BG;
    }

    /**
     * Get text color.
     * 
     * @return text color
     */
    public String getTextColor() {
        return (currentTheme == Theme.LIGHT) ? LIGHT_TEXT : DARK_TEXT;
    }

    /**
     * Get border color.
     * 
     * @return border color
     */
    public String getBorderColor() {
        return (currentTheme == Theme.LIGHT) ? LIGHT_BORDER : DARK_BORDER;
    }

    /**
     * Get circle stroke color.
     * 
     * @return JavaFX Color for circle
     */
    public Color getCircleColor() {
        return (currentTheme == Theme.LIGHT) ? Color.BLACK : Color.web("#888888");
    }

    /**
     * Get style for control panel.
     * 
     * @return CSS style string
     */
    public String getControlPanelStyle() {
        return String.format(
                "-fx-background-color: %s; -fx-padding: 20; -fx-spacing: 15; " +
                        "-fx-border-color: %s; -fx-border-width: 0 0 0 1;",
                getPanelBackgroundColor(), getBorderColor());
    }

    /**
     * Get style for labels.
     * 
     * @return CSS style string
     */
    public String getLabelStyle() {
        return String.format("-fx-text-fill: %s; -fx-font-size: 14px;", getTextColor());
    }

    /**
     * Get style for title labels.
     * 
     * @return CSS style string
     */
    public String getTitleStyle() {
        return String.format(
                "-fx-text-fill: %s; -fx-font-size: 18px; -fx-font-weight: bold;",
                getTextColor());
    }

    /**
     * Get style for buttons.
     * 
     * @param type button type: "primary", "success", "danger", "default"
     * @return CSS style string
     */
    public String getButtonStyle(String type) {
        String bgColor;
        switch (type.toLowerCase()) {
            case "success":
                bgColor = ACCENT_SUCCESS;
                break;
            case "danger":
                bgColor = ACCENT_DANGER;
                break;
            case "warning":
                bgColor = ACCENT_WARNING;
                break;
            case "primary":
                bgColor = ACCENT_PRIMARY;
                break;
            default:
                bgColor = (currentTheme == Theme.LIGHT) ? "#e0e0e0" : "#404040";
                return String.format(
                        "-fx-background-color: %s; -fx-text-fill: %s; " +
                                "-fx-padding: 8 16; -fx-cursor: hand; -fx-background-radius: 4;",
                        bgColor, getTextColor());
        }
        return String.format(
                "-fx-background-color: %s; -fx-text-fill: white; " +
                        "-fx-padding: 8 16; -fx-cursor: hand; -fx-background-radius: 4;",
                bgColor);
    }

    /**
     * Get hover style for buttons.
     * 
     * @param type button type
     * @return CSS style string
     */
    public String getButtonHoverStyle(String type) {
        String bgColor;
        switch (type.toLowerCase()) {
            case "success":
                bgColor = "#27ae60";
                break;
            case "danger":
                bgColor = "#c0392b";
                break;
            case "warning":
                bgColor = "#e67e22";
                break;
            case "primary":
                bgColor = "#2980b9";
                break;
            default:
                bgColor = (currentTheme == Theme.LIGHT) ? "#d0d0d0" : "#505050";
                return String.format(
                        "-fx-background-color: %s; -fx-text-fill: %s; " +
                                "-fx-padding: 8 16; -fx-cursor: hand; -fx-background-radius: 4;",
                        bgColor, getTextColor());
        }
        return String.format(
                "-fx-background-color: %s; -fx-text-fill: white; " +
                        "-fx-padding: 8 16; -fx-cursor: hand; -fx-background-radius: 4;",
                bgColor);
    }
}
