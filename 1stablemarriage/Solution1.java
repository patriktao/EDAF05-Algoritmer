import java.util.*;

public class Solution1 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int peopleOfEachGender = s.nextInt();
        int totalPeople = peopleOfEachGender * 2;

        // Preference Maps
        Map<Integer, List<Integer>> manPref = new HashMap<>();
        Map<Integer, List<Integer>> womPref = new HashMap<>();

        // Read the Preferences
        for (int i = 0; i < totalPeople; i++) {
            // Which person we are going to read the preferences from
            int person = s.nextInt();

            // We go through the pref list
            List<Integer> prefList = new ArrayList<>();
            for (int j = 0; j < peopleOfEachGender; j++) {
                prefList.add(s.nextInt());
            }

            // If woman preference list doesn't contain
            if (!womPref.containsKey(person)) {
                womPref.put(person, invertList(prefList));
            } else {
                manPref.put(person, prefList);
            }
        }

        // GS-Algorithm Match
        Map<Integer, Integer> matches = match(manPref, womPref);

        // result
        for (int i = 1; i <= matches.size(); i++) {
            System.out.println(matches.get(i));
        }
    }

    public static List<Integer> invertList(List<Integer> list) {
        List<Integer> invertedList = new ArrayList<>(list);
        for (int i = 0; i < list.size(); i++) {
            // list.get(3)-1 = 3-1 = 2 4
            invertedList.set(list.get(i) - 1, i + 1);
        }
        return invertedList;
    }

    public static Map<Integer, Integer> match(Map<Integer, List<Integer>> manPref,
            Map<Integer, List<Integer>> womPref) {

        // matchedPairs
        Map<Integer, Integer> matchedPairs = new HashMap<>();

        // adds each man to a new list
        Queue<Integer> singleMen = new LinkedList<>();
        for (int i = 1; i <= manPref.size(); i++) {
            singleMen.add(i);
        }

        while (!singleMen.isEmpty()) {
            // take out the first element from p
            int m = singleMen.poll(); // 1

            // the woman m prefers the most and m has not yet proposed to preference lists
            // first woman. remove from preference list since we are not going to ask her
            // again
            int w = manPref.get(m).remove(0);

            // this womans own preference list
            List<Integer> womanPref = womPref.get(w);

            // if w has no partner then
            if (!matchedPairs.containsKey(w)) {
                matchedPairs.put(w, m);
            } else if (womanPref.get(m - 1) < womanPref.get(matchedPairs.get(w) - 1)) { //eftersom i listan börjar man på index 0, och i män listan börjar man på 1. 
                // else if w prefers m over hesr current partner mw then
                // remove the pair (mw ,w)
                int prevMan = matchedPairs.remove(w);
                // (m,w) becomes a pair
                matchedPairs.put(w, m);
                // add previous man to singleMen
                singleMen.add(prevMan);
            } else {
                singleMen.add(m); // the woman didnt want the man
            }
        }

        return matchedPairs;
    }

}
