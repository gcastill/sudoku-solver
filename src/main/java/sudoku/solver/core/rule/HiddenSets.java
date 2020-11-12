package sudoku.solver.core.rule;

import sudoku.solver.core.Coordinate;
import sudoku.solver.core.Iteration;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class HiddenSets {


    public static void analyze(Iteration iteration) {
        for (Iteration.Options<?> options : iteration
                .getLineOptions()
                .values()) {
            analyze(2, iteration, options);
            analyze(3, iteration, options);
            analyze(4, iteration, options);

        }

        for (Iteration.Options<?> options : iteration
                .getBoxOptions()
                .values()) {
            analyze(2, iteration, options);
            analyze(3, iteration, options);
            analyze(4, iteration, options);

        }
    }

    public static void analyze(int size, Iteration iteration, Iteration.Options<?> options) {
        Map<SortedSet<Coordinate>, Set<Integer>> items = options
                .getOptions()
                .entrySet()
                .stream()
                .filter((e -> e
                        .getValue()
                        .size() == size))
                .collect(Collectors.groupingBy(e -> e.getValue()))
                .entrySet()
                .stream()
                .filter(e -> e
                        .getValue()
                        .size() == size)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e
                        .getValue()
                        .stream()
                        .map(entry -> entry.getKey())
                        .collect(Collectors.toSet())));

        if(!items.isEmpty()){
            System.out.println();
        }
        items.forEach((nakedPairCoordinates, nakedPairOptions) -> {
            options
                    .getOptions()
                    .entrySet()
                    .forEach(e -> {
                        Set<Coordinate> toDelete;
                        if (nakedPairOptions.contains(e.getKey())) {
                            toDelete = e
                                    .getValue()
                                    .stream()
                                    .filter(coordinate -> !nakedPairCoordinates.contains(coordinate))
                                    .collect(Collectors.toSet());

                        } else {
                            toDelete = e
                                    .getValue()
                                    .stream()
                                    .filter(coordinate -> nakedPairCoordinates.contains(coordinate))
                                    .collect(Collectors.toSet());


                        }
                        toDelete.forEach(c -> iteration.removeOption(c, e.getKey()));
                    });
        });

    }
}


