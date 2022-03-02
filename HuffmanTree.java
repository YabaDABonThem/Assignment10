// Allen Bao
// CS 211
// Instructor: Craig Niiyama
// 2/22/2022
// HuffmanTree class for Huffman Coding Assignment (Assignment 10)

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {

    public HuffmanTree(Map<Character, Integer> counts) { // Constructor
        priorityQueueToTree((sortItems(counts)));
    }

    public PriorityQueue<HuffmanNode> sortItems(Map<Character, Integer> characterCountMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        Collection<Character> characters = characterCountMap.keySet();
        for (Character key : characters) {
            priorityQueue.add(new HuffmanNode(key, characterCountMap.get(key)));
        }
        return priorityQueue;
    }


    public HuffmanNode priorityQueueToTree(PriorityQueue<HuffmanNode> priorityQueue) {
        if (priorityQueue.isEmpty()) {
            throw new IllegalArgumentException();
        }
        while (priorityQueue.size() > 1) {
            priorityQueue.add(new HuffmanNode(priorityQueue.poll(), priorityQueue.poll()));
        }
        // Return root node
        return priorityQueue.poll();
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
        StringBuilder fileText = new StringBuilder();
        // create map to store the leaf nodes from traversing the tree
        Map<Character, String> encodingMap = new HashMap<>();

        // unfortunately we can't mark and reset the inputFile, or else that'd be a lot quicker.
        StringBuilder originalText = new StringBuilder();
        while (inputFile.available() > 0) {
            originalText.append((char)inputFile.read());
        }
        System.out.println(originalText);
        FileOutputStream outputStream = new FileOutputStream("output.txt");
        byte[] bytes = originalText.toString().getBytes();
        outputStream.write(bytes);
        outputStream.close();

        FileInputStream inputFile2 = new FileInputStream("output.txt");
        FileInputStream inputFile3 = new FileInputStream("output.txt");
        FileInputStream inputFile4 = new FileInputStream("output.txt"); // THIS ONE FOR TESTING
        //inputFile2.mark(inputFile2.available());
        // get the map of encoded map, where every character is mapped to their new binary representation
        buildMap(encodingMap, priorityQueueToTree(sortItems(HuffmanNode.getCounts(inputFile2))), "");
        //inputFile2.reset();
        // loop through all characters in the text and add their new binary representation
        // to the StringBuilder we created earlier
        System.out.println(HuffmanNode.getCounts(inputFile4));

        while(inputFile3.available() > 0) {
            fileText.append(encodingMap.get((char) inputFile3.read()));
        }
        // return the encoded text
        System.out.println(encodingMap);
        return fileText;
    }

    public StringBuilder decompress(StringBuilder inputString) {
        return null;
    }

    public String printSideways() {
        return null;
    }
}
