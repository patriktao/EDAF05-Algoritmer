import java.util.*;
import java.io.*;

public class StableMarriage {

    private Map<Integer, LinkedList<Integer>> manPref;
    private Map<Integer, LinkedList<Integer>> womPref;

    public static void main(String[] args) throws IOException {
        new StableMarriage().run();
    }

    public void run() {
        Parser parser = new Parser(new Scanner(System.in));
        manPref = parser.manPref;
        womPref = parser.womPref;
        List<Pair> temp = match().getList();
        temp.sort((e1, e2) -> e1.woman - e2.woman);
        for (Pair p : temp) {
            System.out.println(p.man);
        }
    }

    private PairList match() {
        PairList matchedPairs = new PairList();

        Queue<Integer> singleMen = new LinkedList<>();
        for (int i = 1; i <= manPref.size(); i++) {
            singleMen.add(i); // 1 2 3 4, på position 0 1 2 3
        }

        while (!singleMen.isEmpty()) {
            int m = singleMen.poll(); // 1 från position 0
            int w = manPref.get(m).remove(0);

            // this womans own preference list
            LinkedList<Integer> womanPref = womPref.get(w);

            /*
             * OBS: On all linkedList, the preference lists inside
             * womPref and menPref all start with index 0
             */

            // if w has no partner then
            if (!matchedPairs.containsWoman(w)) {
                matchedPairs.add(w, m);
            } else if (womanPref.get(m - 1) < womanPref.get(matchedPairs.getMan(w) - 1)) {
                int prevMan = matchedPairs.getMan(w);
                matchedPairs.removePair(w);
                matchedPairs.add(w, m);
                singleMen.add(prevMan);
            } else {
                singleMen.add(m);
            }
        }
        return matchedPairs;
    }
}

class Parser {

    private Scanner scan;
    HashMap<Integer, LinkedList<Integer>> manPref;
    HashMap<Integer, LinkedList<Integer>> womPref;

    public Parser(Scanner scan) {
        this.scan = scan;
        manPref = new HashMap<>();
        womPref = new HashMap<>();
        run();
    }

    private void run() {
        int peopleOfEachGender = scan.nextInt();
        int totalPeople = peopleOfEachGender * 2;
        for (int i = 0; i < totalPeople; i++) {
            int person = scan.nextInt();
            LinkedList<Integer> prefList = new LinkedList<>();
            for (int j = 0; j < peopleOfEachGender; j++) {
                prefList.add(scan.nextInt());
            }

            if (!womPref.containsKey(person)) {
                womPref.put(person, invertList(prefList));
            } else {
                manPref.put(person, prefList);
            }
        }
    }

    private LinkedList<Integer> invertList(List<Integer> list) {
        LinkedList<Integer> invertedList = new LinkedList<>(list);
        for (int i = 0; i < list.size(); i++) {
            invertedList.set(list.get(i) - 1, i + 1);
        }
        return invertedList;
    }
}

class Pair {
    int man;
    int woman;

    public Pair(int w, int m) {
        this.man = m;
        this.woman = w;
    }
}

class PairList {

    private List<Pair> list;

    public PairList() {
        this.list = new ArrayList<>();
    }

    public List<Pair> getList() {
        return list;
    }

    public void add(int w, int m) {
        list.add(new Pair(w, m));
    }

    public boolean containsWoman(int w) {
        for (Pair p : list) {
            if (p.woman == w) {
                return true;
            }
        }
        return false;
    }

    public int getMan(int w) {
        for (Pair p : list) {
            if (p.woman == w) {
                return p.man;
            }
        }
        System.out.println("Cant find Woman in getMan");
        return -1;
    }

    public void removePair(int w) {
        list.removeIf(p -> p.woman == w);
    }
}