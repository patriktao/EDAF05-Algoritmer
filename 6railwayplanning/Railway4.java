
import java.util.*;

public class Railway4 {
    private Node[] graph;
    private Node[] rGraph;
    private List<Edge> edges;
    int[] routesToRemove;
    private int N;
    private int C;

    public static void main(String[] args) {
        new Railway4().run();
    }

    public void run() {
        initialize();
        createGraph();
        solution();
    }

    private void initialize() {
        Parser p = new Parser(new Scanner(System.in));
        this.N = p.N; // total nodes
        this.C = p.C; // total students to transfer
        this.edges = p.edges;
        this.routesToRemove = p.routesToRemove;
        this.graph = new Node[N];
        this.rGraph = new Node[N];
    }

    private void createGraph() {
        // Create all nodes
        for (int i = 0; i < N; i++) {
            graph[i] = new Node();
        }
        // Create links between nodes
        for (Edge e1 : edges) {
            Edge e2 = new Edge(e1.v, e1.u, e1.capacity);
            graph[e1.u].edges.add(e1);
            graph[e1.v].edges.add(e2);
        }
    }

    private Edge getRoute(int from, int to) {
        for (int i = 0; i < rGraph[from].edges.size(); i++) {
            Edge route = rGraph[from].edges.get(i);
            if (to == route.v && from == route.u) {
                return route;
            }
        }
        return null;
    }

    private int getRouteCapacity(int from, int to) {
        for (int i = 0; i < rGraph[from].edges.size(); i++) {
            Edge route = rGraph[from].edges.get(i);
            if (to == route.v && from == route.u) {
                return route.capacity;
            }
        }
        return 0;
    }

    private void removeRoute(int index) {
        Edge removeRoute = edges.get(index);
        for (Node n : graph) {
            for (Edge e : n.edges) {
                if (e.u == removeRoute.u && e.v == removeRoute.v) {
                    n.edges.remove(e);
                }
                if (e.v == removeRoute.u && e.u == removeRoute.v) {
                    n.edges.remove(e);
                }
            }
        }
    }

    public boolean bfs(int s, int t, int[] parent) {

        // Keep track of visited nodes
        boolean[] visited = new boolean[N];
        for (int i = 0; i < N; ++i) {
            visited[i] = false;
        }

        // Queue for BFS
        Queue<Integer> queue = new LinkedList<>();

        // Insert Source Node
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < N; v++) {
                if (visited[v] == false && getRouteCapacity(u, v) > 0) {
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return false;
    }

    private Node[] cloneGraph(Node[] graph) {
        Node[] newGraph = new Node[N];
        for(Node n : graph){
            Node newNode = new Node();
            for(Edge e : n.edges){
                newNode.edges.add(e);
            }
            newGraph;
        }

    }

    public int folkFulkerson(int s, int t) {
        int u, v;
        int max_flow = 0; // No initial flow

        this.rGraph = graph.clone(); // Create residual graph

        int[] parent = new int[N]; // Stores path

        while (bfs(s, t, parent)) {
            int path_flow = Integer.MAX_VALUE;

            // Hitta vägflödet
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, getRouteCapacity(u, v));
            }

            // uppdatera residuala grafen
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                getRoute(u, v).capacity -= path_flow;
                getRoute(v, u).capacity += path_flow;
            }
            max_flow += path_flow;
        }
        return max_flow;
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
            } else {
                break;
            }
        }
        System.out.println(removeCounter + " " + maxFlow);
    }
}

class Edge {
    int u, v, capacity, flow;

    public Edge(int u, int v, int capacity) {
        this.u = u;
        this.v = v;
        this.capacity = capacity;
        this.flow = 0;
    }

    public String toString() {
        return u + "-" + v;
    }
}

class Node {
    ArrayList<Edge> edges = new ArrayList<>();
}

class Parser {
    List<Edge> edges = new ArrayList<>();
    int[] routesToRemove;
    int N = 0;
    int M = 0;
    int C = 0;
    int P = 0;

    Parser(Scanner scan) {
        run(scan);
    }

    private void run(Scanner scan) {
        this.N = scan.nextInt();
        this.M = scan.nextInt();
        this.C = scan.nextInt();
        this.P = scan.nextInt();

        // Creates initial graph
        for (int i = 0; i < M; i++) {
            int u = scan.nextInt();
            int v = scan.nextInt();
            int capacity = scan.nextInt();
            edges.add(new Edge(u, v, capacity));
        }
        // All the routes to remove
        routesToRemove = new int[P];
        for (int i = 0; i < P; i++) {
            int removeRouteIndex = scan.nextInt();
            routesToRemove[i] = removeRouteIndex;
        }
    }
}