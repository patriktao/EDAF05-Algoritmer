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
        if (size == 1) {
            return Double.MAX_VALUE;
        } else if (size == 2) {
            return getDistance(Px[0], Px[1]);
        }

        int mid = size / 2;

        // Divide Px to left and right
        Point[] Lx = Arrays.copyOfRange(Px, 0, mid);
        Point[] Rx = Arrays.copyOfRange(Px, mid + 1, size);

        // Find minimum distance from l and r array
        double deltaL = closest(Lx, Lx.length);
        double deltaR = closest(Rx, Rx.length);
        double delta = Math.min(deltaL, deltaR);

        // Create sY, add the points of sY that are within delta
        ArrayList<Point> sY = new ArrayList<>();

        // Mid Point to compare to
        double midPoint = Rx[0].x;
        for (int i = 0; i < sY.size(); i++) {
            if (midPoint - delta < sortedY[i].x || midPoint + delta > sortedY[i].x) {
                sY.add(sortedX[i]);
            }
        }

        // Sort sY on y
        sY.sort((p1, p2) -> p1.y - p2.y);

        int C;
        // Let's determine the closests distance
        for (int i = 0; i < sY.size() - 1; i++) {
            // bestämma C
            C = Math.min(sY.size() - 1, 16);
            for (int j = i + 1; j < i + C; j++) {
                double distance = getDistance(sY.get(i), sY.get(j));
                if (distance < delta) {
                    delta = distance;
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
