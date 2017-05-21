package VoronnoiDiagram;

import java.awt.*;

/**
 * Basic class for events (site and circle)
 *
 * Created by anastasia on 5/20/17.
 */
public abstract class Event {
    private Point site;

    abstract boolean isSiteEvent();

    public Point getSite() {
        return site;
    }

    void setSite(Point site) {
        this.site = site;
    }
}
