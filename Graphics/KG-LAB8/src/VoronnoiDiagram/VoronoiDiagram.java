package VoronnoiDiagram;

import common.Edge;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class VoronoiDiagram {
    private ArrayList<Edge> edges;
    private PriorityQueue<Event> events;
    private BinaryTree beachLine;

    public void build(ArrayList<Point> sites) {
        initialize(sites);
        while (!events.isEmpty()) {
            Event current = events.poll();
            if (current.isSiteEvent()) {
                processSiteEvent(current);
            } else {
                processCircleEvent(current);
            }
        }
        finishAllEdges();
    }

    private void initialize(ArrayList<Point> sites) {
        createEventPriorityQueue(sites);
        beachLine = new BinaryTree();
    }

    private void createEventPriorityQueue(ArrayList<Point> sites) {
        events = new PriorityQueue<>(sites.size(), new EventComparator());
        for (Point site : sites) {
            Event siteEvent = new SiteEvent(site);
            events.add(siteEvent);
        }
    }

    private void processSiteEvent(Event event) {
        beachLine.addArch(event);
        beachLine.update(event.getSite().y);
    }

    private void processCircleEvent(Event event) {

    }

    private void finishAllEdges() {

    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    private class EventComparator implements Comparator<Event> {

        @Override
        public int compare(Event event1, Event event2) {
            if (event1.getSite().y > event2.getSite().y)
                return 1;
            else if (event1.getSite().y < event2.getSite().y)
                return -1;
            return 0;
        }
    }
}
