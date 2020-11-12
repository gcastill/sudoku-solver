package sudoku.solver.core.rule;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HiddenSets {


    public static Set<Set<Integer>> generate(Set<Integer> values, int size) {
        if (size > values.size()) {
            return Collections.emptySet();
        }
        return generate(State
                .builder()
                .selection(new HashSet<>(values))
                .targetSize(size)
                .build())
                .map(State::getResults)
                .collect(Collectors.toSet());

    }


    private static Stream<State> generate(State state) {

        if (state.results.size() == state.getTargetSize()) {
            return Stream.of(state);
        }

        return state
                .getSelection()
                .stream()
                .map(item -> state
                        .toBuilder()
                        .selection(cloneAndRemove(state.getSelection(),
                                item))
                        .result(item)
                        .build())
                .flatMap(HiddenSets::generate);


    }


     public static <T> Set<T> cloneAndRemove(Set<T> set, T item) {
        Set<T> result = new HashSet<>(set);
        result.remove(item);
        return result;
    }

    @Value
    @Builder(toBuilder = true)
    public static class State {
        private final @Singular
        Set<Integer> results;
        private final Set<Integer> selection;
        private final int targetSize;
    }

}
