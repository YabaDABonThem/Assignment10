// Allen Bao
// CS 211
// Instructor: Craig Niiyama
// 2/22/2022
// HuffmanNode class for Huffman Coding Assignment (Assignment 10)

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HuffmanNode implements Comparable<HuffmanNode> {
    // public fields
    public int frequency;
    public char character;
    public HuffmanNode left;
    public HuffmanNode right;
    public boolean ifLeaf;

    // this constructor is made to create all the leaf nodes, all of which store the frequency of a certain character.
    public HuffmanNode(char character, int frequency) {
        this.frequency = frequency;
        this.character = character;
        ifLeaf = true;
    }

    // this constructor is made for the artificial tree nodes that make up the HuffmanTree
    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.frequency = left.frequency + right.frequency;
        this.left = left;
        this.right = right;
        ifLeaf = false;
    }

    // this method returns a map that counts the number of appearances for each character
    public static Map<Character, Integer> getCounts(FileInputStream input) throws IOException {

        Map<Character, Integer> characterCountMap = new HashMap<Character, Integer>();
        while(input.available() > 0) {
            Character characterRead = (char) input.read();

            if (characterCountMap.containsKey(characterRead)) {
                characterCountMap.replace(characterRead, characterCountMap.get(characterRead)+1);
            } else {
                characterCountMap.put(characterRead, 1);
            }
        }
        // put EOF (end of file) character
        characterCountMap.put((char)256, 1);
        System.out.println(characterCountMap);
        return characterCountMap;
    }

    public boolean isLeaf() {
        return ifLeaf;
    }

    // Code so program doesn't break (need to implement compareTo method)
    // Method to "sort" items in the PriorityQueue
    // We sort the items in reverse order in order to build the tree from the leaf nodes up to the root node.
    public int compareTo(HuffmanNode o) {
        if (frequency > o.frequency) {
            return 1;
        } else if (frequency == o.frequency) {
            return 0;
        }
        return -1;
    }

}
