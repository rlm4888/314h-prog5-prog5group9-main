package assignment;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.*;


public class GameManager implements BoggleGame {
    char[][] cubes = new char[16][6];
    private char[][] board1;
    GameDictionary dictionary;
    private ArrayList<ArrayList<String>> words;
    private List<Point> lastAddedWord;
    private List<Point> listOfBoardPoints;
    int[] scores;
    private SearchTactic tactic1;
    private String file;
    private Trie trie;
    int size1;
    int scoreToAdd;
    int numPlayers1;
    private ArrayList<String> playerWords;
    ArrayList<String> listOfWords;
    private String word;
    ArrayList<Object> letterCoords;
    ArrayList<String> compWords;
    Trie trie1;
    TrieNode root1;


    public GameManager() {
        this.board1 = new char[size1][size1];
        this.words = new ArrayList<>();
        this.lastAddedWord = new ArrayList<>();
        this.listOfWords = new ArrayList<String>();
        this.playerWords = new ArrayList<String>();
        this.listOfBoardPoints = wordBoardFinder(word);
        // this.tactic1 = setSearchTactic(tactic1);
    }

    @Override
    public int addWord(String word, int player) {
        // TODO - implement me!
        // add casing (where there must also be a case of !=
        if (!dictionary.contains(word)) {
            System.out.println("The word is not in the dictionary.");
            return 0;
        } else if (word.length() < 4) {
            System.out.println("This word is less than 4 letters, and therefore is too short.");
            return 0;
        } else if (!listOfWords.contains(word)) {
            System.out.println("The word does not exist in the number of possible words.");
            return 0;
        } else if (playerWords.contains(word)) {
            System.out.println("This word has already been found.");
            return 0;
        }
        playerWords.add(word);
        for (int i = 0; i < listOfWords.size(); i++) {
            // System.out.println("I got to listOfWords.");
            if (listOfWords.get(i).equals(word)) {
                // System.out.println("WBF: " + wordBoardFinder(word));
                lastAddedWord = wordBoardFinder(word);
                // System.out.println(lastAddedWord);
            }
        }
        // System.out.println("word = " + word);
        // System.out.println("word Length: " + word.length());
        // System.out.println("WL-3: " + (word.length() - 3));
        scoreToAdd = word.length() - 3;
        // System.out.println("scoresToAdd: " + scoreToAdd);
        scores[player] += scoreToAdd;
        // System.out.println("the score of Player " + player + ": " + scores[player]);

        return scoreToAdd;
    }

    @Override
    public Collection<String> getAllWords() {
        // System.out.println(tactic1);
        switch (tactic1) {
            case SEARCH_DICT:
                return searchDict();
            case SEARCH_BOARD:
                return searchBoard();
        }
        return null;
    }

    public Collection<String> searchBoard() {
        boolean[][] wordsTraversed = new boolean[board1.length][board1[0].length];
        for (int x = 0; x < board1.length; x++) {
            for (int y = 0; y < board1[0].length; y++) {
                depthFS("", wordsTraversed, x, y, listOfWords);
            }
        }
        // Use a HashSet to remove duplicates while preserving the list's order
        Set<String> uniqueWords = new LinkedHashSet<>(listOfWords);
        listOfWords.clear();
        listOfWords.addAll(uniqueWords);

        System.out.println("searchBoard");
        System.out.println(listOfWords);
        return listOfWords;
    }

    public Collection<String> searchDict() {
        for (String word : dictionary) {
            // System.out.println("word: " + word);
            for (int x = 0; x < board1.length; x++) {
                for (int y = 0; y < board1[0].length; y++) {
                    boolean[][] wordsTraversed2 = new boolean[board1.length][board1[0].length];
                    if (iteratorSearch(wordsTraversed2, word, x, y, 0) && (word.length() >= 4)) {
                        listOfWords.add(word);
                    }
                }
            }
        }
        Set<String> uniqueWords = new LinkedHashSet<>(listOfWords);
        listOfWords.clear();
        listOfWords.addAll(uniqueWords);

        System.out.println("searchDict");
        System.out.println(listOfWords);
        return listOfWords;
    }


    public boolean iteratorSearch(boolean[][] wordsTraversed, String word, int x, int y, int index) {
        // System.out.println("word:" + word);
        if (x < 0 || x >= board1.length) {
            return false;
        }
        if (y < 0 || y >= board1[0].length) {
            return false;
        }
        if (wordsTraversed[x][y]) {
            return false;
        }
        char letter = board1[x][y];
        char currentCharacter = word.charAt(index);
        // System.out.println(board1[x][y]);
        if (letter != currentCharacter) {
            return false;
        }
        if (index + 1 == word.length()) {
            return true;
        }
        wordsTraversed[x][y] = true;
        index++;
        // System.out.println("THE INDEX IS WORKING");
        int[] allX = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] allY = {-1, -1, 0, 1, 1, 1, 0, -1};

        for (int i = 0; i < allX.length; i++) {
            // System.out.println("it got into the loop: " + i);
            // System.out.println("current y: " + y);
            int updatedValueY = y + allY[i];
            // System.out.println("updated Y Val: " + updatedValueY);
            // System.out.println("current x: " + x);
            int updatedValueX = x + allX[i];
            // System.out.println("updated X Val: " + updatedValueX);
            if (iteratorSearch(wordsTraversed, word, updatedValueX, updatedValueY, index)) {
                wordsTraversed[x][y] = false;
                return true;
            }
        }
        wordsTraversed[x][y] = false;
        return false;
    }
    public List<Point> wordBoardFinder(String word) {
        for (int x = 0; x < board1.length; x++) {
            for (int y = 0; y < board1[0].length; y++) {
                boolean[][] wordsTraversed3 = new boolean[board1.length][board1[0].length];
                ArrayList<Point> listOfBoardPoints = new ArrayList<Point>();
                if (findPoints(wordsTraversed3, word, x, y, 0, listOfBoardPoints)) {
                    System.out.println("List of Board Points: " + listOfBoardPoints);
                    return listOfBoardPoints;
                }
            }
        }
        return null;
    }

    public boolean findPoints(boolean[][] wordsTraversed, String word, int x, int y, int index, ArrayList<Point> listOfBoardPoints) {
        if (x < 0 || x >= board1.length) {
            return false;
        }
        if (y < 0 || y >= board1[0].length) {
            return false;
        }
        if (wordsTraversed[x][y]) {
            return false;
        }

        char letter = board1[x][y];
        char currentCharacter = word.charAt(index);
        // System.out.println(board1[x][y]);
        if (letter != currentCharacter) {
            return false;
        }
        listOfBoardPoints.add(new Point(x, y));
        if (index + 1 == word.length()) {
            return true;
        }
        wordsTraversed[x][y] = true;
        index++;
        // System.out.println("THE INDEX IS WORKING");
        int[] allX = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] allY = {-1, -1, 0, 1, 1, 1, 0, -1};

        for (int i = 0; i < allX.length; i++) {
            // System.out.println("it got into the loop: " + i);
            // System.out.println("current y: " + y);
            int updatedValueY = y + allY[i];
            // System.out.println("updated Y Val: " + updatedValueY);
            // System.out.println("current x: " + x);
            int updatedValueX = x + allX[i];
            // System.out.println("updated X Val: " + updatedValueX);
            if (findPoints(wordsTraversed, word, updatedValueX, updatedValueY, index, listOfBoardPoints)) {
                wordsTraversed[x][y] = false;
                return true;
            }
        }
        wordsTraversed[x][y] = false;
        return false;
    }

//    public Collection<String> searchDict() {
////        GameDictionary gameDictionary = new GameDictionary();
////        Iterator<String> stringIterator = gameDictionary.iterator();
////        while (stringIterator.hasNext()) {
////            String dictionary =
////        }
//        boolean[][] wordsTraversed2 = new boolean[board1.length][board1[0].length];
//        ArrayList<String> listOfWords2 = new ArrayList<>();
//        for (String word : dictionary) {
//            System.out.println("word: " + word);
//            for (int x = 0; x < board1.length; x++) {
//                for (int y = 0; y < board1[0].length; y++) {
//                    /*
//                    if (iteratorSearch(listOfPoints, word, null)) {
//                        listOfWords2.add(word);
//                    }
//
//                     */
//                }
//            }
//        }
//        Set<String> uniqueWords = new LinkedHashSet<>(listOfWords);
//        listOfWords.clear();
//        listOfWords.addAll(uniqueWords);
//
//        return listOfWords;
//    }


/*
    public boolean iteratorSearch(List<Point> readPoints, String word, Point coordinate) {
        if (word.isEmpty()) {
            lastAddedWord = readPoints;
            return true;
        }
        char letter = word.charAt(0);
        HashSet<Point> potentials = _
        if (potentials == null) {
            if (!readPoints.isEmpty()) {
                readPoints.remove(readPoints.size() -1);
            }
            return false;
        }

        boolean pathWorks = false;

        if (coordinate == null) {
            Iterator<Point> runThroughPoints = potentials.iterator();
            while(runThroughPoints.hasNext()) {
                Point nextPoint = runThroughPoints.next();
                readPoints.add(nextPoint);
                pathWorks = pathWorks || iteratorSearch(readPoints, word.substring(1), nextPoint);
                readPoints.remove(nextPoint);
            }
        }
        else {
            // looks through all possible points
            ArrayList<Point> potenPoints = getNeighbors(coordinate);
            for (Point point : potenPoints) {
                if (!(readPoints.contains(coordinate)) && (potentials.contains(point))) {
                    readPoints.add(point);
                    pathWorks = pathWorks || iteratorSearch(readPoints, word.substring(1), nextPoint);
                    readPoints.remove(point);
                }
            }
        }

        return pathWorks;
    }

 */

//    public void iteratorSearch(String tempString, boolean[][] wordsTraversed, String word, int x, int y, int index, ArrayList<String> list) {
//        // System.out.println("word:" + word);
//        if (x < 0 || x >= board1.length) {
//            return;
//        }
//        if (y < 0 || y >= board1[0].length) {
//            return;
//        }
//        if (wordsTraversed[x][y]) {
//            return;
//        }
//        // System.out.println(board1[x][y]);
//        if (board1[x][y] != word.charAt(index)) {
//            return;
//        }
//        char temporaryLetter = board1[x][y];
//        tempString += temporaryLetter;
//        System.out.println("tempstring: " + tempString);
//        // System.out.println("the right letter has been passed in");
//        // System.out.println(list);
//
//        System.out.println("board[x][y]: " + board1[x][y]);
//        System.out.println("word@index: " + word.charAt(index));
//        System.out.println("index: " + index);
//        System.out.println("word.length: " + word.length());
//        if ((index + 1) == word.length()) {
//            System.out.println("IT GOT PAST STAGE 1");
//            if (board1[x][y] == word.charAt(index)) {
//                System.out.println("IT GOT PAST STAGE 2");
//                if (word.length() >= 4) {
//                    System.out.println("IT GOT PAST STAGE 3");
//                    list.add(word);
//                    System.out.println(list);
//                }
//            }
//        }
//        /*
//        if ((board1[x][y] == word.charAt(index)) && ((index + 1) == word.length())) {
//            //  && word.length() >= 4
//            list.add(word);
//        }
//        *
//         */
//        wordsTraversed[x][y] = true;
//
//        /*
//        if (dictionary.contains(tempString) && tempString.length() >= 4) {
//            list.add(tempString);
//        }
//
//         */
//
//
////        char temporaryLetter = board1[x][y];
////        tempString += temporaryLetter;
////
////        if (dictionary.contains(tempString) && tempString.length() >= 4) {
////            list.add(tempString);
////        }
////        wordsTraversed[x][y] = true;
////        // after adding, all the cells near this must be checked as well
////        int[] allX = {0, 1, 1, 1, 0, -1, -1, -1};
////        int[] allY = {-1, -1, 0, 1, 1, 1, 0, -1};
////
////        for (int i = 0; i < allX.length; i++) {
////            int updatedValueY = y + allY[i];
////            int updatedValueX = x + allX[i];
////            depthFS(tempString, wordsTraversed, updatedValueX, updatedValueY, list);
////        }
////        wordsTraversed[x][y] = false;
//        /*
//        if (dictionary.contains(word) && word.length() >= 4) {
//            list.add(word);
//        }
//
//         */
//
////        if(dictionary.isPrefix(word)) {
////            wordsTraversed[x][y] = true;
////        }
////        index++;
//
//
//        /*
//        if (iteratorSearch(wordsTraversed, word,x + 1, y + 1, index, list) ||
//                iteratorSearch(wordsTraversed, word, x + 1, y, index, list) ||
//                iteratorSearch(wordsTraversed, word, x + 1, y - 1, index, list) ||
//                iteratorSearch(wordsTraversed, word, x - 1, y + 1, index, list) ||
//                iteratorSearch(wordsTraversed, word, x - 1, y, index, list) ||
//                iteratorSearch(wordsTraversed, word, x - 1, y - 1, index, list) ||
//                iteratorSearch(wordsTraversed, word, x, y + 1, index, list) ||
//                iteratorSearch(wordsTraversed, word, x, y - 1, index, list)) {
//            return true;
//        }
//
//         */
//    }




    public void depthFS(String tempString, boolean[][] wordsTraversed, int x, int y, ArrayList<String> list) {
        if (x < 0 || x >= board1.length) {
            return;
        }
        if (y < 0 || y >= board1[0].length) {
            return;
        }
        if (wordsTraversed[x][y]) {
            return;
        }
        // System.out.println(list);
        if(!(dictionary.isPrefix(tempString))) {
            return;
        }

        char temporaryLetter = board1[x][y];
        tempString += temporaryLetter;

        if (dictionary.contains(tempString) && tempString.length() >= 4) {
            list.add(tempString);
        }
        wordsTraversed[x][y] = true;
        // after adding, all the cells near this must be checked as well
        int[] allX = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] allY = {-1, -1, 0, 1, 1, 1, 0, -1};

        for (int i = 0; i < allX.length; i++) {
            int updatedValueY = y + allY[i];
            int updatedValueX = x + allX[i];
            depthFS(tempString, wordsTraversed, updatedValueX, updatedValueY, list);
        }
        wordsTraversed[x][y] = false;
    }

    @Override
    public char[][] getBoard() {
        return board1;
    }

    @Override
    public List<Point> getLastAddedWord() {
        return lastAddedWord;
    }

    public ArrayList<Point> getNeighbors (Point p){
        ArrayList<Point> neighbors = new ArrayList<Point>();
        int x = p.x;
        int y = p.y;
        System.out.println("x: " + x + "      " + "y: " + y);
        Point b = new Point(x-1, y-1);
        neighbors.add(b);
        neighbors.add(new Point(x, y-1));
        neighbors.add(new Point(x+1, y-1));
        neighbors.add(new Point(x-1, y));
        neighbors.add(new Point(x+1, y));
        neighbors.add(new Point(x-1, y+1));
        neighbors.add(new Point(x, y+1));
        neighbors.add(new Point(x+1, y+1));

        for(int i = neighbors.size() -1; i>= 0; i--){
            int ix = (int) neighbors.get(i).getX();
            int iy = (int) neighbors.get(i).getY();
            System.out.println("x: " + ix + "      " + "y: " + iy);
            System.out.println("size = " + size1);
            // iterate through scores
    }
        System.out.println("\n\n\n");
        for(int i = neighbors.size(); i>= 0; i--){
            int ix = (int) neighbors.get(i).getX();
            int iy = (int) neighbors.get(i).getY();
            System.out.println("x: " + ix + "      " + "y: " + iy);
        }
        return neighbors;
    }

    @Override
    public int[] getScores() {
        return scores;
    }
    public void newGame(int size, int numPlayers, String cubeFile, BoggleDictionary dict) throws IOException {
        size1 = size;
        board1 = new char[size][size];
        numPlayers1 = numPlayers;
        scores = new int[numPlayers];
        words = new ArrayList<ArrayList<String>>();
        tactic1 = SEARCH_DEFAULT;
        ArrayList<String> cubes = new ArrayList<>();
        dictionary = (GameDictionary) dict;
        letterCoords = new ArrayList<Object>();
        readCubeFile(cubeFile);

        for(int i = 0; i < numPlayers; i++) {
            scores[i] = 0;
        }
        BufferedReader buff = new BufferedReader(new FileReader(cubeFile));
        String line;
        try {
            if ((line = buff.readLine()) == null) {
                buff.close();
                throw new IllegalArgumentException("cubeFile is empty");
            }
            do {
                if (line.length() != 6) {
                    buff.close();
                    throw new IllegalArgumentException("at least one of the cubes is not the right length (6)");
                }
                cubes.add(line);
            } while ((line = buff.readLine()) != null && !line.isEmpty());


            if (cubes.size() < (size1 * size1) - 1) {
                throw new IllegalArgumentException("Not enough cubes in cubeFile to complete board");
            }
            buff.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileNotFoundException("file not found");
        }

        // System.out.println("I got here.");
        // System.out.println(cubes);
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size1; j++) {
                // System.out.println("second round");
                int cubeChosen = (int) (Math.random() * cubes.size());
                char letter = Character.toLowerCase(cubes.get(cubeChosen).charAt((int) (Math.random() * 6)));
                board1[i][j] = letter;
                Point p = new Point(j, i);
                letterCoords.add(letter);
                letterCoords.add(p);
                //letterCoords.put(p, letter);
                cubes.remove(cubeChosen);
                // System.out.println(cubes);
            }
            // System.out.println("first round ended");
        }
        // System.out.println("I got here.");
        for (int i = 0; i < numPlayers1; i++) {
            words.add(new ArrayList<String>());
        }
        // System.out.println(dictionary);
        trie1 = dictionary.getTrie();
        root1 = dictionary.getTrieRoot();
        compWords = new ArrayList<String>();
    }
    public void printBoard() {
        for(int i = 0; i < board1.length; i++){
            for(int j = 0; j < board1[0].length; j++){
                System.out.print("["+Character.toUpperCase(board1[i][j])+"]");
            }
            System.out.println();
        }
    }
    public void printBoard(char[][] board) {
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++) {
                System.out.print("["+Character.toUpperCase(board1[i][j])+"]");
            }
            System.out.println();
        }
    }
    public void printScores() {

    }

    public void addedWordBoard(){
        char[][] altBoard = new char [size1][size1];
        for(int i = 0; i < size1; i++){
            for(int j = 0; j < size1; j++) {
                altBoard[i][j] = board1[i][j];
            }
        }
    }

    public void lowercaseBoard() {
        for (int a = 0; a < board1.length; a++) {
            for (int b = 0; b < board1[0].length; b++) {
                boolean catch22 = false;
                for (int z = 0; z < lastAddedWord.size(); z++) {
                    int x = (int) lastAddedWord.get(z).getX();
                    // System.out.println("XValue: " + x + ", AValue: " + a);
                    int y = (int) lastAddedWord.get(z).getY();
                    // System.out.println("YValue: " + y + ", BValue: " + b);
                    if ((a == x) && (b == y)) {
                        // System.out.println("I got here.");
                        System.out.print("["+Character.toLowerCase(board1[a][b])+"]");
                        catch22 = true;
                        break;
                    }
                }
                if (!catch22) {
                    System.out.print("["+Character.toUpperCase(board1[a][b])+"]");
                }
            }
            System.out.println();
        }
    }



    // @Override
    // public void newGame(int size, int numPlayers, String cubeFile, BoggleDictionary dict) throws IOException {
    //         size1 = size;
    //         board1 = new char[size][size];
    //         numPlayers1 = numPlayers;
    //         scores = new int[numPlayers];
    //         words = new ArrayList<ArrayList<String>>();
    //         tactic1 = SEARCH_DEFAULT;
    //         ArrayList<String> cubes = new ArrayList<>();
    //         dictionary = (GameDictionary) dict;
    //         letterCoords = new ArrayList<Object>();
    //         readCubeFile(cubeFile);

    //         for(int i = 0; i < numPlayers; i++) {
    //             scores[i] = 0;
    //         }
            
    //         try {
    //             Scanner in = new Scanner(file);

    //             if(!in.hasNextLine()) {
    //                 in.close();
    //                 throw new IllegalArgumentException("cubeFile is empty");
    //             }
    //             while(in.hasNextLine()) {
    //                 if(!in.hasNextLine()) break;
    //                 String line = in.nextLine();
    //                 if(line.length() != 6) {
    //                     in.close();
    //                     throw new IllegalArgumentException("");
    //                     //throw error - not right amount of faces in cube/ not a valid cube
    //                 }
    //                 if(line == "") break;
    //                 cubes.add(line);
    //             }
    //             if(cubes.size() < (size1 * size1)) {
    //                 throw new IllegalArgumentException("Not enough cubes in cubeFile to complete board");
    //             }
    //             in.close();
    //         }
    //         catch(Exception e){
    //             throw new FileNotFoundException("file not found");
    //         }

    //         for(int i = 0; i < size1; i++) {
    //             for(int j = 0; j < size1; j++){
    //                 int cubeChosen = (int) (Math.random() * cubes.size());
    //                 char letter = Character.toLowerCase(cubes.get(cubeChosen).charAt((int)(Math.random() * 6)));
    //                 board1[i][j] = letter;
    //                 Point p = new Point(j, i);
    //                 letterCoords.add(letter);
    //                 letterCoords.add(p);
    //                 //letterCoords.put(p, letter);
    //                 cubes.remove(cubeChosen);
    //             }
    //         }
    //         for(int i = 0; i < numPlayers1; i++){
    //             words.add(new ArrayList<String>());
    //         }
    //         trie1 = dictionary.getTrie();
    //         root1 = dictionary.getTrieRoot();
    //         compWords = new ArrayList<String>();

    //     }

    public void readCubeFile(String cubeFile) throws IOException {
        FileReader reader = new FileReader(cubeFile);
        BufferedReader buff = new BufferedReader(reader);
        String file = "";
        String mock;
            while ((mock = buff.readLine()) != null) {
                file += mock + "\n";
            }
        buff.close();
        reader.close();
        System.out.println(file);
    }

    @Override
    public void setGame(char[][] board) {
        // TODO - implement me!
       for(int i = 0; i < size1; i++) {
        for(int j = 0; j < size1; j++) {
            board1[i][j] = board[i][j];
        }
       }

       for(int i = 0; i < numPlayers1; i++) {
        scores[i] = 0;
        words.get(i).clear();

       }
    }

    @Override
    public void setSearchTactic(SearchTactic tactic) {
        // TODO - implement me!
        if (tactic == SearchTactic.SEARCH_BOARD || tactic == SearchTactic.SEARCH_DICT) {
            tactic1 = tactic;
        }
        else {
            tactic1 = SEARCH_DEFAULT;
        }
    }
}

