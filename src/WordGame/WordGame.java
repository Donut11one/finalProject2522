package WordGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

/**
 * The WordGame class represents a trivia game about world geography.
 */
public final class WordGame 
{
    private final World world;
    private int gamesPlayed;
    private int correctFirstAttempt;
    private int correctSecondAttempt;
    private int incorrectAttempts;

    /**
     * Constructs a WordGame and initializes the world.
     */
    public WordGame() 
    {
        world = new World();
        loadCountries();
    }

    /**
     * Loads country data from files named a.txt to z.txt.
     */
    private void loadCountries() 
    {
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";

        for (final char letter : alphabet.toCharArray()) 
        {
            final String fileName = "src/WordGame/Countries/" + letter + ".txt";

            // Check if the file exists before proceeding
            File file = new File(fileName);
            if (!file.exists()) 
            {
                System.err.println("File does not exist: " + fileName);
                continue; // Skip this file if it doesn't exist
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) 
            {
                String line;

                // Iterate through all lines in the file
                while ((line = reader.readLine()) != null) 
                {
                    line = line.trim();

                    // Skip empty lines
                    if (line.isEmpty()) 
                    {
                        continue;
                    }

                    // Read the first line (country:capital)
                    String countryAndCapital = line;
                    if (!countryAndCapital.contains(":")) 
                    {
                        System.err.println("Invalid format in file: " + fileName + ". Skipping...");
                        continue; // Skip this line if the format is invalid
                    }

                    // Split the country and capital
                    final String[] parts = countryAndCapital.split(":", 2);
                    final String name = parts[0].trim(); // Extract the country name
                    final String capital = parts[1].trim(); // Extract the capital city

                    // Read the next 3 facts, skipping any empty lines
                    final String[] facts = new String[3];
                    for (int i = 0; i < 3; i++) 
                    {
                        line = reader.readLine();
                        if (line != null) 
                        {
                            facts[i] = line.trim(); // Trim each fact to remove extra spaces
                        } 
                        else 
                        {
                            facts[i] = "No fact available"; // Default value if no facts are available
                        }
                    }

                    // Create a new Country object and add it to the world
                    final Country country = new Country(name, capital, facts);
                    world.addCountry(country);
                }
            } 
            catch (IOException e) 
            {
                System.err.println("Error loading file: " + fileName + ". Skipping...");
            }
        }
    }


    /**
     * Starts the WordGame.
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
     * Plays a single round of the game.
     *
     * @param scanner the Scanner for user input
     */
    private void playRound(final Scanner scanner) 
    {
        int roundCorrectFirstAttempt = 0;
        int roundCorrectSecondAttempt = 0;
        int roundIncorrectAttempts = 0;
        world.printAllCountries();
        for (int i = 0; i < 10; i++) 
        {
            final Country country = world.getRandomCountry();
            final int questionType = new Random().nextInt(3);
            boolean correct = false;

            switch (questionType) 
            {
                case 0: // Capital -> Country
                    System.out.println("What country has the capital " + country.getCapitalCityName() + "?");
                    correct = askQuestion(scanner, country.getName());
                    break;

                case 1: // Country -> Capital
                    System.out.println("What is the capital of " + country.getName() + "?");
                    correct = askQuestion(scanner, country.getCapitalCityName());
                    break;

                case 2: // Fact -> Country
                    final String fact = country.getFacts()[new Random().nextInt(3)];
                    System.out.println("Which country does this describe: " + fact);
                    correct = askQuestion(scanner, country.getName());
                    break;

                default:
                    throw new IllegalStateException("Unexpected question type: " + questionType);
            }

            if (correct) 
            {
                if (i == 0) 
                {
                    roundCorrectFirstAttempt++;
                } 
                else 
                {
                    roundCorrectSecondAttempt++;
                }
            } 
            else 
            {
                roundIncorrectAttempts++;
            }
        }

        gamesPlayed++;
        correctFirstAttempt += roundCorrectFirstAttempt;
        correctSecondAttempt += roundCorrectSecondAttempt;
        incorrectAttempts += roundIncorrectAttempts;

        System.out.println("- " + gamesPlayed + " word games played");
        System.out.println("- " + correctFirstAttempt + " correct answers on the first attempt");
        System.out.println("- " + correctSecondAttempt + " correct answers on the second attempt");
        System.out.println("- " + incorrectAttempts + " incorrect answers on two attempts each");
    }

    /**
     * Asks a question and checks the user's answer.
     *
     * @param scanner the Scanner for user input
     * @param correctAnswer the correct answer
     * @return true if the user answers correctly within two guesses
     */
    private boolean askQuestion(final Scanner scanner, final String correctAnswer) 
    {
        for (int attempt = 1; attempt <= 2; attempt++) 
        {
            System.out.println("Your guess: ");
            final String guess = scanner.nextLine().trim();

            if (guess.equalsIgnoreCase(correctAnswer)) 
            {
                System.out.println("CORRECT!");
                return attempt == 1;
            } 
            else 
            {
                System.out.println("INCORRECT.");
            }
        }

        System.out.println("The correct answer was: " + correctAnswer);
        return false;
    }

    /**
     * Saves the game score to a file.
     */
    private void saveScore() 
    {
        final LocalDateTime currentTime;
        final DateTimeFormatter formatter;
        final String formattedDateTime;
        final int totalScore;
        
        currentTime = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedDateTime = currentTime.format(formatter);
        totalScore = correctFirstAttempt * 2 + correctSecondAttempt;
        
        final String scoreData = formattedDateTime + " | " +
                                gamesPlayed + " word games played | " +
                                correctFirstAttempt + " correct answers on the first attempt | " +
                                correctSecondAttempt + " correct answers on the second attempt | " +
                                incorrectAttempts + " incorrect answers on two attempts each | " +
                                "Total Score: " + totalScore;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt", true))) 
        {
            writer.write(scoreData);
            writer.newLine();
        } 
        catch (IOException e) 
        {
            System.err.println("Error saving score: " + e.getMessage());
        }

        // Print the final result
        System.out.println("Your score has been saved!");
    }
}
