package sudoku.solver.util;

import sudoku.solver.core.Coordinate;
import sudoku.solver.core.Grid;

import java.util.Optional;

public class PrettyPrint {


    public static String toString(Grid grid) {

        StringBuilder sb = new StringBuilder();
        grid
                .cellStream()
                .forEach(cell -> {
                    if (cell
                            .getCoordinate()
                            .equals(new Coordinate(0, 5)) || cell
                            .getCoordinate()
                            .equals(new Coordinate(0
                                    , 2))) {
                        sb.append("------+-----+------\n");
                    }

                    if (cell
                            .getCoordinate()
                            .getX() == 0) {
                        sb.append("|");

                    }
                    sb.append(Optional
                            .ofNullable(cell.getValue())
                            .map(String::valueOf)
                            .orElse(" "));
                    sb.append("|");
                    if (cell
                            .getCoordinate()
                            .getX() == 8) {
                        sb.append("\n");
                    }


                })
        ;
        return sb.toString();
    }

    public static String toString(Coordinate coordinate) {
        return "(" + coordinate.getX() + "," + coordinate.getY() + ")";
    }

}
