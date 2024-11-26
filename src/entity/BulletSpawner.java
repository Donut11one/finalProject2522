package entity;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

/**
 * Spawns and manages bullets in the game.
 */
public class BulletSpawner {

    private double spawnRate;   // Time in seconds between each spawn
    private double timeSinceLastSpawn = 0;  // Time accumulator for spawn rate
    private List<Bullet> bullets; // List of bullets to track

    /**
     * Constructor for BulletSpawner with a given spawn rate.
     * @param spawnRate the rate at which bullets spawn (in seconds)
     */
    public BulletSpawner(double spawnRate) {
        this.spawnRate = spawnRate;
        this.bullets = new ArrayList<>();
    }

    /**
     * Adds a bullet to the list and the scene.
     * @param bullet the bullet to add
     * @param camera the camera (pane) to add the bullet to
     */
    public void addBullet(Bullet bullet, Pane camera) {
        camera.getChildren().add(bullet.getBullet());  // Add the bullet to the scene
        bullets.add(bullet);  // Add bullet to the list to keep track of it
    }

    /**
     * Spawns bullets in a stream at specified angles.
     * @param camera the camera (pane) to add the bullets to
     * @param x the x-coordinate where bullets will be spawned
     * @param y the y-coordinate where bullets will be spawned
     * @param bulletCount the number of bullets to spawn
     * @param bulletSize the size of each bullet
     * @param bulletSpeed the speed of each bullet
     * @param spawnAngle the starting angle of the first bullet
     * @param angles an array of angles for the bullet stream
     */
    public void spawnStream(Pane camera, double x, double y, int bulletCount, int bulletSize, int bulletSpeed, double spawnAngle, double[] angles, double bulletDuration) {
        for (double angle : angles) {
            for (int i = 0; i < bulletCount; i++) {
                // Create a bullet at the specified location, with specified angle, speed, and size
                Bullet bullet = new Bullet(x, y, spawnAngle + angle, bulletSpeed, bulletSize, bulletDuration);
                addBullet(bullet, camera);  // Add bullet to the scene and the list
            }
        }
    }

    /**
     * Updates the bullets' positions over time and handles spawn timing.
     * @param deltaTime the time elapsed since the last update (in seconds)
     * @param camera the camera (pane) that holds the bullets
     * @param x the x-coordinate where bullets will be spawned
     * @param y the y-coordinate where bullets will be spawned
     * @param bulletCount the number of bullets to spawn
     * @param bulletSize the size of each bullet
     * @param bulletSpeed the speed of each bullet
     * @param spawnAngle the starting angle of the first bullet
     * @param angles an array of angles for the bullet stream
     */
    public void update(double deltaTime, Pane camera, double x, double y, int bulletCount, int bulletSize, int bulletSpeed, double spawnAngle, double[] angles,double bulletDuration) {
        timeSinceLastSpawn += deltaTime;  // Accumulate time since last spawn

        // Check if it's time to spawn new bullets based on the spawn rate
        if (timeSinceLastSpawn >= spawnRate) {
            timeSinceLastSpawn = 0;  // Reset the spawn timer

            // Spawn bullets after the rate has passed
            spawnStream(camera, x, y, bulletCount, bulletSize, bulletSpeed, spawnAngle, angles, bulletDuration);
        }

        // Update each bullet's position in the list
        for (Bullet bullet : bullets) {
            bullet.updatePosition(deltaTime, camera);
        }
    }
}
