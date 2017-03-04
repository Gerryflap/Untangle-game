package graphs;

import javafx.geometry.Pos;

/**
 * Created by gerben on 3-3-17.
 */
public class Edge {
    private Node from;
    private Node to;

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public boolean crosses(Edge edge) {

        // Calculate scaling parameters
        Position e1p1, e1p2;
        if (from.getX() < to.getX()) {
            e1p1 = from.getPosition();
            e1p2 = to.getPosition();
        } else {
            e1p2 = from.getPosition();
            e1p1 = to.getPosition();
        }
        double d = length();
        if (d == 0) {
            return false;
        }
        double angle = Math.atan2(e1p2.y - e1p1.y, e1p2.x - e1p1.x);

        // Scale other edge
        Position e2p1, e2p2;
        if (edge.getFrom().getX() < edge.getTo().getX()) {
            e2p1 = edge.getFrom().getPosition();
            e2p2 = edge.getTo().getPosition();
        } else {
            e2p2 = edge.getFrom().getPosition();
            e2p1 = edge.getTo().getPosition();
        }

        // If the 2 edges share a position, they don't cross
        if (e1p1.equals(e2p1) || e1p1.equals(e2p2) || e1p2.equals(e2p1) || e1p2.equals(e2p2)) {
            return false;
        }


        double e2x1 = (e2p1.x - e1p1.x)/d;
        double e2y1 = (e2p1.y - e1p1.y)/d;
        double e2x1r = e2x1 * Math.cos(angle) + e2y1 * Math.sin(angle);
        double e2y1r = e2x1 * -Math.sin(angle) + e2y1 * Math.cos(angle);

        double e2x2 = (e2p2.x- e1p1.x)/d;
        double e2y2 = (e2p2.y- e1p1.y)/d;
        double e2x2r = e2x2 * Math.cos(angle) + e2y2 * Math.sin(angle);
        double e2y2r = e2x2 * -Math.sin(angle) + e2y2 * Math.cos(angle);

        // If the closest point is still out of the range 0 - 1, the lines can never cross
        if (e2x2r < 0 || e2x1r > 1) {
            return false;
        }

        if (e2x1r == e2x2r) {
            return (e2y1r > 0 && e2y2r < 0) || (e2y1r < 0 && e2y2r > 0);
        }

        // Construct formula ax + b for edge
        double a = (e2y2r - e2y1r)/(e2x2r - e2x1r);
        double b = e2y1r - a * e2x1r;

        // Calculate endpoints
        double startY = Math.max(0, e2x1r) * a + b;
        double endY = Math.min(1, e2x2r) * a + b;
        boolean su = startY > 0;
        boolean eu = endY > 0;

        return (su && !eu) || (eu && !su);


        
    }

    public double length() {
        return Math.sqrt(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2));
    }
}
