import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
/**
 * Represents a visualization based on times tables using circular geometry.
 * The class generates a series of lines based on a given times table number and a circle of a given radius.
 * <p>
 * The visualization works by taking a circle with a specified number of points on its circumference,
 * and drawing lines between the points based on the times table number.
 * </p>
 * <p>
 * For instance, if there are 100 points on the circle and the times table number is 2,
 * then a line will be drawn from point 0 to point (0 * 2) % 100 = point 0, from point 1 to point (1 * 2) % 100 = point 2,
 * from point 2 to point (2 * 2) % 100 = point 4, and so on.
 * </p>
 *
 * @author Adam Abusang
 */

public class Visualization {

    private double timesTableNumber;
    private final double radius;

    /**
     * Constructs a Visualization object with a specified times table number and circle radius.
     *
     * @param timesTableNumber The initial times table number.
     * @param radius The radius of the circle used in the visualization.
     */
    public Visualization(double timesTableNumber, double radius) {
        this.timesTableNumber = timesTableNumber;
        this.radius = radius;
    }

    /**
     * Retrieves the current times table number.
     *
     * @return The current times table number.
     */
    public double getTimesTableNumber() {
        return timesTableNumber;
    }

    /**
     * Sets the times table number for the visualization.
     *
     * @param ttn The new times table number.
     */
    public void setTimesTableNum(double ttn) {
        this.timesTableNumber = ttn;
    }

    /**
     * Increments the current times table number by a specified step value.
     *
     * @param stepNum The increment step for the times table number.
     */
    public void incrementTTN(double stepNum) {
        timesTableNumber += stepNum;
    }

    /**
     * Generates a group of lines for the visualization based on the current times table number,
     * number of points, and a specified color.
     *
     * @param numPoints The number of points on the circle's circumference.
     * @param color The color of the lines in the visualization.
     * @return A group containing the lines for the visualization.
     */
    public Group generateLines(double numPoints, Color color) {
//        incrementRed();

        Group lines = new Group();
        PointOnCircle[] points = PointOnCircle.generatePoints(radius, numPoints);

        for (PointOnCircle poc: points) {
            double correspondingPointID = (getTimesTableNumber() * poc.getID()) % numPoints;
            PointOnCircle pointTo = points[(int) correspondingPointID];
            PointOnCircle pointFrom = points[poc.getID()];

            Line line = new Line(pointFrom.getX(), pointFrom.getY(), pointTo.getX(), pointTo.getY());
            line.setStroke(color);
            lines.getChildren().add(line);
        }

        return lines;
    }




}
