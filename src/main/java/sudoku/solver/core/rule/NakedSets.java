package sudoku.solver.core.rule;

import lombok.extern.slf4j.Slf4j;
import sudoku.solver.core.Cell;
import sudoku.solver.core.Coordinate;
import sudoku.solver.core.Iteration;
import sudoku.solver.util.PermutationUtil;
import sudoku.solver.util.PrettyPrint;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class NakedSets {


    public static long analyze(Iteration iteration) {
        long updateCount = 0;
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

        Map<Coordinate, SortedSet<Integer>> coordinateOptions = options
                .getHouse()
                .getCellCollection()
                .stream()
                .filter(Cell::requiresSolving)
                .map(Cell::getCoordinate)
                .collect(Collectors.toMap(Function.identity(),
                        iteration.getCoordinateOptions()::get));


        Set<SortedSet<Coordinate>> allCellCombinations = PermutationUtil.permutateSorted(coordinateOptions.keySet(), size);
        long updateCount = 0;
        for (SortedSet<Coordinate> combination : allCellCombinations) {
            Set<Integer> combinationOptions = combination
                    .stream()
                    .map(coordinateOptions::get)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());

            if (combinationOptions.size() == size) {

                updateCount += coordinateOptions
                        .entrySet()
                        .stream()
                        .filter(e -> !combination.contains(e.getKey()))
                        .mapToLong(e -> removeOtherCoordinateOptionsByNakedSet(iteration, options.getId(), e.getKey(),
                                e.getValue(),

                                combination, combinationOptions))
                        .sum();


            }
        }

        return updateCount;

    }

    public static long removeOtherCoordinateOptionsByNakedSet(Iteration iteration,
                                                              Object houseId, Coordinate otherCoordinate,
                                                              SortedSet<Integer> otherCoordinateOptions,
                                                              SortedSet<Coordinate> nakedSetCoordinates, Set<Integer> nakedSetOptions) {

        Set<Integer> toDelete = otherCoordinateOptions
                .stream()
                .filter(nakedSetOptions::contains)
                .collect(Collectors.toSet());

        toDelete
                .stream()
                .peek(option -> {
                    LOG.debug("in {} {} forms a naked set of size {} with {} so {} can be deleted from {}", houseId,
                            PrettyPrint.toCoordinateCollectionString(nakedSetCoordinates), nakedSetCoordinates.size(),
                            nakedSetOptions, option,
                            otherCoordinate);

                })
                .forEach(c -> iteration.removeOption(otherCoordinate, c));
        return toDelete.size();
    }

}


