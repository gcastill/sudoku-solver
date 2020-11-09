import org.junit.jupiter.api.Test;
import sudoku.solver.core.Grid;
import sudoku.solver.util.PrettyPrint;

class PrettyPrintTest {


    @Test
    public void test() {
        Grid g = new Grid();
        g
                .getCell(0, 0)
                .setValue(5);
        g
                .getCell(3, 3)
                .setValue(2);
        System.out.println(PrettyPrint.toString(g));

    }
}