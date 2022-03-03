/*
 * HuffmanNode class for Huffman Coding Assignment (Assignment 10)
 *
 * @author  Allen Bao
 * @class   CS 211
 * @version 1.0
 * @since   2022-02-22
 */

// Import libraries
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HuffmanNode implements Comparable<HuffmanNode> {
    // Public fields
    public int frequency;
    public char character;
    public HuffmanNode left;
    public HuffmanNode right;
    public boolean ifLeaf;

    // This constructor is made to create all the leaf nodes, all of which store the frequency of a certain character.
    public HuffmanNode(char character, int frequency) {
        this.frequency = frequency;
        this.character = character;
        ifLeaf = true;
    }

    // This constructor is made for the artificial tree nodes that make up the HuffmanTree
    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.frequency = left.frequency + right.frequency;
        this.left = left;
        this.right = right;
        ifLeaf = false;
    }

    // This method returns a map that counts the number of appearances for each character
    public static Map<Character, Integer> getCounts(FileInputStream input) throws IOException {
        // Create map to hold the character counts
        Map<Character, Integer> characterCountMap = new HashMap<>();

        // While there's text left to read, read the first byte
        while(input.available() > 0) {
            // We need to cast the read data to a char because the read() method returns an int
            Character characterRead = (char) input.read();

            // If the character is already in the map, update the count by 1
            if (characterCountMap.containsKey(characterRead)) {
                characterCountMap.replace(characterRead, characterCountMap.get(characterRead)+1);
            } else { // if a character isn't in the map yet, add it to the map with a counter of 1
                characterCountMap.put(characterRead, 1);
            }
        }

        // Put EOF (end of file) character
        characterCountMap.put((char)256, 1);

        // Return the map
        return characterCountMap;
    }

    // Return if the node is a leaf node or not
    public boolean isLeaf() {
        return ifLeaf;
    }

    // Method to "sort" items in the PriorityQueue
    // It essentially determines which node has a higher priority.
    // We sort the items in reverse order in order to build the tree from the leaf nodes up to the root node.
    public int compareTo(HuffmanNode o) {
        // Another way to do this would just be to return the result from subtracting o.frequency from frequency
        if (frequency > o.frequency) {
            return 1;
        } else if (frequency == o.frequency) {
            return 0;
        }
        return -1;
    }

}
