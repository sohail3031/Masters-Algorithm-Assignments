import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Question_1 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the input string:");

        String pat = scanner.nextLine().toLowerCase();

        System.out.println("Enter file name:");

        String fileName = scanner.nextLine();
        String fileData = readFileData(fileName);
        List<String> matches = KMPSearch(pat, fileData);

        if (!matches.isEmpty()) {
            System.out.println("The input string is found in the file. Here are the matches and context:");


            for (String match : matches) {
                System.out.println(match + "\n");
            }
        } else {
            List<String> closestMatches = findClosestMatches(pat, fileName);

            if (!closestMatches.isEmpty()) {
                System.out.println("The input string is not found in the file. Here are the top 5 closest matches:");

                int count = 0;

                for (String closestMatch : closestMatches) {
                    if (count < 5) {
                        System.out.println(closestMatch);
                    } else {
                        break;
                    }

                    count++;
                }
            } else {
                System.out.println("The input string is not found in the file. No closest matches are found either.");
            }
        }
    }

    //    method that reads data from a file
    private static String readFileData(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\" + fileName));
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();

        while (line != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());

            line = bufferedReader.readLine();
        }

        bufferedReader.close();

        return stringBuilder.toString().toLowerCase();
    }

    //    method to find the closest matches
    private static List<String> findClosestMatches(String pat, String fileData) {
        List<String> closestMatches = new ArrayList<>();
        int n = pat.length();
        int threshold = (int) (0.5 * n);
        int minDistance = Integer.MAX_VALUE;
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(s -> editDistance(pat, s)));

        for (String word : fileData.split("\\s+")) {
            int distance = editDistance(pat, word);

            if (distance < threshold && distance < minDistance) {
                closestMatches.add(word);
                minDistance = distance;
            }
        }

        for (int i = 0; i < 5 && !pq.isEmpty(); i++) {
            closestMatches.add(pq.poll());
        }

        return closestMatches;
    }

    //    method that uses edit distance to find the closest matches
    private static int editDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1]));
                }
            }
        }

        return dp[m][n];
    }

    //    method to search string in a file using KMP algorithm
    private static List<String> KMPSearch(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();
        int[] lps = computeLPS(pat);
        int i = 0;
        int j = 0;

        List<String> matches = new ArrayList<>();

        while (i < n) {
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }

            if (j == m) {
                int start = Math.max(0, i - j - 7);
                int end = Math.min(n, i + 5);

                matches.add("..." + txt.substring(start, end) + "...");

                j = lps[j - 1];
            } else if (i < n && pat.charAt(j) != txt.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i = i + 1;
                }
            }
        }

        return matches;
    }

    //    method that calculates The Longest Prefix Suffix of the pattern
    private static int[] computeLPS(String pat) {
        int m = pat.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;
        lps[0] = 0;

        while (i < m) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;

                lps[i] = len;

                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }
}