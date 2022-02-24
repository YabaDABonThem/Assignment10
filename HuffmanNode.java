// Allen Bao
// CS 211
// Instructor: Craig Niiyama
// 2/22/2022
// HuffmanNode class for Huffman Coding Assignment (Assignment 10)

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanNode implements Comparable<HuffmanNode> {
    public int frequency;
    public char character;
    public HuffmanNode left;
    public HuffmanNode right;

    public HuffmanNode(char character, int frequency) {
        this.frequency = frequency;
        this.character = character;
    }

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
        // System.out.println(characterCountMap);
        return characterCountMap;
    }

    // Code so program doesn't break (need to implement compareTo method)
    // Method to "sort" items in the PriorityQueue
    public int compareTo(HuffmanNode o) {
        if (frequency < o.frequency) {
            return -1;
        } else if (frequency == o.frequency) {
            return 0;
        }
        return 1;
    }

}
