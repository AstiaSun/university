package VoronoiDiagram;

import common.Edge;

import java.util.*;


public class Voronoi {
    static final double MIN_DRAW_DIM = -5;
    static final double MAX_DRAW_DIM = 5;
    private static final double MAX_DIM = 10;
    private static final double MIN_DIM = -10;
    private double sweepLoc;
    private final ArrayList<Point> sites;
    private final ArrayList<VoronoiEdge> edgeList;
    private HashSet<BreakPoint> breakPoints;
    private TreeMap<ArcKey, CircleEvent> arcs;
    private TreeSet<Event> events;

    public Voronoi(ArrayList<java.awt.Point> points) {
        sites = new ArrayList<>(points.size());
        for (java.awt.Point point : points) {
            this.sites.add(new Point(point.x, point.y));
        }
        edgeList = new ArrayList<>(sites.size());
        initialize();
        buildDiagram();
    }

    double getSweepLoc() {
        return sweepLoc;
    }

    private void initialize() {
        events = new TreeSet<>();
        breakPoints = new HashSet<>();
        arcs = new TreeMap<>();

        for (Point site : sites) {
            events.add(new Event(site));
        }
        sweepLoc = MAX_DIM;
    }

    private void buildDiagram() {
        do {
            Event currentEvent = events.pollFirst();
            sweepLoc = currentEvent.p.y;
            if (currentEvent.getClass() == Event.class) {
                handleSiteEvent(currentEvent);
            }
            else {
                CircleEvent ce = (CircleEvent) currentEvent;
                handleCircleEvent(ce);
            }
        } while ((events.size() > 0));
        this.sweepLoc = MIN_DIM;
        for (BreakPoint bp : breakPoints) {
            bp.finish();
        }
    }

    private void handleSiteEvent(Event currentEvent) {
        if (arcs.size() == 0) {
            arcs.put(new Arc(currentEvent.p, this), null);
            return;
        }

        Map.Entry<ArcKey, CircleEvent> arcEntryAbove = arcs.floorEntry(new ArcQuery(currentEvent.p));
        Arc arcAbove = (Arc) arcEntryAbove.getKey();

        if (arcs.size() == 1 && arcAbove.site.y == currentEvent.p.y) {
            handleFirstTwoPointsOnTheSameLevel(arcAbove, currentEvent);
            return;
        }

        CircleEvent falseCE = arcEntryAbove.getValue();
        if (falseCE != null) {
            events.remove(falseCE);
        }

        BreakPoint breakL = arcAbove.left;
        BreakPoint breakR = arcAbove.right;
        VoronoiEdge newEdge = new VoronoiEdge(arcAbove.site, currentEvent.p);
        this.edgeList.add(newEdge);
        BreakPoint newBreakL = new BreakPoint(arcAbove.site, currentEvent.p, newEdge, true, this);
        BreakPoint newBreakR = new BreakPoint(currentEvent.p, arcAbove.site, newEdge, false, this);
        breakPoints.add(newBreakL);
        breakPoints.add(newBreakR);

        Arc arcLeft = new Arc(breakL, newBreakL, this);
        Arc center = new Arc(newBreakL, newBreakR, this);
        Arc arcRight = new Arc(newBreakR, breakR, this);

        arcs.remove(arcAbove);
        arcs.put(arcLeft, null);
        arcs.put(center, null);
        arcs.put(arcRight, null);

        checkForCircleEvent(arcLeft);
        checkForCircleEvent(arcRight);
    }

    private void handleFirstTwoPointsOnTheSameLevel(Arc arcAbove, Event currentEvent) {
        VoronoiEdge newEdge = new VoronoiEdge(arcAbove.site, currentEvent.p);
        newEdge.p1 = new Point((currentEvent.p.x + arcAbove.site.x)/2, Double.POSITIVE_INFINITY);
        BreakPoint newBreak = new BreakPoint(arcAbove.site, currentEvent.p, newEdge, false, this);
        breakPoints.add(newBreak);
        this.edgeList.add(newEdge);
        Arc arcLeft = new Arc(null, newBreak, this);
        Arc arcRight = new Arc(newBreak, null, this);
        arcs.remove(arcAbove);
        arcs.put(arcLeft, null);
        arcs.put(arcRight, null);
    }

    private void handleCircleEvent(CircleEvent ce) {
        Arc arcRight = (Arc) arcs.higherKey(ce.arc);
        Arc arcLeft = (Arc) arcs.lowerKey(ce.arc);
        if (arcRight != null) {
            CircleEvent falseCe = arcs.get(arcRight);
            if (falseCe != null){ events.remove(falseCe);}
            arcs.put(arcRight, null);
        }
        if (arcLeft != null) {
            CircleEvent falseCe = arcs.get(arcLeft);
            if (falseCe != null) events.remove(falseCe);
            arcs.put(arcLeft, null);
        }
        arcs.remove(ce.arc);

        ce.arc.left.finish(ce.vert);
        ce.arc.right.finish(ce.vert);

        breakPoints.remove(ce.arc.left);
        breakPoints.remove(ce.arc.right);

        VoronoiEdge e = new VoronoiEdge(ce.arc.left.s1, ce.arc.right.s2);
        edgeList.add(e);


        boolean turnsLeft = Point.ccw(arcLeft.right.edgeBegin, ce.p, arcRight.left.edgeBegin) == 1;

        boolean isLeftPoint = (turnsLeft) ? (e.m < 0) : (e.m > 0);
        if (isLeftPoint) {
            e.p1 = ce.vert;
        }
        else {
            e.p2 = ce.vert;
        }
        BreakPoint newBP = new BreakPoint(ce.arc.left.s1, ce.arc.right.s2, e, !isLeftPoint, this);
        breakPoints.add(newBP);

        arcRight.left = newBP;
        arcLeft.right = newBP;

        checkForCircleEvent(arcLeft);
        checkForCircleEvent(arcRight);
    }

    private void checkForCircleEvent(Arc a) {
        Point circleCenter = a.checkCircle();
        if (circleCenter != null) {
            double radius = a.site.distanceTo(circleCenter);
            Point circleEventPoint = new Point(circleCenter.x, circleCenter.y - radius);
            CircleEvent ce = new CircleEvent(a, circleEventPoint, circleCenter);
            arcs.put(a, ce);
            events.add(ce);
        }
    }

    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> edges = new ArrayList<>(edgeList.size());
        for (VoronoiEdge edge: edgeList) {
            if (edge.p1 != null && edge.p2 != null) {
                java.awt.Point from = new java.awt.Point((int) edge.p1.x, (int) edge.p1.y);
                java.awt.Point to = new java.awt.Point((int) edge.p2.x, (int) edge.p2.y);
                fixPoint(from);
                if (to.x < 0) to.x = 0;
                edges.add(new Edge(from, to));
            }
        }
        return edges;
    }

    private void fixPoint(java.awt.Point point) {
        double topY = (point.getY() == Double.POSITIVE_INFINITY) ? MAX_DIM : point.y;
        if (point.y < 0) point.y = (int) topY;
    }
}

