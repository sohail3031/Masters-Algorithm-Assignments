import java.util.*;

public class Question_2 {
    public static int minSum = Integer.MAX_VALUE;
    public static int maxPossibleSum;
    public static int minMaxSum = Integer.MAX_VALUE;
    public static int[] optimalDivision;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of elements in the list: ");

        int size = scanner.nextInt();
        int[] numbers = new int[size];

        for (int i = 0; i < size; i++) {
            System.out.println("Enter number " + (i + 1));
            numbers[i] = scanner.nextInt();
        }

        System.out.println("Enter the value of k: ");

        int k = scanner.nextInt();

        int[] sortedArr = numbers.clone();
        int[] reversedArr = new int[size];

        Arrays.sort(sortedArr);

        for (int j = 0; j < size; j++) {
            reversedArr[j] = sortedArr[size - j - 1];
        }

        int total = 0;

        for (int num : numbers) {
            total += num;
        }

        int[] sums = new int[k];
        int[] division = new int[size];

        Arrays.fill(division, -1);

        long startTime, endTime, duration;

        System.out.println("\nRunning the brute force branch and bound algorithm on the test case: " + Arrays.toString(numbers) + " with k = " + k);

        startTime = System.nanoTime();

        bruteForceBranchAndBound(numbers, 0, sums, division, 0, k);

        endTime = System.nanoTime();
        duration = endTime - startTime;

        System.out.println("\nThe minimum maximum sum is: " + minMaxSum + "\n");

        printDivision(numbers, optimalDivision);

        System.out.println("The execution time is: " + duration + " nanoseconds.");
        System.out.println("\n------------------------------------------------\n");

        Arrays.fill(sums, 0);
        Arrays.fill(division, -1);

        maxPossibleSum = total;

        System.out.println("Running the backtracking algorithm with heuristics on the test case: " + Arrays.toString(numbers) + " with k = " + k);

        startTime = System.nanoTime();

        backtrackWithHeuristics(reversedArr, 0, sums, division, 0, k);

        endTime = System.nanoTime();
        duration = endTime - startTime;

        System.out.println("\nThe minimum maximum sum is: " + minMaxSum + "\n");

        printDivision(numbers, optimalDivision);

        System.out.println("The execution time is: " + duration + " nanoseconds.");
        System.out.println();
    }

    //    method that uses Back Tracking with Heuristics to find the Minimum Maximum Sum
    public static void backtrackWithHeuristics(int[] arr, int index, int[] sums, int[] division, int maxSum, int k) {
        if (index == arr.length) {
            if (maxSum < minMaxSum) {
                minMaxSum = maxSum;
                optimalDivision = division.clone();
                minSum = Integer.MAX_VALUE;

                for (int sum : sums) {
                    minSum = Math.min(minSum, sum);
                }
            }

            return;
        }

        if (maxSum > minMaxSum || maxSum + maxPossibleSum > minMaxSum - minSum) {
            return;
        }

        for (int i = 0; i < k; i++) {
            sums[i] += arr[index];
            division[index] = i;
            maxPossibleSum -= arr[index];

            backtrackWithHeuristics(arr, index + 1, sums, division, Math.max(maxSum, sums[i]), k);

            sums[i] -= arr[index];
            division[index] = -1;
            maxPossibleSum += arr[index];
        }
    }

    //    method that uses prints Minimum Maximum Sum
    public static void printDivision(int[] arr, int[] division) {
        int k = Arrays.stream(division).max().orElse(0) + 1;
        List<List<Integer>> subsets = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            subsets.add(new ArrayList<>());
        }

        for (int i = 0; i < division.length; i++) {
            subsets.get(division[i]).add(arr[i]);
        }

        for (List<Integer> subset : subsets) {
            if (!subset.isEmpty()) {
                int sum = subset.stream().mapToInt(Integer::intValue).sum();

                StringJoiner joiner = new StringJoiner(" + ");

                for (int num : subset) {
                    joiner.add(Integer.toString(num));
                }

                System.out.println(joiner + " = " + sum);
            }
        }

        int maxFavour = subsets.stream()
                .mapToInt(subset -> subset.stream().mapToInt(Integer::intValue).sum())
                .max().orElse(0);

        System.out.println("\nFavour = " + maxFavour + "\n");
    }

    //    method that uses Brute Force with Branch and Bound to find the Minimum Maximum Sum
    public static void bruteForceBranchAndBound(int[] arr, int index, int[] sums, int[] division, int maxSum, int k) {
        if (index == arr.length) {
            if (maxSum < minMaxSum) {
                minMaxSum = maxSum;
                optimalDivision = division.clone();
            }

            return;
        }

        if (maxSum > minMaxSum) {
            return;
        }

        for (int i = 0; i < k; i++) {
            sums[i] += arr[index];
            division[index] = i;

            bruteForceBranchAndBound(arr, index + 1, sums, division, Math.max(maxSum, Math.min(sums[i], minMaxSum)), k);

            sums[i] -= arr[index];
            division[index] = -1;
        }
    }
}