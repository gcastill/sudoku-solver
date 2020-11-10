package sudoku.solver.core;

import lombok.Value;

import java.util.Comparator;

@Value
public class Coordinate implements Comparable<Coordinate> {

    public static Comparator<Coordinate> COMPARATOR = Comparator
            .comparing(Coordinate::getY)
            .reversed()
            .thenComparing(Coordinate::getX);

    private final int x;
    private final int y;


    @Override
    public int compareTo(Coordinate o) {
        return COMPARATOR.compare(this, o);
    }
}
