
import java.util.*;

public class WordLadders {
    private ArrayList<Pair> pairs;
    private ArrayList<String> words;
    private HashMap<String, LinkedList<String>> neighborList = new HashMap<>();

    public static void main(String[] args) {
        new WordLadders().run(args);
    }

    public void run(String[] args) {
        Parser parser = new Parser(new Scanner(System.in));
        this.pairs = parser.getPairs();
        this.words = parser.getWords();

        // Create NeighborList
        createNeighborList();

        // Calculate shortest path between two words
        for (Pair p : pairs) {
            int res = bfs(p.s1, p.s2);
            if (res == -1) {
                System.out.println("Impossible");
            } else {
                System.out.println(res);
            }
        }
    }

    private void createNeighborList() {
        for (String s : words) {
            char[] array = s.toCharArray();
            Arrays.sort(array);
            String sortedArray = String.valueOf(array);
            for (int i = 0; i < 5; i++) {
                String rep = sortedArray.substring(0, i) + sortedArray.substring(i + 1);
                neighborList.computeIfAbsent(rep, k -> new LinkedList<>());
                List<String> neighbours = neighborList.get(rep);
                if (neighbours != null) {
                    neighbours.add(s);
                }
            }
        }
    }

    private int getSize(HashMap<String, String> pred, String t) {
        int counter = -1;
        if (pred.get(t) != null) {
            for (String str = t; str != null; str = pred.get(str)) {
                counter++;
            }
        }
        return counter;
    }

    private List<String> getNeighborList(String s) {
        char[] ar = s.substring(1).toCharArray();
        Arrays.sort(ar);
        return neighborList.get(String.valueOf(ar));
    }

    public int bfs(String s, String t) {
        HashMap<String, String> pred = new HashMap<>();
        Map<String, Boolean> visited = new HashMap<>();
        Queue<String> q = new LinkedList<>(); // new list q containing s, the first one we visit
        q.add(s);
        visited.put(s, true);
        if (s.equals(t)) { // return if already at destination
            return 0;
        }
        while (!q.isEmpty()) {
            String v = q.poll(); // take out the first element form q
            for (String w : getNeighborList(v)) {
                if (visited.get(w) == null) {
                    visited.put(w, true);
                    q.add(w);
                    pred.put(w, v);
                    if (w.equals(t)) {
                        return getSize(pred, t);
                    }
                }
            }
        }
        return -1;
    }
}

class Pair {
    public String s1;
    public String s2;

    public Pair(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
}

class Parser {
    private Scanner scan;
    private ArrayList<Pair> pairs;
    private ArrayList<String> words;

    public Parser(Scanner scan) {
        this.scan = scan;
        this.pairs = new ArrayList<>();
        this.words = new ArrayList<>();
        read();
    }

    private void read() {
        // scan first two integers
        int numberOfWords = scan.nextInt();
        int numberOfQueries = scan.nextInt();
        // scan the words
        for (var i = 0; i < numberOfWords; i++) {
            words.add(scan.next());
        }
        // Scan pairs
        for (var i = 0; i < numberOfQueries; i++) {
            String firstWord = scan.next();
            String secondWord = scan.next();
            pairs.add(new Pair(firstWord, secondWord));
        }
        scan.close();
    }

    public ArrayList<Pair> getPairs() {
        return pairs;
    }

    public ArrayList<String> getWords() {
        return words;
    }
}