package DeloneTriangulation;

import common.Computations;
import common.Edge;
import common.Polygon;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Delone triangulation implementation using incrementation algorithm.
 *
 * Created by anastasia on 5/21/17.
 */
public class DeloneTriangulation {
    private ArrayList<Polygon> triangles;
    private final double MAX_VALUE = 100000.0;

    public ArrayList<Polygon> triangulate(ArrayList<Point> points) {
        if (points.size() < 3)
            return null;
        triangles = new ArrayList<>(points.size() / 3);
        Edge edge = findEdgeFromConvexHull(points);
        PriorityQueue<Edge> frontier = new PriorityQueue<>(points.size(), new EdgeComparator());
        frontier.add(edge);
        while (!frontier.isEmpty()) {
            Polygon triangle = stepTriangle(frontier, points);
            if (triangle != null)
                triangles.add(triangle);
        }
        return triangles;
    }

    private Polygon stepTriangle(PriorityQueue<Edge> frontier, ArrayList<Point> points) {
        Edge currentEdge = frontier.poll();
        Point mate = findMate(currentEdge, points);
        if (mate != null) {
            updateFrontier(frontier, new Edge(mate, currentEdge.getFrom()));
            updateFrontier(frontier, new Edge(currentEdge.getTo(), mate));
            return Computations.createTriangle(currentEdge.getFrom(), currentEdge.getTo(), mate);
        }
        return null;
    }

    private Edge findEdgeFromConvexHull(ArrayList<Point> points) {
        Edge edge = new Edge();
        ArrayList<Point> pointsFromConvexHull = new QuickHull().createConvexHull(points);
        edge.setFrom(Computations.findTheHighestPoint(pointsFromConvexHull));
        for (int i = 0; i < pointsFromConvexHull.size(); i++) {
            int j = 0;
            for (; j < pointsFromConvexHull.size(); j++) {
                Point vectorA = Computations.createVector(edge.getFrom(), pointsFromConvexHull.get(i));
                Point vectorB = Computations.createVector(edge.getFrom(), pointsFromConvexHull.get(j));
                if (Computations.isOnTheLeftSide(vectorA, vectorB)) {
                    break;
                }
            }
            if ((j == pointsFromConvexHull.size()) && (edge.getFrom() != pointsFromConvexHull.get(i))) {
                edge.setTo(pointsFromConvexHull.get(i));
                break;
            }
        }
        edge.setAlive();
        return edge;
    }

    private Point findMate (Edge currentEdge, ArrayList<Point> points) {
        Point bestMate = null;
        double  currentBestRadius = MAX_VALUE;
        Edge perpendicularToCurrentEdge = currentEdge.rotate();
        for (Point point : points)
            if (Computations.classify(point, currentEdge) == Computations.POSITION.RIGHT) {
                Edge anotherEdge = new Edge(currentEdge.getTo(), point);
                Edge perpendicularToAnotherEdge = anotherEdge.rotate();
                //Polygon triangle = Computations.createTriangle(currentEdge.getFrom(), currentEdge.getTo(), points.get(i));
                //double radius = Computations.findRadiusOfCircle(triangle);
                double radius = getRadius(perpendicularToCurrentEdge, perpendicularToAnotherEdge);
                if (radius < currentBestRadius) {
                    bestMate = point;
                    currentBestRadius = radius;
                }
            }
        return bestMate;
    }

    private double getRadius(Edge firstPerpendicular, Edge secondPerpendicular) {
        Computations.IntersectionResult result = Computations.intersect(firstPerpendicular, secondPerpendicular);
        if (result.getLinePosition() == Computations.LINE_POSITION.SKEW) {
            return result.getParameter();
        }
        return MAX_VALUE;
    }

    private boolean isOnTheRightSide(Edge edge, Point point) {
        Point vectorA = Computations.createVector(edge.getFrom(), edge.getTo());
        Point vectorB = Computations.createVector(edge.getFrom(), point);
        return !Computations.isOnTheLeftSide(vectorA, vectorB);
    }

    private void updateFrontier (PriorityQueue<Edge> frontier, Edge ab)
    {
        if (frontier.contains(ab))
            frontier.remove(ab);
        else {
            ab.flip();
            frontier.add(ab);
        }
    }

    private class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge a, Edge b) {
            if (isALowerThenB(a.getFrom(), b.getFrom())) return 1;
            if (isAGreaterThenB(a.getFrom(), b.getFrom())) return 1;
            if (isALowerThenB(a.getTo(), b.getTo())) return -1;
            if (isAGreaterThenB(a.getTo(), b.getTo())) return 1;
            return 0;
        }

        private boolean isALowerThenB(Point a, Point b) {
            return a.x < b.x || a.x <= b.x && a.x == b.x && a.y < b.y;
        }

        private boolean isAGreaterThenB(Point a, Point b) {
            return a.x > b.x || a.x >= b.x && a.x == b.x && a.y > b.y;
        }
    }
}
