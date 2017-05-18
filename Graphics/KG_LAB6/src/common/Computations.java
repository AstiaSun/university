package common;

import java.awt.*;
import java.util.ArrayList;


public class Computations {
    public static boolean isOnTheLeftSide(Point vectorA, Point vectorB) {
        int res = vectorA.x * vectorB.y - vectorA.y * vectorB.x;
        return res > 0;
    }

    public static boolean isBetween(int value, int from, int to) {
        return ((from <= value) && (value < to)) || ((to <= value) && (value < from));
    }

    public static boolean isPointInsideRectangle(Point point, int x, int y, int width, int height) {
        return isBetween(point.x, x, x + width) && isBetween(point.y, y, y + height);
    }

    public static ArrayList<Point> sortByAxis(ArrayList<Point> points) {
        ArrayList<Point> result = (ArrayList<Point>) points.subList(0, points.size());
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if (points.get(i).x > points.get(j).x) {
                    result.set(i, points.get(j));
                    result.set(j, points.get(i));
                }
            }
        }
        return result;
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

    public static Edge findBaseLine(ArrayList<Point> fromArray, ArrayList<Point> toArray) {
        return new Edge();
    }
    
}
