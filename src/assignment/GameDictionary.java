package assignment; 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.*;
import java.io.File;
import java.io.*;

public class GameDictionary implements BoggleDictionary {

    Trie trie1;
    TrieNode root1;

    ArrayList<String> dictionaryList;

    public Trie getTrie() {
        return trie1;
    }
    public TrieNode getTrieRoot() {
        return root1;
    }

    @Override
    public boolean contains(String word) {
        return trie1.search(word);
    }

    @Override
    public boolean isPrefix(String prefix) {
        return trie1.isPrefix(prefix);
    }

    @Override
    public void loadDictionary(String filename) throws IOException {
        // check if the file is not a txt file
        File file = new File(filename);
        if (!file.canRead()) {
            throw new IllegalArgumentException("this file cannot be read");
        }
        dictionaryList = new ArrayList<String>();
        trie1 = new Trie();
        root1 = new TrieNode();
        trie1.insert(root1);
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = br.readLine()) != null) {
                trie1.insert(line);
            }
        }
    }

//        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                 trie1.insert(line);
//            }
//        }

    @Override
    public Iterator<String> iterator() {
        ArrayList<String> words = new ArrayList<String>();
        return getTreeList("", words, root1).iterator();
        //return new DictionaryIterator();
    }

    public ArrayList<String> getTreeList(String wordString, ArrayList<String> words, TrieNode node)
    {
        Set<Character> children = node.getChildren().keySet();

        if(node.letter1 != 0) {
            wordString += node.letter1;
        }
        if(node.isEndOfWord){
            words.add(wordString);
        }
        for(Character x : children) {
            getTreeList(wordString, words, node.getChildren().get(x));
        }
    
        return words;
    }

}
    // private Trie.Node getNode(String word) {
    //     Trie.Node curr = trie.getRoot();
    //     for (int i = 0; i < word.length(); i++) {
    //         char c = word.charAt(i);
    //         if (!curr.children.containsKey(c)) {
    //             return null;
    //         }
    //         curr = curr.children.get(c);
    //     }
    //     return curr;
    // }

    // public void insert(String word) {
    //     Trie.Node curr = trie.getRoot();
    //     for (int i = 0; i < word.length(); i++) {
    //         char c = word.charAt(i);
    //         if (!curr.children.containsKey(c)) {
    //             curr.children.put(c, new Trie.Node(c));
    //         }
    //         curr = curr.children.get(c);
    //     }
    //     curr.isWord = true;
    // }

    // private class DictionaryIterator implements Iterator<String> {
    //     private Trie.Node current;
    //     private StringBuilder nextWord;
    
    //     public DictionaryIterator() {
    //         this.current = trie.getRoot();
    //         this.nextWord = new StringBuilder();
    //         findNext();
    //     }
    
    //     @Override
    //     public boolean hasNext() {
    //         return nextWord != null && nextWord.length() > 0;
    //     }
    
    //     @Override
    //     public String next() {
    //         String toReturn = nextWord.toString();
    //         findNext();
    //         return toReturn;
    //     }
    
    //     private void findNext() {
    //         if (current == null) {
    //             nextWord = null;
    //             return;
    //         }
    //         Trie.Node temp = current;
    //         nextWord = new StringBuilder();
    //         findNextWord(temp, nextWord);
    //     }
    
    //     private void findNextWord(Trie.Node node, StringBuilder word) {
    //         if (node.isWord) {
    //             nextWord = word;
    //             return;
    //         }
    //         for (char c = 'a'; c <= 'z'; c++) {
    //             Trie.Node nextNode = node.children.get(c);
    //             if (nextNode != null) {
    //                 word.append(c);
    //                 findNextWord(nextNode, word);
    //                 if (nextWord != null) {
    //                     return;
    //                 }
    //                 word.deleteCharAt(word.length() - 1);
    //             }
    //         }
    //     }
    // }
    

    // @Override
    // public Iterator<String> iterator() {
    //     return null;
    //     //maybe have to override next method?
    // }

// package assignment; 
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.Iterator;
// import java.util.Map;
// import java.util.TreeMap;

// class Trie {
//     static class Node {
//         public char c;
//         public boolean isWord;
//         public Map<Character, Node> children;

//         Node(char c) {
//             this.c = c;
//             isWord = false;
//             children = new TreeMap<>();
//         }
//     }

//     private Node root;

//     public Trie() {
//         root = new Node('\0');
//     }

//     public Node getRoot() {
//         return root;
//     }
// }

// public class GameDictionary implements BoggleDictionary {

//     private String dictionary;
//     private Trie trie;

//     public GameDictionary() {
//         this.trie = new Trie();
//     }

//     @Override
//     public boolean contains(String word) {
//         Trie.Node node = getNode(word);
//         return node != null && node.isWord;
//     }

//     @Override
//     public boolean isPrefix(String prefix) {
//         return getNode(prefix) != null;
//     }

//     @Override
//     public void loadDictionary(String filename) throws IOException {
//         try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//             dictionary = " ";
//             String line;
//             while ((line = br.readLine()) != null) {
//                 dictionary += line + "\n";
//             }
//         }
//     }

//     public void insert(String word) {
//         Trie.Node curr = trie.getRoot();
//         for (int i = 0; i < word.length(); i++) {
//             char c = word.charAt(i);
//             if (!curr.children.containsKey(c)) {
//                 curr.children.put(c, new Trie.Node(c));
//             }
//             curr = curr.children.get(c);
//         }
//         curr.isWord = true;
//     }

//     private Trie.Node getNode(String word) {
//         Trie.Node curr = trie.getRoot();
//         for (int i = 0; i < word.length(); i++) {
//             char c = word.charAt(i);
//             if (!curr.children.containsKey(c)) {
//                 return null;
//             }
//             curr = curr.children.get(c);
//         }
//         return curr;
//     }

//     @Override
//     public Iterator<String> iterator() {
//         return null;
//         override methods?
//     }

// }
// package assignment; 
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.Iterator;

// class Trie {
//     static class Node {
//         public char c;
//         public boolean isWord;
//         public Node[] children;

//         Node(char c) {
//             this.c = c;
//             isWord = false;
//             children = new Node[26];
//         }
//     }

//     private Node root;

//     public Trie() {
//         root = new Node('\0');
//     }

//     public Node getRoot() {
//         return root;
//     }
// }

// public class GameDictionary implements BoggleDictionary {

//     private String dictionary;
//     private Trie trie;

//     public GameDictionary() {
//         this.trie = new Trie();
//     }

//     @Override
//     public boolean contains(String word) {
//         Trie.Node node = getNode(word);
//         return node != null && node.isWord;
//     }

//     @Override
//     public boolean isPrefix(String prefix) {
//         return getNode(prefix) != null;
//     }

//     @Override
//     public void loadDictionary(String filename) throws IOException {
//         try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//             dictionary = " ";
//             String line;
//             while ((line = br.readLine()) != null) {
//                 dictionary += line + "\n";
//             }
//         }
//     }

//     public void insert(String word) {
//         Trie.Node curr = trie.getRoot();
//         for (int i = 0; i < word.length(); i++) {
//             char c = word.charAt(i);
//             if (curr.children[c - 'a'] == null) {
//                 curr.children[c - 'a'] = new Trie.Node(c);
//                 curr = curr.children[c - 'a'];
//             }
//         }
//         curr.isWord = true;
//     }

//     private Trie.Node getNode(String word) {
//         Trie.Node curr = trie.getRoot();
//         for (int i = 0; i < word.length(); i++) {
//             char c = word.charAt(i);
//             if (curr.children[c - 'a'] == null)
//                 return null;
//             curr = curr.children[c - 'a'];
//         }
//         return curr;
//     }

//     @Override
//     public Iterator<String> iterator() {
//         return null;
//     }

// }

