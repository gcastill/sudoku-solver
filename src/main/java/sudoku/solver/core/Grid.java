package sudoku.solver.core;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grid {

    private final HashMap<Coordinate, Cell> cells = new HashMap<>();
    private final HashMap<LineAddress, Line> lines = new HashMap<>();
    private final HashMap<Coordinate, Box> boxes = new HashMap<>();


    public Grid() {
        init();
    }


    public Optional<Line> getNextLine(byte index, Orientation orientation) {
        return getNextLine(new LineAddress(index, orientation));
    }

    public Optional<Line> getNextLine(LineAddress address) {
        return Optional.ofNullable(lines.get(address));
    }

    public Optional<Box> getNextBox(Coordinate coordinate, Direction direction) {
        return Optional.ofNullable(boxes.get(direction.getNext(coordinate)));
    }

    public Optional<Box> getNextBox(Box box, Direction direction) {
        return getNextBox(box.getCoordinate(), direction);
    }


    public void init() {

        IntStream
                .range(0, 9)
                .mapToObj(x -> IntStream
                        .range(0, 9)
                        .mapToObj(y -> new Coordinate(x, y))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .forEach(coordinate -> cells.put(coordinate, new Cell(coordinate)));


        IntStream
                .range(0, 9)
                .mapToObj(index -> Stream
                        .of(Orientation.values())
                        .map(orientation -> new LineAddress(index
                                , orientation))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .forEach(lineAddress -> lines.put(lineAddress, new Line(lineAddress)));


        IntStream
                .range(0, 3)
                .mapToObj(x -> IntStream
                        .range(0, 3)
                        .mapToObj(y -> new Coordinate(x, y))
                        .collect(Collectors.toList()))
        .flatMap(List::stream).forEach(coordinate ->new Box(coordinate));
    }
}
