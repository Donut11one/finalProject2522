package app;

import entity.BulletSpawner;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import player.Player;
import world.World;

import java.util.ArrayList;
import java.util.List;

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

        // List of bullet spawners
        final List<BulletSpawner> bulletSpawners = new ArrayList<>();

        // Add multiple bullet spawners
        BulletSpawner spawner1 = new BulletSpawner(1.0);
        BulletSpawner spawner2 = new BulletSpawner(0.5);
        bulletSpawners.add(spawner1);
        bulletSpawners.add(spawner2);

        // Set up the spawners at different locations in the world
        spawner1.spawnSpiral(world.getRoot(), worldWidth / 2.0, 0, 1000, 5, 10, 5,1,30);

        // Animation loop
        new AnimationTimer() {
            long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastTime) / 1e9; // Calculate delta time in seconds
                lastTime = now;

                spawner1.update(deltaTime, world.getRoot());
                spawner2.update(deltaTime, world.getRoot());
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
