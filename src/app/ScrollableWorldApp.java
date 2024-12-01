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
    final static int WORLD_WIDTH = 600;
    final static int WORLD_HEIGHT = 800;
    final static int WINDOW_WIDTH = 800;
    final static int WINDOW_HEIGHT = 600;
    private Stage primaryStage;
    @Override
    public void start(final Stage primaryStage) {

        this.primaryStage = primaryStage;

        // Create the world
        final World world;
        // Create the player
        final Player player;
        // Create a camera (root Pane containing the world)
        final Pane camera;
        // Create the scene
        final Scene scene;

        world = new World(WORLD_WIDTH, WORLD_HEIGHT);
        player = new Player(50, 50);
        world.addEntity(player.getPlayer());
        camera = new Pane(world.getRoot());

        scene = new Scene(camera, WINDOW_WIDTH, WINDOW_HEIGHT);
        player.enableControls(scene, WORLD_WIDTH, WORLD_HEIGHT, camera);

        // Create bullet spawners with adjusted spawn rates
        BulletSpawner spawner1;
        BulletSpawner spawner2;
        spawner1 = new BulletSpawner(0.5);  // Spawn every 1 second
        spawner2= new BulletSpawner(1);  // Spawn every 1 second

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
                spawner1.update(deltaTime, camera, WORLD_WIDTH / 2, 0, 5, 10, 100, 0, angles1, 10);
                spawner2.update(deltaTime, camera, WORLD_WIDTH / 2, WORLD_HEIGHT, 5, 10, 100, 0, angles2, 5);
            }
        }.start();

        // Set up the primary stage
        primaryStage.setTitle("Scrollable World App");
        primaryStage.setScene(scene);
    }
    public void showGame() {
        primaryStage.show();
    }
}
