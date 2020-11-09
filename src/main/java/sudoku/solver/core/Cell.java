package sudoku.solver.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Cell {

    private final Coordinate gridCoordinate;
    @Getter
    private Integer value;


    public void setValue(Integer value){

    }
}
