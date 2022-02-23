//Marty Stepp
// Homework 8: Huffman Coding  (instructor-provided file)
//
// modified extensively for Bellevue College 
//     removed BitInputStream and BitOutputStream class, files, and operations
//     removed HuffMain and saving of counts file, tree counts are now attributes
//	   changed GUI significantly to have file, bits, and tree displayed
//     added JCheckBox due to extremely slow .setText() method for large files
//	   cleaned up some labels...
//
// This graphical client program interacts with the user to compress and
// decompress files using your HuffmanTree class.

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class HuffmanGUI implements ActionListener {
    // Runs the program and starts the GUI.
    public static void main(String[] args) {
        new HuffmanGUI().start();
    }
    
    // font size for input/output file text
    private static final Font MONOSPACED_FONT = new Font("monospaced", Font.PLAIN, 12);
    
    // fields of the Iverson GUI
    private JFrame frame;
    private JTextField inputFileField; 
    private JTextArea inputFileArea, huffTreeArea, outputFileArea;
    private JButton inputBrowse, inputLoad, compress, decompress, clear;
    private JLabel inputStatusLabel, outputStatusLabel, compressStatusLabel;
    private JFileChooser chooser;
    private JCheckBox displayOption;
    
    private Map<Character, Integer> counts = new TreeMap<Character, Integer>();
    private HuffmanTree huffmanTree;
    private StringBuilder huffResults;
    private String fileInput; 
    // There were file streams in the original UW version
    // to simplify, we'll read the whole input file into a string
    // and the entire stream of bits from the Huffman process into another string (huffResults)
    
    // Construct the GUI, sets up all graphical components, layout, and events.
    // A little complicated, as I started with a very different GUI for the UW version
    public HuffmanGUI() {
        // main window frame
        frame = new JFrame("CS211 (CSE143) Huffman Coding");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // LEFT Panel for input file user interaction 
        Container textAndBrowse = new JPanel(new BorderLayout());
        inputFileField = new JTextField(10);
        inputFileField.addActionListener(this);
        textAndBrowse.add(new JLabel("Input file: "), BorderLayout.NORTH);
        textAndBrowse.add(inputFileField);
        
        // Another sub-Container for the Buttons
        Container textButtons = new JPanel(new FlowLayout());
        inputBrowse = createButton("Browse...", 'B', this, textButtons);
        inputLoad = createButton("Reload", 'L', this, textButtons);
        clear = createButton("Clear", 'C', this, textButtons);
        textAndBrowse.add(textButtons, BorderLayout.SOUTH);
        
        // text area in which to load input file contents (left side)
        inputFileArea = new JTextArea(20, 40);
        inputFileArea.setFont(MONOSPACED_FONT);
        inputFileArea.setEditable(false);
        inputFileArea.setFocusable(false);
        inputStatusLabel = new JLabel("No file loaded");
        
        // and a container to hold text area and label together
        Container south = new JPanel(new BorderLayout());
        south.add(new JScrollPane(inputFileArea));
        south.add(inputStatusLabel, BorderLayout.SOUTH);
        
        // finally a container with all of above together
        Container inputFilePanel = new JPanel(new BorderLayout());
        inputFilePanel.add(textAndBrowse, BorderLayout.NORTH);
        inputFilePanel.add(south, BorderLayout.CENTER);

        // For displaying the derived bit stream
        Container bitStream = new JPanel(new BorderLayout());
        bitStream.add(new JLabel("Output Stream (String of bits): "), BorderLayout.NORTH);
        
        // text area in which to load output file contents
        outputFileArea = new JTextArea(20, 40);
        outputFileArea.setFont(MONOSPACED_FONT);
        outputFileArea.setEditable(false);
        outputFileArea.setFocusable(false);
        outputStatusLabel = new JLabel("No file loaded");
        
        bitStream.add(new JScrollPane(outputFileArea));
        bitStream.add(outputStatusLabel, BorderLayout.SOUTH);
        
        Container outputFilePanel = new JPanel(new BorderLayout());
        outputFilePanel.add(bitStream, BorderLayout.NORTH);
        outputFilePanel.add(bitStream, BorderLayout.CENTER);
        
        // text area in which to load tree
        huffTreeArea = new JTextArea(20, 40);
        huffTreeArea.setFont(MONOSPACED_FONT);
        huffTreeArea.setEditable(false);
        huffTreeArea.setFocusable(false);
        
        Container right = new JPanel(new BorderLayout());
        right.add(new JScrollPane(huffTreeArea));
        
        Container huffTreePanel = new JPanel(new BorderLayout());
        huffTreePanel.add(new JLabel("printSideways() Huffman Tree: "), BorderLayout.NORTH);
        huffTreePanel.add(right, BorderLayout.CENTER);        
        
        // bottom section for buttons to compress/decompress
        Container compressPanel = new JPanel(new BorderLayout());
        Container compressCenter = new JPanel(new FlowLayout());
        compress = createButton("Compress", 'C', this, compressCenter);
        decompress = createButton("Decompress", 'D', this, compressCenter);
        compressStatusLabel = new JLabel("No file has been compressed yet");
        compressStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        displayOption = new JCheckBox("Check to display data", true);
        displayOption.addActionListener(this);
        compressPanel.add(compressCenter, BorderLayout.CENTER);
        compressPanel.add(compressStatusLabel, BorderLayout.SOUTH);
        compressPanel.add(displayOption, BorderLayout.EAST);
        
        // overall window's layout
        JPanel contentPane = new JPanel(new BorderLayout(15, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(inputFilePanel, BorderLayout.WEST);
        contentPane.add(outputFilePanel, BorderLayout.CENTER);
        contentPane.add(compressPanel, BorderLayout.SOUTH);
        contentPane.add(huffTreePanel, BorderLayout.EAST);
        
        // size frame and place it in the center of the screen
        frame.add(contentPane);
        frame.pack();
        center(frame);
    }
    
    // Called whenever the user clicks a button or presses Enter on a text field.
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        JTextField field = inputFileField; 
        JTextArea area = inputFileArea;
        JLabel label = inputStatusLabel;        
        if (source == inputBrowse) { 
            // pop up a Browse... file chooser
            String currentDir = System.getProperty("user.dir");
            if (chooser == null) {
                chooser = new JFileChooser(currentDir);
            }
   
            // load the selected file
            int result = chooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File inputFile = chooser.getSelectedFile();
                if (inputFile != null) {
                    // shorten displayed file names if possible for prettier GUI
                    String filename = inputFile.getAbsolutePath();
                    if (filename.startsWith(currentDir + File.separator)) {
                        filename = filename.replace(currentDir + File.separator, "");
                    }
                    field.setText(filename);
                    loadFile(filename, area, label);
                }
            }
        } else if (source == inputLoad ) {
            // load file from disk and display on text area
            String filename = field.getText().trim();
            loadFile(filename, area, label);
        } else if (source == clear) {
        	// clear display of file name and contents
        	area.setText(" ");
        	//field.setText(" ");
        } else if (source == inputFileField) {
            // user pressed Enter on input file text field; load file and display it
            String inputFileName = inputFileField.getText().trim();
            if (inputFileName.length() == 0) {
                return;
            }
            loadFile(inputFileName, inputFileArea, inputStatusLabel);
        } else if (source == compress) {
            // compress the currently selected file
            compress();
        } else if (source == decompress) {
            // decompress the currently selected file
            decompress();
        }
    }

    // Helper to load the contents of the given FILE and display them into the
    // given text area.  The given label displays status updates along the way.
    // Works for both text and binary files.
    public void loadFile(final String filename, final JTextArea area, final JLabel label) {
        final File file = new File(filename);
        if (file.exists()) {
            new Thread(new Runnable() {
                public void run() {
                    label.setText("Loading ...");
                    try {
                        area.setText(readEntireFile(filename)); // helper to convert file to a String
                        area.setLineWrap(false);
                        label.setText("Text file, " + file.length() + " bytes (" + file.length() / 1024 + " kB)");
                        area.setSelectionStart(0);   // scroll to top
                        area.setSelectionEnd(0);
                    } catch (IOException ioe) {		// these exceptions are tough to get to now
                        area.setText("");
                        label.setText("No file loaded");
                        ioe.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "I/O error occurred:\n"
                                + ioe.getMessage(), "I/O error", JOptionPane.ERROR_MESSAGE);
                    } catch (OutOfMemoryError mem) {
                        area.setText("(file too large to display)");
                        label.setText("No file loaded");
                        mem.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Out of memory error occurred:\n"
                                + mem.getMessage(), "Memory error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }).start();
        } else {
            // user has not yet selected an input and output file
            area.setText("");
            label.setText("No file loaded");
        }
    }
    
    // Runs the GUI by displaying its graphical frame.
    public void start() {
        frame.setVisible(true);
    }
    
    // Helper to compress the currently selected input file to the selected output file.
    private void compress() {
        final String inputFileName = inputFileField.getText().trim();
        if (inputFileName.length() == 0) { 
            return;
        }
        
        // run compression in a separate thread of execution because it can be slow
        new Thread(new Runnable() {
            public void run() {
                try {
                    // get file's character counts
                    compressStatusLabel.setText("Compression in progress ...");
                    outputFileArea.setText("...");
                    outputStatusLabel.setText("Counting characters ...");
                    counts = HuffmanNode.getCounts(new FileInputStream(inputFileName));
                    huffmanTree = new HuffmanTree(counts);
                    if (displayOption.isSelected())
                    	huffTreeArea.setText(huffmanTree.printSideways());
                    else
                    	huffTreeArea.setText("size = " + huffmanTree.printSideways().length() + " characters");
                 
                    outputStatusLabel.setText("Creating encodings ...");

                    // compress
                    FileInputStream input = new FileInputStream(inputFileName);

                    // use Huffman tree and counts to compress the input file
                    // (also time it so we can display the runtime afterward)
                    outputStatusLabel.setText("Compressing ...");
                    long startTime = System.currentTimeMillis();
                    huffResults = huffmanTree.compress(input);

                    // display compressed file
                    if (displayOption.isSelected())
                    	outputFileArea.setText(new String(huffResults));	// only use for small files
                    else
                    	outputFileArea.setText("size = " + huffResults.length() + " bits");
                    outputFileArea.setLineWrap(true);
                    outputStatusLabel.setText("String of bits, size = " + huffResults.length()/8 + " bytes  (" + huffResults.length() / (8*1024) + " kB) ");
                    outputFileArea.setSelectionStart(0);   // scroll to top

                    long endTime = System.currentTimeMillis();
                    long elapsed = endTime - startTime;
                    compressStatusLabel.setText("Compression complete (" + elapsed + "ms)");
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "I/O error occurred:\n"
                            + ioe.getMessage(), "I/O error", JOptionPane.ERROR_MESSAGE);
                } catch (OutOfMemoryError mem) {
                    mem.printStackTrace();
                    outputFileArea.setText("(file too large to display)");
                    JOptionPane.showMessageDialog(frame, "Out of memory error occurred:\n"
                            + mem.getMessage(), "Memory error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }).start();
    }

    private void decompress() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // use Huffman tree and counts to decompress
                    // (also time it so we can display the runtime afterward)
                    outputStatusLabel.setText("Decompressing above bits...");
                    long startTime = System.currentTimeMillis();
                    try {
                    fileInput = new String(huffmanTree.decompress(huffResults));
                    }
                    catch (Exception e) {}

                    long endTime = System.currentTimeMillis();
                    long elapsed = endTime - startTime;
                    
                    // display decompressed file
                    inputFileArea.setText(fileInput);
                    inputFileArea.setLineWrap(true);
                    inputStatusLabel.setText("Decompressed file size=  " + fileInput.length() + " bytes  (" + fileInput.length() / (1024) + " kB) ");
                    inputFileArea.setSelectionStart(0);   // scroll to top
                    outputStatusLabel.setText("Done... ");
                    compressStatusLabel.setText("Decompression complete (" + elapsed + "ms)");
                } catch (OutOfMemoryError mem) {
                    JOptionPane.showMessageDialog(frame, "Out of memory error occurred:\n"
                            + mem.getMessage(), "Memory error", JOptionPane.ERROR_MESSAGE);
                    mem.printStackTrace();
                }
            }
        }).start();
    }

    // Moves the given window to the center of the screen.
    private static void center(Component comp) {
        Dimension size = comp.getSize();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        comp.setLocation(Math.max(0, (screen.width - size.width) / 2), Math
                .max(0, (screen.height - 24 - size.height) / 2));
    }

    // Helper method to create a JButton with the given properties.
    private static JButton createButton(String text, char mnemonic, ActionListener listen, Container panel) {
        JButton button = new JButton(text);
        if (mnemonic != '\0') {
            button.setMnemonic(mnemonic);
        }
        button.addActionListener(listen);
        if (panel != null) {
            panel.add(button);
        }
        button.setFocusable(false);
        return button;
    }
    
    // Returns the entire contents of the given file as a string.
    // To be used with text files.
    private static String readEntireFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }
        StringBuilder sb = new StringBuilder((int) file.length() + 10);
        Reader in = new BufferedReader(new FileReader(file));
        while (in.ready()) {
            sb.append((char) in.read());
        }
        return sb.toString();
    }
}
