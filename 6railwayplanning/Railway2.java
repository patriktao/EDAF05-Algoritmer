import java.util.*;

public class Railway2 {

    private List<ArrayList<Edge>> graph;
    private List<Edge> edges;
    private List<Integer> routesToRemove;
    private int totalStudents;
    private int totalNodes;
    private int totalRoutes;
    private int totalEdges;

    public static void main(String[] args) {
        new Railway2().run();
    }

    public void run() {
        initialize();
        createGraph();
        fordFulkerson();
    }

    private void initialize() {
        Parser p = new Parser(new Scanner(System.in));
        edges = p.edges;
        routesToRemove = p.routesToRemove;
        totalStudents = p.totalStudents;
        totalNodes = p.totalNodes;
        totalRoutes = p.totalRoutes;
        totalEdges = p.totalEdges;
    }

    public void createGraph() {
        this.graph = new ArrayList<>();
        for (int i = 0; i < totalNodes; i++) {
            graph.add(new ArrayList<>());
        }
        for (Edge e : edges) {
            Edge to = new Edge(e.to, e.from, e.capacity);
            // Residual
            e.residual = to;
            to.residual = e;
            // Add to graph
            graph.get(e.from).add(e);
            graph.get(e.to).add(to);
        }
    }

    public void removeRoutes() {
        boolean working = true;
        int route = 0;

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

        while (working) {
            Edge edgeToBeRemoved = edges.get(routesToRemove.get(0));
            edges.remove(edgeToBeRemoved);
            fordFulkerson();
        }
    }

    public void fordFulkerson() {
        int maxFlow = 0;

        // Residual Graph
        int rGraph[][] = new int[V][V];

        // Hitta enkel väg
        int pathFlow = bfs(0, totalNodes - 1);

    }

    public int bfs(int s, int t) {
        HashMap<Integer, Integer> pred = new HashMap<>();
        HashMap<Integer, Boolean> visited = new HashMap<>();
        Queue<Integer> q = new LinkedList<>();

        // first node
        q.add(s);
        visited.put(s, true);

        // base cond
        if (s == t) {
            return 0;
        }

        while (!q.isEmpty()) {
            int v = q.poll();
            for (Edge e : graph.get(v)) { // we go through all edges and find neighbours
                int w = e.to; // w - neighbour node
                if (visited.get(w) == null) { // if the neighbor node is not visited yet
                    q.add(w); // we go to that node by adding to the queue
                    visited.put(w, true); // it is now visited!
                    pred.put(w, v); // now this new node and previous node are connected
                    if (w == t) { // if that node is the string t we are looking for
                        return getPathFlow(pred, t);
                    }
                }
            }
        }
        return 0;
    }

    private int getPathFlow(HashMap<Integer, Integer> pred, int n2) {
        int path_flow = 0;
        if (pred.get(n2) != null) {
            for (int node = n2; node != -1; node = pred.get(node)) {
                for (Edge e : graph.get(node)) {
                    if (e.from == node && e.to == pred.get(node) && e.capacity >= totalStudents
                            && path_flow > e.capacity) {
                        path_flow = e.capacity;
                    }
                }
            }
        }
        return path_flow;
    }
}

class Parser {
    List<Edge> edges;
    List<Integer> routesToRemove;
    int totalStudents;
    int totalNodes;
    int totalRoutes;
    int totalEdges;

    public Parser(Scanner scan) {
        edges = new ArrayList<>();
        routesToRemove = new ArrayList<>();
        totalStudents = 0;
        totalNodes = 0;
        totalRoutes = 0;
        totalEdges = 0;
        run(scan);
    }

    private void run(Scanner scan) {
        this.totalNodes = scan.nextInt();
        this.totalEdges = scan.nextInt();
        this.totalStudents = scan.nextInt();
        this.totalRoutes = scan.nextInt();
        for (int i = 0; i < totalEdges; i++) {
            int from = scan.nextInt();
            int to = scan.nextInt();
            int capacity = scan.nextInt();
            edges.add(new Edge(from, to, capacity));
        }
        for (int i = 0; i < totalRoutes; i++) {
            int removeRouteIndex = scan.nextInt();
            routesToRemove.add(removeRouteIndex);
        }
    };

}

class Edge {
    int to, from, capacity, flow;
    Edge residual;

    public Edge(int from, int to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        flow = 0;
    }

    public String toString() {
        return from + "-" + to + ":" + capacity;
    }
}