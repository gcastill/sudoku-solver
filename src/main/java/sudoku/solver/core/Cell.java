package sudoku.solver.core;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Cell {

    private final Coordinate gridCoordinate;
    private final Coordinate boxCoordinate;
    private int value;
}
