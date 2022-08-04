import java.nio.file.Path;
import java.util.*;

public class RailwayBinary {

    private int graph[][];
    private List<Edge> edges;
    Edge[] routesToRemove;
    private int N;
    private int C;
    private int P;
    private int s, t;

    public static void main(String[] args) {
        new RailwayBinary().run();
    }

    public void run() {
        initialize();
        solution();
    }

    private void initialize() {
        Parser p = new Parser(new Scanner(System.in));
        this.N = p.N; // total nodes
        this.C = p.C; // total students to transfer
        this.P = p.P; // total routes to remove
        this.s = 0;
        this.t = N - 1;
        this.edges = p.edges;
        this.routesToRemove = p.routesToRemove;
        this.graph = p.graph;
    }

    public void solution() {
        int start = 0;
        int end = P;
        int removeCounter = 0;
        int pathFlow, mid;

        // Check how many routes we can remove before we find the max flow
        while (start <= end) {
            mid = (start + end) / 2;

            int[][] updatedNodeGraph = removeRoutesUntil(mid);
            pathFlow = fordFulkerson(updatedNodeGraph);

            // Try to remove more routes if maxFlow is >= C
            if (pathFlow >= C) {
                start = mid + 1;
            }
            // Otherwise try to remove less routes if maxFlow < C
            else {
                removeCounter = mid;
                end = mid - 1;
            }
        }
        // Check what the maxFlow is once the max amount of routes are removed
        int maxRemoved = removeCounter - 1; // Adjust index
        int maxFlow = fordFulkerson(removeRoutesUntil(removeCounter - 1));
        System.out.println(maxRemoved + " " + maxFlow);
    }

    public int[][] copyGraph(int[][] graph) {
        int[][] newGraph = new int[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                newGraph[i][j] = graph[i][j];
            }
        }
        return newGraph;
    }

    private int[][] removeRoutesUntil(int index) {
        int[][] newGraph = copyGraph(graph);
        for (int i = 0; i < index; i++) {
            Edge removedEdge = routesToRemove[i];
            newGraph[removedEdge.u][removedEdge.v] = 0;
            newGraph[removedEdge.v][removedEdge.u] = 0;
        }
        return newGraph;
    }

    public int fordFulkerson(int[][] graph) {
        int u, v;

        int max_flow = 0; // No initial flow

        int[][] rGraph = new int[N][N]; // Residual graph
        for (u = 0; u < N; u++) {
            for (v = 0; v < N; v++) {
                rGraph[u][v] = graph[u][v];
            }
        }

        int[] parent = new int[N]; // Stores path

        // Folk Fulkerson
        while (bfs(rGraph, parent)) {
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

    public boolean bfs(int[][] rGraph, int[] parent) {
        boolean foundPath = false;
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
                    q.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        foundPath = visited[t];
        return foundPath;
    }

}

class Edge {
    int u, v, capacity;

    public Edge(int u, int v, int capacity) {
        this.u = u;
        this.v = v;
        this.capacity = capacity;
    }

    public String toString() {
        return u + "-" + v;
    }
}

class Parser {
    List<Edge> edges = new ArrayList<>();
    Edge[] routesToRemove;
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
            edges.add(new Edge(u, v, capacity));
        }
        // All the routes to remove
        routesToRemove = new Edge[P];
        for (int i = 0; i < P; i++) {
            int removeRouteIndex = scan.nextInt();
            routesToRemove[i] = edges.get(removeRouteIndex);
        }
    }
}
