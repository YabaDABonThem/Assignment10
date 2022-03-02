Huffman Coding Assignment
For CS211, Bellevue College, Fall 2013  
(original from Marty Stepp, UW CSE 143, modified for Bellevue College) 
Discord Yabadaba#4444 for full README
 
For this last CS211 assignment, you are to submit the following Java code for a Huffman Tree data structure, along with the Huffman Node structure that you have used.   
 
For the Huffman Nodes, read through the description of Huffman compression below and the following organization will become apparent.  We’re building a binary tree, where the “data” is a count of the frequency of each character. 
public class HuffmanNode implements Comparable<HuffmanNode> {     public int frequency;     public char character;     public HuffmanNode left;     public HuffmanNode right; 
The Huffman Node class will also need a boolean isLeaf() method, plus a static method to actually provide a count of the characters in an input file, and place those counts into a Map, with character as the unique key mapped into the integer counts of that character: 
		 public static Map<Character, Integer> getCounts(FileInputStream input) 
 
Your Huffman Tree class must have the following defined: 
public HuffmanTree(Map<Character, Integer> counts) // Constructor  	public StringBuilder compress(InputStream inputFile) // inputFile is a text file public StringBuilder decompress(StringBuilder inputString) //inputString 1’s & 0’s public String printSideways()  	// as per the method presented in Chapter 17. 
Note that the input file here can actually be any format, but plain text will allow us to see what we’re doing.  The compress method returns a string of 1’s and 0’s (bits) as per the description below.  The decompress method can take that string of 1’s and 0’s, use the Huffman tree structure (different for every text file) and recreate the text file from those bits.  Finally, the printSideways here is similar to what is presented in Chapter 17 of Building Java Programs, but in this case we build a string to return into a graphical text area.  You should actually use the String Builder class, as it is mutable.   
 
We normally don’t use System.out calls in a GUI.  But I understand they are useful during debugging.  You must comment out all System.out calls before submitting your work.  The client program (HuffmanGUI.java) is provided, and you need to meet these specifications listed above.  I expect two files to be submitted (HuffmanNode.java and HuffmanTree.java) which I will copy into my Eclipse folder and run the provided GUI for testing. 
Huffman coding is an algorithm devised by David A. Huffman of MIT in 1952 for compressing text data to make a file occupy a smaller number of bytes.  This relatively simple compression algorithm is powerful enough that variations of it are still used today in computer networks, fax machines, modems, HDTV, and other areas. 
Normally text data is stored in a standard format of 8 bits per character, commonly using an encoding called ASCII that maps every character to a binary integer value from 0-255.  The idea of Huffman coding is to abandon the rigid 8-bits-percharacter requirement and use different-length binary encodings for different characters.  The advantage of doing this is that if a character occurs frequently in the file, such as the letter 'e', it could be given a shorter encoding (fewer bits), making the file smaller.  The tradeoff is that some characters may need to use encodings that are longer than 8 bits, but this is reserved for characters that occur infrequently, so the extra cost is worth it. 
 
The table below compares ASCII values of various characters to possible Huffman encodings for the text of Shakespeare's Hamlet.  First step is to count the number of occurrences of each character in the text.  Frequent characters such as space and 'e' should have short encodings, while rarer ones like 'z' have longer ones. 
The steps involved in Huffman coding a given text source file into a destination compressed file are the following: 
1.	Examine the source file's contents and count the number of occurrences of each character. 
2.	Place each character and its frequency (count of occurrences) into a sorted "priority" queue. 
3.	Convert the contents of this priority queue into a binary tree with a particular structure. 
4.	Traverse the tree to discover the binary encodings of each character. 
5.	Re-examine the source file's contents, and for each character, output the encoded binary version of that character to the destination file. 

Decoding a File: 
You can use a Huffman tree to decode text that was compressed with its encodings.  The decoding algorithm is to read each bit from the file, one at a time, and use this bit to traverse the Huffman tree.  If the bit is a 0, you move left in the tree.  If the bit is 1, you move right.  You do this until you hit a leaf node.  Leaf nodes represent characters, so once you reach a leaf, you output that character.  For example, suppose we are asked to decode a file containing the following bits: 
101101000110111011 
Using the Huffman tree, we walk from the root until we find characters, then we output them and go back to the root.   

When the selected input files get very large (try Moby.txt) the display will take forever (maybe 20 minutes) to load.  This is a well know problem with the Java set text methods, as they require constant refresh, rebuffer, and reload.  So I provide a check box on the GUI to skip the text displays, so you can see your compression can really run in under a second even for huge files.  In real compression algorithms, the 100011100… bit stream would actually go out over the wire as binary data, and not be converted into character, strings, and graphics (so slow). 

Deliverables: 1) HuffmanNode.java 2) HuffmanTree.java 3) QA document with screenshots showing your working program.