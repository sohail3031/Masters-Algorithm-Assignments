import java.util.*;


// it uses kruskal's algorithm to find MST
public class Question_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of vertices: ");

        int size = scanner.nextInt();
        Graph graph = new Graph(size);

        for (int i = 0; i < size; i++) {
            System.out.println("Enter " + (i + 1) + " vertex values: ");

            for (int j = 0; j < size; j++) {
                int weight = scanner.nextInt();
                if (weight != -1 && i != j) {
                    graph.addEdge(i, j, weight);
                }
            }
        }

        List<Edge> initialMST = graph.findMST();

        int initialMSTWeight = 0;

        for (Edge edge : initialMST) {
            initialMSTWeight += edge.weight;
        }

        System.out.println("Initial weight of MST: " + initialMSTWeight);

        while (true) {
            System.out.println("\nEnter edge values: ");

            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int newWeight = scanner.nextInt();

            if (u == -1 && v == -1 && newWeight == -1) {
                break;
            }

            for (Edge edge : graph.edges) {
                if ((edge.point1 == u - 1 && edge.point2 == v - 1) || (edge.point1 == v - 1 && edge.point2 == u - 1)) {
                    edge.weight = newWeight;

                    break;
                }
            }

            List<Edge> updatedMST = graph.findMST();

            int newMSTWeight = 0;

            for (Edge edge : updatedMST) {
                newMSTWeight += edge.weight;
            }

            if (newMSTWeight < initialMSTWeight) {
                System.out.println("MST weight changes to " + newMSTWeight);

                initialMSTWeight = newMSTWeight;
            } else {
                System.out.println("MST weight does not change");
            }
        }

        scanner.close();
    }
}

//    class that holds the edges of the nodes
class Edge implements Comparable<Edge> {
    int point1, point2, weight;

    public Edge(int point1, int point2, int weight) {
        this.point1 = point1;
        this.point2 = point2;
        this.weight = weight;
    }

    public int compareTo(Edge edge) {
        return this.weight - edge.weight;
    }
}

// class that represents the graph
class Graph {
    int V;
    List<Edge> edges;

    Graph(int V) {
        this.V = V;
        edges = new ArrayList<>();
    }

    //    method that add edges to the graph
    void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }

    //    method that will find the MST and return the list
    List<Edge> findMST() {
        Collections.sort(edges);

        List<Edge> result = new ArrayList<>();
        UnionFind unionFind = new UnionFind(V);

        for (Edge edge : edges) {
            if (unionFind.find(edge.point1) != unionFind.find(edge.point2)) {
                result.add(edge);
                unionFind.union(edge.point1, edge.point2);
            }
        }

        return result;
    }
}

// class that keep track of a collection of disjoint (non-overlapping) subsets of elements
class UnionFind {
    int[] parent;
    int[] rank;

    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    //    recursive method that returns the representative of the subset that contains element x
    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }

        return parent[x];
    }

    //    method that merges the subsets that contain elements x and y, if they are different
    void union(int x, int y) {
        int xRoot = find(x);
        int yRoot = find(y);

        if (xRoot == yRoot) {
            return;
        }

        if (rank[xRoot] < rank[yRoot]) {
            parent[xRoot] = yRoot;
        } else if (rank[xRoot] > rank[yRoot]) {
            parent[yRoot] = xRoot;
        } else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }
}
