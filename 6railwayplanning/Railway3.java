import java.util.*;

public class Railway3 {

    private int graph[][];
    private List<Edge> edges;
    int[] routesToRemove;
    private int N;
    private int C;
    private int M;

    public static void main(String[] args) {
        new Railway3().run();
    }

    public void run() {
        initialize();
        solution();
    }

    private void initialize() {
        Parser p = new Parser(new Scanner(System.in));
        this.N = p.N; // total nodes
        this.C = p.C; // total students to transfer
        this.M = p.M; // total routes
        this.edges = p.edges;
        this.routesToRemove = p.routesToRemove;
        this.graph = p.graph;
    }

    public boolean bfs(int[][] rGraph, int s, int t, int[] parent) {
        // Keep track of visited nodes
        boolean[] visited = new boolean[N];
        for (int i = 0; i < N; ++i) {
            visited[i] = false;
        }
        // Queue for BFS
        Queue<Integer> q = new LinkedList<>();

        // Insert Source Node
        q.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (q.isEmpty() == false) {
            Integer u = q.poll();
            for (int v = 0; v < N; v++) {
                if (visited[v] == false && rGraph[u][v] > 0) {
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    q.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return false;
    }

    public int fordFulkerson(int[][] graph, int s, int t) {
        int u, v;

        int max_flow = 0; // No initial flow

        int[][] rGraph = new int[N][N]; // Create residual graph
        for (u = 0; u < N; u++) {
            for (v = 0; v < N; v++) {
                rGraph[u][v] = graph[u][v];
            }
        }

        int[] parent = new int[N]; // Stores path

        // Folk Fulkerson
        while (bfs(rGraph, s, t, parent)) {
            int path_flow = Integer.MAX_VALUE;

            // Hitta vägflödet
            for (v = t; v != s; v = parent[v]) {
                u = parent[v]; // u -> v
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            // uppdatera residuala grafen
            for (v = t; v != s; v = parent[v]) {
                u = parent[v]; // u -> v
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            max_flow += path_flow;
        }
        return max_flow;

    }

    public void removeRoute(int index) {
        Edge e = edges.get(index);
        // System.out.println("Removing edge: " + e.toString());
        graph[e.u][e.v] = 0;
        /*
         * for (int u = 0; u < N; u++) {
         * for (int v = 0; v < N; v++) {
         * System.out.println(graph[u][v]);
         * }
         * }
         */
    }

    public void solution() {
        int start = 0;
        int end = M;
        int ans = 0;
        int newMaxFlow, mid;

        while (start <= end) {

            mid = (start + end) / 2;
            int[][] updatedNodeGraph = removeRoutesUntil(mid);
            newMaxFlow = fordFulkerson(updatedNodeGraph, start, end);

            // Try to remove more routes if maxFlow is >= nbrStudents
            if (newMaxFlow >= C) {
                start = mid + 1;
            }
            // Otherwise try to remove less routes if maxFlow < nbrStudents
            else {
                ans = mid;
                end = mid - 1;
            }
        }
        // Check what the maxFlow is once the max amount of routes are removed
        int maxRemoved = ans - 1;
        int maxFlow = fordFulkerson(removeRoutesUntil(maxRemoved), start, end);
        System.out.println(maxRemoved + " " + maxFlow);
    }

    private int[][] removeRoutesUntil(int index) {
        int[][] updatedGraph = new int[N][N];
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                updatedGraph[u][v] = graph[u][v];
            }
        }
        for (int i = 0; i < index; i++) {
            Edge removedEdge = edges.get(i);
            updatedGraph[removedEdge.u][removedEdge.v] = 0;
            updatedGraph[removedEdge.v][removedEdge.u] = 0;
        }
        return updatedGraph;
    }
}

class Edge {
    int u, v;
    Edge reverse;

    public Edge(int u, int v) {
        this.u = u;
        this.v = v;
    }

    public String toString() {
        return u + "-" + v;
    }
}

class Parser {
    List<Edge> edges = new ArrayList<>();
    int[] routesToRemove;
    int N = 0;
    int M = 0;
    int C = 0;
    int P = 0;
    int graph[][];

    Parser(Scanner scan) {
        run(scan);
    }

    private void run(Scanner scan) {
        this.N = scan.nextInt();
        this.M = scan.nextInt();
        this.C = scan.nextInt();
        this.P = scan.nextInt();
        this.graph = new int[N][N];

        // Creates initial graph
        for (int i = 0; i < M; i++) {
            int u = scan.nextInt();
            int v = scan.nextInt();
            int capacity = scan.nextInt();
            graph[u][v] = capacity;
            graph[v][u] = capacity;
            edges.add(new Edge(u, v));
        }
        // All the routes to remove
        routesToRemove = new int[P];
        for (int i = 0; i < P; i++) {
            int removeRouteIndex = scan.nextInt();
            routesToRemove[i] = removeRouteIndex;
        }
    }
}
