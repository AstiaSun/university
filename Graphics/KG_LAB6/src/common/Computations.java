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
    
}
