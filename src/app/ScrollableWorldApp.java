package app;

import entity.BulletSpawner;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import player.Player;
import world.World;

/**
 * Main application entry point for the Scrollable World App.
 */
public final class ScrollableWorldApp extends Application {

    @Override
    public void start(final Stage primaryStage) {
        final int worldWidth = 800;
        final int worldHeight = 1000;
        final int windowWidth = 800;
        final int windowHeight = 600;

        // Create the world
        final World world = new World(worldWidth, worldHeight);

        // Create the player
        final Player player = new Player(50, 50);
        world.addEntity(player.getPlayer());

        // Create a camera (root Pane containing the world)
        final Pane camera = new Pane(world.getRoot());

        // Create the scene
        final Scene scene = new Scene(camera, windowWidth, windowHeight);
        player.enableControls(scene, worldWidth, worldHeight, camera);

        // Initialize BulletSpawner
        final BulletSpawner bulletSpawner = new BulletSpawner();

        // Spawn a spiral of bullets at the center of the world
        bulletSpawner.spawnSpiralWithDuration(
            world.getRoot(),
            worldWidth / 2.0,
            worldHeight / 2.0,
            100,  // Speed
            5,    // Lifetime
            20,   // Angle step
            10,   // Spawn rate
            10    // Duration
        );

        // Animation loop for updating bullet spawner
        new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) / 1e9; // Convert nanoseconds to seconds
                lastTime = now;

                // Update bullets
                bulletSpawner.update(deltaTime, world.getRoot());
            }
        }.start();

        world.addEntity(bulletSpawner);
        // Set up the primary stage
        primaryStage.setTitle("Scrollable World App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
