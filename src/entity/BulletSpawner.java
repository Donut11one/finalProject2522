package entity;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Spawns bullets in various patterns over a specified duration.
 */
public final class BulletSpawner {

    private final List<Bullet> activeBullets = new ArrayList<>();
    private boolean isSpawning = false; // Indicates whether the spawner is active

    /**
     * Starts spawning bullets in a spiral pattern for a specified duration.
     *
     * @param world        The Pane to spawn bullets in.
     * @param centerX      The X coordinate of the spawn center.
     * @param centerY      The Y coordinate of the spawn center.
     * @param speed        The speed of the bullets.
     * @param lifetime     The lifetime of the bullets in seconds.
     * @param angleStep    The angular increment between bullets in degrees.
     * @param spawnRate    The rate at which bullets are spawned (bullets per second).
     * @param spawnDuration The total duration to spawn bullets in seconds.
     */
    public void spawnSpiralWithDuration(final Pane world, final double centerX, final double centerY,
                                        final double speed, final double lifetime, final double angleStep,
                                        final double spawnRate, final double spawnDuration) {
        if (isSpawning) {
            return; // Prevent multiple spawners from running simultaneously
        }

        isSpawning = true;


       

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long startTime = System.nanoTime();
                long lastSpawnTime = System.nanoTime();
                double angle = 0; // Initial angle for the spiral pattern

                double elapsedTime = (now - startTime) / 1e9; // Convert nanoseconds to seconds

                // Stop spawning after the duration has passed
                if (elapsedTime > spawnDuration) {
                    isSpawning = false;
                    this.stop();
                    return;
                }

                // Spawn bullets periodically based on the spawnRate
                double timeSinceLastSpawn = (now - lastSpawnTime) / 1e9;
                if (timeSinceLastSpawn >= 1.0 / spawnRate) {
                    lastSpawnTime = now;

                    // Spawn bullets in a spiral pattern
                    final Bullet bullet = new Bullet(centerX, centerY, speed, Math.toRadians(angle), lifetime);
                    bullet.addToPane(world);
                    activeBullets.add(bullet);

                    angle += angleStep; // Increment the angle for the next bullet
                }
            }
        };

        timer.start();
    }

    /**
     * Updates all active bullets.
     *
     * @param deltaTime Time elapsed since the last update.
     * @param world     The Pane containing the bullets.
     */
    public void update(final double deltaTime, final Pane world) {
        activeBullets.removeIf(bullet -> {
            if (!bullet.update(deltaTime)) {
                bullet.removeFromPane(world);
                return true; // Remove expired bullets
            }
            return false;
        });
    }
}
