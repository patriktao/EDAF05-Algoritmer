from collections import deque
import heapq


class Edge:
    def __init__(self, u, v, weight):
        self.u = u
        self.v = v
        self.weight = weight

    def __lt__(self, other):
        return self.weight < other.weight

    def __eq__(self, other):
        return self.weight == other.weight

    def __hash__(self):
        return hash((self.u, self.v, self.weight))

    def __str__(self):
        return f"u:{self.u}, v:{self.v}, weight:{self.weight}"


class Kruskal:
    def __init__(self):
        self.edges = []
        self.nodes = []
        self.graph = {}
        self.parser()
        self.create_graph()
        self.kruskals()

    def parser(self):
        line = input().split()
        number_people = int(line[0])
        number_edges = int(line[1])
        for _ in range(number_edges):
            new_line = input().split()
            u = int(new_line[0])
            v = int(new_line[1])
            weight = int(new_line[2])
            edge = Edge(u, v, weight)
            self.edges.append(edge)
        for i in range(1, number_people + 1):
            self.nodes.append(i)

    def create_graph(self):
        for i in range(len(self.nodes)):
            self.graph[i] = []
        for edge in self.edges:
            self.graph[edge.u - 1].append(edge)
            self.graph[edge.v - 1].append(Edge(edge.v, edge.u, edge.weight))

    def kruskals(self):
        pred = {}
        distance = 0
        queue = []
        heapq.heapify(queue)

        for node, edges in self.graph.items():
            for e in edges:
                heapq.heappush(queue, e)

        while queue:
            e = queue[0]
            if self.find(pred, e.u) != self.find(pred, e.v):
                self.union(pred, e)
                distance += e.weight
            heapq.heappop(queue)
        print(distance)

    def find(self, pred, node):
        if node not in pred:
            return node
        if pred[node] != node:
            pred[node] = self.find(pred, pred[node])
        return pred[node]

    def union(self, pred, e):
        u = self.find(pred, e.u)
        v = self.find(pred, e.v)
        if u is not None and v is not None and u != v:
            pred[v] = u


if __name__ == "__main__":
    Kruskal()
