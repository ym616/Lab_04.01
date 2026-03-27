import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Lab 04.01: Scrabble Word Finder
 * This class generates a random Scrabble rack and finds all playable words from a dictionary including support for blank tiles.
 * @author 28mehta
 * @version 3-27-26
 *
 */
public class ScrabbleWordFinder {

    private ArrayList<String> dictionary;
    private ArrayList<String> tileRack;

    /**
     * initializes the dictionary from a file and generates random 7 letter rack
     */
    public ScrabbleWordFinder() {
        dictionary = new ArrayList<>();
        loadDictionary();
        generateRack();
    }


    private void loadDictionary() {
        try {
            Scanner file = new Scanner(new File("SCRABBLE_WORDS.txt"));
            while (file.hasNext()) {
                dictionary.add(file.next().toUpperCase());
            }
            file.close();
            Collections.sort(dictionary);
        } catch (Exception e) {
            System.out.println("Error loading dictionary: " + e.getMessage());
        }
    }


    private void generateRack() {
        String[] distribution = {
                "A","A","A","A","A","A","A","A","A", "B","B", "C","C", "D","D","D","D",
                "E","E","E","E","E","E","E","E","E","E","E","E", "F","F", "G","G","G",
                "H","H", "I","I","I","I","I","I","I","I","I", "J", "K", "L","L","L","L",
                "M","M", "N","N","N","N","N","N", "O","O","O","O","O","O","O","O",
                "P","P", "Q", "R","R","R","R","R","R", "S","S","S","S", "T","T","T","T","T","T",
                "U","U","U","U", "V","V", "W","W", "X", "Y","Y", "Z", " ", " "
        };

        ArrayList<String> masterList = new ArrayList<>(Arrays.asList(distribution));


        Collections.shuffle(masterList);

        tileRack = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            int randomIndex = (int) (Math.random() * masterList.size());
            tileRack.add(masterList.remove(randomIndex));
        }
    }


    public void printTiles() {
        System.out.println("Letters in the rack: " + tileRack);
    }

    /**
     * ;ogic to determine if a word can be formed from the rack
     * bonus logic to handle blank tiles
     */
    private boolean canForm(String word) {
        ArrayList<String> tempRack = new ArrayList<>(tileRack);
        for (int i = 0; i < word.length(); i++) {
            String letter = String.valueOf(word.charAt(i));
            if (tempRack.contains(letter)) {
                tempRack.remove(letter);
            } else if (tempRack.contains(" ")) {
                tempRack.remove(" ");
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Builds and returns a list of words that can be made from the rack.
     */
    public ArrayList<String> getPlaylist() {
        ArrayList<String> matches = new ArrayList<>();
        for (String word : dictionary) {
            if (canForm(word)) {
                matches.add(word);
            }
        }
        return matches;
    }

    public void printMatches() {
        ArrayList<String> matches = getPlaylist();

        if (matches.isEmpty()) {
            System.out.println("Sorry, NO words can be played from those tiles.");
            return;
        }

        System.out.println("You can play the following words from the letters in your rack:");
        for (int i = 0; i < matches.size(); i++) {
            String word = matches.get(i);
            if (word.length() == 7) {
                word += "*";
            }

            // Using a tab and ensuring exactly 10 words per line
            System.out.print(word + "\t");

            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }


        System.out.println("\n\n* denotes BINGO");
    }


    public static void main(String[] args) {
        ScrabbleWordFinder app = new ScrabbleWordFinder();
        app.printTiles();
        app.printMatches();
    }
}
