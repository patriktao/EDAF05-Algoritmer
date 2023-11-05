from collections import deque
import sys


class StableMatching:
    def __init__(self):
        self.student_pref = dict()
        self.company_pref = dict()
        self.nbr_pairs = 0
        self.parser()
        self.run()

    def run(self):
        matched = self.match()
        matched = sorted(matched.items(), key=lambda x: x[0])
        for i in matched:
            print(i[1])

    def parser(self):
        self.nbr_pairs = int(input())
        raw_lines = sys.stdin.readlines()
        lines = []
        for i in range(len(raw_lines)):
            data = raw_lines[i].strip().split()
            data = list(map(int, data))
            lines.extend(data)
        chunk_size = self.nbr_pairs + 1
        chunks = [lines[i : i + chunk_size] for i in range(0, len(lines), chunk_size)]
        for line in chunks:
            person = line[0]
            preferences = line[1:]
            if person not in self.company_pref:
                self.company_pref[person] = self.invert_list(preferences)
            else:
                self.student_pref[person] = preferences

    def invert_list(self, pref):
        inverted_list = [0] * self.nbr_pairs
        for i in range(self.nbr_pairs):
            inverted_list[pref[i] - 1] = i + 1
        return inverted_list

    # Gale-Shapley Algorithm
    def match(self):
        available_students = deque(range(1, self.nbr_pairs + 1))
        matches = {}

        while available_students:
            s = available_students.popleft()
            c = self.student_pref[s].pop(0)

            # company pref
            company_prefList = self.company_pref[c]

            if c not in matches:
                matches[c] = s
            elif company_prefList[s - 1] < company_prefList[matches[c] - 1]:
                prev = matches[c]
                matches[c] = s
                available_students.append(prev)
            else:
                available_students.append(s)
        return matches


def main():
    StableMatching()


if __name__ == "__main__":
    main()
