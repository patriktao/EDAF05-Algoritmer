import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClosestPair {

    private Point[] sortedY;
    private Point[] sortedX;

    public static void main(String[] args) {
        new ClosestPair().run();
    }

    public void run() {
        Parser parser = new Parser(new Scanner(System.in));
        closest_points(parser.getPoints());
    }

    private void closest_points(Point[] points) {
        this.sortedY = points.clone();
        this.sortedX = points.clone();
        Arrays.sort(sortedX, (p1, p2) -> p2.x - p1.x);
        Arrays.sort(sortedY, (p1, p2) -> p2.y - p1.y);
        printResult(closest(sortedX, points.length));
    }

    /* Räknar avstånd mellan p1 och p2 genom ren matte */
    private double getDistance(Point p1, Point p2) {
        return (double) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow((p1.y - p2.y), 2));
    }

    // Print result with 6 decimals
    private void printResult(double f) {
        String s = String.format(java.util.Locale.US, "%.6f", f);
        System.out.println(s);
    }

    public double closest(Point[] Px, int size) {
        if (size <= 1) {
            return Double.MAX_VALUE;
        } else if (size == 2) {
            return getDistance(Px[0], Px[1]);
        }
        int mid = size / 2;

        // Divide Px to left and right, find minimum distance from l and r array
        Point[] Lx = Arrays.copyOfRange(Px, 0, mid);
        Point[] Rx = Arrays.copyOfRange(Px, mid + 1, size);
        double leftDelta = closest(Lx, Lx.length);
        double rightDelta = closest(Rx, Rx.length);
        double delta = Math.min(leftDelta, rightDelta);

        // Create s, add the points of s that are within delta
        ArrayList<Point> s = new ArrayList<>();
        double midPoint = Rx[0].x;
        for (int i = 0; i < sortedY.length; i++) {
            double negThres = midPoint - delta;
            double posThres = midPoint + delta;
            if (sortedY[i].x > negThres && sortedY[i].x < posThres) {
                s.add(sortedY[i]);
            }
        }

        s.sort((p1, p2) -> p1.y - p2.y);
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
