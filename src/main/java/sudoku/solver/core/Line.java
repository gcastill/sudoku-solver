package sudoku.solver.core;

import lombok.Value;

import java.util.HashMap;

@Value
public class Line {

    private final LineAddress address;
    private final HashMap<Integer, Cell> cells;

    public Cell get(Integer index){
        return cells.get(index);
    }
}
