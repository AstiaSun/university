package VoronnoiDiagram;

/**
 * Arch - part of parabola, with a site as a focus
 *
 * Created by anastasia on 5/20/17.
 */
public class Arch extends BinaryTreeNode{
    private SiteEvent siteEvent;
    @Override
    public boolean isArch() {
        return true;
    }

    @Override
    public boolean isBreakpoint() {
        return false;
    }
}
