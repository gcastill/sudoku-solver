package sudoku.solver.core;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solver {


    public static List<Iteration> solve(Grid grid) {


        List<Iteration> iterations = new ArrayList<>();
        Iteration last = new Iteration(grid);

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
