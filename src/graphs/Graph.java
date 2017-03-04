package graphs;

import java.util.*;

/**
 * Created by gerben on 3-3-17.
 */
public class Graph {

    private List<Node> nodes = new ArrayList<>();
    private Set<Edge> edges = new HashSet<>();

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void connectNodes(Node n1, Node n2) {
        Edge edge = new Edge(n1, n2);
        connectNodes(edge);
    }

    public void connectNodes(Edge edge) {
        if (hasEdge(edge)) {
            return;
        }
        edges.add(edge);
        edge.getFrom().addEdge(edge);
        edge.getTo().addEdge(edge);
    }

    public void connectNodes(int i1, int i2) {
        connectNodes(getNode(i1), getNode(i2));
    }

    public List<Node> getNodes() {
        return this.nodes;
    }

    public Collection<Edge> getEdges() {
        return edges;
    }

    public Node getNode(int id) {
        return nodes.get(id);
    }

    public boolean hasEdge(Edge edge) {
        return edges.contains(edge) || edges.contains(new Edge(edge.getTo(), edge.getFrom()));
    }

    public static Graph constructPlanar(int vertices, int minPos, int maxPos, double edgeChance) {
        Graph graph = new Graph();
        Random random = new Random();

        int rowLength = (int) Math.sqrt(vertices);


        for (int i = 0; i < vertices; i++) {
            int xn = i%rowLength;
            int yn = i/rowLength;

            graph.addNode(
                new Node(
                    random.nextInt(maxPos - minPos) + minPos,
                    random.nextInt(maxPos - minPos) + minPos
                )
            );


            for (int dy = -1; dy <= 0; dy++) {
                for (int dx = -1; dx <= 0; dx++) {
                    int x = xn + dx;
                    int y = yn + dy;
                    if (x >= 0 && y >= 0 && (dx != 0 || dy != 0) && random.nextFloat() < edgeChance) {
                        int i2 = x + y * rowLength;
                        graph.connectNodes(i, i2);
                    }
                }
            }
        }

        return graph;
    }

    public boolean isPlanar() {
        return getFirstCrossingEdges() == null;
    }

    public Edge[] getFirstCrossingEdges() {
        Set<Edge> todoSet = new HashSet<>();
        todoSet.addAll(edges);
        for (Edge edge :
                edges) {
            todoSet.remove(edge);
            for (Edge edge1: todoSet) {
                if (edge.crosses(edge1)) {
                    /*
                    System.out.printf("Found cross at with todoSet size %d\n", todoSet.size());
                    System.out.printf("Edge from %d, %d to %d, %d and %d, %d to %d, %d\n",
                            edge.getFrom().getX(), edge.getFrom().getY(), edge.getTo().getX(), edge.getTo().getY(),
                            edge1.getFrom().getX(), edge1.getFrom().getY(), edge1.getTo().getX(), edge1.getTo().getY()
                    );
                    //*/
                    return new Edge[]{edge, edge1};
                }
            }
        }
        return null;
    }
}
