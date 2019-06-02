package Shapes;

import java.awt.*;

/**
 * Specifies the fill method of the shapes that need to implement this functionality.
 */
public interface FillingShape {
    /**
     * Fills a shape by using the fill methods of the <code>java.awt</code> package.
     * @param g the Graphics2D tool the shape is to be filled
     */
    void fill(Graphics2D g);
}
