package sudoku.solver.core.rule;

import sudoku.solver.core.Iteration;
import sudoku.solver.core.Line;
import sudoku.solver.core.Orientation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XWing {


    public static long analyze(Iteration iteration) {

        return analyzeLines(iteration, Orientation.HORIZONTAL) + analyzeLines(iteration, Orientation.VERTICAL);
    }


    public static long analyzeLines(Iteration iteration, Orientation orientation) {
        long updates = 0;
        List<Line> lines = iteration
                .getGrid()
                .lineStream()
                .filter(Line::requiresSolving)
                .filter(l -> l
                        .isOriented(orientation))
                .collect(Collectors.toList());


        return updates;
    }


}
