package common;

import java.awt.*;
import java.util.ArrayList;


public class Computations {
    public static boolean isOnTheLeftSide(Point vectorA, Point vectorB) {
        int res = vectorA.x * vectorB.y - vectorA.y * vectorB.x;
        return res > 0;
    }

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

    public static Point findTheLeftestPoint(Point[] points) {
        Point leftest = points[0];
        for (Point point : points) {
            if (point.x < leftest.x) {
                leftest = point;
            }
        }
        return leftest;
    }

    public static boolean isPointPresent(Point e, ArrayList<Point> points, int radius) {
        for (Point current : points) {
            if (Computations.isPointInsideRectangle(e, current.x - radius / 2, current.y - radius / 2,
                    radius, radius)) {
                return true;
            }
        }
        return false;
    }

    public static int[] solveQuadraticEquation(double a, double b, double c) {
        double discriminant = Math.pow(b, 2) - 4 * a * c;
        if (discriminant < 0)
            return null;
        int[] x = new int[2];
        x[0] = (int) ((-b - Math.sqrt(discriminant)) / (2 * a));
        x[1] = (int) ((-b + Math.sqrt(discriminant)) / (2 * a));
        return x;
    }
}
