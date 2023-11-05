import java.util.Scanner;
import java.util.*;

public class Gorilla {

    private int[][] costs;
    private int[][] optTable;
    private HashMap<Character, Integer> chars;
    private final static int delta = -4; // kostnad

    public static void main(String[] args) {
        new Gorilla().run();
    }

    public void run() {
        Parser p = new Parser(new Scanner(System.in));
        this.costs = p.costs;
        this.chars = p.chars;
        /*
         * För att veta hur lika två strängar är vill vi hitta den minsta kostnaden för
         * att fixa två strängar så att de blir indentiska
         */
        for (Query q : p.queries) {
            makeOptTable(q.s1, q.s2);
            System.out.println(calculateOpt(q.s1, q.s2));
        }
    }

    private void makeOptTable(String s1, String s2) {
        /*
         * solved by saving the optimal scores for the solution of every subproblem
         * instead of recalculating them
         */
        
        // n:rows, m:columns
        int n = s1.length() + 1;
        int m = s2.length() + 1;
        this.optTable = new int[n][m]; // Vi sparar alla optimerade värden, följer pseudokoden för makeTable
        /* Då i = 0 */
        for (int i = 0; i < n; i++) {
            optTable[i][0] = delta * i;
        }
        /* Då j = 0 */
        for (int j = 0; j < m; j++) {
            optTable[0][j] = delta * j;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                char c1 = s1.charAt(i - 1);
                char c2 = s2.charAt(j - 1);
                int alpha = costs[chars.get(c1)][chars.get(c2)]; // Kostnaden att byta från c1 till c2, finns i vår
                                                                 // kostnadsmatris.
                // Olika fallen
                int opt1 = alpha + optTable[i - 1][j - 1]; // Mismatch: vi tar bort båda tecken och det kostar alpha.
                int opt2 = delta + optTable[i][j - 1]; // s1 saknar tecknet, konsumera tecken från s2,
                int opt3 = delta + optTable[i - 1][j]; // s2 saknar tecknet, konsumera tecken från s1
                optTable[i][j] = calcMax(opt1, opt2, opt3); // Sparar mest optimala lösningen i tabellen
            }
        }
    }
    
    private String calculateOpt(String s1, String s2) {
        int i = s1.length();
        int j = s2.length();

        while (true) {
            /* Basfallen */
            if (i == 0) {
                return "*".repeat(j) + s1 + " " + s2;
            }
            if (j == 0) {
                return s1 + " " + "*".repeat(i) + s2;
            }

            /* Annars utvärderar vi de andra fallen */
            int alpha = costs[chars.get(s1.charAt(i - 1))][chars.get(s2.charAt(j - 1))];
            int opt1 = alpha + optTable[i - 1][j - 1]; // använder vi alpha, konsumerar vi tecken från båda strängar
            int opt2 = delta + optTable[i][j - 1]; // s1 saknar tecken, konsumera från s2
            int opt3 = delta + optTable[i - 1][j]; // s2 saknar tecken, konsumera från s1

            // Ifall en av fallen är sann minimerar vi endast en av strängarna s1 och s2
            if (opt2 > Math.max(opt1, opt3)) {
                s1 = s1.substring(0, i) + "*" + s1.substring(i);
                i++; // vi försäkrar att tecken i s1 inte minskas och bara s2
            } else if (opt3 > opt1) {
                s2 = s2.substring(0, j) + "*" + s2.substring(j);
                j++; // försäkra att tecken i s2 inte minskas och bara s1.
            }
            i--;
            j--;
        }
    }

    private int calcMax(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }
}

class Parser {

    private Scanner scan;
    int[][] costs;
    Query[] queries;
    HashMap<Character, Integer> chars;

    public Parser(Scanner scan) {
        this.scan = scan;
        this.chars = new HashMap<>();
        run();
    }

    private void run() {
        /* Read Characters */
        String characters = String.join("", scan.nextLine().split(" "));

        /* Dimension */
        int dimension = characters.length();

        /* Put Characters in Map connected to an index */
        for (int i = 0; i < dimension; i++) {
            chars.put(characters.charAt(i), i);
        }

        /* Read Cost Matrix */
        this.costs = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                costs[i][j] = scan.nextInt();
            }
        }

        /* Read Queries */
        int q = scan.nextInt();
        this.queries = new Query[q];
        for (int i = 0; i < q; i++) {
            String firstSeq = scan.next();
            String secondSeq = scan.next();
            queries[i] = new Query(firstSeq, secondSeq);
        }
        scan.close();
    }
}

class Query {
    String s1;
    String s2;

    public Query(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public String toString() {
        return s1 + " " + s2;
    }
}