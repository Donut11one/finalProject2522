package NumberGame;

import java.util.Random;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * NumberGame is a simple game where the user guesses a randomly generated number.
 */
public final class NumberGame 
{
    private static final int MAX_ATTEMPTS = 5;
    private static final int MAX_NUMBER = 100;
    private int gamesPlayed;
    private int successfulGames;
    private int totalAttempts;

    /**
     * Main method to run the NumberGame.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(final String[] args) 
    {
        NumberGame game = new NumberGame();
        game.play();
    }

    /**
     * Plays the NumberGame.
     */
    public void play() 
    {
        final Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        do 
        {
            playRound(scanner);
            System.out.println("Do you want to play again? (Yes/No): ");
            final String response = scanner.nextLine().trim().toLowerCase();
            playAgain = response.equals("yes");
        } 
        while (playAgain);

        saveScore();
    }

    /**
     * Plays a single round of the NumberGame.
     *
     * @param scanner the Scanner for user input.
     */
    private void playRound(final Scanner scanner) 
    {
        final Random random = new Random();
        final int targetNumber = random.nextInt(MAX_NUMBER) + 1;

        System.out.println("Guess the number (between 1 and " + MAX_NUMBER + "):");
        int attempts = 0;
        boolean guessedCorrectly = false;

        while (attempts < MAX_ATTEMPTS) 
        {
            attempts++;
            totalAttempts++;
            System.out.print("Attempt " + attempts + ": ");
            final int guess = scanner.nextInt();

            if (guess == targetNumber) 
            {
                System.out.println("Congratulations! You've guessed the number!");
                guessedCorrectly = true;
                break;
            } 
            else if (guess < targetNumber) 
            {
                System.out.println("Too low! Try again.");
            } 
            else 
            {
                System.out.println("Too high! Try again.");
            }
        }

        if (!guessedCorrectly) 
        {
            System.out.println("Sorry, you've used all your attempts. The correct number was " + targetNumber + ".");
        } 
        else 
        {
            successfulGames++;
        }

        gamesPlayed++;
    }

    /**
     * Saves the game score to a file.
     */
    private void saveScore() 
    {
        final LocalDateTime currentTime = LocalDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final String formattedDateTime = currentTime.format(formatter);

        final String scoreData = formattedDateTime + " | " +
                                 gamesPlayed + " games played | " +
                                 successfulGames + " successful games | " +
                                 totalAttempts + " total attempts";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("NumberGame/score.txt", true))) 
        {
            writer.write(scoreData);
            writer.newLine();
        } 
        catch (IOException e) 
        {
            System.err.println("Error saving score: " + e.getMessage());
        }

        System.out.println("Your score has been saved!");
    }
}
