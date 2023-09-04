import static java.lang.Math.cos;
import static java.lang.Math.toRadians;
import static java.lang.StrictMath.sin;

/**
 * Represents a point on a circle.
 * <p>
 * This class encapsulates the concept of a point on a circle,
 * providing methods to obtain the x and y coordinates of the point,
 * as well as a method to generate an array of points along the circumference of a circle.
 * </p>
 * @author Adam Abusang
 */
public class PointOnCircle {
    private int ID;
    private double x, y;

    /**
     * Constructs a new point on a circle.
     *
     * @param ID the identifier of the point
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public PointOnCircle(int ID, double x, double y) {
        this.ID = ID;
        this.x = x;
        this.y = y;
    }
    /**
     * Returns the identifier of the point.
     *
     * @return the ID of the point
     */
    public int getID() {
        return ID;
    }
    /**
     * Sets the identifier for the point.
     *
     * @param ID the new ID for the point
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    /**
     * Returns the x-coordinate of the point, offset by the width offset from the Main class.
     *
     * @return the offset x-coordinate of the point
     */
    public double getX() {
        return x + Main.OFFSET_W;
    }
    /**
     * Sets the x-coordinate of the point.
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the point, offset by the height offset from the Main class.
     *
     * @return the offset y-coordinate of the point
     */
    public double getY() {
        return y + Main.OFFSET_H;
    }
    /**
     * Sets the y-coordinate of the point.
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Generates an array of points along the circumference of a circle.
     *
     * @param radius the radius of the circle
     * @param num the number of points to be generated
     * @return an array of points on the circle
     */
    public static PointOnCircle[] generatePoints(double radius, double num) {

        PointOnCircle[] points = new PointOnCircle[(int)num];
        int i = 0;
        double pointSeparation = 360.0 / num;

        for (double angle = 180; angle < 540; angle += pointSeparation) {
            double x = cos(toRadians(angle)) * radius;
            double y = sin(toRadians(angle)) * radius;
            points[i] = new PointOnCircle(i, x, y);
            i++;
        }
        return points;
    }

}
