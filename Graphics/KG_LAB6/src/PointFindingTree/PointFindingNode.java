package PointFindingTree;

import java.awt.*;
import java.util.ArrayList;


class PointFindingNode {
    private ArrayList<Point> bHull;
    private int height;
    private PointFindingNode parent;
    private PointFindingNode leftChild;
    private PointFindingNode rightChild;

    PointFindingNode(PointFindingNode parent) {
        this.parent = parent;
        if (parent != null)
            this.height = parent.height + 1;
        else
            this.height = 0;
        leftChild = null;
        rightChild = null;
        bHull = new ArrayList<>();
    }

    PointFindingNode getLeftChild() {
        return leftChild;
    }

    PointFindingNode getRightChild() {
        return rightChild;
    }

    PointFindingNode getParent() {
        return parent;
    }

    public PointFindingNode getGrandparent() {
        if (parent == null)
            return null;
        return parent.getParent();
    }

    boolean isLeaf() {
        return bHull.size() <= 1;
    }

    void setLeftChild(PointFindingNode node) {
        leftChild = node;
    }

    void setRightChild(PointFindingNode node) {
        rightChild = node;
    }

    ArrayList<Point> getbHull() {
        return bHull;
    }
}
