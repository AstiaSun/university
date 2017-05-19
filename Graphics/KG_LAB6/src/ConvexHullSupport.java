import PointFindingTree.PointFindingTree;

import java.awt.*;
import java.util.ArrayList;


public class ConvexHullSupport {
    private PointFindingTree tree;

    public void buildTree(ArrayList<Point> points) {
        tree = new PointFindingTree();
        tree.build(points);
    }

    public ArrayList<Point> getConvexHull() {
        return tree.getConvexHull();
    }
}
