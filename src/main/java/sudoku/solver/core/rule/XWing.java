package sudoku.solver.core.rule;

import lombok.extern.slf4j.Slf4j;
import sudoku.solver.core.*;
import sudoku.solver.util.PrettyPrint;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@Slf4j
public class XWing {


    public static long analyze(Iteration iteration) {

        return analyzeLines(iteration, Orientation.HORIZONTAL) + analyzeLines(iteration, Orientation.VERTICAL);
    }


    public static long analyzeLines(Iteration iteration, Orientation orientation) {

        List<Line> lines = iteration
                .getGrid()
                .lineStream()
                .filter(Line::requiresSolving)
                .filter(l -> l
                        .isOriented(orientation))
                .collect(Collectors.toList());

        return IntStream
                .range(1, 10)
                .boxed()
                .mapToLong(option -> analyzeLines(iteration, orientation, lines, option))
                .sum();

    }


    public static long analyzeLines(Iteration iteration, Orientation orientation, List<Line> lines, Integer option) {
        Map<LineAddress, SortedSet<Coordinate>> lineAddressToCoordinates = new TreeMap<>();
        for (Line line : lines) {
            SortedSet<Coordinate> coordinates = iteration
                    .getLineOptions()
                    .get(line.getAddress())
                    .getOptions()
                    .getOrDefault(option,
                            Collections.emptySortedSet());

            if (coordinates.size() == 2) {
                lineAddressToCoordinates.put(line.getAddress(),
                        coordinates
                                .stream()
                                .collect(Collectors.toCollection(TreeSet::new)));
            }
        }

        Map<LineAddress, TreeSet<Integer>> lineAddressToOppositeIndices = lineAddressToCoordinates
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e
                                .getValue()
                                .stream()
                                .map(orientation.opposite()::indexFor)
                                .collect(Collectors.toCollection(TreeSet::new))));


        Map<TreeSet<Integer>, List<Map.Entry<LineAddress, TreeSet<Integer>>>> temp =
                lineAddressToOppositeIndices
                        .entrySet()
                        .stream()
                        .collect(Collectors.groupingBy(e -> e.getValue()));

        Map<TreeSet<Integer>, TreeSet<LineAddress>> oppositeIndicesToLineAddress = temp
                .entrySet()
                .stream()
                .filter(e -> e
                        .getValue()
                        .size() == 2)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e
                                .getValue()
                                .stream()
                                .map(Map.Entry::getKey)
                                .collect(Collectors.toCollection(TreeSet::new))));


        long updateCount = 0;


        for (Map.Entry<LineAddress, TreeSet<Integer>> entry : lineAddressToOppositeIndices.entrySet()) {
            if (!oppositeIndicesToLineAddress.containsKey(entry.getValue())) {
                continue;
            }
            TreeSet<LineAddress> pairLineAddresses = oppositeIndicesToLineAddress.get(entry.getValue());
            LineAddress lineAddress = entry.getKey();
            LOG.trace("xWing option={} {}", option, entry);
            SortedSet<Coordinate> coordinates =
                    pairLineAddresses
                            .stream()
                            .map(lineAddressToCoordinates::get)
                            .flatMap(Collection::stream)
                            .collect(Collectors.toCollection(TreeSet::new));
            SortedSet<LineAddress> oppositeLineAddresses
                    = coordinates
                    .stream()
                    .map(orientation.opposite()::lineAddressFor)
                    .collect(Collectors.toCollection(TreeSet::new));
            LOG.trace("xWing option={}, lineAddress={}, coordinates={} oppositeLines={}", option, lineAddress,
                    coordinates,
                    oppositeLineAddresses);

            for (LineAddress oppositeLineAddress : oppositeLineAddresses) {
                SortedSet<Coordinate> oppositeOptionCoordinates = iteration
                        .getLineOptions()
                        .get(oppositeLineAddress)
                        .getOptions()
                        .get(option);
                Set<Coordinate> toDelete = oppositeOptionCoordinates
                        .stream()
                        .filter(c -> !coordinates.contains(c))
                        .collect(Collectors.toSet());
                if (!toDelete.isEmpty()) {
                    LOG.debug("xWing option={}, lineAddress={}, coordinates={} oppositeLines={}, toDelete={}", option,
                            lineAddress, coordinates,
                            oppositeLineAddresses, toDelete);
                }


                updateCount += toDelete.size();
                toDelete.forEach(c -> iteration.removeOption(c, option));
            }

        }


        return updateCount;
    }


}
