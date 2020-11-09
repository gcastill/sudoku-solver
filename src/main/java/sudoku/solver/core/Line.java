package sudoku.solver.core;

import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public class Line {

    private final LineAddress address;
    private final Map<Integer, Cell> cells = new HashMap<>();

    public Cell get(Integer index) {
        return cells.get(index);
    }

    Cell put(Integer index, Cell cell) {
        return cells.put(index, cell);
    }
}
