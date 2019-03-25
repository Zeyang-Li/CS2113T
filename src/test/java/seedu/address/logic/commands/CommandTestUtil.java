package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_CS2113 = "CS2113";
    public static final String VALID_NAME_CS2101 = "CS2101";
    public static final String VALID_STARTDATE_CS2113 = "11-11-11";
    public static final String VALID_STARTDATE_CS2101 = "22-22-22";
    public static final String VALID_STARTTIME_CS2113 = "11.11";
    public static final String VALID_STARTTIME_CS2101 = "22.22";
    public static final String VALID_ENDDATE_CS2113 = "33-33-33";
    public static final String VALID_ENDDATE_CS2101 = "44-44-44";
    public static final String VALID_ENDTIME_CS2113 = "33.33";
    public static final String VALID_ENDTIME_CS2101 = "44.44";
    public static final String VALID_DESCRIPTION_CS2113 = "Do sequence diagram";
    public static final String VALID_DESCRIPTION_CS2101 = "Do user guide";
    public static final String VALID_TAG_CS2113 = "CS2113";
    public static final String VALID_TAG_CS2101 = "CS2101";

    public static final String NAME_DESC_CS2113 = " " + PREFIX_NAME + VALID_NAME_CS2113;
    public static final String NAME_DESC_CS2101 = " " + PREFIX_NAME + VALID_NAME_CS2113;
    public static final String STARTDATE_DESC_CS2113 = " " + PREFIX_STARTDATE + VALID_STARTDATE_CS2113;
    public static final String STARTDATE_DESC_CS2101 = " " + PREFIX_STARTDATE + VALID_STARTDATE_CS2101;
    public static final String STARTTIME_DESC_CS2113 = " " + PREFIX_STARTTIME + VALID_STARTTIME_CS2113;
    public static final String STARTTIME_DESC_CS2101 = " " + PREFIX_STARTTIME + VALID_STARTTIME_CS2101;
    public static final String ENDDATE_DESC_CS2113 = " " + PREFIX_ENDDATE + VALID_ENDDATE_CS2113;
    public static final String ENDDATE_DESC_CS2101 = " " + PREFIX_ENDDATE + VALID_ENDDATE_CS2101;
    public static final String ENDTIME_DESC_CS2113 = " " + PREFIX_ENDTIME + VALID_ENDTIME_CS2113;
    public static final String ENDTIME_DESC_CS2101 = " " + PREFIX_ENDTIME + VALID_ENDTIME_CS2101;
    public static final String DESCRIPTION_DESC_CS2113 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_CS2113;
    public static final String DESCRIPTION_DESC_CS2101 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_CS2101;
    public static final String TAG_DESC_CS2113 = " " + PREFIX_TAG + VALID_TAG_CS2113;
    public static final String TAG_DESC_CS2101 = " " + PREFIX_TAG + VALID_TAG_CS2101;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + ""; // blank not allowed in names
    public static final String INVALID_STARTDATE_DESC = " " + PREFIX_STARTDATE + "a"; // 'a' not allowed in startDates
    public static final String INVALID_STARTTIME_DESC = " " + PREFIX_STARTTIME + "a"; // 'a' not allowed in startTimes
    public static final String INVALID_ENDDATE_DESC = " " + PREFIX_ENDDATE + "a"; // 'a' not allowed in endDates
    public static final String INVALID_ENDTIME_DESC = " " + PREFIX_ENDTIME + "a"; // 'a' not allowed in endTimes
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION; // empty string not allowed for desc
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the task book, filtered task list and selected task in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TaskBook expectedTaskBook = new TaskBook(actualModel.getTaskBook());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());
        Task expectedSelectedTask = actualModel.getSelectedTask();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedTaskBook, actualModel.getTaskBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
            assertEquals(expectedSelectedTask, actualModel.getSelectedTask());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s task book.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTaskList(new TaskContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }

    /**
     * Deletes the first task in {@code model}'s filtered list from {@code model}'s task book.
     */
    public static void deleteFirstTask(Model model) {
        Task firstTask = model.getFilteredTaskList().get(0);
        model.deleteTask(firstTask);
        model.commitTaskBook();
    }

}
