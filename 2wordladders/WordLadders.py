from collections import deque


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
            res = self.bfs(p1, p2)
            if res == -1:
                print("Impossible")
            else:
                print(res)

    def createNeighborList(self):
        for word in self.words:
            char_array = sorted(word)
            sorted_string = "".join(char_array)
            for i in range(5):
                rep = sorted_string[:i] + sorted_string[i + 1 :]
                self.neighborList.setdefault(rep, []).append(word)

    def getNeighborList(self, s):
        array = sorted(s[1:])
        sorted_array = "".join(array)
        return self.neighborList.get(sorted_array)

    def bfs(self, start, end):
        if start == end:
            return 0

        pred = dict()
        visited = set()
        visited.add(start)
        queue = deque([start])

        while queue:
            v = queue.popleft()
            for w in self.getNeighborList(v):
                if w not in visited:
                    visited.add(w)
                    queue.append(w)
                    pred[w] = v
                    if w == end:
                        return self.getSize(pred, end)
        return -1

    def getSize(self, pred, t):
        counter = -1
        curr = t
        while curr is not None:
            counter += 1
            if pred.get(curr) is None:
                break
            curr = pred[curr]
        return counter


if __name__ == "__main__":
    WordLadders()