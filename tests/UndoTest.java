import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import paint_gui.Undo;
import paint_gui.Canvas;
import Exceptions.UndoException;

import static org.junit.jupiter.api.Assertions.*;

class UndoTest {
    private Canvas canvas;
    private Undo undo;

    @BeforeEach
    void setUp() {
        canvas = new Canvas();
        undo = new Undo(canvas);
    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void testNoMoreUndoesException() {
        assertThrows(UndoException.class, () -> undo.undo());
    }

    @Test
    void testNoMoreUndoesExceptionMessage() {
        try {
            undo.undo();
        } catch(UndoException e) {
            assertEquals("No more commands to undo", e.getMessage());}
    }
}