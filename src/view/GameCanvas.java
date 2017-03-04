package view;

import graphs.Edge;
import graphs.Graph;
import graphs.Node;
import graphs.Position;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by gerben on 3-3-17.
 */
public class GameCanvas extends Canvas implements MouseListener, MouseMotionListener{
    private Graph graph;
    private Node selected = null;
    private Image buffer;
    private Graphics bufferGraphics;
    private boolean planar;
    public static final Color NODE_BACK_SELECTED = new Color(150, 150, 230);
    public static final Color NODE_BACK_NORMAL = new Color(200, 200, 230);
    public static final Color NODE_BORDER = new Color(0,0,0);
    public static final Color EDGE = new Color(0,0,0);
    public static final Color EDGE_CROSSING = new Color(230,55,55);
    public static final Color EDGE_VICTORY = new Color(0,100,0);
    public static final Color BACKGROUND = new Color(240,240,240);


    public static final int NODE_WIDTH = 13;
    private Edge[] crossing = null;


    public GameCanvas(Graph graph) {
        init(graph);
    }

    public GameCanvas(GraphicsConfiguration config, Graph graph) {
        super(config);
        init(graph);
    }

    public void init(Graph graph) {
        this.graph = graph;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        buffer = new BufferedImage(500, 500, Image.SCALE_SMOOTH);
        bufferGraphics = buffer.getGraphics();

        crossing = graph.getFirstCrossingEdges();
        planar = crossing == null;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(bufferGraphics);
        bufferGraphics.setColor(BACKGROUND);
        bufferGraphics.fillRect(0,0,getWidth(), getHeight());
        bufferGraphics.setColor(planar?EDGE_VICTORY:EDGE);
        for (Edge edge: graph.getEdges()) {
            if (crossing != null && (edge == crossing[0] || edge == crossing[1])) {
                bufferGraphics.setColor(EDGE_CROSSING);
            }
            bufferGraphics.drawLine(edge.getFrom().getX(), edge.getFrom().getY(),
                    edge.getTo().getX(), edge.getTo().getY());
            bufferGraphics.setColor(planar?EDGE_VICTORY:EDGE);
        }

        for (Node n :
                graph.getNodes()) {
            if (n == selected) {
                bufferGraphics.setColor(NODE_BACK_SELECTED);
            } else {
                bufferGraphics.setColor(NODE_BACK_NORMAL);
            }
            bufferGraphics.fillOval(n.getX()-NODE_WIDTH/2, n.getY()-NODE_WIDTH/2, NODE_WIDTH, NODE_WIDTH);
            bufferGraphics.setColor(NODE_BORDER);
            bufferGraphics.drawOval(n.getX()-NODE_WIDTH/2, n.getY()-NODE_WIDTH/2, NODE_WIDTH, NODE_WIDTH);
        }
        g.drawImage(buffer, 0, 0, this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Position mousePos = new Position(e.getX(), e.getY());
        for (Node node :
                graph.getNodes()) {
            if (node.getPosition().getDistance(mousePos) < 10) {
                selected = node;
                break;
            }
        }

        if (selected != null) {
            selected.setPosition(mousePos);
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selected = null;
        crossing = graph.getFirstCrossingEdges();
        planar = crossing == null;
        repaint();
        System.out.printf("Is graph planar: %b\n", planar);

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selected != null) {
            selected.setPosition(e.getX(), e.getY());
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
