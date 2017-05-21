import common.Computations;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Drawing and mouse handling implementations
 *
 * Created by anastasia on 4/12/17.
 */
public class DrawArea extends JComponent{
    //.............................................VARIABLES..........................................................//

    private ArrayList<Point> points;

    private Image image;
    private Graphics2D graphics2D;

    private final int PAINT_RADIUS = 7;

    //.............................................PUBLIC..METHODS....................................................//

    DrawArea() {
        setDoubleBuffered(false);
    }

    void generatePoints(int amount) {
        Random random = new Random();
        points = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            int x = random.nextInt(this.getWidth() - 6 * PAINT_RADIUS) + 3 * PAINT_RADIUS;
            int y = random.nextInt(this.getHeight() - 6 * PAINT_RADIUS) + 3 * PAINT_RADIUS;
            Point newPoint = new Point(x, y);
            while (Computations.isPointPresent(newPoint, points, 4 * PAINT_RADIUS)){
                newPoint = new Point(random.nextInt(this.getWidth()), random.nextInt(this.getHeight()));
            }
            points.add(newPoint);
        }
        clear();
        drawPoints();
    }

    public void triangulate() {

    }

    void clearData() {
        clear();
        initialize();
    }

    //.............................................PRIVATE..METHODS...................................................//

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics2D = (Graphics2D) image.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clearData();
        }
        g.drawImage(image, 0, 0, null);
    }

    private void initialize() {
        points = new ArrayList<>();
        graphics2D.setPaint(Color.black);
    }

    private void drawPoint(Point e) {
        graphics2D.fillOval(e.x - PAINT_RADIUS / 2, e.y - PAINT_RADIUS / 2, PAINT_RADIUS, PAINT_RADIUS);
        repaint();
    }

    private void drawPoint(Integer i) {
        graphics2D.fillOval(points.get(i).x - PAINT_RADIUS / 2,
                points.get(i).y - PAINT_RADIUS / 2, PAINT_RADIUS, PAINT_RADIUS);
        repaint();
    }

    private void drawLine(Point from, Point to) {
        graphics2D.drawLine(from.x, from.y, to.x, to.y);
        repaint();
    }

    private void drawPoints() {
        for (int i = 0; i < points.size(); i++) {
            drawPoint(i);
        }
    }

    private void clear() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        graphics2D.setPaint(Color.BLACK);
        repaint();
    }
}
