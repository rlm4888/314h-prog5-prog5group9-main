package assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;

/*
 * Any comments and methods here are purely descriptions or suggestions.
 * This is your test file. Feel free to change this as much as you want.
 */

public class BoggleTest {
    private GameManager gameManager;
    private GameDictionary gameDictionary;

    // This will run ONCE before all other tests. It can be useful to setup up
    // global variables and anything needed for all of the tests.

    @BeforeAll
    static void setupAll() {

    }

    @BeforeEach
    void setupEach() {
        gameManager = new GameManager();
        gameDictionary = new GameDictionary();
        try {
            gameDictionary.loadDictionary("/Users/vsundaram/Desktop/314H Code/314h-prog5-prog5group9/words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testContains() {
        GameDictionary gameDictionary = new GameDictionary();
        try {
            gameDictionary.loadDictionary("/Users/vsundaram/Desktop/314H Code/314h-prog5-prog5group9/testWords.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(gameDictionary.contains("apple"));
        // assertTrue(gameDictionary.contains("banana"));
    }

    @Test
    public void testIsPrefix() {
        GameDictionary gameDictionary = new GameDictionary();
        try {
            gameDictionary.loadDictionary("/Users/vsundaram/Desktop/314H Code/314h-prog5-prog5group9/words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(gameDictionary.isPrefix("sdfsdfsdfsdf"));
        assertTrue(gameDictionary.isPrefix("ban"));
    }

    // Test words with different lengths
    @Test
    public void testWordLengths() {
        GameDictionary gameDictionary = new GameDictionary();
        try {
            gameDictionary.loadDictionary("/Users/vsundaram/Desktop/314H Code/314h-prog5-prog5group9/words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(gameDictionary.contains("a"));
        assertTrue(gameDictionary.contains("aa"));
        assertFalse(gameDictionary.contains("aaa"));
    }

    // Test loading an empty dictionary file
    @Test
    public void testLoadEmptyDictionary() throws Exception {
        GameDictionary gameDictionary = new GameDictionary();
        try {
            gameDictionary.loadDictionary("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertFalse(gameDictionary.iterator().hasNext());
    }

    // Test for words with special characters
    @Test
    public void testSpecialCharacterWords() {
        GameDictionary gameDictionary = new GameDictionary();
        try {
            gameDictionary.loadDictionary("/Users/vsundaram/Desktop/314H Code/314h-prog5-prog5group9/words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(gameDictionary.contains("hello"));
        assertFalse(gameDictionary.contains("hello$"));
        assertFalse(gameDictionary.contains("hello123"));
    }


    // @Test
    // public void testIterator() throws Exception {
    //     GameDictionary gameDictionary = new GameDictionary();
    //     gameDictionary.insert("apple");
    //     gameDictionary.insert("banana");

    //     // Iterating over the dictionary
    //     for (String word : gameDictionary) {
    //         assertTrue(gameDictionary.contains(word));
    //     }
    // }
    @Test
    void testDictionaryIterator() {
        GameDictionary gameDictionary = new GameDictionary();
        try {
            gameDictionary.loadDictionary("/Users/vsundaram/Desktop/314H Code/314h-prog5-prog5group9/words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Iterating over the dictionary
        for (String word : gameDictionary) {
            assertTrue(gameDictionary.contains(word));
        }
    }

    @Test
    public void testLoadDictionary() {
        GameDictionary gameDictionary = new GameDictionary();
        try {
            gameDictionary.loadDictionary("/Users/vsundaram/Desktop/314H Code/314h-prog5-prog5group9/words.txt");
            System.out.println(gameDictionary.contains("random"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBoard() {
        GameManager gameManager = new GameManager();
        try {
            // assertTrue(Arrays.deepEquals(gameManager.getBoard(), {{'a', 'e', 'c', 'a'}, {'a', 'l', 'e', 'p'}, {'h', 'n', 'b', 'o'}, {'q', 't', 't', 'y'}}));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addWord() {
        GameDictionary gameDictionary = new GameDictionary();
        GameManager gameManager = new GameManager();
        try {
            // gameDictionary.loadDictionary("/Users/vsundaram/Desktop/314H Code/314h-prog5-prog5group9/words.txt");
            gameManager.newGame(4, 1, "/Users/vsundaram/Desktop/314H Code/314h-prog5-prog5group9/cubes.txt", gameDictionary);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(gameManager.addWord("smile", 0), 2);
    }

    @Test
    public void testEmptyLine() {
        GameManager gameManager = new GameManager();
        try {
            assertEquals(gameManager.addWord("", 0), 5);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testBoggle() {
    }

    // You can test GameManager here. You will want to make additional tests as well
    @Test
    void testGameManager() {

    }

    // You can test GameDictionary here. You will want to make additional tests as well
    @Test
    void testGameDictionary() {

    }
}