package game;

/**
 * Interface for objects that can detect intersections with a Player.
 */
public interface IntersectionDetectable {
    /**
     * Checks if there is an intersection with a Player.
     *
     * @param player The Player object to check for intersection.
     * @return {@code true} if an intersection occurs, {@code false} otherwise.
     */
    boolean intersects(Player player);
}
