package VoronnoiDiagram;

import common.Computations;

import java.awt.*;

/**
 * Arch - part of parabola, with a site as a focus
 *
 * Created by anastasia on 5/20/17.
 */
public class Arch extends BinaryTreeNode{
    private Point leftBreakPoint;
    private Point rightBreakPoint;
    private int currentSweepLine;

    @Override
    public boolean isArch() {
        return true;
    }

    @Override
    public boolean isBreakpoint() {
        return false;
    }

    void update(Arch leftArch, Arch rightArch) {
        Point[] intersectionsWithLeftArch = findBreakPoints(leftArch);
        Point[] intersectionsWithRightArch = findBreakPoints(rightArch);
        updateBreakPoints(intersectionsWithLeftArch, intersectionsWithRightArch);
    }

    private void updateBreakPoints(Point[] intersectionsWithLeftArch, Point[] intersectionsWithRightArch) {
        if (intersectionsWithLeftArch == null) {
            if(intersectionsWithRightArch == null) {
                intersectWithZero();
            } else {
                intersectWithRightArch(intersectionsWithRightArch);
            }
        } else {
            if(intersectionsWithRightArch == null) {
                intersectWithLeftArch(intersectionsWithLeftArch);
            } else {
                intersectWithBothArches(intersectionsWithLeftArch, intersectionsWithRightArch);
            }
        }
    }

    private void intersectWithZero() {
        setLeftBreakPointAtZero();
        setRightBreakPointAtZero();
    }

    private void intersectWithRightArch(Point[] intersectionsWithRightArch) {
        if ((intersectionsWithRightArch[0].y > 0) &&
                (intersectionsWithRightArch[0].x < getNodeEvent().getSite().x)){
            leftBreakPoint = intersectionsWithRightArch[0];
            rightBreakPoint = intersectionsWithRightArch[1];
        } else {
            setLeftBreakPointAtZero();
            rightBreakPoint = intersectionsWithRightArch[0];
        }
    }

    private void intersectWithLeftArch(Point[] intersectionsWithLeftArch) {
        if ((intersectionsWithLeftArch[0].y > 0) &&
                (intersectionsWithLeftArch[1].x > getNodeEvent().getSite().x)){
            leftBreakPoint = intersectionsWithLeftArch[1];
            rightBreakPoint = intersectionsWithLeftArch[0];
        } else {
            leftBreakPoint = intersectionsWithLeftArch[1];
            setRightBreakPointAtZero();
        }
    }

    private void intersectWithBothArches(Point[] intersectionsWithLeftArch, Point[] intersectionsWithRightArch) {
        if ((intersectionsWithRightArch[0].y > 0) &&
                (intersectionsWithRightArch[0].x < getNodeEvent().getSite().x) &&
                (intersectionsWithLeftArch[1].y > intersectionsWithRightArch[0].y)) {
            leftBreakPoint = intersectionsWithRightArch[0];
        } else {
            leftBreakPoint = intersectionsWithLeftArch[1];
        }

        if ((intersectionsWithLeftArch[0].y > 0) &&
                (intersectionsWithLeftArch[1].x > getNodeEvent().getSite().x) &&
                (intersectionsWithLeftArch[1].y > intersectionsWithRightArch[0].y)) {
            rightBreakPoint = intersectionsWithLeftArch[1];
        } else {
            rightBreakPoint = intersectionsWithRightArch[0];
        }
    }

    private void setLeftBreakPointAtZero() {
        int[] x = x(0);
        leftBreakPoint = new Point(x[0], 0);
    }

    private void setRightBreakPointAtZero() {
        int[] x = x(0);
        rightBreakPoint = new Point(x[1], 0);
    }


    private int[] x(int y) {
        Point focus = getNodeEvent().getSite();
        int[] x = new int[2];
        double relevantX = Math.sqrt((2 * (focus.y - currentSweepLine)) * y +
                Math.pow(currentSweepLine, 2) - Math.pow(focus.y, 2));
        x[0] = (int) (- relevantX + focus.x);
        x[1] = (int) (relevantX + focus.x);
        return x;
    }

    private Point[] findBreakPoints(Arch arch) {
        if (arch == null)
            return null;
        int[] x = findIntersectionAbscissa(arch.getNodeEvent().getSite());
        if ((x != null) && (x[1] > 0)) {
            Point[] breakpoints = new Point[x.length];
            int[] y = computeArray(x);
            for (int i = 0; i < x.length; i++) {
                breakpoints[i] = new Point(x[i], y[i]);
            }
            return breakpoints;
        }
        return null;
    }

    private int[] findIntersectionAbscissa(Point secondFocus) {
        Point firstFocus = getNodeEvent().getSite();

        double firstSquareRelevantY = Math.pow(firstFocus.y, 2) - Math.pow(currentSweepLine, 2);
        double secondSquareRelevantY = Math.pow(secondFocus.y, 2) - Math.pow(currentSweepLine, 2);

        double firstRelevantY = firstFocus.y - currentSweepLine;
        double secondRelevantY = secondFocus.y - currentSweepLine;

        double a = firstRelevantY - secondRelevantY;
        double b = firstRelevantY * secondFocus.x - secondRelevantY * firstFocus.x;
        double c = firstRelevantY * (Math.pow(secondFocus.x, 2) + secondSquareRelevantY) -
                secondRelevantY * (Math.pow(firstFocus.x, 2) + firstSquareRelevantY);

        return Computations.solveQuadraticEquation(a, - 2.0 * b, c);
    }

    private int[] computeArray(int[] x) {
        int[] y = new int[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = y(x[i]);
        }
        return y;
    }

    private int y(int x) {
        Point focus = getNodeEvent().getSite();
        return (int) ((Math.pow(x - focus.x, 2) + Math.pow(focus.y, 2) + Math.pow(currentSweepLine, 2)) /
                        (2 * (focus.y - currentSweepLine)));
    }

    public Point getLeftBreakPoint() {
        return leftBreakPoint;
    }

    public Point getRightBreakPoint() {
        return rightBreakPoint;
    }

    void setCurrentSweepLine(int sweepLine) {
        this.currentSweepLine = sweepLine;
    }

    @Override
    public Arch clone() throws CloneNotSupportedException {
        super.clone();
        Arch arch = new Arch();
        arch.setParent(getParent());
        arch.rightBreakPoint = rightBreakPoint;
        arch.leftBreakPoint = leftBreakPoint;
        arch.currentSweepLine = currentSweepLine;
        arch.setNodeEvent(getNodeEvent());
        return arch;
    }
}
