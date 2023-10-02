import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class Main {

    public static String getRandomWord() {
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/Andy/Desktop/Java/Hangman/src/Words"))) {
            ArrayList<String> wordsList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                wordsList.add(line);
            }

            if (!wordsList.isEmpty()) {
                Random random = new Random();
                int randomIndex = random.nextInt(wordsList.size());
                return wordsList.get(randomIndex);
            } else {
                return "No words found in the file.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred while reading the file.";
        }
    }

    public static void displayWord(String word, boolean[] guessedLetters) {
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (guessedLetters[i]) {
                System.out.print(letter);
            } else {
                System.out.print("-");
            }
        }
        System.out.println();
    }

    public static boolean isGameWon(boolean[] guessedLetters) {
        for (boolean guessed : guessedLetters) {
            if (!guessed) {
                return false;
            }
        }
        return true;
    }

    public static void playGame() {
        String wordToGuess = getRandomWord();
        int guessesAllowed = 10;

        boolean[] guessedLetters = new boolean[wordToGuess.length()];
        Scanner keyword = new Scanner(System.in);
        boolean gameWon = false;

        while (guessesAllowed > 0 ) {
            displayWord(wordToGuess, guessedLetters);

            System.out.println("Enter a letter");
            String guess = keyword.nextLine().toLowerCase();

            if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
                continue;
            }

            char guessedLetter = guess.charAt(0);

            boolean alreadyGuessed = false;

            for (int i = 0; i < wordToGuess.length(); i++) {
                if (wordToGuess.charAt(i) == guessedLetter && guessedLetters[i]) {
                    alreadyGuessed = true;
                    break;
                }
            }

            if (alreadyGuessed) {
                System.out.println("Letter already guessed");
            } else {
                boolean correctGuess = false;
                for (int i = 0; i < wordToGuess.length(); i++) {
                    if (wordToGuess.charAt(i) == guessedLetter) {
                        guessedLetters[i] = true;
                        correctGuess = true;
                    }
                }

                if (correctGuess) {
                    System.out.println("Correct!");
                    if (isGameWon(guessedLetters)) {
                        gameWon = true;
                    }
                } else {
                    System.out.println("Incorrect! Try again");
                }
            }

            guessesAllowed--;
        }

        if (gameWon) {
            System.out.println("Congratulations! You've won!");

        } else {
            System.out.println("Sorry, you've run out of attempts. The word was: " + wordToGuess);
        }
    }

    public static void main(String[] args) {
        playGame();
    }
}
