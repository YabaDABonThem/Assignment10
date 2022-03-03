// Allen Bao
// CS 211
// Instructor: Craig Niiyama
// 2/22/2022
// HuffmanTree class for Huffman Coding Assignment (Assignment 10)

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    // private fields
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

    // this method
    public void priorityQueueToTree(PriorityQueue<HuffmanNode> priorityQueue) {
        if (priorityQueue.isEmpty()) {
            throw new IllegalArgumentException();
        }
        while (priorityQueue.size() > 1) {
            priorityQueue.add(new HuffmanNode(priorityQueue.poll(), priorityQueue.poll()));
        }
        // save root node in private field
        huffmanTree = priorityQueue.poll();
    }

    public void buildMap(Map<Character, String> encodingMap, HuffmanNode root, String prefix) {
        if (root == null) {
            return;
        } else if (root.isLeaf()) {
            encodingMap.put(root.character, prefix);
            // don't need to return here, since the left and right nodes of a leaf node are null
        }
        buildMap(encodingMap, root.left, prefix+"0");
        buildMap(encodingMap, root.right, prefix+"1");
    }

    public StringBuilder compress(InputStream inputFile) throws IOException { // inputFile is a text file
        // create StringBuilder to hold the compressed text
        StringBuilder compressedText = new StringBuilder();
        // create map to store the leaf nodes from traversing the tree
        Map<Character, String> encodingMap = new HashMap<>();
        // get the map of encoded map, where every character is mapped to their new binary representation
        priorityQueueToTree(sortItems(counts));
        buildMap(encodingMap, huffmanTree,"");
        // loop through all characters in the text and add their new binary representation
        // to the StringBuilder we created earlier
        //System.out.println(HuffmanNode.getCounts(inputFile4));

        while(inputFile.available() > 0) {
            compressedText.append(encodingMap.get((char) inputFile.read()));
        }
        // return the encoded text
        //System.out.println(encodingMap);
        return compressedText;
    }

    public StringBuilder decompress(StringBuilder inputString) {
        StringBuilder fileText = new StringBuilder();
        StringBuilder characterText = new StringBuilder();
        HuffmanNode currentNode = huffmanTree;
        for(int i = 0; i < inputString.length(); ++i) {
            // if 1, go right; if 0, go left
            if (inputString.charAt(i) == '0') {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
            if (currentNode.isLeaf()) {
                fileText.append(currentNode.character);

                currentNode = huffmanTree;
            }

        }
        System.out.println(fileText);
        return fileText;
    }

    public String printSideways() {
        return null;
    }
}
