import java.util.*;

public class Railway2 {

    Node[] graph;
    private List<Edge> edges;
    private List<Integer> routesToRemove;
    private int C;
    private int totalNodes;
    private int totalRoutes;
    private int totalEdges;
    private int maxFlow;

    // C: Total students to transport
    // Edge capacity: How many students it can carry
    // Total Maximal Flow: needs to be higher than amount of people
    // If cannot remove a route then start implementing the plan
    // How many routes i can remove and what the maximal flow are after removing
    // these routes

    public static void main(String[] args) {
        new Railway2().run();
    }

    public void run() {
        initialize();
        createGraph();
        removeRoutes();
    }

    private void initialize() {
        Parser p = new Parser(new Scanner(System.in));
        this.edges = p.edges;
        this.routesToRemove = p.routesToRemove;
        this.C = p.totalStudents;
        this.totalNodes = p.totalNodes;
        this.totalRoutes = p.totalRoutes;
        this.totalEdges = p.totalEdges;
        this.graph = new Node[totalNodes];
    }

    public void createGraph() {
        // Creating Cities
        for (int i = 0; i < totalNodes; i++) {
            graph[i] = new Node();
        }
        // Creating Routes
        for (Edge a : edges) {
            Edge b = new Edge(a.to, a.from, 0, 0);
            // Set reverse path
            a.setReverse(b);
            b.setReverse(a);
            // Add to graph
            graph[a.from].edges.add(a);
            graph[a.to].edges.add(b);
        }
    }

    public void removeRoutes() {
        boolean working = true;
        maxFlow = 0;
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
            System.out.println(2 + " " + maxFlow);
        }
    }

    public void fordFulkerson() {
        // source and destination
        int s = 0;
        int t = totalNodes - 1;

        // bfs
        while (true) {
            Edge[] pred = new Edge[totalNodes];
            HashMap<Integer, Boolean> visited = new HashMap<>();
            Queue<Node> q = new LinkedList<>();

            // first node
            q.add(graph[s]);
            visited.put(s, true);

            // base cond
            if (s == t) {
                return;
            }

            while (!q.isEmpty()) {
                Node v = q.poll();
                // we go through all edges and find neighbours
                for (Edge e : v.edges) {
                    // if the neighbor node is not visited yet
                    if (visited.get(e.to) == null && e.to != s && e.capacity > e.flow) {
                        visited.put(e.to, true);
                        pred[e.to] = e;
                        q.add(graph[e.to]);
                    }
                }
            }

            // Destination not reached, terminate algorithm and print maxFlow
            if (pred[t] == null) {
                break;
            }

            // destination is reached
            int pathFlow = Integer.MAX_VALUE;

            // find maximum flow that can be pushed through the route, by finding minimum
            // residual flow of every edge in the path
            for (Edge e = pred[t]; e != null; e = pred[e.from]) {
                pathFlow = Math.min(pathFlow, e.capacity - e.flow);
            }

            // Adds to flow values and subtracts from reverse flow values in path
            for (Edge e = pred[t]; e != null; e = pred[e.from]) {
                e.flow += pathFlow;
                e.reverse.flow -= pathFlow;
            }

            maxFlow += pathFlow;
        }

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
            edges.add(new Edge(from, to, capacity, 0));
        }
        for (int i = 0; i < totalRoutes; i++) {
            int removeRouteIndex = scan.nextInt();
            routesToRemove.add(removeRouteIndex);
        }
    };

}

class Edge {
    int to, from, capacity, flow;
    Edge reverse;

    public Edge(int from, int to, int capacity, int flow) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = flow;
        flow = 0;
    }

    public void setReverse(Edge e) {
        reverse = e;
    }

    public String toString() {
        return from + "-" + to + ":" + capacity;
    }
}

class Node {
    ArrayList<Edge> edges = new ArrayList<>();
}