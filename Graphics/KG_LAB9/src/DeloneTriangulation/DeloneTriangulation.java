package DeloneTriangulation;

import common.Polygon;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Delone triangulation implementation using incrementation algorithm.
 *
 * Created by anastasia on 5/21/17.
 */
public class DeloneTriangulation {
    private ArrayList<Polygon> triangles;

    public ArrayList<Polygon> triangulate(ArrayList<Point> points) {
        if (points.size() < 3)
            return null;
        return triangles;
    }
}
