import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by anastasia on 5/21/17.
 */
public class QuickHull {
    private LinkedList<Point> convexHull;

    public LinkedList<Point> createConvexHull (ArrayList<Point> points) {
        convexHull = new LinkedList<>();

        if (!isValid(points)) {
            return convexHull;
        }

        Point left = findTheLeftestPoint(points);
        Point right = findTheRightestPoint(points);

        for(int i = 1; i < points.size(); i++) {
            if(points.get(i).getX() < left.getX())
                left = points.get(i);
            else if(points.get(i).getX() > right.getX())
                right = points.get(i);
        }

        ArrayList<Point> upperPoints = new ArrayList<>(points.size());
        ArrayList<Point> lowerPoints  = new ArrayList<>(points.size());

        Point splittingVector = new Point(right.x - left.x, right.y - left.y);
        for(int i = 0; i < points.size(); i++) {
            if(isOnTheLeftSide(new Point(points.get(i).x - left.x, points.get(i).y - left.y), splittingVector)) {
                upperPoints.add(points.get(i));
            } else {
                lowerPoints.add(points.get(i));
            }
        }

        convexHull.add(left);
        convexHull.add(right);

        convexHullRecursive(upperPoints, left, right);

        convexHullRecursive(lowerPoints, right, left);

        return convexHull;
    }

    private boolean isValid(ArrayList<Point> points) {
        if(points.isEmpty())
            return false;

        if(points.size() == 1){
            convexHull.add(points.get(0));
            return false;
        }
        return true;
    }

    private Point findTheLeftestPoint(ArrayList<Point> points) {
        Point left = points.get(0);
        for(int i = 1; i < points.size(); i++) {
            if(points.get(i).getX() < left.getX())
                left = points.get(i);
        }
        return left;
    }

    private Point findTheRightestPoint(ArrayList<Point> points) {
        Point right = points.get(0);
        for(int i = 1; i < points.size(); i++) {
            if(points.get(i).getX() > right.getX())
                right = points.get(i);
        }
        return right;
    }

    private void convexHullRecursive(ArrayList<Point> points, Point left, Point right) {

        if(points.isEmpty())
            return;

        if(points.size() == 1) {
            convexHull.add(points.get(0));
            return;
        }

        Point splittingVector = new Point(right.x - left.x, right.y - left.y);

        Point farthest = findTheFarthestPoint(points, left, splittingVector);
        convexHull.add(farthest);

        ArrayList<Point> newUpperPointsLeft = new ArrayList<>(points.size());
        ArrayList<Point> newUpperPointsRight = new ArrayList<>(points.size());

        splittingVector = new Point(farthest.x - left.x, farthest.y - left.y);
        Point rightSplittingVector = new Point(right.x - farthest.x, right.y - farthest.y);

        for(int i = 0; i < points.size(); i++) {
            if(points.get(i).equals(farthest))
                continue;
            if(isOnTheLeftSide(new Point(points.get(i).x - left.x, points.get(i).y - left.y), splittingVector)) {
                newUpperPointsLeft.add(points.get(i));
            } else if(isOnTheLeftSide(new Point(points.get(i).x - farthest.x, points.get(i).y - farthest.y), rightSplittingVector)) {
                newUpperPointsRight.add(points.get(i));
            }
        }
        convexHullRecursive(newUpperPointsLeft, left, farthest);
        convexHullRecursive(newUpperPointsRight, farthest, right);

    }

    private boolean isOnTheLeftSide(Point vectorA, Point vectorB) {
        int res = vectorA.x * vectorB.y - vectorA.y * vectorB.x;
        if(res <= 0) {
            return false;
        }
        return true;
    }

    private double distance(Point M, double A, double B, double C) {
        return Math.abs(A * M.getX() - B * M.getY() + C) / Math.sqrt(A * A + B * B);
    }

    private Point findTheFarthestPoint(ArrayList<Point> upperPoints, Point left, Point splittingVector) {
        double A =  splittingVector.y;
        double B = splittingVector.x;
        double C = left.getY() * splittingVector.getX() - left.getX() * splittingVector.getY();
        double maxDistance = distance(upperPoints.get(0), A, B, C);
        int maxId = 0;
        for(int i = 1; i < upperPoints.size(); i++){
            double d = distance(upperPoints.get(i), A, B, C);
            if (d > maxDistance) {
                maxDistance = d;
                maxId = i;
            } else if( d == maxDistance) {
                if (upperPoints.get(i).getX() < upperPoints.get(maxId).getX()) {
                    maxId = i;
                }
            }
        }
        return upperPoints.get(maxId);
    }

}
