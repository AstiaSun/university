package VoronoiDiagram;

public class Parabola {
    private final double a, b, c;

    public Parabola(Point focus, double directrixY) {
        this.a = focus.x;
        this.b = focus.y;
        this.c = directrixY;
    }
}
