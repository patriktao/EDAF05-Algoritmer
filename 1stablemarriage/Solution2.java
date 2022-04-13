import java.util.*;

public class Solution2 {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        int peopleOfEachGender = s.nextInt();
        int totalPeople = peopleOfEachGender * 2;

        // Preference Maps
        Map<Integer, LinkedList<Integer>> manPref = new HashMap<>();
        Map<Integer, LinkedList<Integer>> womPref = new HashMap<>();

        // Read the Preferences
        for (int i = 0; i < totalPeople; i++) {
            // Which person we are going to read the preferences from
            int person = s.nextInt();

            // We go through the pref list
            LinkedList<Integer> prefList = new LinkedList<>();
            for (int j = 0; j < peopleOfEachGender; j++) {
                prefList.add(s.nextInt()); //position 0 and onwards
            }

            // If woman preference list doesn't contain
            if (!womPref.containsKey(person)) {
                womPref.put(person, invertList(prefList));
            } else {
                manPref.put(person, prefList);
            }
        }

        // matchedPairs
        int[] matchedPairs = new int[peopleOfEachGender];

        // GS-Algorithm Match
        int[] matches = match(manPref, womPref, matchedPairs);

        // Results
        for(int res : matches){
            System.out.println(res);
        }
    }
public static LinkedList<Integer> invertList(List<Integer> list) {
		LinkedList<Integer> invertedList = new LinkedList<>(list);
		for (int i = 0; i < list.size(); i++) {
			invertedList.set(list.get(i)-1, i+1); 
			// list.get(3)-1 = 3-1 = 2   4
		}
		return invertedList;
	}

    public static int[] match(Map<Integer, LinkedList<Integer>> manPref,
            Map<Integer, LinkedList<Integer>> womPref, int[] matchedPairs) {

        // adds each man to a new list
        Queue<Integer> singleMen = new LinkedList<>();
        for (int i = 1; i <= manPref.size(); i++) {
            singleMen.add(i); // 1 2 3 4, på position 0 1 2 3 
        }

        while (!singleMen.isEmpty()) {
            // take out the first element from p
            int m = singleMen.poll(); // 1 från position 0

            // the woman m prefers the most and m has not yet proposed to preference lists
            // first woman. remove from preference list since we are not going to ask her again
            int w = manPref.get(m).remove(0);

            // this womans own preference list
            LinkedList<Integer> womanPref = womPref.get(w); //

            /* OBS: On all linkedList, the matchedPairs and the preferenceList inside womPref and menPref, all start with index 0 */

            // if w has no partner then
            if (matchedPairs[w-1] == 0) { 
                matchedPairs[w-1] = m;
            } else if (womanPref.get(m - 1) < womanPref.get(matchedPairs[w-1] - 1)) { 
                // else if w prefers m over hesr current partner mw then remove the pair (mw ,w)
                int prevMan = matchedPairs[w-1];
                // (m,w) becomes a pair
                matchedPairs[w-1] = m;
                // add previous man to singleMen
                singleMen.add(prevMan);
            } else {
                singleMen.add(m); // the woman didnt want the man
            }
        }
        return matchedPairs;
    }
}
