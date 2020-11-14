package sudoku.solver.core;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grid {

    private final Map<Coordinate, Cell> cells;
    private final Map<LineAddress, Line> lines;
    private final Map<Coordinate, Box> boxes;


    public Grid() {


        this.lines = IntStream
                .range(0, 9)
                .mapToObj(index -> Stream
                        .of(Orientation.values())
                        .map(orientation -> new LineAddress(index
                                , orientation))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toUnmodifiableMap(Function.identity(), Line::new));

        this.boxes = IntStream
                .range(0, 3)
                .mapToObj(x -> IntStream
                        .range(0, 3)
                        .mapToObj(y -> new Coordinate(x, y))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toUnmodifiableMap(Function.identity(), Box::new));

        this.cells = Collections.unmodifiableMap(new TreeMap<>(IntStream
                .range(0, 9)
                .mapToObj(x -> IntStream
                        .range(0, 9)
                        .mapToObj(y -> new Coordinate(x, y))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .map(Cell::new)
                .peek(cell -> getVerticalLine(cell).put(cell
                        .getCoordinate()
                        .getY(), cell))
                .peek(cell -> getHorizontalLine(cell).put(cell
                        .getCoordinate()
                        .getX(), cell))
                .peek(cell -> getBox(cell).put(cell))
                .collect(Collectors.toMap(Cell::getCoordinate, Function.identity()))));


    }

    public Box getBox(Cell cell) {
        Coordinate coordinate = cell.getCoordinate();
        int boxX = (coordinate.getX() / 3);
        int boxY = (coordinate.getY() / 3);
        return boxes.get(new Coordinate(boxX, boxY));
    }

    public Line getHorizontalLine(Cell cell) {
        return getHorizontalLine(cell.getCoordinate());
    }

    public Line getHorizontalLine(Coordinate coordinate) {
        return getLine(coordinate,Orientation.HORIZONTAL);
    }

    public Line getLine(Coordinate coordinate, Orientation orientation) {
        return lines.get(orientation.lineAddressFor(coordinate));
    }

    public Line getVerticalLine(Cell cell) {
        return getVerticalLine(cell.getCoordinate());
    }

    public Line getVerticalLine(Coordinate coordinate) {
        return getLine(coordinate,Orientation.VERTICAL);
    }

    public Optional<Line> getNextLine(byte index, Orientation orientation) {
        return getNextLine(new LineAddress(index, orientation));
    }

    public Cell getCell(Coordinate coordinate) {
        return cells.get(coordinate);
    }

    public Cell getCell(int x, int y) {
        return getCell(new Coordinate(x, y));
    }

    public void setValue(int x, int y, int value) {
        getCell(x, y).setValue(value);
    }

    public void setValue(Coordinate coordinate, int value) {
        getCell(coordinate).setValue(value);
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

    public void copyTo(Grid dest) {
        cells
                .values()
                .forEach(src -> dest.cells
                        .get(src.getCoordinate())
                        .setValue(src.getValue()));

    }

    public Grid clone() {
        Grid copy = new Grid();
        copyTo(copy);
        return copy;
    }


    public Stream<Cell> cellStream() {
        return cells
                .values()
                .stream();


    }

    public Stream<Box> boxStream() {
        return boxes
                .values()
                .stream();
    }

    public Stream<Line> lineStream() {
        return lines
                .values()
                .stream();
    }


    public boolean isComplete() {
        return cellStream()
                .allMatch(Cell::isComplete);

    }
    public boolean isValid() {
        return lineStream().allMatch(House::isValid) && boxStream().allMatch(House::isValid);
    }

}