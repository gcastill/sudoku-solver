package sudoku.solver.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    @Test
    void testIsValid() {
        Grid g = new Grid();
        g.setValue(0, 0, 1);
        g.setValue(0, 1, 1);
        Assertions.assertFalse(g.isValid());
    }
}