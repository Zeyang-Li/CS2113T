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
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AccountListChangedEvent;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.export.Export;
import seedu.address.export.ExportManager;
import seedu.address.export.Import;
import seedu.address.export.ImportManager;
import seedu.address.model.account.Account;
import seedu.address.model.account.LoggedInAccount;
import seedu.address.model.account.Username;
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
    private ObservableList<Task> remindList = FXCollections.observableArrayList();

    private final VersionedAccountList versionedAccountList;
    private final FilteredList<Account> filteredAccounts;
    private final LoggedInAccount loggedInAccount;

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
     * Initializes a ModelManager with the given taskBook, userPrefs and accountList.
     */
    public ModelManager(ReadOnlyTaskBook taskBook, ReadOnlyUserPrefs userPrefs, ReadOnlyAccountList accountList) {
        super();
        requireAllNonNull(taskBook, userPrefs, accountList);

        logger.fine("Initializing with task book: " + taskBook + " and user prefs " + userPrefs + " and accounts" + accountList);

        versionedAccountList = new VersionedAccountList(accountList);
        filteredAccounts = new FilteredList<>(versionedAccountList.getAccountList());
        loggedInAccount = new LoggedInAccount();

        versionedTaskBook = new VersionedTaskBook(taskBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTasks = new FilteredList<>(versionedTaskBook.getTaskList());
        filteredTasks.addListener(this::ensureSelectedTaskIsValid);
        filteredDays = new FilteredList<>(versionedTaskBook.getDayList());
        filteredDays.addListener(this::ensureSelectedDayIsValid);
        for (Task task : versionedTaskBook.getTaskList()) {
            remindList.add(task);
        }
    }


    public ModelManager() {
        this(new TaskBook(), new UserPrefs(), new AccountList());
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
    public void sortRemindListByStart() {
        FXCollections.sort(remindList, startComparator);
    }

    @Override
    public void sortRemindListByEnd() {
        FXCollections.sort(remindList, endComparator);

    }

    @Override
    public void reinitializeRemindList() {
        remindList = FXCollections.observableArrayList();
        for (Task task : versionedTaskBook.getTaskList()) {
            remindList.add(task);
        }
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
    public void filterRemindList(String str) {
        Predicate<Task> predicate = task -> meetRequirement(task, str);
        remindList = remindList.filtered(predicate);
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

    //=========== TaskBook =================================================================================

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

    /**
     * Determine the predicate
     */
    private boolean meetRequirement(Task task, String givenCategory) {
        if (task.getCategories().value.equals(givenCategory)) {

            return true;
        } else {

            return false;
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

    //=========== Account List ==============================================================================

    @Override

    public boolean hasAccount(Account account) {
        requireNonNull(account);
        return versionedAccountList.hasAccount(account);
    }

    @Override
    public void addAccount(Account account) {
        versionedAccountList.addAccount(account);
        updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        indicateAccountListChanged();
    }


    @Override
    public void updateAccount(Account target, Account editedAccount) {
        requireAllNonNull(target, editedAccount);

        versionedAccountList.updateAccount(target, editedAccount);
        indicateAccountListChanged();
    }

    @Override
    public void deleteAccount(Account target) {
        versionedAccountList.removeAccount(target);
        indicateAccountListChanged();
    }

    @Override
    public ReadOnlyAccountList getAccountList() {
        return versionedAccountList;
    }


    @Override
    public ObservableList<Account> getFilteredAccountList() {
        return FXCollections.unmodifiableObservableList(filteredAccounts);
    }


    @Override
    public void updateFilteredAccountList(Predicate<Account> predicate) {
        requireNonNull(predicate);
        filteredAccounts.setPredicate(predicate);
    }

	@Override
    public void resetAccountData(ReadOnlyAccountList newData) {
        versionedAccountList.resetData(newData);
        indicateAccountListChanged();
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAccountListChanged() {
        raise(new AccountListChangedEvent(versionedAccountList));
    }

    private void raise(AccountListChangedEvent accountListChangedEvent) {
		// TODO Auto-generated method stub
	}

	@Override
    public boolean getLoginStatus() {
        return loggedInAccount.getLoginStatus();
    }

    @Override
    public String getLoggedInUser() {
        return loggedInAccount.getUsername().toString();
    }

    @Override
    public void setLoggedOutStatus() {
        loggedInAccount.setLoggedOutStatus();
    }

    @Override
    public void setLoggedInUser(Username username) {
        loggedInAccount.setLoggedInUser(username);
    }

}
