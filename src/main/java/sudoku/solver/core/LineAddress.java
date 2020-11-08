package sudoku.solver.core;

import lombok.Value;

@Value
public class LineAddress {
    private final int index;
    private final Orientation orientation;
}