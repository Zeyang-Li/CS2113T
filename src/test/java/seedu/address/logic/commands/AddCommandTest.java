package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TaskBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = new AddCommand(validTask).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTask), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        Task validTask = new TaskBuilder().build();
        AddCommand addCommand = new AddCommand(validTask);
        ModelStub modelStub = new ModelStubWithTask(validTask);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Task cs2113 = new TaskBuilder().withName("CS2113").build();
        Task cs2101 = new TaskBuilder().withName("CS2101").build();
        AddCommand addCs2113Command = new AddCommand(cs2113);
        AddCommand addCs2101Command = new AddCommand(cs2101);

        // same object -> returns true
        assertTrue(addCs2113Command.equals(addCs2113Command));

        // same values -> returns true
        AddCommand addCs2113CommandCopy = new AddCommand(cs2113);
        assertTrue(addCs2113Command.equals(addCs2113CommandCopy));

        // different types -> returns false
        assertFalse(addCs2113Command.equals(1));

        // null -> returns false
        assertFalse(addCs2113Command.equals(null));

        // different task -> returns false
        assertFalse(addCs2113Command.equals(addCs2101Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTaskBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTaskBookFilePath(Path taskBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTaskBook(ReadOnlyTaskBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTaskBook getTaskBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTask(Task target, Task editedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoTaskBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoTaskBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoTaskBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoTaskBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTaskBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Task> selectedTaskProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Task getSelectedTask() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single task.
     */
    private class ModelStubWithTask extends ModelStub {
        private final Task task;

        ModelStubWithTask(Task task) {
            requireNonNull(task);
            this.task = task;
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return this.task.isSameTask(task);
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(task::isSameTask);
        }

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public void commitTaskBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyTaskBook getTaskBook() {
            return new TaskBook();
        }
    }

}
