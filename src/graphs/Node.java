package graphs;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by gerben on 3-3-17.
 */
public class Node {

    private Set<Edge> edges = new HashSet<>();
    private int x;
    private int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean hasEdge(Edge edge) {
        return edges.contains(edge);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

    public void setPosition(Position position) {
        x = position.getX();
        y = position.getY();
    }
}
