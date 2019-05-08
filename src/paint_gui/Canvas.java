package paint_gui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Canvas extends JPanel {
    Dimension minSize = new Dimension(200, 200);
    private int x1, y1, x2, y2;

    public Canvas(Color color) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(color);
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x1 = e.getX();
                y1 = e.getY();
                System.out.println("Start co-ords are: " + x1 + " and " + y1);
            }
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                x2 = e.getX();
                y2 = e.getY();
                System.out.println("End co-ords are: " + x2 + " and " + y2);
            }
        });

    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    public Dimension getPreferredSize() {
        return minSize;
    }
}
