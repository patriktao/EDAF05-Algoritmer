import java.util.*;

public class Solution {

	public static void main(String[] args) {
		// Read from the console
		Scanner scan = new Scanner(System.in);

		// Read the number of people of first line
		int peopleOfEachGender = scan.nextInt();
		int totalNbrPeople = peopleOfEachGender * 2;

		// Map
		Map<Integer, List<Integer>> manPref = new HashMap<>();
		Map<Integer, List<Integer>> womPref = new HashMap<>();
		Map<Integer, Integer> matchedPairs = new HashMap<>(); // The first is the woman index!

		// Read the Preferences
		for (int i = 1; i <= totalNbrPeople; i++) {
			// Which person we are going to read the preferences from
			int person = scan.nextInt();
			List<Integer> prefList = new ArrayList<>();

			// We go through the pref list
			for (int j = 1; j <= peopleOfEachGender; j++) {
				prefList.add(scan.nextInt());
			}

			// If woman preference list doesn't contain
			if (!womPref.containsKey(person)) {
				// System.out.print("preflist: " + prefList);
				womPref.put(person, invertList(prefList));
			} else {
				manPref.put(person, prefList);
			}

		}

		// GS-Algorithm Match
		Map<Integer, Integer> results = match(manPref, womPref, matchedPairs);

		for (int i = 1; i <= results.size(); i++) {
			System.out.println(results.get(i));
		}

		/*
		 * The output should consist of exactly N rows. Row i should contain exactly one
		 * integer, the index of the man
		 * paired with woman i.
		 * 
		 * 1 man 1
		 * 2 man 2
		 * 6 man 3
		 * 5 man 4
		 */
		Map<Integer, Integer> invertPairs = new HashMap<>();

		for (var entry : results.entrySet()) {
			invertPairs.put(entry.getValue(), entry.getKey());
		}

	}

	/*
	 * 4, 2, 1, 3. original
	 * 1, 2, 3, 4 index
	 * 3, 2, 4, 1. inverted
	 * i = 1 list.get(1) = 4 = man 4 = index 4 sÃ¤tt 1 pÃ¥ index 4
	 * i = 2 list.get(2) = 2 = man 2 = index 2 sÃ¤tt 2 pÃ¥ index 2
	 * i = 3 list.get(3) = 1 = man 1 = index 1 sÃ¤tt 3 pÃ¥ index 1
	 */
	
	/*
	 * 
	 * 3 4 1 2
	 * 3 4 1 2
	 * 
	 * 1 4 2 3       list.get(1) == 4    
	 * 1 3 4 2       list get(4) == 2 
	 * 
	 */
	public static List<Integer> invertList(List<Integer> list) {
		List<Integer> invertedList = new ArrayList<>(list);
		for (int i = 0; i < list.size(); i++) {
			invertedList.set(list.get(i)-1, i+1); 
			// list.get(3)-1 = 3-1 = 2   4
		}
		return invertedList;
	}

	public static Map<Integer, Integer> match(Map<Integer, List<Integer>> manPref,
			Map<Integer, List<Integer>> womPref,
			Map<Integer, Integer> matchedPairs) {

		// add each man m âˆˆ M to a list p
		List<Integer> manList = new ArrayList<>();
		for (int i = 1; i <= manPref.size(); i++) {
			manList.add(i); // 1 2 3 4
		}
		

		while (!manList.isEmpty()) {
			// take out the first element from p
			int man = manList.remove(0); //1 queue

			// the woman m prefers the most and m has not yet proposed to
			int prefWoman = manPref.get(man).get(0); // for example get mman 1's preference lists first woman

			// remove from preference list since we are not going to ask her again
			manPref.get(man).remove(0);

			List<Integer> prefWomanPref = womPref.get(prefWoman); // this womans own preference list

			if (!matchedPairs.containsKey(prefWoman)) { // if w has no partner then
				matchedPairs.put(prefWoman, man); // (m,w) becomes a pair
			} 
			else if (prefWomanPref.get(man-1) < prefWomanPref.get(matchedPairs.get(prefWoman)-1) ) { // else if w prefers m over her current partner mw then																							
				int oldMan = matchedPairs.get(prefWoman);
				matchedPairs.remove(prefWoman); // remove the pair (mw ,w)
				matchedPairs.put(prefWoman, man); // (m,w) becomes a pair
				manList.add(oldMan); // add mw to p
			} else {
				manList.add(man); // the woman didnt want the man
			}
		}
		return matchedPairs;
	}
}