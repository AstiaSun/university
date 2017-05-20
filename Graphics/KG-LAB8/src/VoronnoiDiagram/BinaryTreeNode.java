package VoronnoiDiagram;

/**
 * Binary tree node implementation. There are 3 types of nodes:
 * arches, breakpoints and breakpoint owners (has 2 breakpoints
 * as children).
 *
 * Created by anastasia on 5/20/17.
 */
public abstract class BinaryTreeNode {
    private BinaryTreeNode rightChild;
    private BinaryTreeNode leftChild;

    BinaryTreeNode() {
        rightChild = null;
        leftChild = null;
    }

    private boolean isLeaf() {
        return (rightChild != null) && (leftChild != null);
    }

    public BinaryTreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BinaryTreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public BinaryTreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BinaryTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public abstract boolean isArch();

    public abstract boolean isBreakpoint();

    public boolean isBreakPointOwner() {
        return !isLeaf() && rightChild.isBreakpoint() && leftChild.isBreakpoint();
    }
}
