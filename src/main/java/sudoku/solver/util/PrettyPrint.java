package sudoku.solver.util;

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

}
