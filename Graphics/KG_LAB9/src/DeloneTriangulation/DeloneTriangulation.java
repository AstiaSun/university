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

    public ArrayList<Polygon> triangulate(ArrayList<Point> points) {
        if (points.size() < 3)
            return null;
        ArrayList<Polygon> triangles = new ArrayList<>(points.size() / 3);
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
            updateFrontier(frontier, new Edge(currentEdge.getFrom(), mate));
            updateFrontier(frontier, new Edge(mate, currentEdge.getTo()));
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
            if (edge.getFrom() == pointsFromConvexHull.get(i))
                continue;
            Edge vectorA = new Edge(edge.getFrom(), pointsFromConvexHull.get(i));
            for (; j < pointsFromConvexHull.size(); j++) {
                if ((pointsFromConvexHull.get(j) != vectorA.getFrom()) && (pointsFromConvexHull.get(j) != vectorA.getTo())
                        && !isOnTheRightSide(vectorA, pointsFromConvexHull.get(j))) {
                    break;
                }
            }
            if (j == pointsFromConvexHull.size()) {
                edge.setTo(pointsFromConvexHull.get(i));
                break;
            }
        }
        edge.setAlive();
        return edge;
    }

    private Point findMate (Edge currentEdge, ArrayList<Point> points) {
        Point bestMate = null;
        for (Point point : points)
            if (isOnTheRightSide(currentEdge, point)) {
                Polygon triangle = Computations.createTriangle(currentEdge.getFrom(), currentEdge.getTo(), point);
                if ((!Computations.isPointInsideCircle(bestMate, triangle))) {
                    bestMate = point;
                }
            }
        return bestMate;
    }

    private boolean isOnTheRightSide(Edge edge, Point point) {
        if ((edge.getFrom() == point) || (edge.getTo() == point))
            return false;
        Point vectorA = Computations.createVector(edge.getFrom(), edge.getTo());
        Point vectorB = Computations.createVector(edge.getFrom(), point);
        return (vectorA.getX() * vectorB.getY() - vectorA.getY() * vectorB.getX()) > 0;
    }

    private void updateFrontier (PriorityQueue<Edge> frontier, Edge ab)
    {
        if (frontier.contains(ab)) {
            frontier.remove(ab);
        }
        else {
            ab.flip();
            if (frontier.contains(ab)) {
                frontier.remove(ab);
            } else {
                ab.flip();
                frontier.add(ab);
            }
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
