package game;

import java.awt.*;

/**
 * Represents a spinning rectangle in the game, extending the Polygon class.
 * Implements the IntersectionDetectable interface for collision detection.
 */
public class SpinningRectangle extends Polygon implements IntersectionDetectable {
    protected int rotationSpeed = 2;

    /**
     * Constructs a SpinningRectangle with the specified initial position and rotation.
     *
     * @param initialPosition The initial position of the spinning rectangle.
     * @param initialRotation The initial rotation angle in degrees.
     */
    public SpinningRectangle(Point initialPosition, double initialRotation) {
        super(new Point[] {
                new Point(0, 0),
                new Point(50, 0),
                new Point(50, 15),
                new Point(0, 15)
        }, initialPosition, initialRotation);
    }

    /**
     * Moves the spinning rectangle by adjusting its rotation based on the rotation speed.
     */
    public void move() {
        rotation += rotationSpeed;
    }

    /**
     * Checks for intersection with a player using custom intersection logic.
     *
     * @param player The player to check for intersection.
     * @return True if the spinning rectangle intersects with the player, false otherwise.
     */
    public boolean intersects(Player player) {
        // Check if any point of the player is inside the spinning rectangle
        for (Point playerPoint : player.getPoints()) {
            if (this.contains(playerPoint)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Paints the spinning rectangle on the graphics context.
     *
     * @param brush The graphics context to paint on.
     */
    public void paint(Graphics brush) {
        brush.setColor(Color.pink);

        Point[] points = getPoints();  // Get the updated points after rotation
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int) points[i].getX();
            yPoints[i] = (int) points[i].getY();
        }

        brush.fillPolygon(xPoints, yPoints, points.length);
    }
}
