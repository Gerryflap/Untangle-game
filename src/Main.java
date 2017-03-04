import graphs.Graph;
import graphs.Node;
import view.GameCanvas;
import view.GameFrame;

/**
 * Created by gerben on 3-3-17.
 */
public class Main {

    public static void main(String[] args) {

        Graph graph = Graph.constructPlanar(10, GameCanvas.NODE_WIDTH, 500 - GameCanvas.NODE_WIDTH, 0.8);
        GameCanvas gameCanvas = new GameCanvas(graph);
        GameFrame gameFrame = new GameFrame();
        gameFrame.build(gameCanvas);
    }
}
