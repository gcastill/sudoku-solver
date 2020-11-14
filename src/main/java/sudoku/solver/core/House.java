package sudoku.solver.core;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface House {

    Collection<Cell> getCellCollection();


    default SortedSet<Integer> getCellValues() {
        return getCellCollection()
                .stream()
                .filter(Cell::isComplete)
                .map(Cell::getValue)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    default SortedSet<Integer> getMissingCellValues() {
        SortedSet<Integer> cellValues = getCellValues();
        return IntStream
                .range(1, 10)
                .boxed()
                .filter(v -> !cellValues.contains(v))
                .collect(Collectors.toCollection(TreeSet::new));
    }


    default boolean isComplete() {
        return getMissingCellValues().isEmpty();
    }

    default boolean requiresSolving() {
        return !isComplete();
    }


    default boolean isValid() {
        Set<Integer> values = IntStream
                .range(1, 10)
                .boxed()
                .collect(Collectors.toSet());

        return getCellValues()
                .stream()
                .allMatch(values::remove);
    }

}
