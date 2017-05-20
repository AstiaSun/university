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

    void update(Arch leftArch, Arch rightArch, int sweepLine) {
        currentSweepLine = sweepLine;
        if ((leftArch == null) && (rightArch == null)) {
            setLeftBreakPoint(0);
            setRightBreakPoint(0);
        } else {
            setLeftBreakPoint(0);

        }

        if (currentSweepLine == getNodeEvent().getSite().y) {
            initializeBreakPoints(leftArch, rightArch);
        } else {
            updateBreakPoints(leftArch, rightArch);
        }

    }

    private void updateBreakPoints(Arch leftArch, Arch rightArch) {
        if (leftBreakPoint == null) {

            setLeftBreakPoint(0);
            setRightBreakPoint(0);
        } else {
            setLeftBreakPoint(leftBreakPoint.y);
            setRightBreakPoint(rightBreakPoint.y);
        }
    }

    private void setLeftBreakPoint(int y) {
        int[] x = x(y);
        leftBreakPoint = new Point(x[0], y);
    }

    private void setRightBreakPoint(int y) {
        int[] x = x(rightBreakPoint.y);
        rightBreakPoint = new Point(x[1], rightBreakPoint.y);
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

    private Point findLeftBreakPoint(Arch arch, int sweepLine) {
        int[] x = findIntersectionAbscissa(arch.getNodeEvent().getSite());
        if (x != null) {
            Point leftBeakPoint = new Point();
            int[] y = computeArray(x);
        }
        return null;
    }

    private Point findRightBreakPoint(Arch arch) {
        return new Point();
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

    private void initializeBreakPoints(Arch leftArch, Arch rightArch) {

    }
}
