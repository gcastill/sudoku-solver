package sudoku.solver.core.rule;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HiddenSetsTest {

    @Test
    public void testGenerateSets() {
        HiddenSets
                .generate(Set.of(1, 2, 3), 2)
                .forEach(System.out::println);
    }
}