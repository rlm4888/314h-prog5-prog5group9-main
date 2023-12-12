package assignment;

import java.util.*;
import java.io.*;

public class Trie {

    TrieNode root1;

    public void insert(TrieNode root) {
        root1 = root;
    }

    public void insert(String key) {
        TrieNode currentNode = root1;
        key = key.toLowerCase();

        for(int i = 0; i < key.length(); i++) {
            char currentLetter = key.charAt(i);

            if(currentNode.children.get(currentLetter) == null){
                TrieNode newChild = new TrieNode(currentLetter);
                currentNode.children.put(currentLetter, newChild);

            }
            currentNode = currentNode.children.get(currentLetter);
        }
        currentNode.isEndOfWord = true;
    }
    public boolean search(String key) {
        TrieNode currentNode = root1;
        for(int i = 0; i < key.length(); i++){
            char currentLetter = key.charAt(i);

            if(currentNode.children.get(currentLetter) == null) {
                return false;
            }

            currentNode = currentNode.children.get(currentLetter);
        }
         return (currentNode.isEndOfWord);
    }
    public boolean isPrefix(String key) {
        TrieNode currentNode = root1;

        for(int i = 0; i < key.length(); i++) {
            char currentLetter = key.charAt(i);

            if(currentNode.children.get(currentLetter) == null) {
                return false;
            }

            currentNode = currentNode.children.get(currentLetter);
        }
        return true;
    }
}
