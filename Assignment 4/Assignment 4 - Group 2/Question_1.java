import java.util.Arrays;
import java.util.Scanner;

public class Question_1 {
    public static int minDiff = Integer.MAX_VALUE;
    public static int maxSum = 0;
    private static int subsetSum;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of elements in the list: ");

        int size = scanner.nextInt();
        int[] numbers = new int[size];

        for (int i = 0; i < size; i++) {
            System.out.println("Enter number " + (i + 1));
            numbers[i] = scanner.nextInt();
        }

        System.out.println("Enter the sum of the sub set: ");

        subsetSum = scanner.nextInt();

        int total = 0;
        int[] sortedArr = numbers.clone();
        int[] reversedArr = new int[size];

        Arrays.sort(sortedArr);

        for (int num : numbers) {
            total += num;
        }

        for (int j = 0; j < size; j++) {
            reversedArr[j] = sortedArr[size - j - 1];
        }

        boolean[] subset = new boolean[size];
        long startTime, endTime, duration;

        System.out.println("\nRunning the brute force backtracking algorithm on the test case: " + Arrays.toString(numbers));

        startTime = System.nanoTime();

        boolean res1 = bruteForceBacktrack(numbers, 0, 0, subset, total);

        endTime = System.nanoTime();
        duration = endTime - startTime;

        if (res1) {
            System.out.println("\nA fair split is found.");
        } else {
            System.out.println("\nNo fair split is found.");
        }

        System.out.println("\nThe execution time is: " + duration + " nanoseconds.");
        System.out.println("\n--------------------------------------------------\n");

        Arrays.fill(subset, false);

        minDiff = Integer.MAX_VALUE;
        maxSum = 0;

        System.out.println("\nRunning the backtracking algorithm with heuristics on the test case: " + Arrays.toString(numbers));

        startTime = System.nanoTime();


        boolean res2 = backtrackWithHeuristics(reversedArr, 0, 0, subset);

        endTime = System.nanoTime();
        duration = endTime - startTime;

        if (res2) {
            System.out.println("\nA fair split is found.");
        } else {
            System.out.println("\nNo fair split is found.");
        }

        System.out.println("\nThe execution time is: " + duration + " nanoseconds.");
    }

    public static boolean backtrackWithHeuristics(int[] arr, int index, int sum, boolean[] subset) {
        if (index == arr.length) {
            if (sum == subsetSum) {
                printSubsets(arr, subset);

                minDiff = 0;
                maxSum = subsetSum;

                return true;
            } else {
                int diff = Math.abs(sum - subsetSum);

                if (diff < minDiff) {
                    minDiff = diff;
                    maxSum = Math.max(sum, subsetSum);
                }
                return false;
            }
        }

        subset[index] = true;

        boolean res1 = backtrackWithHeuristics(arr, index + 1, sum + arr[index], subset);

        subset[index] = false;

        boolean res2 = backtrackWithHeuristics(arr, index + 1, sum, subset);

        return res1 || res2;
    }

    public static boolean bruteForceBacktrack(int[] arr, int index, int sum, boolean[] subset, int total) {
        if (index == arr.length) {
            if (sum == subsetSum) {
                printSubsets(arr, subset);

                return true;
            } else {
                return false;
            }
        }

        if (sum > total / 2) {
            return false;
        }

        subset[index] = true;

        boolean res1 = bruteForceBacktrack(arr, index + 1, sum + arr[index], subset, total);

        subset[index] = false;

        boolean res2 = bruteForceBacktrack(arr, index + 1, sum, subset, total);

        return res1 || res2;
    }

    public static void printSubsets(int[] arr, boolean[] subset) {
        System.out.print("\nFirst subset: ");

        for (int i = 0; i < arr.length; i++) {
            if (subset[i]) {
                System.out.print(arr[i] + " ");
            }
        }

        System.out.print("\nSecond subset: ");

        for (int i = 0; i < arr.length; i++) {
            if (!subset[i]) {
                System.out.print(arr[i] + " ");
            }
        }

        System.out.println();
    }
}