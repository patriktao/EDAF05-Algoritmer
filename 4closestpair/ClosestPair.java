import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClosestPair {

    public static void main(String[] args) {
        new ClosestPair().run();
    }

    public void run() {
        Parser parser = new Parser(new Scanner(System.in));
        closest_points(parser.getPoints());
    }

    private void closest_points(Point[] points) {
        Point[] sortedX = points.clone();
        Point[] sortedY = points.clone();
        Arrays.sort(sortedX, (p1, p2) -> p2.x - p1.x); // O(nlog(n))
        Arrays.sort(sortedY, (p1, p2) -> p2.y - p1.y);
        printResult(closest(sortedX, sortedY, points.length));
    }

    private double getDistance(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }

    private void printResult(double f) {
        String s = String.format(java.util.Locale.US, "%.6f", f);
        System.out.println(s);
    }

    public double closest(Point[] Px, Point[] Py, int size) {

        if (size <= 1) {
            return Double.MAX_VALUE;
        } else if (size == 2) {
            return getDistance(Px[0], Px[1]);
        }

        int mid = size / 2;

        // Divide Px to left and right
        Point[] Lx = Arrays.copyOfRange(Px, 0, mid);
        Point[] Rx = Arrays.copyOfRange(Px, mid + 1, size);

        // en lista likadan som x sorterad på y
        Point[] Ly = Arrays.copyOfRange(Px, 0, mid);
        Point[] Ry = Arrays.copyOfRange(Px, mid + 1, size);

        Arrays.sort(Ly, (p1, p2) -> p2.y - p1.y);
        Arrays.sort(Ry, (p1, p2) -> p2.y - p1.y);

        // Recursion: Find Minimum Distance From L and R Array
        double leftDelta = closest(Lx, Ly, Lx.length); 
        double rightDelta = closest(Rx, Ry, Rx.length);

        // Choose the shortest distance between points
        double delta = Math.min(leftDelta, rightDelta);

        // Create s, add the points of PY that are within delta
        ArrayList<Point> s = new ArrayList<>();

        double midX = Rx[0].x;
        for (Point p : Py) {
            double dist = Math.abs(midX - p.x);
            if (dist < delta) {
                s.add(p);
            }
        }
        
        //Bruteforce
        for (int i = 0; i < s.size(); i++) {
            for (int j = i + 1; j < Math.min(s.size(), i + 4); j++) {
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
            points[i] = new Point(x, y);
        }
    }

    public Point[] getPoints() {
        return points;
    }

}

class Point {

    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "x:" + x + "," + "y:" + y;
    }
}
