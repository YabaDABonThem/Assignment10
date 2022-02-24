// Allen Bao
// CS 211
// Instructor: Craig Niiyama
// 2/22/2022
// HuffmanTree class for Huffman Coding Assignment (Assignment 10)

import java.io.InputStream;
import java.util.Collection;
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

    public StringBuilder compress(InputStream inputFile) { // inputFile is a text file

        return null;
    }

    public StringBuilder decompress(StringBuilder inputString) {
        return null;
    }

    public String printSideways() {
        return null;
    }
}
