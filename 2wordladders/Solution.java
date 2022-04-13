import java.util.*;

public class Solution {

    private Map<String, List<String>> neighborList = new HashMap<>();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // scan first two integers
        int numberOfWords = scan.nextInt();
        int numberOfQueries = scan.nextInt();

        List<String> words = new ArrayList<>();
        Map<String, String> doubleWords = new HashMap<>();

        // scan the words
        for (int i = 0; i < numberOfWords; i++) {
            words.add(scan.next());
        }
        for (int j = 0; j < numberOfQueries; j++) {
            String firstWord = scan.next();
            String secondWord = scan.next();
            doubleWords.put(firstWord, secondWord);
        }
        scan.close();

        // for every double word, calculate the shortest apth
        for (Map.Entry<String, String> entry : doubleWords.entrySet()) {
            //int res = bfs(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey().substring(1));
        }

    }

    public void readWords() {
        Scanner scan = new Scanner(System.in);
        // scan first two integers
        int numberOfWords = scan.nextInt();
        int numberOfQueries = scan.nextInt();

        // scan the words
        for (int i = 0; i < numberOfWords; i++) {
            words.add(scan.next());
        }
        for (int j = 0; j < numberOfQueries; j++) {
            String firstWord = scan.next();
            String secondWord = scan.next();
            doubleWords.put(firstWord, secondWord);
        }
        scan.close();
    }

    public int bfs(String s, String t) {
        // precendents
        Map<String, String> pred = new HashMap<>();
        // set all visisted to false
        Map<String, Boolean> visited = new HashMap<>();
        for (Map.Entry<String, Boolean> v : visited.entrySet()) {
            v.setValue(false);
        }
        // new list q containing s, the first one we visit
        Queue<String> q = new LinkedList<>();
        q.add(s);
        visited.put(s, true);
        // return if already at destination
        if (s.equals(t)) {
            return 0;
        }
        while (!q.isEmpty()) {
            String v = q.poll(); // take out the first element form q
            for (String w : neighborList(v)) {
                if (visited.get(w) == false) {
                    visited.put(w, true);
                    q.add(w);
                    pred.put(w, v);
                    if (w.equals(t)) {
                        System.out.println("Found Path s-t");
                        return getSize(pred, t);
                    }
                }
            }
        }
        System.out.println("Found no path s - t");
        return -1;
    }

    public List<String> neighborList(String s) {
        return null;
    }

    public int getSize(Map<String, String> pred, String t) {
        int counter = -1;
        if (pred.get(t) != null) {
            for (String s = t; s != null; s = pred.get(s)) {
                counter++;
            }
        }
        return counter;
    }
}