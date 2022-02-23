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
    public int frequency;
    public char character;
    public HuffmanNode left;
    public HuffmanNode right;

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



    public boolean isLeaf() {
        return false;
    }

    // Code so program doesn't break (need to implement compareTo method)
    @Override
    public int compareTo(HuffmanNode o) {
        return 0;
    }
}
