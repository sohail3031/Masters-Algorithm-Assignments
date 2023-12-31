//import java.util.ArrayList;
//import java.util.Scanner;
//
//record Point(int x, int y) {
//    @Override
//    public String toString() {
//        return x + " " + y;
//    }
//
//    public int comparePoints(Point point) {
//        return Integer.compare(this.x, point.x);
//    }
//}
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int size = scanner.nextInt();
//        Point[] points = new Point[size];
//
//        for (int i = 0; i < size; i++) {
//            points[i] = new Point(scanner.nextInt(), scanner.nextInt());
//        }
//
//        scanner.close();
//
//        if (points.length < 3) {
//            System.out.println("none");
//        } else {
//            findCollinear(points);
//        }
//    }
//
//    public static void findCollinear(Point[] points) {
//        boolean flag = false;
//        Point point1 = null, point2 = null, point3 = null;
//
//        for (int i = 0; i < points.length; i++) {
//            ArrayList<Point> pointArrayList = new ArrayList<>();
//            pointArrayList.add(points[i]);
//
//            double slope1 = 1;
//
//            for (int j = i + 1; j < points.length; j++) {
//                double slope2 = points[i].comparePoints(points[j]);
//
//                if (slope2 == slope1) {
//                    pointArrayList.add(points[j]);
//                } else {
//                    if (pointArrayList.size() > 2) {
//                        point1 = pointArrayList.get(0);
//                        point2 = pointArrayList.get(1);
//                        point3 = pointArrayList.get(2);
//                        flag = true;
//
//                        break;
//                    } else {
//                        pointArrayList.clear();
//                        pointArrayList.add(points[i]);
//                        pointArrayList.add(points[j]);
//                    }
//
//                    slope1 = slope2;
//                }
//            }
//
//            if (pointArrayList.size() > 2) {
//                point1 = pointArrayList.get(0);
//                point2 = pointArrayList.get(1);
//                point3 = pointArrayList.get(2);
//                flag = true;
//
//                break;
//            }
//        }
//
//        if (flag) {
//            System.out.println(point1 + " " + point2 + " " + point3);
//        } else {
//            System.out.println("none");
//        }
//    }
//}


//-----------------------------------------------------------------


//import java.util.ArrayList;
//import java.util.Scanner;
//
//record Point(int x, int y) {
//    @Override
//    public String toString() {
//        return x + " " + y;
//    }
//
//    public int comparePoints(Point point) {
//        return Integer.compare(this.x, point.x);
//    }
//}
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int size = scanner.nextInt();
//        Point[] points = new Point[size];
//
//        for (int i = 0; i < size; i++) {
//            points[i] = new Point(scanner.nextInt(), scanner.nextInt());
//        }
//
//        scanner.close();
//
//        if (points.length < 3) {
//            System.out.println("none");
//        } else {
//            findCollinear(points);
//        }
//    }
//
//    public static void findCollinear(Point[] points) {
//        boolean flag = false;
//        Point point1 = null, point2 = null, point3 = null;
//
//        for (int i = 0; i < points.length; i++) {
//            ArrayList<Point> pointArrayList = new ArrayList<>();
//            pointArrayList.add(points[i]);
//
//            int slope1 = 1;
//
//            for (int j = i + 1; j < points.length; j++) {
//                int slope2 = points[i].comparePoints(points[j]);
//
//                if (slope1 == slope2) {
//                    pointArrayList.add(points[j]);
//                } else {
//                    if (pointArrayList.size() > 2) {
//                        flag = true;
//                        point1 = pointArrayList.get(0);
//                        point2 = pointArrayList.get(1);
//                        point3 = pointArrayList.get(2);
//
//                        break;
//                    }
//
//                    pointArrayList.clear();
//                    pointArrayList.add(points[i]);
//                    pointArrayList.add(points[j]);
//                }
//
//                slope1 = slope2;
//            }
//
//            if (flag) {
//                point1 = pointArrayList.get(0);
//                point2 = pointArrayList.get(1);
//                point3 = pointArrayList.get(2);
//
//                break;
//            }
//        }
//
//        if (flag) {
//            System.out.println(point1 + " " + point2 + " " + point3);
//        } else {
//            System.out.println("none");
//        }
//    }
//}


