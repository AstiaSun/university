import VoronnoiDiagram.Arch;
import VoronnoiDiagram.BinaryTree;
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
        assertTrue(areArchesSortedByAbscissa());
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
}
