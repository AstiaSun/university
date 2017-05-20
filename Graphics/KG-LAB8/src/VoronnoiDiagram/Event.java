package VoronnoiDiagram;

import java.awt.*;

/**
 * Basic class for events (site and circle)
 *
 * Created by anastasia on 5/20/17.
 */
abstract class Event {
    private Point site;

    abstract boolean isSiteEvent();

    Point getSite() {
        return site;
    }

    void setSite(Point site) {
        this.site = site;
    }
}
