package sudoku.solver.core;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import sudoku.solver.core.rule.HiddenSets;
import sudoku.solver.core.rule.NakedSets;
import sudoku.solver.core.rule.Omission;
import sudoku.solver.core.rule.XWing;
import sudoku.solver.util.PrettyPrint;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Value
public class Iteration {

    private final int round;
    private final Grid grid;
    private final SortedMap<Coordinate, SortedSet<Integer>> coordinateOptions = new TreeMap<>();
    private final SortedMap<Coordinate, Options<Coordinate>> boxOptions = new TreeMap<>();
    private final SortedMap<LineAddress, Options<LineAddress>> lineOptions = new TreeMap<>();


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
                    Integer option = e
                            .getValue()
                            .first();
                    LOG.debug("{} has only one option so setting it to {}", PrettyPrint.toString(coordinate), option);

                    clone.setValue(coordinate.getX(), coordinate.getY(), option);
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


        if (updateCount == 0 && round != 0) {
            return null;
        }
        return new Iteration(round + 1, clone);
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


        if (round != -0) {
            long nakedSetsCount = NakedSets.analyze(this);
            long omissionCount = Omission.analyze(this);
            long xWingCount = XWing.analyze(this);
            long hiddenSetsCount = HiddenSets.analyze(this);


            LOG.debug("nakedSetsCount={}, omissionCount={}, xWingCount={}, hiddenSetsCount={}", nakedSetsCount,
                    omissionCount,
                    xWingCount, hiddenSetsCount);

        }
    }

    public void removeOption(Coordinate coordinate, int value) {
        coordinateOptions
                .get(coordinate)
                .remove(value);

        Stream
                .of(grid
                        .getHorizontalLine(coordinate), grid
                        .getVerticalLine(coordinate))
                .map(Line::getAddress)
                .map(lineOptions::get)
                .map(Options::getOptions)
                .map(map -> map.get(value))
                .forEach(set -> set.remove(coordinate));

        boxOptions
                .get(grid
                        .getBox(grid.getCell(coordinate))
                        .getCoordinate())
                .getOptions()
                .get(value)
                .remove(coordinate);

        LOG.debug("round {} deleting option: location={}, option={}", round, PrettyPrint.toString(coordinate),
                value);
    }

    public boolean isComplete() {
        return grid.isComplete();
    }

    public boolean isValid() {
        return grid.isValid();
    }

    @Value
    public static class Options<T> {
        private final T id;
        private final House house;
        private final SortedMap<Integer, SortedSet<Coordinate>> options = new TreeMap<>();


        public void analyze(Iteration iteration) {

            house
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
                    .filter(e -> clone
                            .getCell(e
                                    .getValue()
                                    .first())
                            .requiresSolving())
                    .peek(e -> LOG.debug("in {}, {} can only be placed in coordinate so setting marking {} to that " +
                                    "value",
                            getId(), e.getKey(), PrettyPrint.toString(e
                                    .getValue()
                                    .first())))
                    .collect(Collectors.toMap(e -> e
                            .getValue()
                            .first(), Map.Entry::getKey));


            toApply.forEach(clone::setValue);


            return toApply.size();
        }


    }


}
