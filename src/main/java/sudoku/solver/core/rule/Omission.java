package sudoku.solver.core.rule;

import sudoku.solver.core.*;

import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Omission {

    public static long analyze(Iteration iteration) {
        long updates = 0;
        updates += iteration
                .getLineOptions()
                .values()
                .stream()
                .mapToLong(options -> analyzeLineOption(iteration, options))
                .sum();

        updates += iteration
                .getBoxOptions()
                .values()
                .stream()
                .mapToLong(options -> analyzeBoxOption(iteration, options))
                .sum();

        return updates;
    }

    public static long analyzeBoxOption(Iteration iteration, Iteration.Options<Coordinate> options) {
        return options
                .getOptions()
                .entrySet()
                .stream()
                .mapToLong(e -> {
                    int updates = 0;
                    updates += analyzeBoxOption(iteration, options, e.getKey(), e.getValue(), c -> iteration
                            .getGrid()
                            .getHorizontalLine(c));
                    updates += analyzeBoxOption(iteration, options, e.getKey(), e.getValue(), c -> iteration
                            .getGrid()
                            .getVerticalLine(c));
                    return updates;
                })
                .sum();
    }

    public static long analyzeLineOption(Iteration iteration, Iteration.Options<LineAddress> options) {
        return options
                .getOptions()
                .entrySet()
                .stream()
                .mapToLong(e -> analyzeLineOption(iteration, options, e.getKey(), e.getValue()))
                .sum();
    }


    public static long analyzeBoxOption(Iteration iteration, Iteration.Options<Coordinate> options, Integer option,
                                        SortedSet<Coordinate> coordinates, Function<Coordinate, Line> lineLookup) {

        Set<Line> lines = coordinates
                .stream()
                .map(lineLookup)
                .collect(Collectors.toSet());


        if (lines.size() == 1) {
            Line line = lines
                    .iterator()
                    .next();

            Iteration.Options<LineAddress> coordinateOptions = iteration
                    .getLineOptions()
                    .get(line.getAddress());
            return line
                    .getCellCollection()
                    .stream()
                    .filter(Cell::requiresSolving)
                    .map(Cell::getCoordinate)
                    .filter(coordinate -> !coordinates.contains(coordinate))
                    .filter(coordinate -> coordinateOptions
                            .getOptions()
                            .get(option)
                            .contains(coordinate))
                    .peek(coordinate -> iteration.removeOption(coordinate, option))
                    .count();
        }
        return 0;
    }


    public static long analyzeLineOption(Iteration iteration, Iteration.Options<LineAddress> options, Integer option,
                                         SortedSet<Coordinate> coordinates) {
        Grid grid = iteration.getGrid();
        Set<Box> boxes = coordinates
                .stream()
                .map(c -> grid.getBox(grid.getCell(c)))
                .collect(Collectors.toSet());

        if (boxes.size() == 1) {
            Box box = boxes
                    .iterator()
                    .next();

            Iteration.Options<Coordinate> coordinateOptions = iteration
                    .getBoxOptions()
                    .get(box.getCoordinate());
            return box
                    .getCellCollection()
                    .stream()
                    .filter(Cell::requiresSolving)
                    .map(Cell::getCoordinate)
                    .filter(coordinate -> !coordinates.contains(coordinate))
                    .filter(coordinate -> coordinateOptions
                            .getOptions()
                            .get(option)
                            .contains(coordinate))
                    .peek(coordinate -> iteration.removeOption(coordinate, option))
                    .count();
        }
        return 0;

    }
}
