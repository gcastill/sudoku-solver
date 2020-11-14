package sudoku.solver.core.rule;

import sudoku.solver.core.Coordinate;
import sudoku.solver.core.Iteration;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class NakedSets {


    public static long analyze(Iteration iteration) {
        long updateCount =0;
        for (Iteration.Options<?> options : iteration
                .getLineOptions()
                .values()) {
            updateCount += analyze(2, iteration, options);
            updateCount += analyze(3, iteration, options);
            updateCount += analyze(4, iteration, options);

        }

        for (Iteration.Options<?> options : iteration
                .getBoxOptions()
                .values()) {
            updateCount += analyze(2, iteration, options);
            updateCount += analyze(3, iteration, options);
            updateCount += analyze(4, iteration, options);

        }
        return updateCount;
    }

    public static long analyze(int size, Iteration iteration, Iteration.Options<?> options) {
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


        return items
                .entrySet()
                .stream()
                .mapToLong(e ->
                        analyze(iteration, options, e.getKey(), e.getValue()))
                .sum();


    }

    public static long analyze(Iteration iteration, Iteration.Options<?> options,
                               SortedSet<Coordinate> nakedPairCoordinates, Set<Integer> nakedPairOptions) {

        return options
                .getOptions()
                .entrySet()
                .stream()
                .mapToLong(e -> {
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
                    return toDelete.size();
                })
                .sum();

    }
}


