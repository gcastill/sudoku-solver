package sudoku.solver.core;

import lombok.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Value
public class Box implements CellSupport {

    private final Coordinate coordinate;
    private final Map<Coordinate, Cell> cells = new TreeMap<>();

    public Cell getCell(Coordinate coordinate) {
        return cells.get(coordinate);
    }

    public Cell put(Cell cell) {
        return cells.put(cell.getCoordinate(), cell);
    }


    @Override
    public Collection<Cell> getCellCollection() {
        return cells.values();
    }


}
