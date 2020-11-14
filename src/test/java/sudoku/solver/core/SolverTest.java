package sudoku.solver.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.solver.util.CsvLoader;
import sudoku.solver.util.PrettyPrint;
import sudoku.solver.util.HtmlWriter;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

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
    void testSolve20190713Hard() {
        Grid g = CsvLoader.load("csv/20190713-hard.csv");
        testSolve(g);
    }

    @Test
    void testSolve20190715Evil() {
        Grid g = CsvLoader.load("csv/20190715-evil.csv");
        testSolve(g);
    }

    @Test
    void testSolve20191016Hard() {
        Grid g = CsvLoader.load("csv/20191026-hard.csv");
        testSolve(g);
    }

    @Test
    void testSolve20191028Evil() {
        Grid g = CsvLoader.load("csv/20191028-evil.csv");
        testSolve(g);
    }


    @Test
    void testSolve20190812Evil() {
        Grid g = CsvLoader.load("csv/20190812-evil.csv");
        testSolve(g);
    }


    public void testSolve(Grid g) {
        try {
            List<Iteration> iterations = Solver.solve(g);
            for (Iteration i : iterations) {

                generateHtml(i, iterations.size()-1);
                Assertions.assertTrue(i.isValid());
            }
            Iteration last = iterations
                    .get(iterations.size() - 1);
            System.out.printf("finished after %s iterations, isComplete= %s\n", iterations.size(),last.isComplete());
            Assertions.assertTrue(last.isComplete());
        } catch (Exception e) {
            System.out.println(PrettyPrint.toString(g));
            throw e;

        }
    }

    private void generateHtml(Iteration i, int maxIteration) {
        String html = HtmlWriter.processHMTLTemplate("iteration", i,maxIteration);
        try {
            Files.writeString(new File("build/iteration-" + i.getRound() + ".html").toPath(), html);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }

    }
}