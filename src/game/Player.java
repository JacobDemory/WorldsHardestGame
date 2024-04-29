package game;

import java.awt.*;

/**
 * Represents the player in the game, extending the Polygon class.
 */
public class Player extends Polygon {

    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean spinning = false;

    // Adjust the speed to control the movement distance in each frame
    private double speed = 4.0;

    /**
     * Constructs a Player with the specified initial position and rotation.
     *
     * @param initialPosition The initial position of the player.
     * @param initialRotation The initial rotation of the player.
     */
    public Player(Point initialPosition, double initialRotation) {
        super(new Point[] {
                new Point(0, 0),
                new Point(20, 0),
                new Point(20, 20),
                new Point(0, 20)
        }, initialPosition, initialRotation);
    }

    /**
     * Sets the rotation of the player.
     *
     * @param rotation The new rotation angle in degrees.
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     * Sets the position of the player.
     *
     * @param position The new position of the player.
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Moves the player based on the current movement flags.
     * Checks for collisions before moving and adjusts the rotation if spinning.
     *
     * @param checkeredX     The x-coordinate of the checkered board.
     * @param checkeredY     The y-coordinate of the checkered board.
     * @param checkeredSize  The size of the checkered board.
     */
    public void move(int checkeredX, int checkeredY, int checkeredSize) {
        // Check for collisions before moving
        if (movingUp && !collidesWithTopBorder(checkeredY)) {
            position.setY(position.getY() - speed);
        }
        if (movingDown && !collidesWithBottomBorder(checkeredY, checkeredSize)) {
            position.setY(position.getY() + speed);
        }
        if (movingLeft && !collidesWithLeftBorder(checkeredX)) {
            position.setX(position.getX() - speed);
        }
        if (movingRight && !collidesWithRightBorder(checkeredX, checkeredSize)) {
            position.setX(position.getX() + speed);
        }

        if (spinning) {
            // Rotate the player while spinning
            rotate(2);
        }
    }

    /**
     * Respawns the player to the initial position in the left green area.
     */
    public void respawn() {
        // Reset player position to the left green area
        position.setX(WorldsHardestGame.checkeredX + 10);  // Adjust as needed
        position.setY(WorldsHardestGame.checkeredY + ((double) WorldsHardestGame.checkeredSize / 2) - 10);  // Adjust as needed
    }

    /**
     * Checks if the player collides with the top border of the board.
     *
     * @param checkeredY The y-coordinate of the checkered board.
     * @return True if collides, false otherwise.
     */
    boolean collidesWithTopBorder(int checkeredY) {
        return position.getY() <= checkeredY + 4;
    }

    /**
     * Checks if the player collides with the bottom border of the board.
     *
     * @param checkeredY    The y-coordinate of the checkered board.
     * @param checkeredSize The size of the checkered board.
     * @return True if collides, false otherwise.
     */
    boolean collidesWithBottomBorder(int checkeredY, int checkeredSize) {
        return position.getY() + 20 >= checkeredY + checkeredSize + 5;
    }

    /**
     * Checks if the player collides with the left border of the board.
     *
     * @param checkeredX The x-coordinate of the checkered board.
     * @return True if collides, false otherwise.
     */
    boolean collidesWithLeftBorder(int checkeredX) {
        return position.getX() <= checkeredX + 4;
    }

    /**
     * Checks if the player collides with the right border of the board.
     *
     * @param checkeredX    The x-coordinate of the checkered board.
     * @param checkeredSize The size of the checkered board.
     * @return True if collides, false otherwise.
     */
    boolean collidesWithRightBorder(int checkeredX, int checkeredSize) {
        return position.getX() + 20 >= checkeredX + checkeredSize + 5;
    }

    /**
     * Sets the flag for moving up.
     *
     * @param movingUp True if moving up, false otherwise.
     */
    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    /**
     * Sets the flag for moving down.
     *
     * @param movingDown True if moving down, false otherwise.
     */
    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    /**
     * Sets the flag for moving left.
     *
     * @param movingLeft True if moving left, false otherwise.
     */
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    /**
     * Sets the flag for moving right.
     *
     * @param movingRight True if moving right, false otherwise.
     */
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    /**
     * Sets the flag for spinning.
     *
     * @param spinning True if spinning, false otherwise.
     */
    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }

    /**
     * Paints the player on the graphics context.
     *
     * @param brush The graphics context to paint on.
     */
    public void paint(Graphics brush) {
        brush.setColor(Color.red);

        // Get the points for drawing
        Point[] points = getPoints();

        // Convert the Point array to arrays of x and y coordinates
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int) points[i].getX();
            yPoints[i] = (int) points[i].getY();
        }

        // Draw the player
        brush.fillPolygon(xPoints, yPoints, points.length);
    }
}
