package sudoku.solver.core;

import java.util.HashMap;
import java.util.Optional;

public class Grid {

    private final HashMap<LineAddress, Line> lines = new HashMap<>();
    private final HashMap<Coordinate, Box> boxes = new HashMap<>();

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
}
