from collections import deque
import heapq


class Edge:
    def __init__(self, u, v, weight):
        self.u = u
        self.v = v
        self.weight = weight

    def __lt__(self, other):
        return self.weight < other.weight

    def __str__(self):
        return f"u:{self.u}, v:{self.v}, weight:{self.weight}"


class Node:
    def __init__(self, id):
        self.id = id
        self.parent = self
        self.size = 1
    
    def __str__(self):
        return f"{self.id}"


class Kruskal:
    def __init__(self):
        self.edges = []
        self.nodes = []
        self.parser()
        self.kruskals()

    def parser(self):
        line = input().split()
        number_people = int(line[0])
        number_edges = int(line[1])
        for i in range(1, number_people + 1):
            self.nodes.append(Node(i))
        for _ in range(number_edges):
            new_line = input().split()
            u = int(new_line[0])
            v = int(new_line[1])
            weight = int(new_line[2])
            edge = Edge(self.nodes[u - 1], self.nodes[v - 1], weight)
            self.edges.append(edge)

    def kruskals(self):
        distance = 0
        queue = deque()

        for e in self.edges:
            queue.append(e)
            queue = sorted(queue, key=lambda edge: edge.weight, reverse=False)

        while queue:
            e = queue[0]
            if self.find(e.u) != self.find(e.v):
                self.union(e)
                distance += e.weight
            queue.pop()
            queue = sorted(queue, key=lambda edge: edge.weight, reverse=False)

        print(distance)

    def find(self, node):
        p = node
        while p.parent != p:
            p = p.parent
        while node.parent != p:
            w = node.parent
            node.parent = p
            node = w
        return p

    def union(self, e):
        u = self.find(e.u)
        v = self.find(e.v)

        if u.size < v.size:
            u.parent = v
            v.size += u.size
        else:
            v.parent = u
            u.size += v.size


if __name__ == "__main__":
    Kruskal()
