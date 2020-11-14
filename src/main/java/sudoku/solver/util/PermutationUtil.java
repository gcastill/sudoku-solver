package sudoku.solver.util;

import lombok.Builder;
import lombok.Value;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PermutationUtil {

    public static <T> Set<SortedSet<T>> permutateSorted(Set<T> values, int size) {
        if (values.size() < size) {
            return Collections.emptySet();
        }
        PermutationState<T> initial = PermutationState
                .<T>builder()
                .selection(new TreeSet<>(values))
                .chosen(Collections.emptySortedSet())
                .targetSize(size)
                .build();

        return PermutationUtil.generate(initial)
                .map(PermutationState::getChosen)
                .collect(Collectors.toSet());

    }

    private static <T> Stream<PermutationState<T>> generate(PermutationState<T> state) {
        if (state
                .getChosen()
                .size() == state.getTargetSize()) {
            return Stream.of(state);
        }

        return state
                .getSelection()
                .stream()
                .map(selected -> state
                        .toBuilder()
                        .selection(cloneAndRemove(state.getSelection(), selected))
                        .chosen(cloneAndAdd(state.getSelection(), selected))
                        .build())
                .flatMap(PermutationUtil::generate);
        

    }

    public static <T> SortedSet<T> cloneAndRemove(SortedSet<T> items, T item) {
        TreeSet<T> result = new TreeSet<>(items);
        result.remove(item);
        return result;
    }

    public static <T> SortedSet<T> cloneAndAdd(SortedSet<T> items, T item) {
        TreeSet<T> result = new TreeSet<>(items);
        result.add(item);
        return result;
    }

    @Value
    @Builder(toBuilder = true)
    private static final class PermutationState<T> {
        private final SortedSet<T> chosen;
        private final SortedSet<T> selection;
        private final int targetSize;
    }
}
