import java.io.*;
import java.util.*;

public class Question_2 {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Please enter the dictionary file name");
            System.exit(1);
        } else if (!args[0].contains(".")) {
            System.out.println("Invalid file format");
            System.exit(1);
        }

        Set<String> dictionaryData = readDictionary(args[0]);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter a scrambled word or quit --> ");

            String scrambledWord = scanner.nextLine().toLowerCase(Locale.ROOT);

            if ("quit".equals(scrambledWord)) {
                System.out.println("Bye");
                break;
            }

            ArrayList<String> wordsPermutation = permutationApproach(scrambledWord, dictionaryData);

            System.out.println("Output using permutation approach");

            if (wordsPermutation.size() == 0) {
                System.out.println("UNDEFINED");
            } else {
                System.out.println(String.join("\n", wordsPermutation));
            }

            ArrayList<String> wordsEfficient = efficientApproach(scrambledWord, dictionaryData);

            System.out.println("Output using efficient approach");

            if (wordsEfficient.size() == 0) {
                System.out.println("UNDEFINED");
            } else {
                System.out.println(String.join("\n", wordsEfficient));
            }

            System.out.println();
            System.out.println("The time and space complexity of both approaches are:");
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("| Permutations | Time = O(n! * n * log n)            | Space = O(n! * n) |");
            System.out.println("| Efficient    | Time = O(n * log n + m * n * log n) | Space = O(m)      |");
            System.out.println("--------------------------------------------------------------------------");
            System.out.println();
        }

        scanner.close();
    }

    //    method to sort the word in the alphabetical order
    private static String sortWord(String scrambledWord) {
        char[] chars = scrambledWord.toCharArray();

        Arrays.sort(chars);

        return new String(chars);
    }

    //    method to find the word using the efficient approach
    private static ArrayList<String> efficientApproach(String scrambledWord, Set<String> dictionaryData) {
        ArrayList<String> words = new ArrayList<>();
        String sortedWord = sortWord(scrambledWord);

        for (String s : dictionaryData) {
            if (s.length() == scrambledWord.length()) {
                String dictionaryWord = sortWord(s);

                if (sortedWord.equals(dictionaryWord)) {
                    words.add(s);
                }
            }
        }

        return words;
    }

    //    method that will call the findAllPermutations() method and add them to the ArrayList
    private static ArrayList<String> permutationApproach(String scrambledWord, Set<String> dictionaryData) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> wordPermutations = findAllPermutations(scrambledWord);
        wordPermutations.remove("");

        for (String permutation : wordPermutations) {
            if (dictionaryData.contains(permutation)) {
                words.add(permutation);
            }
        }

        return words;
    }

    //    method that find all the possible permutations of the given word
    private static ArrayList<String> findAllPermutations(String scrambledWord) {
        if (scrambledWord.length() == 0) {
            ArrayList<String> empty = new ArrayList<>();
            empty.add("");

            return empty;
        }

        char ch = scrambledWord.charAt(0);
        String subStr = scrambledWord.substring(1);
        ArrayList<String> results = findAllPermutations(subStr);
        ArrayList<String> wordPermutations = new ArrayList<>();

        for (String str : results) {
            for (int i = 0; i <= str.length(); i++) {
                wordPermutations.add(str.substring(0, i) + ch + str.substring(i));
            }
        }

        return wordPermutations;
    }

    //    method that will read the data from the file provided
    private static Set<String> readDictionary(String fileName) throws IOException {
        Set<String> dictionaryData = new HashSet<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("").getAbsolutePath() + "\\" + fileName));
        String readLine;

        while ((readLine = bufferedReader.readLine()) != null) {
            dictionaryData.add(readLine.toLowerCase(Locale.ROOT));
        }

        bufferedReader.close();

        return dictionaryData;
    }
}
