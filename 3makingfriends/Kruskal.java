
/* Minimal Spinning Tree */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Kruskal {

    List<Edge> edges;
    List<Set<Integer>> set;
    Node[] nodes;

    public static void main(String[] args) {
        new Kruskal().run();
    }

    public void run() {
        Parser p = new Parser(new Scanner(System.in));
        edges = p.edges;
        nodes = p.nodes;

        int result = kruskals();
        System.out.println(result);

    }

    private Node find(Node node) {
        Node p = node;
        while (p.parent != p) {
            p = p.parent;
        }
        while (node.parent != p) {
            Node w = node.parent;
            node.parent = p;
            node = w;
        }
        return p;
    }

    private int kruskals() {
        int total_weight = 0;
        Queue<Edge> queue = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);
        queue.addAll(edges);
        while (!queue.isEmpty()) {
            Edge e = queue.peek();
            if (!find(e.u).equals(find(e.v))) {
                union(e);
                total_weight += e.weight;
            }
            queue.remove(e);
        }
        return total_weight;
    }

    private void union(Edge e) {
        Node u = find(e.u);
        Node v = find(e.v);
        if (u.size < v.size) {
            u.parent = v;
            v.size += u.size;
        } else {
            v.parent = u;
            u.size += v.size;
        }
    }

}

class Node {
    int id, size;
    Node parent;

    public Node(int id) {
        this.id = id;
        this.parent = this;
        this.size = 1;
    }
}

class Edge {

    Node u, v;
    int weight;

    public Edge(Node u, Node v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    public String toString() {
        return u.id + "-" + v.id + "w:" + weight;
    }
}

class Parser {

    List<Edge> edges;
    Node[] nodes;

    public Parser(Scanner scan) {
        read(scan);
    }

    private void read(Scanner scan) {
        int numberNodes = scan.nextInt();
        int numberEdges = scan.nextInt();

        nodes = new Node[numberNodes];
        for (int i = 0; i < numberNodes; i++) {
            nodes[i] = new Node(i + 1);
        }

        edges = new ArrayList<>();
        for (int i = 0; i < numberEdges; i++) {
            int u = scan.nextInt();
            int v = scan.nextInt();
            int w = scan.nextInt();
            Edge edge = new Edge(nodes[u - 1], nodes[v - 1], w);
            edges.add(edge);
        }


        scan.close();
    }
}