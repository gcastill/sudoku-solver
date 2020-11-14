package sudoku.solver.core;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public interface House {

    Collection<Cell> getCellCollection();


    default List<Integer> getCellValues() {
        return getCellCollection()
                .stream()
                .filter(Cell::isComplete)
                .map(Cell::getValue)
                .collect(Collectors.toList());
    }

    default SortedSet<Integer> getMissingCellValues() {
        List
                <Integer> cellValues = getCellValues();
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
