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

    public HuffmanTree priorityQueueToTree(PriorityQueue<HuffmanNode> priorityQueue) {
        for (HuffmanNode i : priorityQueue) {

        }
    }

    public boolean isLeaf() {
        return false;
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
