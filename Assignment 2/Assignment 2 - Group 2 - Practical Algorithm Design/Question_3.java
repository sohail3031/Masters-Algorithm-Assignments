import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

// Note: comment out the dict arraylist and isWord method and add write because in the question it's mention that
// isWord method is provided

public class Question_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a string: ");

        String input = scanner.nextLine();

        List<String> dict = Arrays.asList("this", "string", "is", "a", "sequence", "of", "words", "written", "without", "any", "spaces");
//        List<String> dict = Arrays.asList("this", "th", "is", "famous", "word", "break", "b", "r", "e", "a", "k", "br", "bre", "brea", "ak", "problem");
//        String s1 = "wordbreakproblem";
//        String s2 = "thisstringisasequenceofwordswrittenwithoutanyspaces";
//        String s3 = "thisisnotaword";
//
//        System.out.println(wordBreak(s1, dict)); // true
//        System.out.println(wordBreak(s2, dict)); // true
//        System.out.println(wordBreak(s3, dict)); // false

        if (wordBreak(input, dict)) {
            System.out.println("\nTRUE");
        } else {
            System.out.println("\nFALSE");
        }
    }

    private static boolean wordBreak(String s, List<String> dict) {
        boolean[] dp = new boolean[s.length() + 1];
        int[] previousWord = new int[s.length() + 1];

        Arrays.fill(previousWord, -1);

        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && isWord(s.substring(j, i), dict)) {
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

    private static boolean isWord(String s, List<String> dict) {
        return dict.contains(s);
    }
}