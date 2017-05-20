package VoronnoiDiagram;

import java.awt.Point;

/**
 * Site event - event when the sweeping line meets a point
 * and a new arch is created
 * Created by anastasia on 5/20/17.
 */
class SiteEvent extends Event{
    SiteEvent(Point site) {
        super();
        this.setSite(site);
    }

    @Override
    boolean isSiteEvent() {
        return true;
    }
}
