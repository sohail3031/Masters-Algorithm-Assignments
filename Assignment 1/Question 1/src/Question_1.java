import java.util.ArrayList;
import java.util.Scanner;

/*
    Note: to run the code, give the input in below format

    5 -> input size
    2 4 -> 2 is x value separated by a space and 4 is y value
    4 5 -> 4 is x value separated by a space and 5 is y value
    1 2 -> 1 is x value separated by a space and 2 is y value
    -10 -20 -> -10 is x value separated by a space and -20 is y value
    13 25 -> 13 is x value separated by a space and 25 is y value
 */


// Point class that stores the data of the points
class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int comparePoints(Point point) {
        return Integer.compare(this.x, point.x);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

public class Question_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of points: ");

        int size = scanner.nextInt();
        Point[] points = new Point[size];

        for (int i = 0; i < size; i++) {
            System.out.println("Enter point " + (i + 1));
            
            points[i] = new Point(scanner.nextInt(), scanner.nextInt());
        }

        scanner.close();

        if (points.length < 3) {
            System.out.println("none");
        } else {
            findCollinear(points);
        }
    }

    //    method that will find the collinear points
    public static void findCollinear(Point[] points) {
        boolean flag = false;
        Point point1 = null, point2 = null, point3 = null;

        for (int i = 0; i < points.length; i++) {
            ArrayList<Point> pointArrayList = new ArrayList<>();
            pointArrayList.add(points[i]);

            int slope1 = 1;

            for (int j = i + 1; j < points.length; j++) {
                int slope2 = points[i].comparePoints(points[j]);

                if (slope2 == slope1) {
                    pointArrayList.add(points[j]);
                } else {
                    if (pointArrayList.size() > 2) {
                        point1 = pointArrayList.get(0);
                        point2 = pointArrayList.get(1);
                        point3 = pointArrayList.get(2);
                        flag = true;

                        break;
                    } else {
                        pointArrayList.clear();
                        pointArrayList.add(points[i]);
                        pointArrayList.add(points[j]);
                    }

                    slope1 = slope2;
                }
            }

            if (pointArrayList.size() > 2) {
                point1 = pointArrayList.get(0);
                point2 = pointArrayList.get(1);
                point3 = pointArrayList.get(2);
                flag = true;

                break;
            }
        }

        if (flag && ((point2.getY() - point1.getY()) * (point3.getX() - point2.getX()) == (point3.getY() - point2.getY()) * (point2.getX() - point1.getX()))) {
            System.out.println(point1 + " " + point2 + " " + point3);
        } else {
            System.out.println("none");
        }
    }
}