package assignment;

import java.util.*;
import java.io.*;

public class TrieNode {
    TreeMap<Character, TrieNode> children;
    char letter1;

    boolean isEndOfWord; 

    public TrieNode(){
        children = new TreeMap<Character, TrieNode>();
        letter1 = 0;
    }

    public TrieNode(char letter) {
        letter1 = letter;
        children = new TreeMap<Character, TrieNode>();
    }

    public TreeMap<Character, TrieNode> getChildren(){
        return children;
    }
};
