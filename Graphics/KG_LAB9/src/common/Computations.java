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

    public static boolean isPointPresent(Point e, ArrayList<Point> points, int radius) {
        for (Point current : points) {
            if (Computations.isPointInsideRectangle(e, current.x - radius / 2, current.y - radius / 2,
                    radius, radius)) {
                return true;
            }
        }
        return false;
    }

    public static Point createVector(Point from, Point to) {
        return new Point(to.x - from.x, to.y - from.y);
    }

    public static double findRadiusOfCircle(Polygon triangle) {
        double coef = (triangle.getVertex(1).x - triangle.getVertex(0).x) /
                (triangle.getVertex(2).x - triangle.getVertex(0).x);
        double squareDifferenceABX = Math.pow(triangle.getVertex(0).x, 2) - Math.pow(triangle.getVertex(1).x, 2);
        double squareDifferenceACX = Math.pow(triangle.getVertex(0).x, 2) - Math.pow(triangle.getVertex(2).x, 2);

        double squareDifferenceABY = Math.pow(triangle.getVertex(0).y, 2) - Math.pow(triangle.getVertex(1).y, 2);
        double squareDifferenceACY = Math.pow(triangle.getVertex(0).y, 2) - Math.pow(triangle.getVertex(2).y, 2);

        double differenceABY = triangle.getVertex(0).y - triangle.getVertex(1).y;
        double differenceACY = triangle.getVertex(0).y - triangle.getVertex(2).y;

        double y = (squareDifferenceACX * coef - squareDifferenceABX / coef +
                squareDifferenceACY * coef - squareDifferenceABY / coef) /
                (2 * (differenceACY * coef - differenceABY / coef));

        double differenceACX = triangle.getVertex(0).x - triangle.getVertex(2).x;
        double x = (2 * differenceACY * y - squareDifferenceACY - squareDifferenceACX) / (2 * differenceACX);
        Point A = triangle.getVertex(0);
        return Math.sqrt(Math.pow(A.x - x, 2) + Math.pow(A.y - y, 2));
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

    public static IntersectionResult intersect(Edge firstEdge, Edge secondEdge)
    {
        IntersectionResult result = new IntersectionResult();
        Point a = firstEdge.getFrom();
        Point b = firstEdge.getTo();
        Point c = secondEdge.getFrom();
        Point d = secondEdge.getTo();
        Point n = new Point(pointDifference(d, c).y, pointDifference(c, d).x);
        double denom = dotProduct(n, pointDifference(b,a));
        if (denom ==0.0) {
            POSITION aclass = classify(a, secondEdge);
            if ((aclass == POSITION.LEFT) || (aclass == POSITION.RIGHT)) {
                result.setLinePosition(LINE_POSITION.PARALLEL);
                return result;
            }
            else {
                result.setLinePosition(LINE_POSITION.COLLINEAR);
                return result;
            }
        }
        double num = dotProduct(n, pointDifference(a, c));
        result.setParameter(-num / denom);
        result.setLinePosition(LINE_POSITION.SKEW);
        return result;
    }
    private static double dotProduct(Point p, Point q)
    {
        return (p.x * q.x + p.y * q.y);
    }

    public static POSITION classify(Point classified, Edge edge) {
        Point a = pointDifference(edge.getTo(), edge.getFrom());
        Point b = pointDifference(classified, edge.getFrom());
        double sa = a. x * b.y - b.x * a.y;
        if (sa > 0.0)
            return POSITION.LEFT;
        if (sa < 0.0)
            return POSITION.RIGHT;
        if ((a.x * b.x < 0.0) || (a.y * b.y < 0.0))
            return POSITION.BEHIND;
        if (length(a) < length(b))
            return POSITION.BEYOND;
        if (edge.getFrom() == classified)
            return POSITION.ORIGIN;
        if (edge.getTo() == classified)
            return POSITION.DESTINATION;
        return POSITION.BETWEEN;
    }

    private static double length(Point p) {
        return Math.sqrt(p.x * p.x + p.y * p.y);
    }


    public enum POSITION {LEFT,  RIGHT,  BEYOND,  BEHIND, BETWEEN, ORIGIN, DESTINATION}

    public enum LINE_POSITION {PARALLEL, COLLINEAR, SKEW}

    public static class IntersectionResult {
        private LINE_POSITION linePosition;
        private double parameter;

        public LINE_POSITION getLinePosition() {
            return linePosition;
        }

        void setLinePosition(LINE_POSITION linePosition) {
            this.linePosition = linePosition;
        }

        public double getParameter() {
            return parameter;
        }

        void setParameter(double parameter) {
            this.parameter = parameter;
        }
    }



}
