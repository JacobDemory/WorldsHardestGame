package game;

import java.awt.*;

/**
 * Represents an enemy in the game, extending the Polygon class.
 * Implements the IntersectionDetectable interface for collision detection.
 */
public class Enemy extends Polygon implements IntersectionDetectable {

    private double radius;  // Radius of the enemy
    private boolean movingUp;  // Direction of movement
    private double speed;

    /**
     * Constructs an Enemy with the specified position, radius, and speed.
     *
     * @param x      The initial x-coordinate of the enemy.
     * @param y      The initial y-coordinate of the enemy.
     * @param radius The radius of the enemy.
     * @param speed  The speed at which the enemy moves.
     */
    public Enemy(double x, double y, double radius, double speed) {
        super(generateCirclePoints(x, y, radius, 36), new Point(x, y), 0);
        this.radius = radius;
        this.speed = speed;
        this.movingUp = true;  // Start moving up
    }

    /**
     * Moves the enemy up or down based on its direction.
     * Reverses direction when reaching the top or bottom of the board.
     */
    public void move() {
        // Move the enemy up or down based on the direction
        if (movingUp) {
            position.setY(position.getY() - speed);
        } else {
            position.setY(position.getY() + speed);
        }

        // Reverse direction when reaching the top or bottom of the board
        if (position.getY() <= WorldsHardestGame.checkeredY + radius ||
                position.getY() + 2 * radius >= WorldsHardestGame.checkeredY + WorldsHardestGame.checkeredSize - radius) {
            movingUp = !movingUp;
        }
    }

    /**
     * Checks for intersection with a player using custom intersection logic.
     *
     * @param player The player to check for intersection.
     * @return True if the enemy intersects with the player, false otherwise.
     */
    public boolean intersects(Player player) {
        // Check if any point of the player is inside the enemy
        for (Point playerPoint : player.getPoints()) {
            if (this.contains(playerPoint)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates points for a circle based on the specified parameters.
     *
     * @param x      The x-coordinate of the circle's center.
     * @param y      The y-coordinate of the circle's center.
     * @param radius The radius of the circle.
     * @param sides  The number of sides to approximate the circle.
     * @return An array of points representing the circle.
     */
    private static Point[] generateCirclePoints(double x, double y, double radius, int sides) {
        Point[] points = new Point[sides];
        double angleIncrement = 360.0 / sides;

        for (int i = 0; i < sides; i++) {
            double angle = Math.toRadians(i * angleIncrement);
            double newX = x + radius * Math.cos(angle);
            double newY = y + radius * Math.sin(angle);
            points[i] = new Point(newX, newY);
        }

        return points;
    }

    /**
     * Paints the enemy on the graphics context.
     *
     * @param brush The graphics context to paint on.
     */
    public void paint(Graphics brush) {
        brush.setColor(Color.blue);
        brush.fillOval((int) position.getX(), (int) position.getY(), (int) (2 * radius), (int) (2 * radius));
    }
}
