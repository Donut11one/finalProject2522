package entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents a bullet that is fired in the game.
 */
public class Bullet {

    private final Circle shape;
    private final double speedX;
    private final double speedY;
    private final double duration; // Duration the bullet is active (in seconds)
    private double age; // Time elapsed since the bullet was created (in seconds)

    /**
     * Creates a new bullet.
     *
     * @param x        Initial X position of the bullet.
     * @param y        Initial Y position of the bullet.
     * @param speedX   Horizontal velocity of the bullet.
     * @param speedY   Vertical velocity of the bullet.
     * @param radius   Radius of the bullet.
     * @param damage   Damage the bullet deals.
     * @param duration Lifetime of the bullet in seconds.
     */
    public Bullet(double x, double y, double speedX, double speedY, int radius, int damage, double duration) {
        this.shape = new Circle(radius, Color.RED);
        this.shape.setCenterX(x);
        this.shape.setCenterY(y);
        this.speedX = speedX;
        this.speedY = speedY;
        this.duration = duration;
        this.age = 0; // Initialize bullet age to 0
    }

    /**
     * Moves the bullet based on its velocity.
     *
     * @param deltaTime Time elapsed since the last update (in seconds).
     */
    public void move(double deltaTime) {
        // Update the bullet's position
        this.shape.setCenterX(this.shape.getCenterX() + speedX * deltaTime);
        this.shape.setCenterY(this.shape.getCenterY() + speedY * deltaTime);

        // Update the bullet's age
        age += deltaTime;
    }

    /**
     * Checks if the bullet has expired based on its duration.
     *
     * @return True if the bullet's lifetime has exceeded its duration, false otherwise.
     */
    public boolean hasExpired() {
        return age >= duration;
    }

    /**
     * Gets the visual representation of the bullet.
     *
     * @return The Circle representing the bullet.
     */
    public Circle getShape() {
        return this.shape;
    }
}
