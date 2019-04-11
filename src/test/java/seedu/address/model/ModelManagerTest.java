package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_CS2101;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.address.testutil.TypicalTasks.CS2100;
import static seedu.address.testutil.TypicalTasks.CS2110;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.task.TaskContainsKeywordsPredicate;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBookBuilder;
import seedu.address.testutil.TaskBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new TaskBook(), new TaskBook(modelManager.getTaskBook()));
        assertEquals(null, modelManager.getSelectedTask());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTaskBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setTaskBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setTaskBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setTaskBookFilePath(null);
    }

    @Test
    public void setTaskBookFilePath_validPath_setsTaskBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setTaskBookFilePath(path);
        assertEquals(path, modelManager.getTaskBookFilePath());
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasTask(null);
    }

    @Test
    public void hasTask_taskNotInTaskBook_returnsFalse() {
        assertFalse(modelManager.hasTask(CS2110));
    }

    @Test
    public void hasTask_taskInTaskBook_returnsTrue() {
        modelManager.addTask(CS2110);
        assertTrue(modelManager.hasTask(CS2110));
    }

    @Test
    public void deleteTask_taskIsSelectedAndFirstTaskInFilteredTaskList_selectionCleared() {
        modelManager.addTask(CS2110);
        modelManager.setSelectedTask(CS2110);
        modelManager.deleteTask(CS2110);
        assertEquals(null, modelManager.getSelectedTask());
    }

    @Test
    public void deleteTask_taskIsSelectedAndSecondTaskInFilteredTaskList_firstTaskSelected() {
        modelManager.addTask(CS2110);
        modelManager.addTask(CS2100);
        assertEquals(Arrays.asList(CS2110, CS2100), modelManager.getFilteredTaskList());
        modelManager.setSelectedTask(CS2100);
        modelManager.deleteTask(CS2100);
        assertEquals(CS2110, modelManager.getSelectedTask());
    }

    @Test
    public void setTask_taskIsSelected_selectedTaskUpdated() {
        modelManager.addTask(CS2110);
        modelManager.setSelectedTask(CS2110);
        Task updatedCS2110 = new TaskBuilder(CS2110).withEndDate(VALID_ENDDATE_CS2101).build();
        modelManager.setTask(CS2110, updatedCS2110);
        assertEquals(updatedCS2110, modelManager.getSelectedTask());
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredTaskList().remove(0);
    }

    @Test
    public void setSelectedTask_taskNotInFilteredTaskList_throwsTaskNotFoundException() {
        thrown.expect(TaskNotFoundException.class);
        modelManager.setSelectedTask(CS2110);
    }

    @Test
    public void setSelectedTask_taskInFilteredTaskList_setsSelectedTask() {
        modelManager.addTask(CS2110);
        assertEquals(Collections.singletonList(CS2110), modelManager.getFilteredTaskList());
        modelManager.setSelectedTask(CS2110);
        assertEquals(CS2110, modelManager.getSelectedTask());
    }

    @Test
    public void equals() {
        TaskBook taskBook = new TaskBookBuilder().withTask(CS2110).withTask(CS2100).build();
        TaskBook differentTaskBook = new TaskBook();
        UserPrefs userPrefs = new UserPrefs();
        AccountList accountList = new AccountList();

        // same values -> returns true
        modelManager = new ModelManager(taskBook, userPrefs, accountList);
        ModelManager modelManagerCopy = new ModelManager(taskBook, userPrefs, accountList);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different taskBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTaskBook, userPrefs, accountList)));

        // different filteredList -> returns false
        String[] keywords = CS2110.getName().fullName.split("\\s+");
        modelManager.updateFilteredTaskList(new TaskContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(taskBook, userPrefs, accountList)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTaskBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(taskBook, differentUserPrefs, accountList)));
    }
}
