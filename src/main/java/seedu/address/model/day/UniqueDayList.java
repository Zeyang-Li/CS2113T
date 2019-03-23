package seedu.address.model.day;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.day.Day;
import seedu.address.model.day.exceptions.DuplicateDayException;
import seedu.address.model.day.exceptions.DayNotFoundException;

/**
 * A list of days that enforces uniqueness between its elements and does not allow nulls.
 * A day is considered unique by comparing using {@code Task#isSameDay(Day)}. As such, adding and updating of
 * tasks uses Day#isSameDay(Day) for equality so as to ensure that the day being added or updated is
 * unique in terms of identity in the UniqueDayList. However, the removal of a day uses Day#equals(Object) so
 * as to ensure that the day with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Day#isSameDay(Day)
 */
public class UniqueDayList implements Iterable<Day> {

    private final ObservableList<Day> internalList = FXCollections.observableArrayList();
    private final ObservableList<Day> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent day as the given argument.
     */
    public boolean contains(Day toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDay);
    }

    /**
     * Adds a day to the list.
     * The day must not already exist in the list.
     */
    public void add(Day toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDayException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the day {@code target} in the list with {@code editedDay}.
     * {@code target} must exist in the list.
     * The day identity of {@code editedDay} must not be the same as another existing day in the list.
     */
    public void setDay(Day target, Day editedDay) {
        requireAllNonNull(target, editedDay);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DayNotFoundException();
        }

        if (!target.isSameDay(editedDay) && contains(editedDay)) {
            throw new DuplicateDayException();
        }

        internalList.set(index, editedDay);
    }

    /**
     * Replaces the contents of this list with {@code days}.
     * {@code days} must not contain duplicate days.
     */
    public void setDays(List<Day> days) {
        requireAllNonNull(days);
        if (!daysAreUnique(days)) {
            throw new DuplicateDayException();
        }

        internalList.setAll(days);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Day> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Day> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDayList // instanceof handles nulls
                && internalList.equals(((UniqueDayList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code days} contains only unique days.
     */
    private boolean daysAreUnique(List<Day> days) {
        for (int i = 0; i < days.size() - 1; i++) {
            for (int j = i + 1; j < days.size(); j++) {
                if (days.get(i).isSameDay(days.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

