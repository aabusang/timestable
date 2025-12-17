/**
 * Defines preset patterns for the modulo times table visualization.
 * Contains famous mathematical patterns like Cardioid and Nephroid.
 * 
 * @author Adam Abusang
 */
public class PresetPatterns {

    /**
     * Represents a preset pattern configuration.
     */
    public static class Pattern {
        private final String name;
        private final double timesTableNumber;
        private final int numPoints;
        private final String description;

        public Pattern(String name, double timesTableNumber, int numPoints, String description) {
            this.name = name;
            this.timesTableNumber = timesTableNumber;
            this.numPoints = numPoints;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public double getTimesTableNumber() {
            return timesTableNumber;
        }

        public int getNumPoints() {
            return numPoints;
        }

        public String getDescription() {
            return description;
        }
    }

    // Famous preset patterns
    public static final Pattern CARDIOID = new Pattern(
            "Cardioid", 2.0, 360,
            "Heart-shaped curve formed by times table 2");

    public static final Pattern NEPHROID = new Pattern(
            "Nephroid", 3.0, 360,
            "Kidney-shaped curve formed by times table 3");

    public static final Pattern EPICYCLOID_4 = new Pattern(
            "Epicycloid 4", 4.0, 360,
            "3-cusped epicycloid pattern");

    public static final Pattern EPICYCLOID_5 = new Pattern(
            "Epicycloid 5", 5.0, 360,
            "4-cusped epicycloid pattern");

    public static final Pattern PATTERN_29 = new Pattern(
            "Pattern 29", 29.0, 360,
            "Beautiful dense star pattern");

    public static final Pattern PATTERN_34 = new Pattern(
            "Pattern 34", 34.0, 360,
            "Complex star pattern with intricate details");

    public static final Pattern PATTERN_51 = new Pattern(
            "Pattern 51", 51.0, 360,
            "Dense circular pattern with beautiful symmetry");

    public static final Pattern PATTERN_79 = new Pattern(
            "Pattern 79", 79.0, 360,
            "Nearly complete with subtle gaps");

    public static final Pattern PATTERN_99 = new Pattern(
            "Pattern 99", 99.0, 360,
            "Nearly complete circle with subtle variations");

    // Irrational number patterns
    public static final Pattern PI = new Pattern(
            "π (Pi)", Math.PI, 360,
            "Irrational pattern using π ≈ 3.14159...");

    public static final Pattern SQRT_2 = new Pattern(
            "√2", Math.sqrt(2), 360,
            "Irrational pattern using √2 ≈ 1.41421...");

    public static final Pattern GOLDEN_RATIO = new Pattern(
            "φ (Phi)", (1 + Math.sqrt(5)) / 2, 360,
            "Golden ratio φ ≈ 1.61803...");

    public static final Pattern EULER = new Pattern(
            "e (Euler)", Math.E, 360,
            "Euler's number e ≈ 2.71828...");

    public static final Pattern SQRT_3 = new Pattern(
            "√3", Math.sqrt(3), 360,
            "Irrational pattern using √3 ≈ 1.73205...");

    /**
     * Get all available preset patterns.
     * 
     * @return array of all preset patterns
     */
    public static Pattern[] getAllPresets() {
        return new Pattern[] {
                CARDIOID,
                NEPHROID,
                EPICYCLOID_4,
                EPICYCLOID_5,
                PATTERN_29,
                PATTERN_34,
                PATTERN_51,
                PATTERN_79,
                PATTERN_99,
                PI,
                SQRT_2,
                GOLDEN_RATIO,
                EULER,
                SQRT_3
        };
    }

    /**
     * Identify pattern name based on times table number.
     * 
     * @param ttn times table number
     * @return pattern name or "Custom Pattern" if not recognized
     */
    public static String identifyPattern(double ttn) {
        for (Pattern pattern : getAllPresets()) {
            if (Math.abs(pattern.getTimesTableNumber() - ttn) < 0.01) {
                return pattern.getName();
            }
        }
        return "Custom Pattern";
    }
}
