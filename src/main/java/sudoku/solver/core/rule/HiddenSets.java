package sudoku.solver.core.rule;

import lombok.extern.slf4j.Slf4j;
import sudoku.solver.core.Coordinate;
import sudoku.solver.core.Iteration;
import sudoku.solver.core.LineAddress;
import sudoku.solver.util.PermutationUtil;
import sudoku.solver.util.PrettyPrint;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class HiddenSets {

    public static long analyze(Iteration iteration) {
        

        return IntStream
                .range(2, 5)
                .mapToLong(size ->
                        iteration
                                .getLineOptions()
                                .values()
                                .stream()
                                .mapToLong(o -> analyzeOptions(iteration,o, size))
                                .sum() +
                                iteration
                                        .getBoxOptions()
                                        .values()
                                        .stream()
                                        .mapToLong(o -> analyzeOptions(iteration,o,size))
                                        .sum()
                )
                .sum();


    }



    private static long analyzeOptions(Iteration iteration, Iteration.Options<?> options, int size) {
        Set<SortedSet<Integer>> setsOfOptions = PermutationUtil.permutateSorted(options
                .getOptions()
                .keySet(), size);
        long updateCount = 0;
        for (SortedSet<Integer> setOfOptions : setsOfOptions) {


            Set<Coordinate> coordinates = setOfOptions
                    .stream()
                    .flatMap(option -> options
                            .getOptions()
                            .get(option)
                            .stream())
                    .collect(Collectors.toCollection(TreeSet::new));
            if (coordinates.size() <= size) {
                for (Coordinate c : coordinates) {
                    List<Integer> toDelete = iteration
                            .getCoordinateOptions()
                            .get(c)
                            .stream()
                            .filter(option -> !setOfOptions.contains(option))
                            .peek(deletable -> {
                                LOG.debug("round={}, house={}, coordinates={}, options={} forms a hidden set, so {} " +
                                                "is not an option for {}",
                                        iteration.getRound(),
                                        options.getId(), PrettyPrint.toCoordinateCollectionString(coordinates),
                                        setOfOptions, deletable, PrettyPrint.toString(c));

                            })
                            .collect(Collectors.toList());
                    updateCount += toDelete.size();
                    toDelete.forEach(x -> iteration.removeOption(c, x));
                }

            }

        }


        return updateCount;
    }


}
