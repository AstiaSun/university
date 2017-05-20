package VoronnoiDiagram;

import java.awt.Point;

/**
 * Breakpoint - binary tree node, where the dot of arch crossing
 * is saved
 *
 * Created by anastasia on 5/20/17.
 */
public class Breakpoint extends BinaryTreeNode{
    private Point archCrossPoint;

    Breakpoint(){
        super();
    }

    Breakpoint(BinaryTreeNode parent) {
        super();
        setParent(parent);
    }

    @Override
    public boolean isArch() {
        return false;
    }

    @Override
    public boolean isBreakpoint() {
        return true;
    }
}
