package sudoku.solver.core;

import lombok.Value;
import sudoku.solver.core.rule.NakedSets;

import java.util.*;
import java.util.stream.Collectors;

@Value
public class Iteration {

    private final Grid grid;
    private final SortedMap<Coordinate, SortedSet<Integer>> coordinateOptions = new TreeMap<>();
    private final SortedMap<Coordinate, Options<Coordinate>> boxOptions = new TreeMap<>();
    private final SortedMap<LineAddress, Options<LineAddress>> lineOptions = new TreeMap<>();

    public Iteration(Grid grid) {
        this.grid = grid;

    }

    public Iteration next() {
        Grid clone = grid.clone();
        long updateCount = 0;
        updateCount += coordinateOptions
                .entrySet()
                .stream()
                .filter(e -> e
                        .getValue()
                        .size() == 1)
                .peek(e -> {
                    Coordinate coordinate = e.getKey();
                    clone.setValue(coordinate.getX(), coordinate.getY(), e
                            .getValue()
                            .first());
                })
                .count();


        updateCount += boxOptions
                .values()
                .stream()
                .mapToLong(boxOption -> boxOption.apply(clone))
                .sum();

        updateCount += lineOptions
                .values()
                .stream()
                .mapToLong(lineOption -> lineOption.apply(clone))
                .sum();


        if (updateCount == 0) {
            return null;
        }
        return new Iteration(clone);
    }

    public void analyze() {
        grid
                .cellStream()
                .filter(Cell::requiresSolving)
                .forEach
                        (cell -> coordinateOptions.put(cell.getCoordinate(), Solver.calculateOptions(cell, grid)));
        grid
                .boxStream()
                .filter(Box::requiresSolving)
                .map(box -> new Options<Coordinate>(box.getCoordinate(), box))
                .forEach(o -> {
                    o.analyze(this);
                    boxOptions.put(o.getId()
                            , o);

                });
        grid
                .lineStream()
                .filter(Line::requiresSolving)
                .map(line -> new Options<LineAddress>(line.getAddress(), line))
                .forEach(o -> {
                    o.analyze(this);
                    lineOptions.put(o.getId(), o);
                });
        NakedSets.analyze(this);


    }

    public boolean isComplete() {
        return grid.isComplete();
    }

    @Value
    public static class Options<T> {
        private final T id;
        private final CellSupport cellSupport;
        private final SortedMap<Integer, SortedSet<Coordinate>> options = new TreeMap<>();


        public void analyze(Iteration iteration) {

            cellSupport
                    .getCellCollection()
                    .stream()
                    .filter(Cell::requiresSolving)
                    .forEach(cell -> {
                        iteration.coordinateOptions
                                .get(cell.getCoordinate())
                                .forEach(option -> {
                                    options
                                            .computeIfAbsent(option,
                                                    key -> new TreeSet<>())
                                            .add(cell.getCoordinate());
                                });
                    });
        }


        public long apply(Grid clone) {

            Map<Coordinate, Integer> toApply = options
                    .entrySet()
                    .stream()
                    .filter(e -> e
                            .getValue()
                            .size() == 1)
                    .collect(Collectors.toMap(e -> e
                            .getValue()
                            .first(), Map.Entry::getKey));


            toApply.forEach(clone::setValue);


            return toApply.size();
        }


    }


}
