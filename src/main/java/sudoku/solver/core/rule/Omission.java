package sudoku.solver.core.rule;

import sudoku.solver.core.*;

import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Omission {

    public static void analyze(Iteration iteration) {
        iteration
                .getLineOptions()
                .values()
                .forEach(options -> analyzeLineOption(iteration, options));

        iteration
                .getBoxOptions()
                .values()
                .forEach(options -> analyzeBoxOption(iteration, options));


    }

    public static void analyzeBoxOption(Iteration iteration, Iteration.Options<Coordinate> options) {
        options
                .getOptions()
                .forEach((k, v) -> {
                    analyzeBoxOption(iteration, options, k, v, c -> iteration
                            .getGrid()
                            .getHorizontalLine(c));
                    analyzeBoxOption(iteration, options, k, v, c -> iteration
                            .getGrid()
                            .getVerticalLine(c));
                });
    }

    public static void analyzeLineOption(Iteration iteration, Iteration.Options<LineAddress> options) {
        options
                .getOptions()
                .forEach((k, v) -> analyzeLineOption(iteration, options, k, v));
    }


    public static void analyzeBoxOption(Iteration iteration, Iteration.Options<Coordinate> options, Integer option,
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
            line
                    .getCellCollection()
                    .stream()
                    .filter(Cell::requiresSolving)
                    .map(Cell::getCoordinate)
                    .filter(coordinate -> !coordinates.contains(coordinate))
                    .filter(coordinate -> coordinateOptions
                            .getOptions()
                            .get(option)
                            .contains(coordinate))
                    .forEach(coordinate -> iteration.removeOption(coordinate, option));
        }
    }


    public static void analyzeLineOption(Iteration iteration, Iteration.Options<LineAddress> options, Integer option,
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
            box
                    .getCellCollection()
                    .stream()
                    .filter(Cell::requiresSolving)
                    .map(Cell::getCoordinate)
                    .filter(coordinate -> !coordinates.contains(coordinate))
                    .filter(coordinate -> coordinateOptions
                            .getOptions()
                            .get(option)
                            .contains(coordinate))
                    .forEach(coordinate -> iteration.removeOption(coordinate, option));
        }
    }
}
