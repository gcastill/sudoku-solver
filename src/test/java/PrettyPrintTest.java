import org.junit.jupiter.api.Test;
import sudoku.solver.core.Grid;
import sudoku.solver.util.PrettyPrint;

class PrettyPrintTest {


    @Test
    public void test() {
        Grid g = new Grid();
        g.setValue(0,8,9);
        g.setValue(3,8,5);
        g.setValue(4,8,1);
        g.setValue(5,8,4);
        g.setValue(8,8,6);

        g.setValue(1,7,3);
        g.setValue(2,7,1);
        g.setValue(4,7,7);
        g.setValue(6,7,8);
        g.setValue(8,7,5);

        g.setValue(0,6,6);
        g.setValue(5,6,2);

        g.setValue(0,5,8);
        g.setValue(2,5,3);
        g.setValue(5,5,1);

        g.setValue(2,4,2);
        g.setValue(3,4,3);
        g.setValue(4,4,4);
        g.setValue(5,4,6);
        g.setValue(6,4,9);

        g.setValue(3,3,9);
        g.setValue(6,3,1);
        g.setValue(8,3,3);

        g.setValue(3,2,4);
        g.setValue(8,2,1);

        g.setValue(0,1,7);
        g.setValue(2,1,8);
        g.setValue(4,1,9);
        g.setValue(6,1,4);
        g.setValue(7,1,6);

        g.setValue(0,0,4);
        g.setValue(3,0,2);
        g.setValue(4,0,8);
        g.setValue(5,0,5);
        g.setValue(8,0,7);



        System.out.println(PrettyPrint.toString(g));

    }
}