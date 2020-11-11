package sudoku.solver.core.rule;

import sudoku.solver.core.Coordinate;
import sudoku.solver.core.Iteration;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class NakedSets {


    public static void analyze(Iteration iteration) {
        for (Iteration.Options<?> options : iteration
                .getLineOptions()
                .values()) {
            analyze(2, options);
            analyze(3, options);
            analyze(4, options);

        }

        for (Iteration.Options<?> options : iteration
                .getBoxOptions()
                .values()) {
            analyze(2, options);
            analyze(3, options);
            analyze(4, options);

        }
    }

    public static void analyze(int size, Iteration.Options<?> options) {
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

        items.forEach((nakedPairCoordinates, nakedPairOptions) -> {
            options
                    .getOptions()
                    .entrySet()
                    .forEach(e -> {
                        if (nakedPairOptions.contains(e.getKey())) {
                            e
                                    .getValue()
                                    .retainAll(nakedPairCoordinates);
                        } else {
                            e
                                    .getValue()
                                    .removeAll(nakedPairCoordinates);
                        }
                    });
        });

    }
}


