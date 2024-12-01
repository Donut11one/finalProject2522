package NumberGame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class NumberGame{

    private static NumberGame instance;
    private Stage primaryStage;

    private static final int GRID_ROWS = 4;
    private static final int GRID_COLUMNS = 5;
    private static final int TOTAL_NUMBERS = 20;

    private final Button[][] gridButtons = new Button[GRID_ROWS][GRID_COLUMNS];
    private final List<Integer> randomNumbers = new ArrayList<>();
    private int currentIndex = 0;

    private final Label nextNumberLabel = new Label();
    private int lastPlacedNumber = -1;

    private int gamesPlayed = 0;
    private int gamesWon = 0;
    private int totalSuccessfulPlacements = 0;

    private Button quitButton; // Quit button to remain visible even when the game is over

    // Get the singleton instance of NumberGame
    public static NumberGame getInstance() {
        return instance;
    }

    public void showGame(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        instance = this; // Set instance after Application has started

        // Initialize random numbers
        generateRandomNumbers();

        // Create GUI components
        GridPane gridPane = createGrid();
        VBox mainLayout = new VBox(10, nextNumberLabel, gridPane); // Include quit button in layout
        nextNumberLabel.setText("Next number: " + randomNumbers.get(currentIndex));

        // Set up the stage
        Scene scene = new Scene(mainLayout, 400, 500);
        primaryStage.setTitle("NumberGame");
        
        primaryStage.setScene(scene);
        primaryStage.show(); // Show the stage when JavaFX is ready
    }

    public void quitGame() {
        if (primaryStage != null) {
            primaryStage.hide();
            primaryStage.setScene(null);
        }
    }    


    private void generateRandomNumbers() {
        Random random = new Random();
        randomNumbers.clear();
        for (int i = 0; i < TOTAL_NUMBERS; i++) {
            randomNumbers.add(random.nextInt(1000) + 1);
        }
    }

    private GridPane createGrid() {
        GridPane gridPane = new GridPane();

        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLUMNS; col++) {
                Button button = new Button("[]");
                button.setPrefSize(60, 60);

                int finalRow = row;
                int finalCol = col;

                // Handle button click
                button.setOnAction(event -> handleButtonClick(finalRow, finalCol, button));

                gridButtons[row][col] = button;
                gridPane.add(button, col, row);
            }
        }
        return gridPane;
    }

    private void handleButtonClick(int row, int col, Button button) {
        if (!button.getText().equals("[]")) {
            return;
        }

        int currentNumber = randomNumbers.get(currentIndex);

        if (currentNumber < lastPlacedNumber) {
            showGameOver(false);
            return;
        }

        button.setText(String.valueOf(currentNumber));
        lastPlacedNumber = currentNumber;
        currentIndex++;
        totalSuccessfulPlacements++;

        if (currentIndex >= TOTAL_NUMBERS) {
            showGameOver(true);
            return;
        }

        if (!hasEmptySpace()) {
            showGameOver(false);
            return;
        }

        nextNumberLabel.setText("Next number: " + randomNumbers.get(currentIndex));
    }

    private boolean hasEmptySpace() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLUMNS; col++) {
                if (gridButtons[row][col].getText().equals("[]")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void showGameOver(boolean won) {
        gamesPlayed++;
        if (won) {
            gamesWon++;
        }

        // Create the Game Over popup with VBox layout
        VBox gameOverLayout = new VBox(10);
        
        Label gameOverLabel = new Label(won ? "Congratulations!" : "Game Over");
        gameOverLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label scoreSummaryLabel = new Label(getScoreSummary());
        
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(event -> resetGame());
        
        // Quit button functionality
        quitButton = new Button("Quit");
        quitButton.setOnAction(event -> quitGame());

        gameOverLayout.getChildren().addAll(gameOverLabel, scoreSummaryLabel, playAgainButton, quitButton);

        // Create the new scene with the VBox containing the game over content
        Scene gameOverScene = new Scene(gameOverLayout, 400, 300);

        // Hide current game and show game over scene
        primaryStage.setScene(gameOverScene);
        primaryStage.show();
    }

    private void resetGame() {
        // Reset game state
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLUMNS; col++) {
                gridButtons[row][col].setText("[]");
            }
        }

        currentIndex = 0;
        lastPlacedNumber = -1;
        generateRandomNumbers();
        nextNumberLabel.setText("Next number: " + randomNumbers.get(currentIndex));

        // Switch back to the game screen
        VBox gameLayout = new VBox(10, nextNumberLabel, createGrid());
        Scene gameScene = new Scene(gameLayout, 400, 500);
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    private String getScoreSummary() {
        int gamesLost = gamesPlayed - gamesWon;
        double averagePlacements = (double) totalSuccessfulPlacements / gamesPlayed;

        return "Games played: " + gamesPlayed +
               "\nGames won: " + gamesWon +
               "\nGames lost: " + gamesLost +
               "\nTotal placements: " + totalSuccessfulPlacements +
               "\nAverage placements per game: " + String.format("%.2f", averagePlacements);
    }
    public void setInstance(NumberGame instance) {
        NumberGame.instance = instance;
    }
}
