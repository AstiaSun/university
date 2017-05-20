package VoronnoiDiagram;

import java.awt.*;

/**
 * Basic class for events (site and circle)
 * Created by anastasia on 5/20/17.
 */
abstract class Event implements Comparable{
    private Point point;

    abstract void process();

    @Override
    public int compareTo(Object o) {
        Event event = (Event) o;
        return (point.y - event.point.y);
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }
}
