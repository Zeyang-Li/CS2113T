package seedu.address.logic.commands;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_TASK_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.task.TaskContainsKeywordsPredicate;
import seedu.address.model.UserPrefs;

public class FindCommandTest {
    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TaskContainsKeywordsPredicate firstPredicate =
                new TaskContainsKeywordsPredicate(Collections.singletonList("first"));
        TaskContainsKeywordsPredicate secondPredicate =
                new TaskContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_TASK_LISTED_OVERVIEW, 0);
        TaskContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        //assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_TASK_LISTED_OVERVIEW, 3);
        TaskContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTaskList(predicate);
        try {
            assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        //assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredTaskList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private TaskContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TaskContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
