package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CS2101;
import static seedu.address.testutil.TypicalTasks.CS2110;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.day.Day;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.TaskBuilder;

public class TaskBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskBook taskBook = new TaskBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskBook.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        taskBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTaskBook_replacesData() {
        TaskBook newData = getTypicalTaskBook();
        taskBook.resetData(newData);
        assertEquals(newData, taskBook);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same identity fields
        Task editedCS2110 = new TaskBuilder(CS2110).withEndDate(VALID_ENDDATE_CS2101).withTags(VALID_TAG_CS2101)
                .build();
        List<Task> newTasks = Arrays.asList(CS2110, editedCS2110);
        TaskBookStub newData = new TaskBookStub(newTasks);

        thrown.expect(DuplicateTaskException.class);
        taskBook.resetData(newData);
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        taskBook.hasTask(null);
    }

    @Test
    public void hasTask_taskNotInTaskBook_returnsFalse() {
        assertFalse(taskBook.hasTask(CS2110));
    }

    @Test
    public void hasTask_taskInTaskBook_returnsTrue() {
        taskBook.addTask(CS2110);
        assertTrue(taskBook.hasTask(CS2110));
    }

    @Test
    public void hasTask_taskWithSameIdentityFieldsInTaskBook_returnsTrue() {
        taskBook.addTask(CS2110);
        Task editedCS2110 = new TaskBuilder(CS2110).withEndDate(VALID_ENDDATE_CS2101).withTags(VALID_TAG_CS2101)
                .build();
        assertTrue(taskBook.hasTask(editedCS2110));
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        taskBook.getTaskList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        taskBook.addListener(listener);
        taskBook.addTask(CS2110);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        taskBook.addListener(listener);
        taskBook.removeListener(listener);
        taskBook.addTask(CS2110);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyTaskBook whose tasks list can violate interface constraints.
     */
    private static class TaskBookStub implements ReadOnlyTaskBook {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TaskBookStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Day> getDayList() {
            return null;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
