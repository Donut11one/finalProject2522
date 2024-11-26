package app;

import entity.BulletSpawner;
import javafx.application.Application;
import javafx.application.Platform;
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

        // Create bullet spawners with adjusted spawn rates
        BulletSpawner spawner1 = new BulletSpawner(1);  // Spawn every 1 second
        BulletSpawner spawner2 = new BulletSpawner(1);  // Spawn every 1 second

        // Define the angles for the bullet streams
        double[] angles1 = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180};
        double[] angles2 = {-45, -135};

        // Start the game loop to update bullets
        new javafx.animation.AnimationTimer() {
            long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) / 1e9; // Convert nanoseconds to seconds
                lastTime = now;

                // Update the spawners (move the bullets and remove expired ones)
                spawner1.update(deltaTime, camera, worldWidth / 2, 0, 5, 10, 100, 0, angles1, 5);
                spawner2.update(deltaTime, camera, worldWidth / 2, worldHeight, 5, 10, 100, 0, angles2, 5);
            }
        }.start();

        // Set up the primary stage
        primaryStage.setTitle("Scrollable World App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
