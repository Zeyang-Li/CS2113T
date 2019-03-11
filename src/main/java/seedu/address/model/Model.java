package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
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
     * Returns the user prefs' address book file path.
     */
    Path getTaskBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setTaskBookFilePath(Path taskBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setTaskBook(ReadOnlyTaskBook taskBook);

    /** Returns the AddressBook */
    ReadOnlyTaskBook getTaskBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasTask(Task task);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteTask(Task target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addTask(Task task);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setTask(Task target, Task editedTask);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoTaskBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoTaskBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoTaskBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoTaskBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitTaskBook();

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     */
    ReadOnlyProperty<Task> selectedTaskProperty();

    /**
     * Returns the selected person in the filtered person list.
     * null if no person is selected.
     */
    Task getSelectedTask();

    /**
     * Sets the selected person in the filtered person list.
     */
    void setSelectedTask(Task task);
}
