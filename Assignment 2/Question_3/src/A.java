//2. Modify the Algorithm to Output Words:

// This modified version of the algorithm maintains a `wordSequence` list to
// store the valid words in the input string when the answer is True.

import java.util.*;

public class A {
    public static boolean scriptioContinua(String s, Set<String> words, List<String> wordSequence) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && words.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        if (!dp[n]) {
            return false;
        }

        // Reconstruct the sequence of words
        int index = n;
        while (index > 0) {
            for (int j = 0; j < index; j++) {
                if (dp[j] && words.contains(s.substring(j, index))) {
                    wordSequence.add(s.substring(j, index));
                    index = j;
                    break;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Set<String> wordDictionary = new HashSet<>();
        wordDictionary.add("this");
        wordDictionary.add("string");
        wordDictionary.add("is");
        wordDictionary.add("a");
        wordDictionary.add("sequence");
        wordDictionary.add("of");
        wordDictionary.add("words");
        wordDictionary.add("written");
        wordDictionary.add("without");
        wordDictionary.add("any");
        wordDictionary.add("spaces");

        String s = "thisstringisasequenceofwordswrittenwithoutanyspaces";
        List<String> wordSequence = new ArrayList<>();
        boolean result = scriptioContinua(s, wordDictionary, wordSequence);

        if (result) {
            System.out.println("The input string can be segmented into valid words:");
            for (String word : wordSequence) {
                System.out.println(word);
            }
        } else {
            System.out.println("The input string cannot be segmented into valid words.");
        }
    }
}



