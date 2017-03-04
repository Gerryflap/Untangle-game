package graphs;

/**
 * Created by gerben on 3-3-17.
 */
public class Position {
    public final int x;
    public final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getDistance(Position pos) {
        return Math.pow(Math.pow(pos.x - x, 2) + Math.pow(pos.y - y, 2), 0.5);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) {
            return super.equals(obj);
        } else {
            Position other = (Position) obj;
            return other.x == x && other.y == y;
        }
    }
}
