package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.day.Day;
import seedu.address.model.day.UniqueDayList;

/**
 * Wraps all data at the daysKeeper level
 * Duplicates are not allowed (by .isSameDay comparison)
 */
public class DaysKeeper implements ReadOnlyDaysKeeper {

    private final UniqueDayList days;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        days = new UniqueDayList();
    }

    public DaysKeeper() {}

    /**
     * Creates an DaysKeeper using the Days in the {@code toBeCopied}
     */
    public DaysKeeper(ReadOnlyDaysKeeper toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the day list with {@code days}.
     * {@code days} must not contain duplicate days.
     */
    public void setDays(List<Day> days) {
        this.days.setDays(days);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code DaysKeeper} with {@code newData}.
     */
    public void resetData(ReadOnlyDaysKeeper newData) {
        requireNonNull(newData);

        setDays(newData.getDayList());
    }

    //// day-level operations

    /**
     * Returns true if a day with the same identity as {@code day} exists in the day book.
     */
    public boolean hasDay(Day day) {
        requireNonNull(day);
        return days.contains(day);
    }

    /**
     * Adds a day to the days keeper.
     * The day must not already exist in the days keeper.
     */
    public void addDay(Day d) {
        days.add(d);
        indicateModified();
    }

    /**
     * Replaces the given day {@code target} in the list with {@code editedDay}.
     * {@code target} must exist in the days keeper.
     * The day identity of {@code editedDay} must not be the same as another existing day in the days keeper.
     */
    public void setDay(Day target, Day editedDay) {
        requireNonNull(editedDay);

        days.setDay(target, editedDay);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the days keeper has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return days.asUnmodifiableObservableList().size() + " days";
    }

    @Override
    public ObservableList<Day> getDayList() {
        return days.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DaysKeeper // instanceof handles nulls
                && days.equals(((DaysKeeper) other).days));
    }

    @Override
    public int hashCode() {
        return days.hashCode();
    }
}
