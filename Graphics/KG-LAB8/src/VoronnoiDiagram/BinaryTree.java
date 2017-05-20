package VoronnoiDiagram;

import java.awt.*;
import java.util.ArrayList;

/**
 * Binary tree for saving the front of the Voronoi diagram
 *
 * Created by anastasia on 5/20/17.
 */
class BinaryTree {
    private BinaryTreeNode root;
    private  ArrayList<Arch> arches;

    BinaryTree() {
        root = new Breakpoint();
        arches = new ArrayList<>();
    }

    void addArch(Event siteEvent) {
        Arch arch = new Arch();
        arch.setNodeEvent(siteEvent);
        if (arches.isEmpty()) {
            addArchAsRootChild(arch);
        } else {
            addArchToTree(arch);
        }
    }

    private void addArchAsRootChild(Arch arch) {
        arch.setParent(root);
        root.setLeftChild(arch);
        arches.add(arch);
    }

    private void addArchToTree(Arch arch) {
        int placeForNewArch = locateArch(arch);
        BinaryTreeNode parent = findParentForNewArch(placeForNewArch);
        arch.setParent(parent);
        if (!parent.isRightChildExist())
            parent.setRightChild(arch);
        else if (!parent.isLeftChildExist())
            parent.setLeftChild(arch);
        addToArches(placeForNewArch, arch);
    }

    private int locateArch(Arch arch) {
        for (int i = 0; i < arches.size(); i++) {
            if (arches.get(i).getNodeEvent().getSite().x > arch.getNodeEvent().getSite().x) {
                return i;
            }
        }
        return arches.size();
    }

    private BinaryTreeNode findParentForNewArch(int placeForNewArch) {
        if (placeForNewArch > 0) {
            if (!arches.get(placeForNewArch - 1).getParent().isRightChildExist()) {
                return arches.get(placeForNewArch - 1).getParent();
            }
            return getNewParentWithArchFromGrandParent(placeForNewArch - 1);
        }
        if (!arches.get(placeForNewArch).getParent().isRightChildExist()) {
            return getParentAndShiftLeftChildToRight(placeForNewArch);
        }
        return getNewParentWithArchFromGrandParent(placeForNewArch);
    }

    private BinaryTreeNode getNewParentWithArchFromGrandParent(int archIndex) {
        BinaryTreeNode parent = arches.get(archIndex).getParent();
        BinaryTreeNode breakpoint = new Breakpoint(parent);
        breakpoint.setRightChild(arches.get(archIndex));
        arches.get(archIndex).setParent(breakpoint);
        parent.setRightChild(breakpoint);
        return breakpoint;
    }

    private BinaryTreeNode getParentAndShiftLeftChildToRight(int archIndex) {
        BinaryTreeNode parent = arches.get(archIndex).getParent();
        parent.setRightChild(arches.get(archIndex));
        parent.setLeftChild(null);
        return parent;
    }

    private void addToArches(int place, Arch arch) {
        if (place < arches.size()) {
            arches.add(place, arch);
        } else {
            arches.add(arch);
        }
    }

    ArrayList<Event> update(Point currentSweepLinePosition) {
        ArrayList<Event> circleEvents = new ArrayList<>();
        return circleEvents;
    }
}
