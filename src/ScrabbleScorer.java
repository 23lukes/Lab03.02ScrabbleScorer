import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Class Scrabble Scorer takes a word given by the user and calculates the score of that word in the game of Scrabble
 */
public class ScrabbleScorer {
    private String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int[] values = {1, 3, 3, 2, 1, 4, 2, 4,
            1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1,
            4, 4, 8, 4, 10};
    private ArrayList<ArrayList<String>> dictionary;

    /**
     * Initializing array list
     */
    public ScrabbleScorer() {
        dictionary = new ArrayList<>();
        for(int i = 0; i < 26; i++)
            dictionary.add(new ArrayList<String>());
        buildDictionary();
    }

    // import data from the text file
    // read file
    // find first letter?
    // go get word

    /**
     * Import data from the text file and reads the file
     * Finds the first letter of the word and goes to get the word from the file
     */
    public void buildDictionary()   {
        try {
            Scanner in = new Scanner(new File("SCRABBLE_WORDS.txt"));
            while(in.hasNext()) {
                String word = in.nextLine();
                int index = alpha.indexOf(word.substring(0,1));
                dictionary.get(index).add(word);
            }
            in.close();
            // sort all the buckets
            for(int i = 0; i < dictionary.size(); i++)
                Collections.sort(dictionary.get(i));
        }
        catch(Exception e)  {
            System.out.println("Error here: " + e);
        }
    }

    /**
     * Checks to see if the word entered is valid
     * @param word word given by the user
     * @return true if word is playable, false otherwise
     */
    public boolean isValidWord(String word) {
        // got help from Hutch
        try {
            return (Collections.binarySearch(dictionary.get(alpha.indexOf(word.substring(0, 1))), word) >= 0);
        }
        catch(Exception e)  {
            return false;
        }
    }

    /**
     * Iterates through the indices of the word finding each letter, finds the score of that letter and counts up the points
     * @param word
     * @return
     */
    public int getWordScore(String word)    {
        int score = 0;
        for(int i = 0; i < word.length(); i++)  {
            int index = alpha.indexOf(word.substring(i,i+1));
            score += values[index];
        }
        return score;
    }

    /**
     * Main method of ScrabbleScorer
     * Asks for word from user, calls all the other methods in this class
     * @param args
     */
    public static void main(String[] args) {
        ScrabbleScorer app = new ScrabbleScorer();
        System.out.println("* Welcome to the Scrabble Word Scorer app *");
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.print("Enter a word to score or 0 to quit: ");
            String word = in.nextLine().toUpperCase();
            if(word.equals("0"))    {
                break;
            }
            if(app.isValidWord(word))   {
                System.out.println(word + " = " + app.getWordScore(word) + "points");
            }
            else
                System.out.println(word + " is not a valid word in the dictionary");
        }
        System.out.println("Exiting the program thanks for playing");
    }
}
