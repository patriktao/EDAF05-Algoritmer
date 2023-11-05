from collections import deque
import sys


class WordLadders:
    def __init__(self):
        self.pairs = []
        self.words = []
        self.parser()
        self.neighborList = {}
        self.createNeighborList()
        self.run()

    def parser(self):
        firstLine = input().strip().split()
        nbrOfWords = int(firstLine[0])
        nbrOfQueries = int(firstLine[1])
        for _ in range(nbrOfWords):
            self.words.append(input())
        for _ in range(nbrOfQueries):
            line = input().strip().split()
            first_word = line[0]
            second_word = line[1]
            self.pairs.append((first_word, second_word))

    def run(self):
        for p1, p2 in self.pairs:
            res = self.depth_first_search(p1, p2)
            if res == sys.maxsize:
                print("Impossible")
            else:
                print(res)

    def createNeighborList(self):
        for word in self.words:
            char_array = sorted(word)
            sorted_string = "".join(char_array)
            for i in range(len(word)):
                rep = sorted_string[:i] + sorted_string[i + 1 :]
                self.neighborList.setdefault(rep, []).append(word)

    def getNeighborList(self, s):
        array = sorted(s[1:])
        sorted_array = "".join(array)
        return self.neighborList.get(sorted_array)
    
    def depth_first_search(self, start, end):
        if start == end:
            return 0
        visited = set()
        return self.dfs(start, end, visited, 0)

    def dfs(self, curr, end, visited, depth):
        visited.add(curr)
        
        if curr == end:
            return depth
        
        min_layer = sys.maxsize

        for neighbor in self.getNeighborList(curr):
            if neighbor not in visited:
                layer = self.dfs(neighbor, end, visited, depth+1)
                if layer != -1:
                    min_layer = min(min_layer, layer)
                
        return min_layer


if __name__ == "__main__":
    WordLadders()
