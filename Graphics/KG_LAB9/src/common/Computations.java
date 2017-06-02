package common;

import java.awt.*;
import java.util.ArrayList;


public class Computations {

    private static boolean isBetween(int value, int from, int to) {
        return ((from <= value) && (value < to)) || ((to <= value) && (value < from));
    }

    private static boolean isPointInsideRectangle(Point point, int x, int y, int width, int height) {
        return isBetween(point.x, x, x + width) && isBetween(point.y, y, y + height);
    }

    public static Point findTheHighestPoint(ArrayList<Point> points) {
        Point highest = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).y < highest.y) {
                highest = points.get(i);
            }
        }
        return highest;
    }

    private static double radiusOfCircumscribedCircle(Polygon triangle) {
        double a = length(createVector(triangle.getVertex(0), triangle.getVertex(1)));
        double b = length(createVector(triangle.getVertex(1), triangle.getVertex(2)));
        double c = length(createVector(triangle.getVertex(2), triangle.getVertex(0)));
        double p = (a + b + c) / 2;
        return a * b * c / (4 * Math.sqrt(p * (p - a) * (p - b) * (p - c)));
    }

    public static boolean isPointInsideCircle(Point point, Polygon triangle) {
        if (point == null)
            return false;

        Point center = findCircumCenter(triangle);
        double R = radiusOfCircumscribedCircle(triangle);
        return Math.pow(center.x - point.x, 2) + Math.pow(center.y - point.y, 2) < R * R;
    }

    private static Point findCircumCenter(Polygon triangle) {
        Point A = triangle.getVertex(0);
        Point B = triangle.getVertex(1);
        Point C = triangle.getVertex(2);

        double D = 2 * (A.x * (B.y - C.y) + B.x * (C.y - A.y) + C.x * ( A.y - B.y));

        int x = (int) ((Math.pow(length(A), 2) * (B.y - C.y) + Math.pow(length(B), 2) * (C.y - A.y) +
                Math.pow(length(C), 2) * (A.y - B.y)) / D);
        int y = (int) ((Math.pow(length(A), 2) * (C.x - B.x) + Math.pow(length(B), 2) * (A.x - C.x) +
                Math.pow(length(C), 2) * (B.x - A.x)) / D);
        return new Point(x, y);
    }

    public static Point createVector(Point from, Point to) {
        return new Point(to.x - from.x, to.y - from.y);
    }

    public static Polygon createTriangle(Point... points) {
        Polygon triangle = new Polygon();
        triangle.addVertex(points[0]);
        triangle.addVertex(points[1]);
        triangle.addVertex(points[2]);
        return triangle;
    }

    static Point pointAddition(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }

    static Point pointDifference(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y);
    }

    static Point multiply(double value, Point a) {
        a.setLocation(a.getX() * value, a.getY() * value);
        return a;
    }

    private static double length(Point p) {
        return Math.sqrt(p.x * p.x + p.y * p.y);
    }

    public static boolean isPointPresent(Point newPoint, ArrayList<Point> points, int radius) {
        for (Point point :
                points) {
            if (isPointInsideRectangle(newPoint, point.x - radius / 2, point.y - radius / 2, radius, radius))
                return true;
        }
        return false;
    }
}
