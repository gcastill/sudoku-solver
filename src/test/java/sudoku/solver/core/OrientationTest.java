package sudoku.solver.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrientationTest {


    @Test
    public void testOpposite() {
        assertEquals(Orientation.HORIZONTAL, Orientation.VERTICAL.opposite());
        assertEquals(Orientation.VERTICAL, Orientation.HORIZONTAL.opposite());
    }
}