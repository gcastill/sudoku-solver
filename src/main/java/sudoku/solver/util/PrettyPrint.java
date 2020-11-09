package sudoku.solver.util;

import sudoku.solver.core.Coordinate;
import sudoku.solver.core.Grid;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrettyPrint {

    public static String toString(Grid grid) {

        int start = 0;
        int end = 9;

        return IntStream
                .range(start, end)
                .map(i -> start + (end - 1 - i))
                .mapToObj(y -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append('|');
                    sb.append(IntStream
                            .range(0, 9)
                            .mapToObj(x -> new Coordinate(x, y))
                            .map(c -> Optional
                                    .ofNullable(grid
                                            .getCell(c)
                                            .getValue())
                                    .map(String::valueOf)
                                    .orElse(" "))
                            .collect(Collectors.joining("|")));
                    sb.append('|');
               return     sb.toString();
                }).collect(Collectors.joining("\n-------------------\n"));

    }

}
