package PointFindingTree;
import common.Computations;
import common.Edge;
import sun.reflect.generics.tree.Tree;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Binary tree implementation for convex hull support algorithm
 *
 * Created by anastasia on 4/12/17.
 */
public class PointFindingTree implements Tree {
    private PointFindingNode root;
    private ArrayList<Point> pointsSortedByAxis;

    public void build(ArrayList<Point> points) {
        pointsSortedByAxis = Computations.sortByAxis(points);
        root = new PointFindingNode(null);
        recursiveBuild(root, 0, pointsSortedByAxis.size());
    }

    private void recursiveBuild(PointFindingNode currentNode, int from, int to) {
        if (from + 2 >= to) {
            currentNode.setLeftChild(createLeaf(pointsSortedByAxis.get(from), currentNode));
            if (from + 1 < to) {
                currentNode.setRightChild(createLeaf(pointsSortedByAxis.get(from + 1), currentNode));
            }
        } else {
            createChildren(currentNode);
            recursiveBuild(currentNode.getLeftChild(), from, (from + to) / 2);
            recursiveBuild(currentNode.getRightChild(), (from + to) / 2, to);
        }
        createHull(currentNode);
    }

    private void createChildren(PointFindingNode currentNode) {
        currentNode.setRightChild(new PointFindingNode(currentNode));
        currentNode.setLeftChild(new PointFindingNode(currentNode));
    }

    private PointFindingNode createLeaf(Point point, PointFindingNode parent) {
        PointFindingNode node = new PointFindingNode(parent);
        node.getbHull().add(point);
        return node;
    }

    private void createHull(PointFindingNode currentNode) {
        PointFindingNode rightChild = currentNode.getRightChild();
        PointFindingNode leftChild = currentNode.getLeftChild();
        if (leftChild.isLeaf()) {
            currentNode.getbHull()
                    .add(leftChild.getbHull().get(0));
            if((rightChild != null) &&(rightChild.isLeaf())) {
                currentNode.getbHull()
                        .add(rightChild.getbHull().get(0));
            }
        } else {
            createHullForMultiplePoints(currentNode);
        }
    }

    private void createHullForMultiplePoints(PointFindingNode currentNode) {
        PointFindingNode rightChild = currentNode.getRightChild();
        PointFindingNode leftChild = currentNode.getLeftChild();
        currentNode.setbHull(createBHullFromChildren(rightChild, leftChild));
    }

    private ArrayList<Point> createBHullFromChildren(PointFindingNode rightChild, PointFindingNode leftChild) {
        Edge baseLine = Computations.findBaseLine(rightChild.getbHull(), leftChild.getbHull());
        ArrayList<Point> firstBHull = rightChild.getbHull();
        Computations.removeAllPointsOnRightSide(firstBHull, baseLine.getFrom());
        ArrayList<Point> secondBHull = leftChild.getbHull();
        Computations.removeAllPointsOnLeftSide(secondBHull, baseLine.getTo());
        return Computations.merge(firstBHull, secondBHull);
    }

    public ArrayList<Point> getConvexHull() {
        return root.getbHull();
    }
}
