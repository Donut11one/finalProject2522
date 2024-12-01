package main;

import java.util.Scanner;
import WordGame.WordGame;

/**
 * Main class to provide a menu for the games.
 */
public final class Main {

    /**
     * Entry point of the program.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(final String[] args) {
        Thread javafxThread = new Thread(() -> GameLauncher.launch(GameLauncher.class, args));
        javafxThread.setDaemon(true);
        javafxThread.start();

        final Scanner scanner = new Scanner(System.in);
        final String menuMessage =
                "\n=================================\n" +
                "Main Menu:\n" +
                "Press W to play the Word game.\n" +
                "Press N to play the Number game.\n" +
                "Press M to play MyGame.\n" +
                "Press Q to quit.\n" +
                "=================================";

        while (true) {
            System.out.println(menuMessage);
            final String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "w":
                    System.out.println("Launching Word Game...");
                    final WordGame wordgame = new WordGame();
                    wordgame.play();
                    break;

                case "n":
                    System.out.println("Launching Number Game...");
                    GameLauncher.showNumberGame();
                    break;

                case "m":
                    System.out.println("Launching MyGame...");
                    GameLauncher.showMyGame();
                    break;

                case "q":
                    System.out.println("Quitting the game. Goodbye!");
                    scanner.close();
                    GameLauncher.exitApplication();
                    return;

                default:
                    System.out.println("Invalid input! Please enter W, N, M, or Q.");
                    break;
            }
        }
    }
}
