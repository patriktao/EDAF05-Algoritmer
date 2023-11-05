import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GoldbergTarjan {

    static class Node {
        int id, height, flow;

        public Node(int id) {
            this.id = id;
            this.height = 0;
            this.flow = 0;
        }
    }

    static class Edge {

        Node u, v;
        int capacity, flow;

        public Edge(Node u, Node v, int capacity) {
            this.u = u;
            this.v = v;
            this.capacity = capacity;
            this.flow = 0;
        }
    }

    static class Parser {

        List<Edge> edges;
        Edge[] routesToRemove;
        Node[] nodes;
        int[][] residual;
        int numberStudents, numberNodes, numberEdges, numberRoutes;

        public Parser(Scanner scan) {
            run(scan);
        }

        private void run(Scanner scan) {
            this.numberNodes = scan.nextInt();
            this.numberEdges = scan.nextInt();
            this.numberStudents = scan.nextInt();
            this.numberRoutes = scan.nextInt();

            this.nodes = new Node[numberNodes];
            for (int i = 0; i < numberNodes; i++) {
                nodes[i] = new Node(i + 1);
            }

            this.residual = new int[numberNodes][numberNodes];

            this.edges = new LinkedList<Edge>();
            for (int i = 0; i < numberEdges; i++) {
                int u = scan.nextInt();
                int v = scan.nextInt();
                int capacity = scan.nextInt();
                Edge u_v = new Edge(nodes[u], nodes[v], capacity);
                Edge v_u = new Edge(nodes[v], nodes[u], capacity);
                residual[u][v] = capacity;
                residual[v][u] = capacity;
                edges.add(u_v);
                edges.add(v_u);
            }

            this.routesToRemove = new Edge[numberRoutes];
            for (int i = 0; i < numberRoutes; i++) {
                int indexOfRoute = scan.nextInt();
                routesToRemove[i] = edges.get(indexOfRoute);
            }
            scan.close();
        }
    }

    List<Edge> edges;
    Edge[] routesToRemove;
    Node[] nodes;
    int totalNodes;
    int totalRoutesToRemove;
    int totalStudents;
    int s, t;

    public static void main(String[] args) {
        new GoldbergTarjan().run();
    }

    private void init() {
        Parser p = new Parser(new Scanner(System.in));
        this.edges = p.edges;
        this.routesToRemove = p.routesToRemove;
        this.nodes = p.nodes;
        this.s = 0;
        this.t = p.numberNodes - 1;
        this.totalRoutesToRemove = routesToRemove.length;
        this.totalStudents = p.numberStudents;
    }

    private void run() {
        init();
        preflow_push();
        int sum = 0;

        int start = 0;
        int end = totalRoutesToRemove;
        int removeCounter = 0;
        int pathFlow, mid;

        // Kollar hur många vägar vi kan ta bort innan vi hittar maximala flödet
        while (start <= end) { // O(log(n)) worst case
            mid = (start + end) / 2;

            removeRoutesUntil(mid);

            // Hittar flödet för vägen utan borttagen väg
            preflow_push(); 

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

    private void removeRoutesUntil(int index) {
        for (int i = 0; i < index; i++) {
            Edge removedEdge = routesToRemove[i];
        }
    }

    private Edge findEdge(Node u, Node v) {
        for (Edge e : edges) {
            if (e.u.equals(u) && e.v.equals(v)) {
                return e;
            }
        }
        return null;
    }

    private void push(Node u, Node v) {
        Edge e = findEdge(u, v);
        int delta = 0;
        while (u.flow > 0 && u.height > v.height && e != null) {
            if (e.flow > 0) {
                delta = Math.min(u.flow, e.capacity - e.flow);
                e.flow += delta;
            } else {
                e = findEdge(v, u);
                delta = Math.min(v.flow, e.flow);
                e.flow -= delta;
            }
        }
    }

    private boolean neighboursHaveLowerFlow(Node u) {
        for (Edge e : edges) {
            if (e.u == u && e.u.height > e.v.height) {
                return false;
            }
        }
        return true;
    }

    private void relabel(Node u) {
        if (u.flow > 0 && neighboursHaveLowerFlow(u)) {
            u.height++;
        }
    }

    private Node findNodeAvailable() {
        for (Node n : nodes) {
            if (n != nodes[t] && n.flow > 0) {
                return n;
            }
        }
        return null;
    }

    private void preflow_push() {
        nodes[s].height = nodes.length;
        // Set all flow of edges from Sink to the capacity of the edges (Preflow)
        for (Edge e : edges) {
            if (e.u.equals(nodes[s])) {
                e.flow = e.capacity;
            }
        }
        // Set all other flows of the edges from Sink Neighbour to 0
        for (Edge e : edges) {
            if (!e.u.equals(nodes[s])) {
                e.flow = 0;
            }
        }
        Node v = findNodeAvailable();
        while (v != null) {
            for (Node w : nodes) {
                if (v.height > w.height && findEdge(v, w) != null) {
                    push(v, w);
                } else {
                    relabel(v);
                }
            }
        }
    }

}
