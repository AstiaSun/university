package VoronnoiDiagram;

import common.Computations;

import java.awt.*;
import java.util.ArrayList;

/**
 * Arch - part of parabola, with a site as a focus
 *
 * Created by anastasia on 5/20/17.
 */
public class Arch extends BinaryTreeNode{
    private Point leftBreakPoint;
    private Point rightBreakPoint;

    @Override
    public boolean isArch() {
        return true;
    }

    @Override
    public boolean isBreakpoint() {
        return false;
    }

    void update(Arch leftArch, Arch rightArch, int sweepLineOrdinate) {
        if ((leftArch == null) && (rightArch == null)) {
            setLeftBreakPoint(0, sweepLineOrdinate);
            setRightBreakPoint(0, sweepLineOrdinate);
        } else {
            setLeftBreakPoint(0, sweepLineOrdinate);

        }

        if (sweepLineOrdinate == getNodeEvent().getSite().y) {
            initializeBreakPoints(leftArch, rightArch);
        } else {
            updateBreakPoints(leftArch, rightArch, sweepLineOrdinate);
        }

    }

    private void updateBreakPoints(Arch leftArch, Arch rightArch, int sweepLineOrdinate) {
        if (leftBreakPoint == null) {

            setLeftBreakPoint(0, sweepLineOrdinate);
            setRightBreakPoint(0, sweepLineOrdinate);
        } else {
            setLeftBreakPoint(leftBreakPoint.y, sweepLineOrdinate);
            setRightBreakPoint(rightBreakPoint.y, sweepLineOrdinate);
        }
    }

    private void setLeftBreakPoint(int y, int sweepLineOrdinate) {
        int[] x = x(y, sweepLineOrdinate);
        leftBreakPoint = new Point(x[0], y);
    }

    private void setRightBreakPoint(int y, int sweepLineOrdinate) {
        int[] x = x(rightBreakPoint.y, sweepLineOrdinate);
        rightBreakPoint = new Point(x[1], rightBreakPoint.y);
    }


    private int[] x(int y, int sweepingLineOrdinate) {
        Point focus = getNodeEvent().getSite();
        int[] x = new int[2];
        double relevantX = Math.sqrt((2 * (focus.y - sweepingLineOrdinate)) * y +
                Math.pow(sweepingLineOrdinate, 2) - Math.pow(focus.y, 2));
        x[0] = (int) (- relevantX + focus.x);
        x[1] = (int) (relevantX + focus.x);
        return x;
    }

    private Point findLeftBreakPoint(Arch arch, int sweepLineOrdinate) {
        int[] x = findIntersectionAbscissa(arch.getNodeEvent().getSite(), sweepLineOrdinate);
        if (x != null) {
            Point leftBeakPoint = new Point();
        }
        return null;
    }

    private Point findRightBreakPoint(Arch arch) {
        return new Point();
    }

    private int[] findIntersectionAbscissa(Point secondFocus, int sweepLineOrdinate) {
        Point firstFocus = getNodeEvent().getSite();

        double firstSquareRelevantY = Math.pow(firstFocus.y, 2) - Math.pow(sweepLineOrdinate, 2);
        double secondSquareRelevantY = Math.pow(secondFocus.y, 2) - Math.pow(sweepLineOrdinate, 2);

        double firstRelevantY = firstFocus.y - sweepLineOrdinate;
        double secondRelevantY = secondFocus.y - sweepLineOrdinate;

        double a = firstRelevantY - secondRelevantY;
        double b = firstRelevantY * secondFocus.x - secondRelevantY * firstFocus.x;
        double c = firstRelevantY * (Math.pow(secondFocus.x, 2) + secondSquareRelevantY) -
                secondRelevantY * (Math.pow(firstFocus.x, 2) + firstSquareRelevantY);

        return Computations.solveQuadraticEquation(a, - 2.0 * b, c);
    }

    private int y(int x, int sweepLineOrdinate) {
        Point focus = getNodeEvent().getSite();
        return (int) ((Math.pow(x - focus.x, 2) + Math.pow(focus.y, 2) + Math.pow(sweepLineOrdinate, 2)) /
                        (2 * (focus.y - sweepLineOrdinate)));
    }

    private void initializeBreakPoints(Arch leftArch, Arch rightArch) {

    }
}
