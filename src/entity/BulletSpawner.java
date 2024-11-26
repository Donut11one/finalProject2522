package entity;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Spawns and manages bullets in the game world.
 */
public class BulletSpawner {

    private final List<Bullet> bullets; // List to store bullets
    private double timeSinceLastSpawn; // Timer to track the spawn rate
    private final double spawnInterval; // Interval between bullet spawns

    /**
     * Initializes a bullet spawner.
     *
     * @param spawnInterval The time interval (in seconds) between bullet spawns.
     */
    public BulletSpawner(double spawnInterval) {
        this.bullets = new ArrayList<>();
        this.timeSinceLastSpawn = 0;
        this.spawnInterval = spawnInterval;
    }

    /**
     * Updates the bullet spawner. Handles spawning and bullet movement.
     *
     * @param deltaTime The time elapsed since the last update (in seconds).
     * @param worldRoot The root pane of the game world.
     */
    public void update(double deltaTime, Pane worldRoot) {
        // Increment time since the last spawn
        timeSinceLastSpawn += deltaTime;
    
        // Spawn bullets if the spawn interval has been reached
        if (timeSinceLastSpawn >= spawnInterval) {
            spawnSpiral(worldRoot, worldRoot.getWidth() / 2, worldRoot.getHeight() / 2, 150, 5, 10, 5, 16, 360.0 / 16);
            timeSinceLastSpawn = 0; // Reset the spawn timer
        }
    
        // Update each bullet
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.move(deltaTime); // Move the bullet
    
            // Remove expired bullets
            if (bullet.hasExpired()) {
                worldRoot.getChildren().remove(bullet.getShape());
                iterator.remove();
            }
        }
    }
    

    /**
     * Spawns bullets in a spiral pattern.
     *
     * @param worldRoot    The root pane of the game world.
     * @param centerX      The X coordinate of the spiral's center.
     * @param centerY      The Y coordinate of the spiral's center.
     * @param speed        The speed of the bullets.
     * @param bulletSize   The size (radius) of the bullets.
     * @param bulletDamage The damage value of the bullets.
     * @param duration     The duration the bullets remain active (in seconds).
     * @param bulletCount  The number of bullets to spawn in the spiral.
     * @param angleStep    The angle step for bullet placement in degrees.
     */
    public void spawnSpiral(
            Pane worldRoot,
            double centerX,
            double centerY,
            double speed,
            int bulletSize,
            int bulletDamage,
            double duration,
            int bulletCount,
            double angleStep
    ) {
        for (int i = 0; i < bulletCount; i++) {
            double angle = Math.toRadians(i * angleStep); // Convert angle to radians
            double bulletX = centerX + Math.cos(angle) * 20; // Starting X position
            double bulletY = centerY + Math.sin(angle) * 20; // Starting Y position

            // Calculate bullet's velocity components
            double speedX = speed * Math.cos(angle);
            double speedY = speed * Math.sin(angle);

            // Create a new bullet
            Bullet bullet = new Bullet(bulletX, bulletY, speedX, speedY, bulletSize, bulletDamage, duration);

            // Add the bullet to the world and the list
            worldRoot.getChildren().add(bullet.getShape());
            bullets.add(bullet);
        }
    }
}
