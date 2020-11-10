package sudoku.solver.core;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface CellSupport {

    Collection<Cell> getCellCollection();


    default SortedSet<Integer> getCellValues() {
        return getCellCollection()
                .stream()
                .map(Cell::getValue)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    default SortedSet<Integer> getMissingCellValues() {
        SortedSet<Integer> cellValues = getCellValues();
        return IntStream
                .range(1,10).boxed().filter(v-> !cellValues.contains(v))
                .collect(Collectors.toCollection(TreeSet::new));
    }

}
