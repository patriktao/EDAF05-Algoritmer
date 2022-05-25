import java.util.Scanner;
import java.util.*;

public class Gorilla {

    private int[][] costs;
    private String s1, s2;
    private HashMap<Character, Integer> chars;
    private final static int delta = -4; // kostnaden

    public static void main(String[] args) {
        new Gorilla().run();
    }

    public void run() {
        Parser p = new Parser(new Scanner(System.in));
        this.costs = p.costs;
        this.chars = p.chars;
        for (Query q : p.queries) {
            this.s1 = q.s1;
            this.s2 = q.s2;
            System.out.println(calculateOpt(makeOptTable(s1, s2), s1.length(), s2.length()));
        }
    }

    private int[][] makeOptTable(String s1, String s2) {
        /* n:rows, m:columns */
        int n = s1.length() + 1;
        int m = s2.length() + 1;
        /* Vi gör en tabell där vi sparar alla optimerade värden */
        int[][] optTable = new int[n][m];
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
                /*
                 * Alpha: Kostnaden att byta från c1 till c2, detta går att hitta i vår
                 * kostnadsmatris: costs
                 */
                int alpha = costs[chars.get(c1)][chars.get(c2)];
                int opt1 = alpha + optTable[i - 1][j - 1];
                /* s1 saknar tecken, konsumera tecken från s2 */
                int opt2 = delta + optTable[i][j - 1];
                /* s2 saknar tacken, konsumera tecken från s1 */
                int opt3 = delta + optTable[i - 1][j];
                /* Sparar mest optimala lösningen i tabellen */
                optTable[i][j] = calcMax(opt1, opt2, opt3);
            }
        }
        return optTable;
    }

    private String calculateOpt(int[][] optTable, int i, int j) {
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

            /* Olika fallen */
            int opt1 = optTable[i - 1][j - 1] + alpha; // använder vi alpha, konsumerar vi tecken från båda strängar
            int opt2 = optTable[i][j - 1] + delta; // s1 saknar tecken, konsumera från s2
            int opt3 = optTable[i - 1][j] + delta; // s2 saknar tecken, konsumera från s1

            // Ifall en av fallen är sann minimerar vi endast en av strängarna s1 och s2
            if (opt2 > Math.max(opt1, opt3)) {
                s1 = s1.substring(0, i) + "*" + s1.substring(i);
                i++; // vi försäkrar att tecken i s1 inte minskas och bara s2 
            } else if (opt3 > opt1) {
                s2 = s2.substring(0, j) + "*" + s2.substring(j);
                j++; //försäkra att tecken i s2 inte minskas och bara s1.
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