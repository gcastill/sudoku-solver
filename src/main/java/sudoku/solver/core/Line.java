package sudoku.solver.core;

import lombok.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Value
public class Line implements CellSupport {

    private final LineAddress address;
    private final Map<Integer, Cell> cells = new HashMap<>();

    public Cell get(Integer index) {
        return cells.get(index);
    }

    Cell put(Integer index, Cell cell) {
        return cells.put(index, cell);
    }

    public Collection<Cell> getCellCollection() {
        return cells.values();
    }
}

