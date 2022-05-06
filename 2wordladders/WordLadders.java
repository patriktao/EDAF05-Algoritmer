
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

        // creates a list of neighbour for every word we have
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

    private List<String> getNeighborList(String s) {
        char[] array = s.substring(1).toCharArray();
        Arrays.sort(array);
        return neighborList.get(String.valueOf(array));
    }

    private void createNeighborList() {
        for (String s : words) {
            /* by sorting them we can compare strings easier */
            char[] array = s.toCharArray();
            Arrays.sort(array);
            String sortedArray = String.valueOf(array);
            for (int i = 0; i < 5; i++) {
                // draw an arc only if the last four letters are present
                String rep = sortedArray.substring(0, i) + sortedArray.substring(i + 1);
                /*
                 * inptu
                 * i = 0: = nptu
                 * i = 1: i + ptu = iptu
                 * i = 2: in + tu = intu
                 * i = 3: inp + u = inpt
                 * i = 4: = inpt
                 */
                // Create new list for each new neighbor node, if there isn't already.
                neighborList.computeIfAbsent(rep, e -> new LinkedList<>());
                // retrieve the list and add the word as neighbor.
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
            //start at string t and loop back each node to count the size of graph.
            for (String str = t; str != null; str = pred.get(str)) {
                counter++;
            }
        }
        return counter;
    }

    public int bfs(String s, String t) {
        HashMap<String, String> pred = new HashMap<>();
        Map<String, Boolean> visited = new HashMap<>();
        Queue<String> q = new LinkedList<>(); // new list q containing node s, the first one we visit
        q.add(s); // we are visiting that node first
        visited.put(s, true); // first node we visit
        if (s.equals(t)) { // return if already at destination
            return 0;
        }
        while (!q.isEmpty()) {
            String v = q.poll(); // take out the first element form q
            for (String w : getNeighborList(v)) {
                if (visited.get(w) == null) { // if the neighbor string is not visited yet
                    visited.put(w, true);
                    q.add(w); // we go to that node by adding to the queue
                    pred.put(w, v); // now this new node and previous node are connected
                    if (w.equals(t)) { // if that node is the string t we are looking for   
                        return getSize(pred, t);
                    }
                }
            }
        }
        return -1;
    }
}

class Pair {
    String s1;
    String s2;

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