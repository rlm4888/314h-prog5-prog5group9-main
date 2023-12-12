package assignment;
import java.util.Arrays;
import java.util.Scanner;

// implement your UI here! we should be able to run it with java assignment.Boggle
import java.util.Arrays;
import java.util.Scanner;

public class Boggle {
    private static Scanner scanner = new Scanner(System.in);
    private static GameDictionary gameDictionary;
    private static GameManager gameManager;
    private static int numPlayers;

    public static void main(String[] args) {
        System.out.println("How many players are playing this game?");
        numPlayers = Integer.parseInt(scanner.nextLine());

        boolean continuePlaying = true;
        while (continuePlaying) {
            playBoggleGame();
            System.out.println("Do you want to play another game? (yes/no)");
            String answer = scanner.nextLine();
            if (!answer.toLowerCase().equals("yes")) {
                continuePlaying = false;
            }
        }
        System.out.println("Thanks for playing Boggle!");
    }

    private static void playBoggleGame() {
        // Initialize Boggle game
        try {
            gameManager = new GameManager();
            gameDictionary = new GameDictionary();
            gameDictionary.loadDictionary("/Users/vsundaram/Desktop/314H Code/314h-prog5-prog5group9/words.txt");
            gameManager.newGame(4, numPlayers, "/Users/vsundaram/Desktop/314H Code/314h-prog5-prog5group9/cubes.txt", gameDictionary);

            // Display initial board
            System.out.println("Initial board:");
            gameManager.getAllWords();
            gameManager.printBoard();
//            gameManager.searchBoard();
//            System.out.println("break stmtn");
//            gameManager.getAllWords();
            // gameManager.searchDict();

            // Accept word inputs from the player
            System.out.println("Enter words (or 'quit the game' to exit):");
            String word;
            boolean playing = true;
            while (playing) {
                // System.out.println(gameManager.numPlayers1);
                for (int i = 0; i < gameManager.numPlayers1; i++) {
                    System.out.println("Player " + (i+1) + "—try to come up with a word!");
                    word = scanner.nextLine();
                    if (word.equals("quit the game")) {
                        playing = false;
                        for (int z = 0; z < gameManager.numPlayers1; z++) {
                            System.out.println("Player " + (z+1)+ "'s final score is: " + gameManager.scores[z] + ".\n");
                        }
                        break;
                    }

                    // int player = 0;
                    word = word.toLowerCase();
                    int score = gameManager.addWord(word, i);
                    System.out.println("Get Last Added Word: " + gameManager.getLastAddedWord());
                    if (score > 0) {
                        System.out.println("Valid word! Player " + (i+1) + "'s Score: " + gameManager.scores[i]);
                        gameManager.lowercaseBoard(); // Print the board with the updated word
                    } else {
                        System.out.println("Please try again.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
