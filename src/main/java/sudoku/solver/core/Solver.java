package sudoku.solver.core;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solver {


    public static Stream<Grid> solveBruteForce(Grid grid) {
        if (grid.isComplete()) {

            return Stream.of(grid);
        }
        Cell cell = grid
                .cellStream()
                .filter(Cell::requiresSolving)
                .findFirst()
                .get();

        return IntStream
                .range(1, 10)
                .mapToObj(value -> {
                    Grid clone = grid.clone();
                    clone.setValue(cell.getCoordinate(), value);
                    return clone;
                })
                .filter(Grid::isValid)
                .flatMap(Solver::solveBruteForce);

    }

    public static List<Iteration> solve(Grid grid) {


        List<Iteration> iterations = new ArrayList<>();
        Iteration last = new Iteration(0, grid);
        iterations.add(last);
        while (!last.isComplete()) {
            last.analyze();
            Iteration next = last.next();
            if (next == null) {
                break;
            }
            iterations.add(next);
            last = next;
        }

        return iterations;
    }

    public static SortedSet<Integer> calculateOptions(Cell cell, Grid grid) {
        SortedSet<Integer> result = IntStream
                .range(1, 10)
                .boxed()
                .collect(Collectors.toCollection(TreeSet::new));

        result.removeAll(grid
                .getBox(cell)
                .getCellValues());

        result.removeAll(grid
                .getVerticalLine(cell)
                .getCellValues());

        result.removeAll(grid
                .getHorizontalLine(cell)
                .getCellValues());

        return result;
    }

}
