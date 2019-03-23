package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.day.Day;

/**
 * Unmodifiable view of a days keeper
 */
public interface ReadOnlyDaysKeeper extends Observable {

    /**
     * Returns an unmodifiable view of the days list.
     * This list will not contain any duplicate days.
     */
    ObservableList<Day> getDayList();

}
