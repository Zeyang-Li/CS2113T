package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalTasks.CS2100;
import static seedu.address.testutil.TypicalTasks.CS2110;
import static seedu.address.testutil.TypicalTasks.CS2113;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TaskBookBuilder;

public class VersionedTaskBookTest {

    private final ReadOnlyTaskBook taskBookWithCS2110 = new TaskBookBuilder().withTask(CS2110).build();
    private final ReadOnlyTaskBook taskBookWithCS2100 = new TaskBookBuilder().withTask(CS2100).build();
    private final ReadOnlyTaskBook taskBookWithCS2113 = new TaskBookBuilder().withTask(CS2113).build();
    private final ReadOnlyTaskBook emptyTaskBook = new TaskBookBuilder().build();

    @Test
    public void commit_singleTaskBook_noStatesRemovedCurrentStateSaved() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(emptyTaskBook);

        versionedTaskBook.commit();
        assertTaskBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyTaskBook),
                emptyTaskBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTaskBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);

        versionedTaskBook.commit();
        assertTaskBookListStatus(versionedTaskBook,
                Arrays.asList(emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100),
                taskBookWithCS2100,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTaskBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        versionedTaskBook.commit();
        assertTaskBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyTaskBook),
                emptyTaskBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleTaskBookPointerAtEndOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);

        assertTrue(versionedTaskBook.canUndo());
    }

    @Test
    public void canUndo_multipleTaskBookPointerAtStartOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        assertTrue(versionedTaskBook.canUndo());
    }

    @Test
    public void canUndo_singleTaskBook_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(emptyTaskBook);

        assertFalse(versionedTaskBook.canUndo());
    }

    @Test
    public void canUndo_multipleTaskBookPointerAtStartOfStateList_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        assertFalse(versionedTaskBook.canUndo());
    }

    @Test
    public void canRedo_multipleTaskBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        assertTrue(versionedTaskBook.canRedo());
    }

    @Test
    public void canRedo_multipleTaskBookPointerAtStartOfStateList_returnsTrue() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        assertTrue(versionedTaskBook.canRedo());
    }

    @Test
    public void canRedo_singleTaskBook_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(emptyTaskBook);

        assertFalse(versionedTaskBook.canRedo());
    }

    @Test
    public void canRedo_multipleTaskBookPointerAtEndOfStateList_returnsFalse() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);

        assertFalse(versionedTaskBook.canRedo());
    }

    @Test
    public void undo_multipleTaskBookPointerAtEndOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);

        versionedTaskBook.undo();
        assertTaskBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyTaskBook),
                taskBookWithCS2110,
                Collections.singletonList(taskBookWithCS2100));
    }

    @Test
    public void undo_multipleTaskBookPointerNotAtStartOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        versionedTaskBook.undo();
        assertTaskBookListStatus(versionedTaskBook,
                Collections.emptyList(),
                emptyTaskBook,
                Arrays.asList(taskBookWithCS2110, taskBookWithCS2100));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(emptyTaskBook);

        assertThrows(VersionedTaskBook.NoUndoableStateException.class, versionedTaskBook::undo);
    }

    @Test
    public void undo_multipleTaskBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        assertThrows(VersionedTaskBook.NoUndoableStateException.class, versionedTaskBook::undo);
    }

    @Test
    public void redo_multipleTaskBookPointerNotAtEndOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);

        versionedTaskBook.redo();
        assertTaskBookListStatus(versionedTaskBook,
                Arrays.asList(emptyTaskBook, taskBookWithCS2110),
                taskBookWithCS2100,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleTaskBookPointerAtStartOfStateList_success() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 2);

        versionedTaskBook.redo();
        assertTaskBookListStatus(versionedTaskBook,
                Collections.singletonList(emptyTaskBook),
                taskBookWithCS2110,
                Collections.singletonList(taskBookWithCS2100));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedTaskBook versionedAddressBook = prepareTaskBookList(emptyTaskBook);

        assertThrows(VersionedTaskBook.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void redo_multipleTaskBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(
                emptyTaskBook, taskBookWithCS2110, taskBookWithCS2100);

        assertThrows(VersionedTaskBook.NoRedoableStateException.class, versionedTaskBook::redo);
    }

    @Test
    public void equals() {
        VersionedTaskBook versionedTaskBook = prepareTaskBookList(taskBookWithCS2110, taskBookWithCS2100);

        // same values -> returns true
        VersionedTaskBook copy = prepareTaskBookList(taskBookWithCS2110, taskBookWithCS2100);
        assertTrue(versionedTaskBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedTaskBook.equals(versionedTaskBook));

        // null -> returns false
        assertFalse(versionedTaskBook.equals(null));

        // different types -> returns false
        assertFalse(versionedTaskBook.equals(1));

        // different state list -> returns false
        VersionedTaskBook differentTaskBookList = prepareTaskBookList(taskBookWithCS2100, taskBookWithCS2113);
        assertFalse(versionedTaskBook.equals(differentTaskBookList));

        // different current pointer index -> returns false
        VersionedTaskBook differentCurrentStatePointer = prepareTaskBookList(
                taskBookWithCS2110, taskBookWithCS2100);
        shiftCurrentStatePointerLeftwards(versionedTaskBook, 1);
        assertFalse(versionedTaskBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedTaskBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedTaskBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedTaskBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertTaskBookListStatus(VersionedTaskBook versionedTaskBook,
                                             List<ReadOnlyTaskBook> expectedStatesBeforePointer,
                                             ReadOnlyTaskBook expectedCurrentState,
                                             List<ReadOnlyTaskBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new TaskBook(versionedTaskBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedTaskBook.canUndo()) {
            versionedTaskBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTaskBook readOnlyTaskBook : expectedStatesBeforePointer) {
            assertEquals(readOnlyTaskBook, new TaskBook(versionedTaskBook));
            versionedTaskBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTaskBook expectedTaskBook : expectedStatesAfterPointer) {
            versionedTaskBook.redo();
            assertEquals(expectedTaskBook, new TaskBook(versionedTaskBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedTaskBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedTaskBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedTaskBook} with the {@code taskBookStates} added into it, and the
     * {@code VersionedTaskBook#currentStatePointer} at the end of list.
     */
    private VersionedTaskBook prepareTaskBookList(ReadOnlyTaskBook... taskBookStates) {
        assertFalse(taskBookStates.length == 0);

        VersionedTaskBook versionedTaskBook = new VersionedTaskBook(taskBookStates[0]);
        for (int i = 1; i < taskBookStates.length; i++) {
            versionedTaskBook.resetData(taskBookStates[i]);
            versionedTaskBook.commit();
        }

        return versionedTaskBook;
    }

    /**
     * Shifts the {@code versionedTaskBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTaskBook versionedTaskBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedTaskBook.undo();
        }
    }
}
