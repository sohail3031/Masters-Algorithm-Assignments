import java.util.*;

public class A {
    // A global variable to store the minimum maximum sum (the Favour value)
    public static int favour = Integer.MAX_VALUE;
    // A global variable to store the optimal division
    public static ArrayList<ArrayList<Integer>> optimalDivision;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the number of elements in the list
        System.out.println("Enter the number of elements in the list: ");
        int size = scanner.nextInt();

        // Create an array to store the elements of the list
        int[] numbers = new int[size];

        // Prompt the user to enter the elements of the list
        System.out.println("Enter the elements of the list: ");
        for (int i = 0; i < size; i++) {
            numbers[i] = scanner.nextInt();
        }

        // Prompt the user to enter the value of k
        System.out.println("Enter the value of k: ");
        int k = scanner.nextInt();

        // Sort the array in ascending order for the second algorithm
        int[] sortedArr = numbers.clone();
        Arrays.sort(sortedArr);

        // Get the total sum of the array
        int total = 0;
        for (int num : numbers) {
            total += num;
        }

        // Create an array to store the sums of the k lists
        int[] sums = new int[k];
        // Create an ArrayList to store the division of the list into k lists
        ArrayList<ArrayList<Integer>> division = new ArrayList<>();
        // Initialize the division with empty ArrayLists
        for (int i = 0; i < k; i++) {
            division.add(new ArrayList<>());
        }

        // Initialize the timer
        long startTime, endTime, duration;

        // Print a separator
        System.out.println("--------------------------------------------------");

        // Run the brute force branch and bound algorithm
        System.out.println("Running the brute force branch and bound algorithm on the test case: " + Arrays.toString(numbers) + " with k = " + k);

        startTime = System.nanoTime();

        bruteForceBranchAndBound(numbers, 0, sums, division, 0, k);

        endTime = System.nanoTime();
        duration = endTime - startTime;

        // Print the result and the time
        System.out.println("The minimum maximum sum is: " + favour);

        printDivision(optimalDivision);

        System.out.println("The execution time is: " + duration + " nanoseconds.");

        // Print a separator
        System.out.println("--------------------------------------------------");

        // Reset the sums and the division for the second algorithm
        Arrays.fill(sums, 0);
        division.clear();
        for (int i = 0; i < k; i++) {
            division.add(new ArrayList<>());
        }

        // Reset the global variables for the second algorithm
        favour = Integer.MAX_VALUE;
        optimalDivision = null;

        // Run the backtracking algorithm with heuristics
        System.out.println("Running the backtracking algorithm with heuristics on the test case: " + Arrays.toString(numbers) + " with k = " + k);

        startTime = System.nanoTime();

        backtrackWithHeuristics(sortedArr, 0, sums, division, 0, k, total);

        endTime = System.nanoTime();
        duration = endTime - startTime;

        // Print the result and the time
        System.out.println("The minimum maximum sum is: " + favour);

        printDivision(optimalDivision);

        System.out.println("The execution time is: " + duration + " nanoseconds.");

        // Print a separator
        System.out.println("--------------------------------------------------");
    }

    // A backtracking algorithm with heuristics to find a division of the given list of integers into k lists with minimum maximum sum
    public static void backtrackWithHeuristics(int[] arr, int index, int[] sums, ArrayList<ArrayList<Integer>> division, int maxSum, int k, int remainingSum) {
        // Base case: if the index reaches the end of the list, check if the current maximum sum is less than the global minimum maximum sum
        if (index == arr.length) {
            if (maxSum < favour) {
                // Update the global minimum maximum sum and the global optimal division
                favour = maxSum;
                optimalDivision = new ArrayList<>();
                for (ArrayList<Integer> list : division) {
                    optimalDivision.add(new ArrayList<>(list));
                }
            }
            return;
        }
        // Pruning condition: if the current maximum sum exceeds the global minimum maximum sum, or the current maximum sum plus the remaining sum is less than the global minimum maximum sum, there is no possible better division
        if (maxSum > favour || maxSum + remainingSum < favour) {
            return;
        }
        // Try to add the current element to each of the k lists
        for (int i = 0; i < k; i++) {
            // Update the sums and the division arrays
            sums[i] += arr[index];
            division.get(i).add(arr[index]);
            // Recursively call the function for the next index
            backtrackWithHeuristics(arr, index + 1, sums, division, Math.max(maxSum, sums[i]), k, remainingSum - arr[index]);
            // Restore the sums and the division arrays
            sums[i] -= arr[index];
            division.get(i).remove(division.get(i).size() - 1);
        }
    }

    // A utility function to print the division
    public static void printDivision(ArrayList<ArrayList<Integer>> division) {
        System.out.println("The optimal division is:");
        for (ArrayList<Integer> list : division) {
            if (!list.isEmpty()) {
                int sum = list.stream().mapToInt(Integer::intValue).sum();

                for (int i = 0; i < list.size() - 1; i++) {
                    System.out.print(list.get(i) + " + ");
                }
                System.out.print(list.get(list.size() - 1));
                System.out.println(" = " + sum);
            }
        }
    }

    // A brute force branch and bound algorithm to find a division of the given list of integers into k lists with minimum maximum sum
    public static void bruteForceBranchAndBound(int[] arr, int index, int[] sums, ArrayList<ArrayList<Integer>> division, int maxSum, int k) {
        // Base case: if the index reaches the end of the list, check if the current maximum sum is less than the global minimum maximum sum
        if (index == arr.length) {
            if (maxSum < favour) {
                // Update the global minimum maximum sum and the global optimal division
                favour = maxSum;
                optimalDivision = new ArrayList<>();
                for (ArrayList<Integer> list : division) {
                    optimalDivision.add(new ArrayList<>(list));
                }
            }
            return;
        }
        // Pruning condition: if the current maximum sum exceeds the global minimum maximum sum,
        if (maxSum > favour) {
            return;
        }

        for (int i = 0; i < k; i++) {
            sums[i] += arr[index];
            division.get(i).add(arr[index]);

            bruteForceBranchAndBound(arr, index + 1, sums, division, Math.max(maxSum, sums[i]), k);

            sums[i] -= arr[index];
            division.get(i).remove(division.get(i).size() - 1);
        }
    }
}
