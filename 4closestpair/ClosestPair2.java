import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClosestPair2 {

    public static void main(String[] args) {
        new ClosestPair().run();
    }

    public void run() {
        Parser parser = new Parser(new Scanner(System.in));
        closest_points(parser.getPoints());
    }

    private void closest_points(Point[] points) {
        Point[] sortedX = points.clone();
        Arrays.sort(sortedX, (p1, p2) -> p2.x - p1.x); // O(nlog(n))
        printResult(closest(sortedX, 0, points.length - 1));
    }

    /* Räknar avstånd mellan p1 och p2 genom ren matte */
    private double getDistance(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }

    // Print result with 6 decimals
    private void printResult(double f) {
        String s = String.format(java.util.Locale.US, "%.6f", f);
        System.out.println(s);
    }

    public double closest(Point[] Px, int start, int stop) {
        // base cases
        if (stop <= start) {
            return Double.MAX_VALUE;
        }

        int mid = start + (stop - start) / 2;
        // Recursion: find minimum distance from l and r array
        double leftDelta = closest(Px, start, mid);
        double rightDelta = closest(Px, mid + 1, stop);
        double delta = Math.min(leftDelta, rightDelta);

        // Create s, add the points of s that are within delta
        ArrayList<Point> s = new ArrayList<>();

        for (int i = mid; i <= stop; i++) {
            if (Math.abs(Px[i].x - Px[mid].x) < delta) {
                s.add(Px[i]);   
            } else {
                break;
            }
        }

        for (int i = mid - 1; i > start; i--) {
            if (Math.abs(Px[i].x - Px[mid].x) < delta) {
                s.add(Px[i]);
            } else {
                break;
            }
        }
        s.sort((p1, p2) -> p2.y - p1.y);
        // bruteforce
        for (int i = 0; i < s.size(); i++) {
            for (int j = i + 1; j < Math.min(s.size(), i + 15); j++) {
                double dist = getDistance(s.get(i), s.get(j));
                if (dist < delta) {
                    delta = dist;
                }
            }
        }
        return delta;
    }
}

class Parser {

    private Scanner scan;
    private Point[] points;

    public Parser(Scanner scan) {
        this.scan = scan;
        run();
    }

    private void run() {
        int NumberOfPoints = scan.nextInt();
        this.points = new Point[NumberOfPoints];
        for (int i = 0; i < NumberOfPoints; i++) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            points[i] = new Point(i, x, y);
        }
    }

    public Point[] getPoints() {
        return points;
    }

}

class Point {

    int number;
    int x;
    int y;

    public Point(int number, int x, int y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "x:" + x + "," + "y:" + y;
    }
}
