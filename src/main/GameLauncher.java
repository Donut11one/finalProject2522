package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import NumberGame.NumberGame;
import app.ScrollableWorldApp;

/**
 * GameLauncher manages the launching and displaying of different games.
 */
public final class GameLauncher extends Application {

    private static NumberGame numberGame;
    private static ScrollableWorldApp myGame;
    private static Stage primaryStage;

    public static void main(final String[] args) {
        launch(args); // Start the JavaFX runtime
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize game instances (but don't show them yet)
        numberGame = new NumberGame();
        myGame = new ScrollableWorldApp();
        GameLauncher.primaryStage = primaryStage;
    }

    /**
     * Show the Number Game window.
     */
    public static void showNumberGame() {
        // Recreate a new stage if needed
        System.out.println(primaryStage);
        if (primaryStage == null) {
            primaryStage = new Stage();
        }
        Platform.runLater(() -> numberGame.showGame(primaryStage));
        System.out.println("PrimaryStage visibility after show: " + primaryStage.isShowing());

    }

    /**
     * Show MyGame (ScrollableWorldApp) window.
     */
    public static void showMyGame() {
        Platform.runLater(() -> myGame.showGame());
    }

    /**
     * Gracefully shut down the JavaFX application.
     */
    public static void exitApplication() {
        Platform.exit(); // Exit JavaFX runtime
    }
}
