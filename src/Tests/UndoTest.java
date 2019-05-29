package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import paint_gui.Undo;
import paint_gui.Canvas;
import Exceptions.UndoException;

import java.awt.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class UndoTest {
    Undo undo;
    private static Canvas canvas = new Canvas(Color.white);
//    @BeforeEach
//    void setUp() {
//        undo = null;
//    }


    //Tests
    @Test
    void test1() throws UndoException {
    undo = new Undo(canvas);
        try { undo.Undo();}
        catch(UndoException e)
        {assertEquals("No more undoes", e.getMessage());}
    }
    




}