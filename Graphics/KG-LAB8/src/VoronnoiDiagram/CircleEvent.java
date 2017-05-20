package VoronnoiDiagram;

/**
 * Circle event - event then the arches are crossing, so one of the arches is
 * disappearing from the front
 *
 * Created by anastasia on 5/20/17.
 */
public class CircleEvent extends Event {
    @Override
    boolean isSiteEvent() {
        return false;
    }
}
