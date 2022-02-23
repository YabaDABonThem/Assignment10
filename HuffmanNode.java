// Allen Bao
// CS 211
// Instructor: Craig Niiyama
// 2/22/2022
// HuffmanNode class for Huffman Coding Assignment (Assignment 10)

import java.io.FileInputStream;
import java.util.Map;

public class HuffmanNode implements Comparable<HuffmanNode>{
    public int frequency;
    public char character;
    public HuffmanNode left;
    public HuffmanNode right;

    public static Map<Character, Integer> getCounts(FileInputStream input) {
        return null;
    }

    public boolean isLeaf() {
        return false;
    }
}
