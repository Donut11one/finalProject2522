package main;

import java.util.Scanner;
import WordGame.WordGame;

/**
 * Main class to provide a menu for the games.
 */
public final class Main
{
    /**
     * Entry point of the program.
     * 
     * @param args the command-line arguments (not used)
     */
    public static void main(final String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        final String menuMessage = 
            "\n=================================\n" +
            "Main Menu:\n" +
            "Press W to play the Word game.\n" +
            "Press N to play the Number game.\n" +
            "Press M to play MyGame.\n" +
            "Press Q to quit.\n" +
            "=================================";

        while (true)
        {
            System.out.println(menuMessage);
            final String choice;
            choice = scanner.nextLine().trim().toLowerCase();

            switch (choice)
            {
                case "w":
                    final WordGame wordgame;
                    wordgame = new WordGame();
                    wordgame.play();
                    break;
                // case "n":
                //     NumberGame.play();
                //     break;
                // case "m":
                //     MyGame.play();
                //     break;
                case "q":
                    System.out.println("Quitting the game. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid input! Please enter W, N, M, or Q.");
                    break;
            }
        }
    }
}
