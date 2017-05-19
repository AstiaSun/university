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
        ArrayList<Point> lowerPart = getLowerPoints(splittingEdge);
        convexHull = buildPart(upperPart);
        lowerPart = Computations.reverse(lowerPart);
        Computations.merge(convexHull, buildPart(lowerPart));
    }

    private ArrayList<Point> getUpperPart(Edge splittingEdge) {
        ArrayList<Point> upperPart = new ArrayList<>();
        for (int i = polygon.getPoints().indexOf(splittingEdge.getFrom());
             i != polygon.getPoints().indexOf(splittingEdge.getTo()) + 1; i = (i + 1) % polygon.getPoints().size()) {
            upperPart.add(polygon.getPoints().get(i));
        }
        return upperPart;
    }

    private ArrayList<Point> getLowerPoints(Edge splittingEdge) {
        ArrayList<Point> upperPart = new ArrayList<>();
        for (int i = polygon.getPoints().indexOf(splittingEdge.getTo());
             i != polygon.getPoints().indexOf(splittingEdge.getFrom()) + 1; i = (i + 1) % polygon.getPoints().size()) {
            upperPart.add(polygon.getPoints().get(i));
        }
        return upperPart;
    }

    private ArrayList<Point> buildPart(ArrayList<Point> points) {
        addZeroPoint(points);
        ArrayList<Point> convexHull = new ArrayList<>();
        convexHull.add(points.get(0));
        convexHull.add(points.get(1));
        int qIndex = 1;
        if (points.size() > 2) {
            convexHull.add(points.get(points.size() - 1));
            for (int i = 2; i < points.size(); i++) {
                Point next = points.get(i);
                if (!isFirstRulePassed(convexHull.get(qIndex - 1), convexHull.get(qIndex), next)) {
                    while ((convexHull.size() > 3) && !isFirstRulePassed(convexHull.get(qIndex - 1), convexHull.get(qIndex), next)) {
                        convexHull.remove(qIndex);
                    }
                    convexHull.add(next);
                }
                if (!isSecondRulePassed(convexHull.get(qIndex), convexHull.get(convexHull.size() - 1), next)) {

                }
                if (!isThirdRulePassed(convexHull.get(qIndex), next, points.get(i - 2))) {

                }

            }
        }
        return convexHull;
    }

    private void addZeroPoint(ArrayList<Point> points) {
        points.add(0, new Point(points.get(0).x, points.get(0).y - 20));
    }

    private boolean isFirstRulePassed(Point qPrevious, Point qCurrent, Point next) {
        Point splitting = Computations.createVector(qPrevious, next);
        Point deciding = Computations.createVector(qPrevious, qCurrent);
        return Computations.isOnTheRightSide(deciding, splitting);
    }

    private boolean isSecondRulePassed(Point qCurrent, Point qLast, Point next) {
        Point splitting = Computations.createVector(qCurrent, qLast);
        Point deciding = Computations.createVector(qCurrent, next);
        return Computations.isOnTheRightSide(deciding, splitting);
    }

    private boolean isThirdRulePassed(Point qCurrent, Point next, Point previous) {
        Point splitting = Computations.createVector(previous, next);
        Point deciding = Computations.createVector(previous, qCurrent);
        return Computations.isOnTheRightSide(deciding, splitting);
    }
}
