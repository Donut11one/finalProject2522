package entity;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents a bullet in the game with a position, speed, and size.
 */
public class Bullet extends Circle {

    private final Circle bullet;
    private double angle;
    private double speed;
    private double size;
    private double lifespan; // Duration the bullet will exist (in seconds)
    private double age = 0;  // Time passed since the bullet was created

    /**
     * Constructs a Bullet with given parameters.
     * @param x the x-coordinate of the bullet
     * @param y the y-coordinate of the bullet
     * @param angle the angle at which the bullet is fired (in degrees)
     * @param speed the speed of the bullet
     * @param size the size of the bullet
     * @param lifespan the lifespan of the bullet (in seconds)
     */
    public Bullet(double x, double y, double angle, double speed, double size, double lifespan) {
        this.angle = angle;
        this.speed = speed;
        this.size = size;
        this.lifespan = lifespan;

        // Initialize the bullet (using a Circle for simplicity)
        this.bullet = new Circle(x, y, size);
        this.bullet.setFill(Color.RED); // Set bullet color to red
    }

    /**
     * Updates the position of the bullet based on its speed and angle, and checks if it should despawn.
     * @param deltaTime the time delta since the last update
     * @param camera the camera (pane) that holds the bullets
     */
    public void updatePosition(double deltaTime, Pane camera) {
        // Update position based on speed and angle
        double radians = Math.toRadians(angle);
        double dx = Math.cos(radians) * speed * deltaTime;
        double dy = Math.sin(radians) * speed * deltaTime;

        // Update the bullet's position
        bullet.setCenterX(bullet.getCenterX() + dx);
        bullet.setCenterY(bullet.getCenterY() + dy);

        // Update the bullet's age
        age += deltaTime;

        // If the bullet has exceeded its lifespan, remove it from the pane
        if (age >= lifespan) {
            camera.getChildren().remove(bullet);
        }
    }

    /**
     * Gets the Circle object representing the bullet.
     * @return the Circle representing the bullet
     */
    public Circle getBullet() {
        return bullet;
    }
}
