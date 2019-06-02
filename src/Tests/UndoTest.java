package Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import paint_gui.Undo;
import paint_gui.Canvas;
import java.awt.*;
import java.util.Random;
import Exceptions.UndoException;

import static org.junit.jupiter.api.Assertions.*;

class UndoTest {

    private Canvas canvas; // Is this meant to be static?
    private Undo undo;

    @BeforeEach
    void setUp() {
        canvas = new Canvas();
        undo = new Undo(canvas);
    }
    @AfterEach
    void tearDown() {
    }

    /** TEST THE FOLLOWING **/

    @Test
    void testUndo() throws UndoException { // Throws null pointer due to clean() and readCommands()
        canvas.addCommand("Drawing 1");
        canvas.addCommand("Drawing 2");
        undo.undo();
        int size = canvas.getCommands().size();
        assertEquals(size, 1);
    }

    @Test
    void testNoMoreUndoes() throws UndoException {
        try {
            undo.undo();
        } catch(UndoException e) {
            assertEquals("No more undoes", e.getMessage());}
    }

    @Test
    void testMultipleUndo() throws  UndoException { // Throws null pointer due to clean() and readCommands()
        canvas.addCommand("Drawing 1");
        canvas.addCommand("Drawing 2");
        undo.undo();
        int size = canvas.getCommands().size();
        assertEquals(size, 0);
    }

}