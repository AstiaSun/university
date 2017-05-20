import VoronnoiDiagram.Arch;
import VoronnoiDiagram.BinaryTree;
import VoronnoiDiagram.BinaryTreeNode;
import VoronnoiDiagram.SiteEvent;
import junit.framework.TestCase;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Tests for VoronoiDiagram.BinaryTree class
 *
 * Created by anastasia on 5/20/17.
 */
public class TestBinaryTree extends TestCase{
    private BinaryTree tree;
    private final Random random = new Random();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tree = new BinaryTree();
    }

    public void testAddArch() {
        addArch();
        addArch();
        assertTrue(areArchesSortedByAbscissa());
        //assertSame(getArchesFromTreeStructure(tree.getRoot(), new ArrayList<>()), tree.getArches());
    }

    private void addArch() {
        Point point = new Point(random.nextInt(), random.nextInt());
        tree.addArch(new SiteEvent(point));
    }

    private boolean areArchesSortedByAbscissa() {
        ArrayList<Arch> arches = tree.getArches();
        for (int i = 1; i < arches.size(); i++) {
            if (arches.get(i - 1).getNodeEvent().getSite().x > arches.get(i).getNodeEvent().getSite().x) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<Arch> getArchesFromTreeStructure(BinaryTreeNode node, ArrayList<Arch> previousArches) {
        if (node.isArch()) {
            previousArches.add((Arch) node);
            return previousArches;
        }
        previousArches = getArchesFromTreeStructure(node.getLeftChild(), previousArches);
        return  getArchesFromTreeStructure(node.getRightChild(), previousArches);
    }

    public void testUpdate() {
        for(int i = 0; i < 3; i++){
            addArch();
        }
        ArrayList<Arch> arches = tree.getArches();
        tree.update(arches.get(0).getNodeEvent().getSite().y);
        assertNotNull(tree.getArches());
        //assertNotSame(tree.getArches(), arches);
    }
}
