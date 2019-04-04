package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.export.Export;
import seedu.address.export.ExportManager;
import seedu.address.export.Import;
import seedu.address.export.ImportManager;
import seedu.address.model.day.Day;
import seedu.address.model.day.exceptions.DayNotFoundException;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Represents the in-memory model of the tasketch data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTaskBook versionedTaskBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Day> filteredDays;
    private final SimpleObjectProperty<Task> selectedTask = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Day> selectedDay = new SimpleObjectProperty<>();
    private ObservableList<Task> remindList;

    private Comparator<Task> startComparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {

            String sdO1 = o1.getStartDate().toString();
            String sd02 = o2.getStartDate().toString();
            String[] dateInfo1 = sdO1.split("-");
            String[] dateInfo2 = sd02.split("-");
            String finalDate1 = dateInfo1[2] + dateInfo1[1] + dateInfo1[0];
            String finalDate2 = dateInfo2[2] + dateInfo2[1] + dateInfo2[0];

            if (finalDate1.compareTo(finalDate2) != 0) {

                return finalDate1.compareTo(finalDate2);
            } else {

                return o1.getStartTime().toString().compareTo(o2.getStartTime().toString());
            }
        }
    };

    private Comparator<Task> endComparator = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {

            String sdO1 = o1.getEndDate().toString();
            String sd02 = o2.getEndDate().toString();
            String[] dateInfo1 = sdO1.split("-");
            String[] dateInfo2 = sd02.split("-");
            String finalDate1 = dateInfo1[2] + dateInfo1[1] + dateInfo1[0];
            String finalDate2 = dateInfo2[2] + dateInfo2[1] + dateInfo2[0];

            if (finalDate1.compareTo(finalDate2) != 0) {

                return finalDate1.compareTo(finalDate2);
            } else {

                return o1.getStartTime().toString().compareTo(o2.getStartTime().toString());
            }
        }
    };

    /**
     * Initializes a ModelManager with the given taskBook and userPrefs.
     */
    public ModelManager(ReadOnlyTaskBook taskBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(taskBook, userPrefs);

        logger.fine("Initializing with task book: " + taskBook + " and user prefs " + userPrefs);

        versionedTaskBook = new VersionedTaskBook(taskBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTasks = new FilteredList<>(versionedTaskBook.getTaskList());
        filteredTasks.addListener(this::ensureSelectedTaskIsValid);
        filteredDays = new FilteredList<>(versionedTaskBook.getDayList());
        filteredDays.addListener(this::ensureSelectedDayIsValid);
        remindList = versionedTaskBook.getTaskList();
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    /**
     * @return startComparator.
     */
    public Comparator<Task> getStartComparator() {
        return startComparator;
    }

    /**
     * @return endComparator.
     */
    public Comparator<Task> getEndComparator() {
        return endComparator;
    }

    @Override
    public Path getTaskBookFilePath() {
        return userPrefs.getTaskBookFilePath();
    }

    @Override
    public void setTaskBookFilePath(Path taskBookFilePath) {
        requireNonNull(taskBookFilePath);
        userPrefs.setTaskBookFilePath(taskBookFilePath);
    }

    //=========== TaskBook ================================================================================

    @Override
    public void setTaskBook(ReadOnlyTaskBook taskBook) {
        versionedTaskBook.resetData(taskBook);
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return versionedTaskBook;
    }

    private void indicateTaskBookChanged() {
        new TaskBookChangedEvent(versionedTaskBook);
    }
    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return versionedTaskBook.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        versionedTaskBook.removeTask(target);
    }

    @Override
    public void deleteTaskList(List<Task> targets) {
        for (Task target : targets) {
            versionedTaskBook.removeTask(target);
        }
    }

    @Override
    public void sortByStart() {
        versionedTaskBook.sortTaskByDate(startComparator);
    }

    @Override
    public void sortByEnd() {
        versionedTaskBook.sortTaskByDate(endComparator);
    }

    @Override
    public void setRemindList(ObservableList<Task> remindList) {
        this.remindList = remindList;
    }

    @Override
    public ObservableList<Task> getRemindTaskList() {
        return remindList;
    }

    @Override
    public ObservableList<Task> getAllTaskList() {
        return versionedTaskBook.getTaskList();
    }

    @Override
    public void addTask(Task task) {
        versionedTaskBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        updateFilteredDayList(PREDICATE_SHOW_ALL_DAYS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        versionedTaskBook.setTask(target, editedTask);
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedTaskBook}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    //=========== Filtered Day List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Day} backed by the internal list of
     * {@code versionedTaskBook}
     */
    @Override
    public ObservableList<Day> getFilteredDayList() {
        return filteredDays;
    }

    @Override
    public void updateFilteredDayList(Predicate<Day> predicate) {
        requireNonNull(predicate);
        filteredDays.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoTaskBook() {
        return versionedTaskBook.canUndo();
    }

    @Override
    public boolean canRedoTaskBook() {
        return versionedTaskBook.canRedo();
    }

    @Override
    public void undoTaskBook() {
        versionedTaskBook.undo();
    }

    @Override
    public void redoTaskBook() {
        versionedTaskBook.redo();
    }

    @Override
    public void commitTaskBook() {
        versionedTaskBook.commit();
    }

    //=========== Selected task ===========================================================================

    @Override
    public ReadOnlyProperty<Task> selectedTaskProperty() {
        return selectedTask;
    }

    @Override
    public Task getSelectedTask() {
        return selectedTask.getValue();
    }

    @Override
    public void setSelectedTask(Task task) {
        if (task != null && !filteredTasks.contains(task)) {
            throw new TaskNotFoundException();
        }
        selectedTask.setValue(task);
    }


    //=========== Selected day ===========================================================================

    @Override
    public ReadOnlyProperty<Day> selectedDayProperty() {
        return selectedDay;
    }

    @Override
    public void setSelectedDay(Day day) {
        if (day != null && !filteredDays.contains(day)) {
            throw new DayNotFoundException();
        }
        selectedDay.setValue(day);
    }

    @Override
    public void setMonth(String month) {}

    /**
     * Ensures {@code selectedTask} is a valid task in {@code filteredTasks}.
     */
    private void ensureSelectedTaskIsValid(ListChangeListener.Change<? extends Task> change) {
        while (change.next()) {
            if (selectedTask.getValue() == null) {
                // null is always a valid selected task, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedTaskReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedTask.getValue());
            if (wasSelectedTaskReplaced) {
                // Update selectedTask to its new value.
                int index = change.getRemoved().indexOf(selectedTask.getValue());
                selectedTask.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedTaskRemoved = change.getRemoved().stream()
                    .anyMatch(removedTask -> selectedTask.getValue().isSameTask(removedTask));
            if (wasSelectedTaskRemoved) {
                // Select the task that came before it in the list,
                // or clear the selection if there is no such task.
                selectedTask.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }


    //=========== Import/ Export ==============================================================================
    @Override
    public void importTasksFromTaskBook(Path importFilePath) throws IOException, DataConversionException {
        Import importManager = new ImportManager(importFilePath);
        ReadOnlyTaskBook taskBookImported = importManager.readTaskBook().orElseThrow(IOException::new);
        boolean hasChanged = addTasksToTaskBook(taskBookImported);

        if (hasChanged) {
            updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
            indicateTaskBookChanged();
        }
    }
    @Override
    public boolean addTasksToTaskBook(ReadOnlyTaskBook addressBookImported) {
        ObservableList<Task> tasks = addressBookImported.getTaskList();
        AtomicBoolean hasChanged = new AtomicBoolean(false);
        tasks.forEach((task) -> {
            if (!hasTask(task)) {
                hasChanged.set(true);
                versionedTaskBook.addTask(task);
            }
        });
        return hasChanged.get();
    }

    @Override
    public void exportFilteredTaskBook(Path exportFilePath) throws IOException, IllegalValueException {
        Export export = new ExportManager(getFilteredTaskList(), exportFilePath);
        export.saveFilteredTasks();
    }

    /**
     * Ensures {@code selectedDay} is a valid day in {@code filteredDays}.
     */
    private void ensureSelectedDayIsValid(ListChangeListener.Change<? extends Day> change) {
        while (change.next()) {
            if (selectedTask.getValue() == null) {
                // null is always a valid selected day, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedDayReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedDay.getValue());
            if (wasSelectedDayReplaced) {
                // Update selectedDay to its new value.
                int index = change.getRemoved().indexOf(selectedDay.getValue());
                selectedDay.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedDayRemoved = change.getRemoved().stream()
                    .anyMatch(removedDay -> selectedDay.getValue().isSameDay(removedDay));
            if (wasSelectedDayRemoved) {
                // Select the day that came before it in the list,
                // or clear the selection if there is no such task.
                selectedDay.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedTaskBook.equals(other.versionedTaskBook)
                && userPrefs.equals(other.userPrefs)
                && filteredTasks.equals(other.filteredTasks)
                && filteredDays.equals(other.filteredDays)
                && Objects.equals(selectedTask.get(), other.selectedTask.get())
                && Objects.equals(selectedDay.get(), other.selectedDay.get());
    }

}
