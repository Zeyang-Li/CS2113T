package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.day.Day;
import seedu.address.model.task.Task;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Task> PREDICATE_MATCHING_NO_TASKS = unused -> false;
    private static final Predicate<Day> PREDICATE_MATCHING_NO_DAYS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Task> toDisplay) {
        Optional<Predicate<Task>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredTaskList(predicate.orElse(PREDICATE_MATCHING_NO_TASKS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Task... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredDayList(Model model, List<Day> toDisplay) {
        Optional<Predicate<Day>> predicate =
                toDisplay.stream().map(ModelHelper::getDayPredicateMatching).reduce(Predicate::or);
        model.updateFilteredDayList(predicate.orElse(PREDICATE_MATCHING_NO_DAYS));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Task} equals to {@code other}.
     */
    private static Predicate<Task> getPredicateMatching(Task other) {
        return task -> task.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Day} equals to {@code other}.
     */
    private static Predicate<Day> getDayPredicateMatching(Day other) {
        return day -> day.equals(other);
    }
}
