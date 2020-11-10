package sudoku.solver.core;

import lombok.Value;

import java.util.Objects;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

@Value
public class Iteration {

    private final Grid grid;
    private final SortedMap<Coordinate, SortedSet<Integer>> coordinateOptions = new TreeMap<>();

    public Iteration(Grid grid) {
        this.grid = grid;

    }

    public Iteration next() {
        Grid clone = grid.clone();

        coordinateOptions
                .entrySet()
                .stream()
                .filter(e -> e
                        .getValue()
                        .size() == 1)
                .forEach(e -> {

                    Coordinate coordinate = e.getKey();
                    clone.setValue(coordinate.getX(), coordinate.getY(), e
                            .getValue()
                            .first());
                });
        return new Iteration(clone);
    }

    public void analyze() {
        grid
                .cellStream()
                .filter(cell -> Objects.isNull(cell.getValue()))
                .forEach
                        (cell -> coordinateOptions.put(cell.getCoordinate(), Solver.calculateOptions(cell, grid)));
    }

    public boolean isComplete() {
        return grid.isComplete();
    }
}
