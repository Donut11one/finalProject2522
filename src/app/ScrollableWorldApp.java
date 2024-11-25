package app;

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

        // Set up the primary stage
        primaryStage.setTitle("Scrollable World App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
