import java.util.*;

public class RailwaySlow {

    private int graph[][];
    private List<Edge> edges;
    int[] routesToRemove;
    private int N;
    private int C;

    public static void main(String[] args) {
        new RailwaySlow().run();
    }

    public void run() {
        initialize();
        solution();
    }

    private void initialize() {
        Parser p = new Parser(new Scanner(System.in));
        this.N = p.N; // total nodes
        this.C = p.C; // total students to transfer
        this.edges = p.edges;
        this.routesToRemove = p.routesToRemove;
        this.graph = p.graph;
    }

    public boolean bfs(int[][] rGraph, int s, int t, int[] parent) {
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

    public int folkFulkerson(int s, int t) {
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
        graph[e.u][e.v] = 0;
        graph[e.v][e.u] = 0;
    }

    public void solution() {
        int maxFlow;
        int removeCounter = 0;

        // First run
        maxFlow = folkFulkerson(0, N - 1);

        // Remove Routes
        for (int i = 0; i < routesToRemove.length; i++) {
            removeRoute(routesToRemove[i]);
            int newFlow = folkFulkerson(0, N - 1);
            if (newFlow >= C) {
                maxFlow = Integer.min(maxFlow, newFlow);
                removeCounter++;
            } else { // Flödet är mindre än C
                break;
            }
        }
        System.out.println(removeCounter + " " + maxFlow);
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

/* En annan lösning med binärsökning */

/* Kopiera graf och ta bort en kant */

/*
 * En tom graf men lägger till en kant istället och hittar flödet för den grafen
 */

/*
 * Ta bort alla kanter fram tills mitten och checkar om kapaciteten, en
 * binörsäkning på när man tar bort kanter
 */

/*
 * 100 kanter, lägga till 50 kanter om det är för lite flöde, gör en ny graf där
 * vi gör 75 grader, om det är för mycket gör en ny iteration där vi gör 67
 * grader tills det blir optimalt.
 */