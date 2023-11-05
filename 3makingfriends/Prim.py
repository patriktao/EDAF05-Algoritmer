from collections import deque
import heapq


class Edge:
    def __init__(self, u, v, weight):
        self.u = u
        self.v = v
        self.weight = weight

    def __str__(self):
        return f"u:{self.u}, v:{self.v}, weight:{self.weight}"


class Prim:
     def __init__(self):
          self.edges = []
          self.nodes = []
          self.graph = {}
          self.parser()
          self.create_graph()
          self.prims()

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

     def prims(self):
          visited = set()

          queue = []
          heapq.heapify(queue)  # Convert the list into a min-heap

          for edge in self.graph[0]:
               heapq.heappush(queue, (edge.weight, edge))

          print(queue[0])
          visited.add(queue[0][1])

          distance = 0

          while queue:
               e = heapq.heappop(queue)

               if e.u in visited and e.v in visited:
                    continue
               else:
                    for next_e in self.graph.get(e.v - 1):
                         if next_e.v not in visited:
                              heapq.heappush(queue, (next_e.weight, next_e))
                         #queue.append(next_e)
                         #queue = deque(sorted(queue, key=lambda e: e.weight))
                    visited.add(e.v)
                    distance += e.weight
          print(distance)


if __name__ == "__main__":
    Prim()
