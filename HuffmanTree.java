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

    public HuffmanTree(Map<Character, Integer> counts) { // Constructor
        priorityQueueToTree((sortItems(counts)));
    }

    public PriorityQueue<HuffmanNode> sortItems(Map<Character, Integer> characterCountMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        Collection<Character> characters = characterCountMap.keySet();
        for (Character key : characters) {
            priorityQueue.add(new HuffmanNode(key, characterCountMap.get(key)));
        }
        System.out.println(priorityQueue);
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

    public void traverseTree(Map<Character, String> treeNodes, HuffmanNode root, String prefix) {
        // go left until a leaf node
        if (root == null) {
            return;
        } else if (root.isLeaf()) {
            treeNodes.put(root.character, prefix);
        }
        traverseTree(treeNodes, root.left, prefix+"0");
        traverseTree(treeNodes, root.right, prefix+"1");
    }

    public StringBuilder compress(InputStream inputFile) throws IOException { // inputFile is a text file
        StringBuilder fileText = new StringBuilder();
        Map<Character, String> treeNodes = new HashMap<>();
        // traverseTree(treeNodes, priorityQueueToTree(sortItems(HuffmanNode.getCounts(inputFile))));
        while(inputFile.available() > 0) {
            fileText.append();
        }
        return null;
    }

    public StringBuilder decompress(StringBuilder inputString) {
        return null;
    }

    public String printSideways() {
        return null;
    }
}
