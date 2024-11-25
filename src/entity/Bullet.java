package entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;

/**
 * Represents a bullet in the game world.
 */
public final class Bullet {

    private static final double DEFAULT_BULLET_RADIUS = 5.0;

    private final Circle bullet;
    private final double speed;
    private final double direction; // Angle in radians
    private final double lifetime; // Bullet lifetime in seconds
    private double elapsedTime; // Tracks how long the bullet has existed

    /**
     * Initializes a bullet with the specified properties.
     *
     * @param startX     The starting X position.
     * @param startY     The starting Y position.
     * @param speed      The speed of the bullet.
     * @param direction  The direction (angle in radians) the bullet moves.
     * @param lifetime   The lifetime of the bullet in seconds.
     */
    public Bullet(final double startX, final double startY, final double speed, final double direction, final double lifetime) {
        this.bullet = new Circle(DEFAULT_BULLET_RADIUS, Color.RED);
        this.bullet.setCenterX(startX);
        this.bullet.setCenterY(startY);
        this.speed = speed;
        this.direction = direction;
        this.lifetime = lifetime;
        this.elapsedTime = 0;
    }

    /**
     * Updates the bullet's position and lifetime.
     *
     * @param deltaTime  Time elapsed since the last update.
     * @return True if the bullet is still active, false if it has expired.
     */
    public boolean update(final double deltaTime) {
        this.elapsedTime += deltaTime;

        if (this.elapsedTime > this.lifetime) {
            return false; // Bullet has expired
        }

        final double dx = Math.cos(this.direction) * this.speed * deltaTime;
        final double dy = Math.sin(this.direction) * this.speed * deltaTime;

        this.bullet.setCenterX(this.bullet.getCenterX() + dx);
        this.bullet.setCenterY(this.bullet.getCenterY() + dy);

        return true; // Bullet is still active
    }

    /**
     * Adds the bullet to a given Pane.
     *
     * @param world The Pane to add the bullet to.
     */
    public void addToPane(final Pane world) {
        world.getChildren().add(this.bullet);
    }

    /**
     * Removes the bullet from a given Pane.
     *
     * @param world The Pane to remove the bullet from.
     */
    public void removeFromPane(final Pane world) {
        world.getChildren().remove(this.bullet);
    }
}
