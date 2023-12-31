import java.io.*;
import java.util.*;

class Node {
    public Node left = null;
    public Node right = null;
    public int frequency;
    public Character character;

    public Node(Character character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public Node(Character character, int frequency, Node left, Node right) {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
}

public class Question_2 {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Character, String> HUFFMAN_CODES = new HashMap<>();
    private static Map<Character, Integer> ALPHABETS_FREQUENCY = new HashMap<>();
    private static Node root;
    private static String fileName = null;

    public static void main(String[] args) throws IOException {
        fileName = getFileName();
        String fileData = readFileData();

        getOptions(fileData);
    }

    private static void showOptions() {
        System.out.println("Select an option: ");
        System.out.println("1. Encode");
        System.out.println("2. Decode");
        System.out.println("3. Show File Data");
        System.out.println("4. Show Characters Frequency");
        System.out.println("5. Show Characters Codes");
        System.out.println("6. Exit");
    }

    private static void getOptions(String fileData) throws IOException {
        boolean flag = true;

        while (flag) {
            showOptions();

            int option = scanner.nextInt();

            switch (option) {
                case 1 -> {
                    getCharactersCountAndSort(fileData);
                    createHuffmanTree(fileData);
                }
                case 2 -> {
                    decode(readEncodedData());
                }
                case 3 -> showFileData(fileData);
                case 4 -> showCharactersFrequency();
                case 5 -> showCharactersCodes();
                case 6 -> flag = false;
                default -> System.out.println("Invalid option!");
            }
        }

        bufferedReader.close();
    }

    private static void showCharactersCodes() {
        if (HUFFMAN_CODES.size() > 0) {
            System.out.println("\nCharacter Codes: ");

            for (Map.Entry<Character, String> map : HUFFMAN_CODES.entrySet()) {
                System.out.println(map.getKey() + " : " + map.getValue());
            }

            System.out.println();
        } else {
            System.out.println("No codes available to show\n");
        }
    }

    private static void showCharactersFrequency() {
        if (ALPHABETS_FREQUENCY.size() > 0) {
            System.out.println("\nCharacter Frequencies: ");

            for (Map.Entry<Character, Integer> map : ALPHABETS_FREQUENCY.entrySet()) {
                System.out.println(map.getKey() + " : " + map.getValue());
            }
        } else {
            System.out.println("No characters available to show\n");
        }
    }

    private static void showFileData(String fileData) {
        System.out.println("\nThe file data is: \n" + fileData);
    }

    private static StringBuilder readEncodedData() throws FileNotFoundException {
        StringBuilder decodedCodedData = new StringBuilder();

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + "\\src\\compressed.bin"));
             BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\src\\decompressed.txt"))) {
            int bitBuffer = 0;
            StringBuilder code = new StringBuilder();

            int ch;

            while ((ch = inputStream.read()) != -1) {
                bitBuffer = ch;
                for (int i = 0; i < 8; i++) {
                    if ((bitBuffer & (1 << (7 - i))) != 0) {
                        code.append('1');
                        decodedCodedData.append('1');
                    } else {
                        code.append('0');
                        decodedCodedData.append('0');
                    }
                    if (HUFFMAN_CODES.containsValue(code.toString())) {
                        // Find the character corresponding to the code
                        for (Map.Entry<Character, String> entry : HUFFMAN_CODES.entrySet()) {
                            if (entry.getValue().equals(code.toString())) {
                                writer.write(entry.getKey());
                                break;
                            }
                        }
                        code = new StringBuilder();
                    }
                }
            }

            System.out.println("\nA decompressed file is created with name \"decompressed.txt\"\n");

            return decodedCodedData;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }


//        StringBuilder decodedData = new StringBuilder();
//
//        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + "\\src\\compressed.bin"))) {
//            Node current = root;
//
//            int bit;
//            int byteRead;
//
//            while ((byteRead = bufferedInputStream.read()) != -1) {
//                for (int i = 7; i >= 0; i--) {
//                    bit = (byteRead >> i) & 1;
//                    if (bit == 0) {
//                        current = current.left;
//                    } else {
//                        current = current.right;
//                    }
//
//                    assert current != null;
//                    if (current.left == null && current.right == null) {
////                        bufferedInputStream.read();
//                        decodedData.append(bit);
//                        System.out.println("Read: " + bit);
//                        current = root;
//                    }
//                }
//            }
//        } catch (Exception ignored) {
//        }
//
//        return decodedData;

//        StringBuilder decodedData = new StringBuilder();
//        File file = new File(System.getProperty("user.dir") + "\\src\\compressed.bin");
//        FileInputStream fileInputStream = new FileInputStream(file);
//
//        try (DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
//            int content;
//
//            while ((content = dataInputStream.readInt()) != -1) {
//                decodedData.append((char) content);
//            }
//
//            System.out.println("\nData Output: \n" + decodedData);
//
//            System.out.println();
//        } catch (Exception ignored) {
//        }
//
//        return decodedData;
    }

    private static void decode(StringBuilder decodedData) {
        System.out.println("\nThe decoded string is: \n");

        if (isLeaf(root)) {
            while (root.frequency-- > 0) {
                System.out.print(root.character);
            }
        } else {
            int index = -1;

            try {
                while (index < decodedData.toString().length() - 1) {
                    index = decodeFileData(root, index, decodedData);
                }
            } catch (StringIndexOutOfBoundsException ignored) {

            }
        }
    }

    private static int decodeFileData(Node root, int index, StringBuilder stringBuilder) {
        if (root == null) {
            return index;
        }

        if (isLeaf(root)) {
            System.out.print(root.character);

            return index;
        }

        index++;

        root = (stringBuilder.charAt(index) == '0') ? root.left : root.right;
        index = decodeFileData(root, index, stringBuilder);

        return index;
    }

    private static void encode(String fileData) throws IOException {
        encodeFileData(root, "", HUFFMAN_CODES);
        storeCharactersCodes();

        StringBuilder encodedData = new StringBuilder();

        for (char c : fileData.toCharArray()) {
            encodedData.append(HUFFMAN_CODES.get(c));
        }

        System.out.println("\nThe encoded string is: \n" + encodedData);

        storeEncodedData();
    }

    private static void storeEncodedData() {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\src\\compressed.bin"));
             BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\" + fileName))) {
            int bitBuffer = 0;
            int bitCount = 0;

            int ch;

            while ((ch = reader.read()) != -1) {
                char character = (char) ch;
                if (Character.isLetterOrDigit(character) || character == ' ' || character == ',' || character == '.') {
                    String code = HUFFMAN_CODES.get(character);
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

            System.out.println("\nA compressed file is created with name \"compressed.bin\"\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        File file = new File(System.getProperty("user.dir") + "\\src\\compressed.bin");
//
//        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
//
//        for (int i = 0; i < encodedData.length(); i++) {
//            dataOutputStream.writeInt(encodedData.charAt(i));
//        }
//
//        dataOutputStream.close();
    }

    private static void createHuffmanTree(String fileData) throws IOException {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(i -> i.frequency));

        for (var entry : ALPHABETS_FREQUENCY.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() != 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            assert left != null;
            assert right != null;
            int sum = left.frequency + right.frequency;

            priorityQueue.add(new Node(null, sum, left, right));
        }

        root = priorityQueue.peek();

        encode(fileData);
    }

    private static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }

    private static void encodeFileData(Node root, String s, Map<Character, String> HUFFMAN_CODES) {
        if (root == null) {
            return;
        }

        if (isLeaf(root)) {
            HUFFMAN_CODES.put(root.character, s.length() > 0 ? s : "1");
        }

        encodeFileData(root.left, s + '0', HUFFMAN_CODES);
        encodeFileData(root.right, s + '1', HUFFMAN_CODES);
    }

    private static void getCharactersCountAndSort(String fileData) {
        for (char c : fileData.toCharArray()) {
            ALPHABETS_FREQUENCY.put(c, ALPHABETS_FREQUENCY.getOrDefault(c, 0) + 1);
        }

        List<Map.Entry<Character, Integer>> list = new ArrayList<>(ALPHABETS_FREQUENCY.entrySet());

        list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        Map<Character, Integer> sortedMap = new LinkedHashMap<>();

        for (Map.Entry<Character, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        ALPHABETS_FREQUENCY = sortedMap;

        storeCharactersFrequency();
        storeCharactersCodes();
    }

    private static void storeCharactersCodes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\src\\codes.txt"))) {
            for (Map.Entry<Character, String> entry : HUFFMAN_CODES.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void storeCharactersFrequency() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\src\\frequency.txt"))) {
            for (Map.Entry<Character, Integer> entry : ALPHABETS_FREQUENCY.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
            }

            System.out.println("\nA file is created with name \"frequency.txt\" for frequencies characters.\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readFileData() throws IOException {
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\" + fileName));
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader1.readLine();

        while (line != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());

            line = bufferedReader1.readLine();
        }

        bufferedReader1.close();

        return stringBuilder.toString();
    }

    private static String getFileName() throws IOException {
        while (true) {
            System.out.println("Enter text file name: ");

            fileName = bufferedReader.readLine();

            if (!" ".equals(fileName) && fileName.split("\\.")[1].equals("txt")) {
                break;
            } else {
                System.out.println("Invalid file format!");
            }
        }

        return fileName;
    }
}
