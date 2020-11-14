package sudoku.solver.util;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PermutationUtilTest {

    @Test
    public void test() {
        PermutationUtil
                .permutateSorted(Set.of(1, 2, 3), 2)
                .forEach(System.out::println);
    }
}