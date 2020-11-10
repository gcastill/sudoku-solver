package sudoku.solver.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class Cell {
    private final Coordinate coordinate;
    private Integer value;


    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean isComplete() {
        return value != null;
    }
    public boolean requiresSolving() {
        return !isComplete();
    }
}
