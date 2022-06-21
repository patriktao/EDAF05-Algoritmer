import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ma5176sc-s on 22/05/18.
 */
public class Railway {

    private int n, source, sink, maxFlow;
    private int visitedToken = 1;
    private int[] visited;
    private boolean solved;
    private List<List<Edge>> graph;
    private int[][] capacityMatrix;

    public static void main(String[] args) {
        new Railway().run(args[0]);

    }

    private void run(String arg) {
        parse(arg);
        source = 0;
        sink = 54;
        solve();
        System.out.println(maxFlow);
    }

    private void addEdge(int from, int to, int capacity) {
        Edge e1 = new Edge(to, capacity);
        Edge e2 = new Edge(from, capacity);
        e1.residual = e2;
        e2.residual = e1;
        graph.get(from).add(e1);
        graph.get(to).add(e2);
    }

    private void solve() {
        if (solved)
            return;
        maxFlow = 0;
        visited = new int[n];
        int flow = Integer.MAX_VALUE;
        while (flow != 0) {
            flow = dfs(source, Integer.MAX_VALUE);
            visitedToken++;
            maxFlow += flow;
        }
        // printMinCut();
        solved = true;
    }

    private void printMinCut() {
        ArrayList<Integer> reachable = new ArrayList<>();
        ArrayList<Integer> unreachable = new ArrayList<>();
        for (int i = 1; i < capacityMatrix.length; i++)
            if (visited[i] == visitedToken - 1)
                reachable.add(i);
            else
                unreachable.add(i);
        for (Integer i : reachable)
            for (Integer j : unreachable)
                if (capacityMatrix[i][j] != 0)
                    System.out.println(i + " " + j + " " + capacityMatrix[i][j]);
    }

    private int dfs(int node, int flow) {
        if (node == sink) {
            return flow;
        }
        List<Edge> edges = graph.get(node);
        visited[node] = visitedToken;
        for (Edge edge : edges) {
            if (visited[edge.to] != visitedToken && edge.capacity > 0) {
                if (edge.capacity < flow)
                    flow = edge.capacity;
                int dfsFlow = dfs(edge.to, flow);
                if (dfsFlow > 0) {
                    Edge res = edge.residual;
                    edge.capacity -= dfsFlow;
                    res.capacity += dfsFlow;
                    return dfsFlow;
                }
            }
        }
        return 0;
    }

    private void parse(String arg) {
        try {
            BufferedReader br = new BufferedReader(new FileReader((arg)));
            n = Integer.parseInt(br.readLine());
            graph = new ArrayList<>(n);
            capacityMatrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());
                br.readLine();
            }
            int numberOfArcs = Integer.parseInt(br.readLine());
            for (int i = 0; i < numberOfArcs; i++) {
                String[] s = br.readLine().split(" ");
                int from = Integer.parseInt(s[0]);
                int to = Integer.parseInt(s[1]);
                int capacity = Integer.parseInt(s[2]);
                if (capacity == -1)
                    capacity = Integer.MAX_VALUE;
                addEdge(from, to, capacity);
                capacityMatrix[from][to] = capacity;
                capacityMatrix[to][from] = capacity;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Edge {
        Edge residual;
        int to, capacity;
        final int originalCapacity;

        Edge(int to, int capacity) {
            this.originalCapacity = capacity;
            this.capacity = capacity;
            this.to = to;
        }
    }
}