/*
 * HuffmanTree class for Huffman Coding Assignment (Assignment 10)
 *
 * @author  Allen Bao
 * @class   CS 211
 * @version 1.0
 * @since   2022-02-22
 */

// Import libraries
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    // Private fields
    private HuffmanNode huffmanTree;
    private Map<Character, Integer> counts;

    // Constructor
    public HuffmanTree(Map<Character, Integer> counts) {
        this.counts = counts;
    }

    // This method goes through all the characters and puts the in a priority queue in order of frequency
    // They are compared automatically by the CompareTo method in the HuffmanNode class
    public PriorityQueue<HuffmanNode> sortItems(Map<Character, Integer> characterCountMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        Collection<Character> characters = characterCountMap.keySet();
        for (Character key : characters) {
            priorityQueue.add(new HuffmanNode(key, characterCountMap.get(key)));
        }
        return priorityQueue;
    }

    // This method creates the tree by creating a bunch of HuffmanNodes that
    // just have a left and right node, and you'd keep repeating the process
    // until you just have the root node remaining. n
    public void priorityQueueToTree(PriorityQueue<HuffmanNode> priorityQueue) {
        if (priorityQueue.isEmpty()) {
            throw new IllegalArgumentException();
        }
        while (priorityQueue.size() > 1) {
            priorityQueue.add(new HuffmanNode(priorityQueue.poll(), priorityQueue.poll()));
        }
        // Save root node in private field
        huffmanTree = priorityQueue.poll();
    }

    // This method traverses the tree and adds ever character and its binary representation to a map.
    public void buildMap(Map<Character, String> encodingMap, HuffmanNode root, String prefix) {
        // Base case
        if (root == null) {
            return;
        } else if (root.isLeaf()) {
            encodingMap.put(root.character, prefix);
            // Don't need to return here, since the left and right nodes of a leaf node are null
        }
        // Recursively call its left and right nodes until you reach a leaf node.
        buildMap(encodingMap, root.left, prefix+"0");
        buildMap(encodingMap, root.right, prefix+"1");
    }

    // This method takes in an InputStream and returns a StringBuilder with the compressed text.
    public StringBuilder compress(InputStream inputFile) throws IOException { // inputFile is a text file
        // Create StringBuilder to hold the compressed text
        StringBuilder compressedText = new StringBuilder();
        // Create map to store the leaf nodes from traversing the tree
        Map<Character, String> encodingMap = new HashMap<>();

        // Get the map of encoded map, where every character is mapped to their new binary representation
        priorityQueueToTree(sortItems(counts));
        buildMap(encodingMap, huffmanTree,"");

        // Loop through all characters in the text and add their new binary representation
        // to the StringBuilder we created earlier
        while(inputFile.available() > 0) {
            compressedText.append(encodingMap.get((char) inputFile.read()));
        }
        // Return the encoded text
        return compressedText;
    }

    // This method takes in the compressed text and returns a StringBuilder containing the decompressed text
    public StringBuilder decompress(StringBuilder inputString) {
        // Create StringBuilder to hold the decompressed text
        StringBuilder fileText = new StringBuilder();
        // Create a "tree pointer" to represent where we currently are in the tree
        HuffmanNode currentNode = huffmanTree;

        // Go through all the available text
        for(int i = 0; i < inputString.length(); ++i) {
            // If the character at index i is 1, go right; if 0, go left
            if (inputString.charAt(i) == '0') {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
            // If we're currently at a leaf node, we add the corresponding character to the fileText.
            if (currentNode.isLeaf()) {
                fileText.append(currentNode.character);

                // Reset the tree pointer back to the root node.
                currentNode = huffmanTree;
            }

        }

        // Return the fileText
        return fileText;
    }

    // We weren't shown how to implement this method yet for some reason
    public String printSideways() {
        return null;
    }
}
