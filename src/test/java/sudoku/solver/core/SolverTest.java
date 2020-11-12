package sudoku.solver.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.solver.util.CsvLoader;
import sudoku.solver.util.PrettyPrint;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class SolverTest {

    private Grid g;


    @BeforeEach
    void setUp() {
        g = new Grid();
        g.setValue(0, 8, 9);
        g.setValue(3, 8, 5);
        g.setValue(4, 8, 1);
        g.setValue(5, 8, 4);
        g.setValue(8, 8, 6);

        g.setValue(1, 7, 3);
        g.setValue(2, 7, 1);
        g.setValue(4, 7, 7);
        g.setValue(6, 7, 8);
        g.setValue(8, 7, 5);

        g.setValue(0, 6, 6);
        g.setValue(5, 6, 2);

        g.setValue(0, 5, 8);
        g.setValue(2, 5, 3);
        g.setValue(5, 5, 1);

        g.setValue(2, 4, 2);
        g.setValue(3, 4, 3);
        g.setValue(4, 4, 4);
        g.setValue(5, 4, 6);
        g.setValue(6, 4, 9);

        g.setValue(3, 3, 9);
        g.setValue(6, 3, 1);
        g.setValue(8, 3, 3);

        g.setValue(3, 2, 4);
        g.setValue(8, 2, 1);

        g.setValue(0, 1, 7);
        g.setValue(2, 1, 8);
        g.setValue(4, 1, 9);
        g.setValue(6, 1, 4);
        g.setValue(7, 1, 6);

        g.setValue(0, 0, 4);
        g.setValue(3, 0, 2);
        g.setValue(4, 0, 8);
        g.setValue(5, 0, 5);
        g.setValue(8, 0, 7);

    }

    @Test
    void calculateOptions() {
        g
                .cellStream()
                .
                        filter(cell -> Objects.isNull(cell.getValue()))
                .forEach(cell ->
                        System.out.printf("%s, options=%s\n", PrettyPrint.toString(cell.getCoordinate()), Solver.calculateOptions(cell,
                                g))

                );
    }


    @Test
    void testSolve() {

    }

    @Test
    void testSolve20191307Hard() {
        Grid g = CsvLoader.load("csv/20191307-hard.csv");
        testSolve(g);
    }

    @Test
    void testSolve20191507Evil() {
        Grid g = CsvLoader.load("csv/20191507-evil.csv");
        testSolve(g);
    }


    public void testSolve(Grid g) {

        List<Iteration> iterations = Solver.solve(g);
        for (Iteration i : iterations) {
            System.out.println(PrettyPrint.toString(i.getGrid()));
            i
                    .getCoordinateOptions()
                    .entrySet()
                    .stream()
//                    .filter(e -> e
//                            .getValue()
//                            .size() == 1)
                    .forEach(e -> System.out.printf("%s, options=%s\n", PrettyPrint.toString(e
                            .getKey()), e.getValue()));
            System.out.println();


            i
                    .getBoxOptions()
                    .entrySet()
                    .forEach(e ->
                            {
                                String header =
                                String.format("Box%s", PrettyPrint.toString(e.getKey()));
                                e
                                        .getValue()
                                        .getOptions()
                                        .entrySet()
                                        .stream()
//                                        .filter(entry -> entry
//                                                .getValue()
//                                                .size() == 1)
                                        .forEach(entry -> System.out.printf("%s, value=%s, options=%s\n",
                                                header,
                                                entry.getKey(),
                                                entry
                                                        .getValue()
                                                        .stream()
                                                        .map(PrettyPrint::toString)
                                                        .collect(Collectors.toList())));
                            }
                    );
            System.out.println();

            i
                    .getLineOptions()
                    .entrySet()
                    .forEach(e ->
                            {
                                String header =
                                String.format("Line(%s,%s)", e
                                        .getKey()
                                        .getOrientation(), e
                                        .getKey()
                                        .getIndex());
                                e
                                        .getValue()
                                        .getOptions()
                                        .entrySet()
                                        .stream()
//                                        .filter(entry -> entry
//                                                .getValue()
//                                                .size() == 1)
                                        .forEach(entry -> System.out.printf("%s, value=%s, options=%s\n",
                                                header,
                                                entry.getKey(),
                                                entry
                                                        .getValue()
                                                        .stream()
                                                        .map(PrettyPrint::toString)
                                                        .collect(Collectors.toList())));
                            }
                    );
            System.out.println();


        }
        System.out.printf("finished after %s iterations, isComplete= %s\n", iterations.size(),
                iterations
                        .get(iterations.size() - 1)
                        .isComplete());
    }
}