package rccookie.geometry;

public class Line {
    public final Vector start;
    public final Vector direction;

    public Line(Vector direction) {
        this(new Vector(), direction);
    }
    public Line(Vector start, Vector direction) {
        this.start = start.clone();
        this.direction = direction.clone();
    }

    public Vector get(double r) {
        return direction.scaled(r).add(start);
    }



    @Override
    public String toString() {
        return "line from " + start + " towards " + direction;
    }
}
