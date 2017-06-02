import common.Computations;
import common.Edge;
import common.Polygon;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by anastasia on 5/19/17.
 */
public class PolygonConvexHull {
    private Polygon polygon;
    private ArrayList<Point> convexHull;

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public ArrayList<Point> getConvexHull() {
        return convexHull;
    }

    public void compute() {
        Edge splittingEdge = Computations.findSplittingEdge(polygon.getPoints());
        ArrayList<Point> upperPart = getUpperPart(splittingEdge);
        ArrayList<Point> lowerPart = getLowerPart(splittingEdge);
        convexHull = buildUpperPart(upperPart);
        lowerPart = buildLowerPart(lowerPart);
        convexHull = Computations.merge(convexHull, lowerPart);
    }

    private ArrayList<Point> getUpperPart(Edge splittingEdge) {
        ArrayList<Point> upperPart = new ArrayList<>();
        for (int i = polygon.getPoints().indexOf(splittingEdge.getFrom());
             i != polygon.getPoints().indexOf(splittingEdge.getTo()) + 1; i = (i + 1) % polygon.getPoints().size()) {
            upperPart.add(polygon.getPoints().get(i));
        }
        return upperPart;
    }

    private ArrayList<Point> getLowerPart(Edge splittingEdge) {
        ArrayList<Point> upperPart = new ArrayList<>();
        for (int i = polygon.getPoints().indexOf(splittingEdge.getTo());
             i != polygon.getPoints().indexOf(splittingEdge.getFrom()) + 1; i = (i + 1) % polygon.getPoints().size()) {
            upperPart.add(polygon.getPoints().get(i));
        }
        return upperPart;
    }

    private ArrayList<Point> buildUpperPart(ArrayList<Point> points) {
        addZeroPointToUpperPart(points);
        return buildPart(points);
    }

    private ArrayList<Point> buildLowerPart(ArrayList<Point> points) {
        addZeroPointToLowerPart(points);
        return buildPart(points);
    }

    private ArrayList<Point> buildPart(ArrayList<Point> points) {
        ArrayList<Point> convexHull = new ArrayList<>();
        convexHull.add(points.get(0));
        convexHull.add(points.get(1));
        runAlgorithm(points, convexHull);
        removeZeroPoint(points);
        removeZeroPoint(convexHull);
        return convexHull;
    }

    private void runAlgorithm(ArrayList<Point> points, ArrayList<Point> convexHull) {
        int currentConvexHullVertexIndex = 1;
        Point lastVertex = points.get(points.size() - 1);
        for (int i = 2; i < points.size(); i++) {
            Point next = points.get(i);
            Point currentConvexHullVertex = convexHull.get(currentConvexHullVertexIndex);
            if (isFirstRulePassed(convexHull.get(currentConvexHullVertexIndex - 1), currentConvexHullVertex, next)) {
                Point previousVertex = points.get(points.indexOf(currentConvexHullVertex) - 1);
                if (isSecondRulePassed(previousVertex, currentConvexHullVertex, next)) {
                    if (isThirdRulePassed(lastVertex, currentConvexHullVertex, next)) {
                        convexHull.add(next);
                        currentConvexHullVertexIndex++;
                    } else {
                        while (!isThirdRulePassed(lastVertex, currentConvexHullVertex, next) && (i < points.size())) {
                            points.remove(next);
                            next = points.get(i);
                        }
                    }
                } else {
                    while (!isSecondRulePassed(previousVertex, currentConvexHullVertex, next) && (i < points.size())) {
                        points.remove(next);
                        next = points.get(Math.min(i, points.size() - 1));
                    }
                }
            } else {
                while ((convexHull.size() > 2) && !isFirstRulePassed(
                        convexHull.get(currentConvexHullVertexIndex - 1), currentConvexHullVertex, next)) {
                    convexHull.remove(currentConvexHullVertexIndex);
                    currentConvexHullVertexIndex--;
                    currentConvexHullVertex = convexHull.get(currentConvexHullVertexIndex);
                }
                convexHull.add(next);
                currentConvexHullVertexIndex++;
            }
        }
        if (!convexHull.contains(lastVertex)) {
            convexHull.add(lastVertex);
        }
    }

    private void addZeroPointToUpperPart(ArrayList<Point> points) {
        points.add(0, new Point(points.get(0).x, points.get(0).y + 20));
    }

    private void addZeroPointToLowerPart(ArrayList<Point> points) {
        points.add(0, new Point(points.get(0).x, points.get(0).y - 20));
    }

    private void removeZeroPoint(ArrayList<Point> points) {
        points.remove(0);
    }

    private boolean isFirstRulePassed(Point qPrevious, Point qCurrent, Point next) {
        Point splitting = Computations.createVector(qPrevious, next);
        Point deciding = Computations.createVector(qPrevious, qCurrent);
        return Computations.isOnTheRightSide(deciding, splitting);
    }

    private boolean isSecondRulePassed(Point previous, Point qCurrent, Point next) {
        Point splitting = Computations.createVector(previous, next);
        Point deciding = Computations.createVector(previous, qCurrent);
        return Computations.isOnTheRightSide(deciding, splitting);
    }

    private boolean isThirdRulePassed(Point qLast, Point qCurrent, Point next) {
        Point splitting = Computations.createVector(qLast, qCurrent);
        Point deciding = Computations.createVector(qLast, next);
        return Computations.isOnTheRightSide(splitting, deciding);
    }

}
