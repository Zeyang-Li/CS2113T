package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' task book file path.
     */
    Path getTaskBookFilePath();

    /**
     * Sets the user prefs' task book file path.
     */
    void setTaskBookFilePath(Path taskBookFilePath);

    /**
     * Replaces task book data with the data in {@code taskBook}.
     */
    void setTaskBook(ReadOnlyTaskBook taskBook);

    /** Returns the TaskBook */
    ReadOnlyTaskBook getTaskBook();

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task book.
     */
    boolean hasTask(Task task);

    /**
     * Deletes the given task.
     * The task must exist in the task book.
     */
    void deleteTask(Task target);

    void deleteTaskList(List<Task> targets);

    /**
     * Sort the task list
     */
    void sortByStart();

    void sortByEnd();

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addTask(Task task);

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the task book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the task book.
     */
    void setTask(Task target, Task editedTask);

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Returns true if the model has previous task book states to restore.
     */
    boolean canUndoTaskBook();

    /**
     * Returns true if the model has undone task book states to restore.
     */
    boolean canRedoTaskBook();

    /**
     * Restores the model's task book to its previous state.
     */
    void undoTaskBook();

    /**
     * Restores the model's task book to its previously undone state.
     */
    void redoTaskBook();

    /**
     * Saves the current task book state for undo/redo.
     */
    void commitTaskBook();

    /**
     * Selected task in the filtered task list.
     * null if no task is selected.
     */
    ReadOnlyProperty<Task> selectedTaskProperty();

    /**
     * Returns the selected task in the filtered task list.
     * null if no task is selected.
     */
    Task getSelectedTask();

    /**
     * Sets the selected task in the filtered task list.
     */
    void setSelectedTask(Task task);

    /**
     * Set the current month in GUI.
     */
    void setMonth(String month);

    //=========== Import/ Export ==============================================================================
    /**
     * Imports the persons from a xml at {@code importFilePath}.
     */
    void importTasksFromTaskBook(Path importFilePath) throws IOException, DataConversionException;

    /**
     * Adds all the persons in {@code addressBookImported} to the current address book.
     * @return hasChanged is true if the addressBook is modified, returns false otherwise.
     */
    boolean addTasksToTaskBook(ReadOnlyTaskBook addressBookToImported);

    /**
     * Exports the current filtered person list to a xml file at {@code exportFilePath}.
     */
    void exportFilteredTaskBook(Path exportFilePath) throws IOException, IllegalValueException;

    /**
     * Exports the current address book state to a .csv file.
     */
    void exportTaskBook() throws IOException;

    /**
     * Exports the given person to a .csv file.
     */
    void exportTask(Task task) throws IOException;

}
