import java.util.*;

class RapidChangesNetwork {
    static final int V = 1005;
    static int[][] graph = new int[V][V];
    static int[] parent = new int[V];
    static int[] key = new int[V];
    static boolean[] mstSet = new boolean[V];

    static int minKey() {
        int min = Integer.MAX_VALUE, min_index = -1;
        for (int v = 0; v < V; v++) {
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        }
        return min_index;
    }

    static void primMST() {
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
        key[0] = 0;
        parent[0] = -1;
        for (int count = 0; count < V - 1; count++) {
            int u = minKey();
            mstSet[u] = true;
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != -1 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }
    }

    static int calculateMSTWeight() {
        int mstWeight = 0;
        for (int i = 1; i < V; i++) {
            mstWeight += graph[i][parent[i]];
        }
        return mstWeight;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input graph
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = sc.nextInt();
            }
        }

        // Find the initial MST and its weight
        primMST();
        int initialMstWeight = calculateMSTWeight();
        System.out.println("Initial MST weight: " + initialMstWeight);

        while (true) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            if (u == -1 && v == -1 && w == -1) {
                break;
            }

            graph[u][v] = w;
            graph[v][u] = w;
            primMST();
            int newMstWeight = calculateMSTWeight();

            if (newMstWeight != initialMstWeight) {
                System.out.println("MST weight changes to " + newMstWeight);
            } else {
                System.out.println("MST weight does not change depending on the result.");
            }
        }
    }
}