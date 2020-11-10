package sudoku.solver.core;

import lombok.Value;

import java.util.Comparator;

@Value
public class LineAddress implements Comparable<LineAddress> {
    public static final Comparator<LineAddress> COMPARATOR =
            Comparator
                    .comparing(LineAddress::getOrientation, Comparator.comparing(Orientation::ordinal))
                    .thenComparing(LineAddress::getIndex);
    private final int index;
    private final Orientation orientation;

    @Override
    public int compareTo(LineAddress o) {
        return COMPARATOR.compare(this, o);
    }
}