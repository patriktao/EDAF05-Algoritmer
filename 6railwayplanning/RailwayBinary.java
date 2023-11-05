import java.util.*;

public class RailwayBinary {

    private int graph[][];
    Edge[] routesToRemove;
    private int totalNodes;
    private int totalStudents;
    private int totalRoutesToRemove;
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
        this.totalNodes = p.totalNodes;
        this.totalStudents = p.totalStudents;
        this.totalRoutesToRemove = p.totalRoutesToRemove;
        this.routesToRemove = p.routesToRemove;
        this.s = 0;
        this.t = totalNodes - 1;
        this.graph = p.graph;
    }

    public void solution() {
        int start = 0;
        int end = totalRoutesToRemove;
        int removeCounter = 0;
        int pathFlow, mid;

        // Kollar hur många vägar vi kan ta bort innan vi hittar maximala flödet
        while (start <= end) { // O(log(n)) worst case
            mid = (start + end) / 2;

            int[][] updatedNodeGraph = removeRoutesUntil(mid);

            pathFlow = fordFulkerson(updatedNodeGraph); // O(E*f) där E antalet kanter och f maximala flödet

            // Try to remove more routes if maxFlow is >= totalStudents
            if (pathFlow >= totalStudents) {
                start = mid + 1;
            }
            // Otherwise try to remove less routes if maxFlow < totalStudents
            else {
                removeCounter = mid;
                end = mid - 1;
            }
        }
        // Check what the maxFlow is once the max amount of routes are removed
        int maxRemoved = removeCounter - 1; // Adjust index
        int[][] maxFlowGraph = removeRoutesUntil(maxRemoved);
        int maxFlow = fordFulkerson(maxFlowGraph);
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

        // Initiala flödet genom grafen
        int max_flow = 0;

        // Residuala grafen⁄
        int[][] rGraph = new int[this.totalNodes][this.totalNodes];
        for (u = 0; u < this.totalNodes; u++) {
            for (v = 0; v < this.totalNodes; v++) {
                rGraph[u][v] = graph[u][v];
            }
        }

        // Sparar path
        int[] parent = new int[this.totalNodes];

        // Folk Fulkerson, true ifall vi hittar en väg genom grafen.
        while (bfs(rGraph, parent)) {
            int path_flow = Integer.MAX_VALUE;

            // Hittar vägflödet
            for (v = t; v != s; v = parent[v]) {
                u = parent[v]; // u -> v
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            // uppdaterar residuala grafen
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
        // Håll koll på besökta noder
        boolean[] visited = new boolean[this.totalNodes];
        for (int i = 0; i < this.totalNodes; ++i) {
            visited[i] = false;
        }
        // Vi skapar en kö för de noderna vi ska besöka
        Queue<Integer> q = new LinkedList<>();

        // Vi lägger till Source-noden (den vi börjar att gå igenom)
        q.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (q.isEmpty() == false) {
            Integer u = q.poll();
            for (int v = 0; v < this.totalNodes; v++) {
                if (visited[v] == false && rGraph[u][v] > 0) {
                    q.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        foundPath = visited[t]; // Har vi en nod kopplad till den sista noden betyder det att det finns en path
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
    int totalNodes = 0;
    int totalRoutes = 0;
    int totalStudents = 0;
    int totalRoutesToRemove = 0;
    int graph[][];

    Parser(Scanner scan) {
        run(scan);
    }

    private void run(Scanner scan) {
        this.totalNodes = scan.nextInt();
        this.totalRoutes = scan.nextInt();
        this.totalStudents = scan.nextInt();
        this.totalRoutesToRemove = scan.nextInt();
        this.graph = new int[totalNodes][totalNodes];

        // Creates initial graph
        for (int i = 0; i < totalRoutes; i++) {
            int u = scan.nextInt();
            int v = scan.nextInt();
            int capacity = scan.nextInt();
            graph[u][v] = capacity;
            graph[v][u] = capacity;
            edges.add(new Edge(u, v, capacity));
        }
        // All the routes to remove
        routesToRemove = new Edge[totalRoutesToRemove];
        for (int i = 0; i < totalRoutesToRemove; i++) {
            int removeRouteIndex = scan.nextInt();
            routesToRemove[i] = edges.get(removeRouteIndex);
        }
    }
}
