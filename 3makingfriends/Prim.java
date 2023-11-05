
import java.util.*;

public class Prim {

    private List<Edge> edges;
    private List<Integer> nodes;
    private ArrayList<ArrayList<Edge>> graph = new ArrayList<>();

    public static void main(String[] args) {
        new Prim().run();
    }

    public void run() {
        Parser parser = new Parser(new Scanner(System.in));
        this.edges = parser.edges;
        this.nodes = parser.nodes;
        createGraph();
        primsAlgorithm();
    }

    private void createGraph() {
        for (int i = 0; i < nodes.size(); i++) {
            graph.add(new ArrayList<>());
        }
        for (Edge e : edges) {
            graph.get(e.from - 1).add(e);
            graph.get(e.to - 1).add(new Edge(e.to, e.from, e.weight));
        }
    }

    private void primsAlgorithm() {
        Set<Integer> visited = new HashSet<>();
        Queue<Edge> queue = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);
        queue.addAll(graph.get(0)); // Lägger till alla kanter från nod 0.
        visited.add(queue.peek().from); // besöker första noden
        int distance = 0;
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            if (visited.contains(edge.from) && visited.contains(edge.to)) {
                continue;
            } else {
                for (Edge nextEdge : graph.get(edge.to - 1)) {
                    if (!visited.contains(nextEdge.to)) {
                        queue.add(nextEdge);
                    }
                }
                visited.add(edge.to);
                distance += edge.weight;
            }   
        }
        System.out.println(distance);
    }
}

class Edge {
    int from;
    int to;
    int weight;

    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

class Parser {
    private Scanner scan;
    List<Edge> edges;
    List<Integer> nodes;

    public Parser(Scanner scan) {
        this.scan = scan;
        this.edges = new ArrayList<>();
        this.nodes = new ArrayList<>();
        read();
    }

    private void read() {
        int numberPeople = scan.nextInt();
        int numberEdges = scan.nextInt();
        for (int i = 0; i < numberEdges; i++) {
            int from = scan.nextInt();
            int to = scan.nextInt();
            int weight = scan.nextInt();
            edges.add(new Edge(from, to, weight));
        }
        for (int i = 1; i <= numberPeople; i++) {
            nodes.add(i);
        }
        scan.close();
    }
}
