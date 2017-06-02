package common;

import java.awt.*;

/**
 * Edge have 3 statuses:
 * - sleeping - the edge is not yet detected by the algorithm
 * - alive - the edge is detected, but we know only one adjacent region
 * - dead - the edge is detected and we know two adjacent regions
 *
 * Created by anastasia on 5/21/17.
 */
public class Edge {
    private Point from;
    private Point to;

    public Edge() {
    }

    public Edge(Point from, Point to) {
        setFrom(from);
        setTo(to);
        setDead();
    }

    public void setAlive() {
    }

    private void setDead() {
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public void flip() {
        Point temp = from;
        from = to;
        to = temp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Edge.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Edge other = (Edge) obj;
        if ((this.from == null) ? (other.from != null) : this.from != other.from) {
            return false;
        }
        if ((this.to == null) ? (other.to != null) : this.to != other.to) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.from != null ? this.from.hashCode() : 0);
        hash = 53 * hash + (this.to != null ? this.to.hashCode() : 0);
        return hash;
    }
}
