import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// Note: comment out the dict arraylist and isWord method and add write because in the question it's mention that
// isWord method is provided

public class Question_3 {
    private static final List<String> dict = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        readFileData();

        System.out.println("Enter a string: ");

        String input = scanner.nextLine();
//        List<String> dict = Arrays.asList("this", "string", "is", "a", "sequence", "of", "words", "written", "without", "any", "spaces");
//        List<String> dict = Arrays.asList("this", "th", "is", "famous", "word", "break", "b", "r", "e", "a", "k", "br", "bre", "brea", "ak", "problem");
//        String s1 = "wordbreakproblem";
//        String s2 = "thisstringisasequenceofwordswrittenwithoutanyspaces";
//        String s3 = "thisisnotaword";
//
//        System.out.println(wordBreak(s1, dict)); // true
//        System.out.println(wordBreak(s2, dict)); // true
//        System.out.println(wordBreak(s3, dict)); // false

        if (wordBreak(input)) {
            System.out.println("\nTRUE");
        } else {
            System.out.println("\nFALSE");
        }
    }

    //    method that will break the input into words
    private static boolean wordBreak(String s) {
        boolean[] dp = new boolean[s.length() + 1];
        int[] previousWord = new int[s.length() + 1];

        Arrays.fill(previousWord, -1);

        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && isWord(s.substring(j, i))) {
                    dp[i] = true;
                    previousWord[i] = j;

                    break;
                }
            }
        }

        if (dp[s.length()]) {
            System.out.println("The sequence of words is: ");

            int i = s.length();
            Stack<String> stack = new Stack<>();

            while (i > 0) {
                stack.push(s.substring(previousWord[i], i));

                i = previousWord[i];
            }

            while (!stack.empty()) {
                System.out.print(stack.pop() + " ");
            }
        }

        return dp[s.length()];
    }

    //    method to check if the word is valid or not
    private static boolean isWord(String s) {
        return dict.contains(s);
    }

    //    method to read file data
    private static void readFileData() throws IOException {
        String fileName;

        while (true) {
            System.out.println("Enter text file name: ");

            fileName = scanner.nextLine();

            if (!" ".equals(fileName) && fileName.split("\\.")[1].equals("txt")) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\" + fileName));
                String line = bufferedReader.readLine();

                while (line != null) {
                    dict.add(line);

                    line = bufferedReader.readLine();
                }

                bufferedReader.close();

                break;
            } else {
                System.out.println("Invalid file format!");
            }
        }
    }
}