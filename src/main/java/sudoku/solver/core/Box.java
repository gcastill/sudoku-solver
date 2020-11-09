package sudoku.solver.core;

import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public class Box {

    private final Coordinate coordinate;
    private final Map<Coordinate, Cell> cells = new HashMap<>();

    public Cell getCell(Coordinate coordinate) {
        return cells.get(coordinate);
    }

    public Cell put(Cell cell) {
        return cells.put(cell.getCoordinate(), cell);
    }
}
