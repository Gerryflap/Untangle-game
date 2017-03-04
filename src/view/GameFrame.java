package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gerben on 3-3-17.
 */
public class GameFrame extends JFrame {

    public void build(GameCanvas canvas) {
        setTitle("Untangle");
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        canvas.setPreferredSize(new Dimension(500,500));
        getContentPane().add(canvas, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

}
