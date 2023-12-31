import java.io.*;
import java.util.*;

class HuffmanNode implements Comparable<HuffmanNode> {
    char data;
    int frequency;
    HuffmanNode left, right;

    public HuffmanNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }

    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
}

public class Example {
    public static void main(String[] args) {
        try {
            // Step 1: Read the input file and build the frequency table
            String inputFileName = System.getProperty("user.dir") + "\\src\\" + "test.txt";
            String outputFileName = "output.bin";
            Map<Character, Integer> frequencyTable = buildFrequencyTable(inputFileName);

            // Step 2: Build the Huffman tree
            HuffmanNode root = buildHuffmanTree(frequencyTable);

            // Step 3: Build the Huffman codes
            Map<Character, String> huffmanCodes = buildHuffmanCodes(root);

            // Step 4: Encode the input and write it to the output file
            encodeAndWriteToFile(inputFileName, outputFileName, huffmanCodes);

            // Step 5: Decode the encoded file and write the decoded text
            String decodedFileName = "decoded.txt";
            decodeAndWriteToFile(outputFileName, decodedFileName, root);

            System.out.println("Huffman encoding and decoding completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to build the frequency table
    private static Map<Character, Integer> buildFrequencyTable(String inputFileName) throws IOException {
        Map<Character, Integer> frequencyTable = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                char character = (char) ch;
                if (Character.isLetterOrDigit(character) || character == ' ' || character == ',' || character == '.') {
                    frequencyTable.put(character, frequencyTable.getOrDefault(character, 0) + 1);
                }
            }
        }
        return frequencyTable;
    }

    // Function to build the Huffman tree
    private static HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencyTable) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            int frequencySum = left.frequency + right.frequency;
            priorityQueue.add(new HuffmanNode(frequencySum, left, right));
        }
        return priorityQueue.poll();
    }

    // Function to build Huffman codes
    private static Map<Character, String> buildHuffmanCodes(HuffmanNode root) {
        Map<Character, String> huffmanCodes = new HashMap<>();
        buildHuffmanCodesHelper(root, "", huffmanCodes);
        return huffmanCodes;
    }

    private static void buildHuffmanCodesHelper(HuffmanNode node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.data, code);
        }
        buildHuffmanCodesHelper(node.left, code + "0", huffmanCodes);
        buildHuffmanCodesHelper(node.right, code + "1", huffmanCodes);
    }

    // Function to encode and write to a binary file
    private static void encodeAndWriteToFile(String inputFileName, String outputFileName, Map<Character, String> huffmanCodes)
            throws IOException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFileName));
             BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            int bitBuffer = 0;
            int bitCount = 0;

            int ch;
            while ((ch = reader.read()) != -1) {
                char character = (char) ch;
                if (Character.isLetterOrDigit(character) || character == ' ' || character == ',' || character == '.') {
                    String code = huffmanCodes.get(character);
                    for (char c : code.toCharArray()) {
                        if (c == '1') {
                            bitBuffer |= (1 << (7 - bitCount));
                        }
                        bitCount++;
                        if (bitCount == 8) {
                            outputStream.write(bitBuffer);
                            bitBuffer = 0;
                            bitCount = 0;
                        }
                    }
                }
            }

            if (bitCount > 0) {
                outputStream.write(bitBuffer);
            }
        }
    }

    // Function to decode and write to a text file
    private static void decodeAndWriteToFile(String inputFileName, String outputFileName, HuffmanNode root)
            throws IOException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFileName));
             BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(inputFileName))) {
            HuffmanNode current = root;
            int bit;
            int byteRead;

            while ((byteRead = inputStream.read()) != -1) {
                for (int i = 7; i >= 0; i--) {
                    bit = (byteRead >> i) & 1;
                    if (bit == 0) {
                        current = current.left;
                    } else {
                        current = current.right;
                    }

                    if (current.left == null && current.right == null) {
                        outputStream.write(current.data);
                        current = root;
                    }
                }
            }
        }
    }
}


// Replace "input.txt" with your file name and put in the same directory.