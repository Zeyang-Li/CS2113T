package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskContainsKeywordsPredicate;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_CS2113 = "CS2113";
    public static final String VALID_NAME_CS2101 = "CS2101";
    public static final String VALID_STARTDATE_CS2113 = "05-05-05";
    public static final String VALID_STARTDATE_CS2101 = "07-07-07";
    public static final String VALID_STARTTIME_CS2113 = "05.00";
    public static final String VALID_STARTTIME_CS2101 = "07.00";
    public static final String VALID_ENDDATE_CS2113 = "06-06-06";
    public static final String VALID_ENDDATE_CS2101 = "08-08-08";
    public static final String VALID_ENDTIME_CS2113 = "06.00";
    public static final String VALID_ENDTIME_CS2101 = "08.00";
    public static final String VALID_DESCRIPTION_CS2113 = "Do sequence diagram";
    public static final String VALID_DESCRIPTION_CS2101 = "Do user guide";
    public static final String VALID_CATEGORY_CS2113 = "a";
    public static final String VALID_CATEGORY_CS2101 = "a";
    public static final String VALID_TAG_CS2113 = "CS2113";
    public static final String VALID_TAG_CS2101 = "CS2101";

    public static final String NAME_DESC_CS2113 = " " + PREFIX_NAME + VALID_NAME_CS2113;
    public static final String NAME_DESC_CS2101 = " " + PREFIX_NAME + VALID_NAME_CS2101;
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
    public static final String CATEGORY_DESC_CS2113 = " " + PREFIX_CATEGORY + VALID_CATEGORY_CS2113;
    public static final String CATEGORY_DESC_CS2101 = " " + PREFIX_CATEGORY + VALID_CATEGORY_CS2101;
    public static final String TAG_DESC_CS2113 = " " + PREFIX_TAG + VALID_TAG_CS2113;
    public static final String TAG_DESC_CS2101 = " " + PREFIX_TAG + VALID_TAG_CS2101;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " "; // blank not allowed in names
    public static final String INVALID_STARTDATE_DESC = " " + PREFIX_STARTDATE + "a"; // arbitary not allowed startDates
    public static final String INVALID_STARTTIME_DESC = " " + PREFIX_STARTTIME + "a"; // arbitary not allowed startTimes
    public static final String INVALID_ENDDATE_DESC = " " + PREFIX_ENDDATE + "a"; // arbitary not allowed in endDates
    public static final String INVALID_ENDTIME_DESC = " " + PREFIX_ENDTIME + "a"; // arbitary not allowed in endTimes
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION; // empty str not allowed for desc
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY + "b"; // arbitary str not allowed in cat
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditTaskDescriptorBuilder DESC_CS2113;
    public static final EditTaskDescriptorBuilder DESC_CS2101;

    static {
        DESC_CS2113 = new EditTaskDescriptorBuilder().withName(VALID_NAME_CS2113)
                .withStartDate(VALID_STARTDATE_CS2113).withStartTime(VALID_STARTTIME_CS2113)
                .withEndDate(VALID_ENDDATE_CS2113).withEndTime(VALID_ENDTIME_CS2113)
                .withCategories(VALID_CATEGORY_CS2113).withDescription(VALID_DESCRIPTION_CS2113)
                .withTags(VALID_TAG_CS2113);
        DESC_CS2101 = new EditTaskDescriptorBuilder().withName(VALID_NAME_CS2101)
                .withStartDate(VALID_STARTDATE_CS2101).withStartTime(VALID_STARTTIME_CS2101)
                .withEndDate(VALID_ENDDATE_CS2101).withEndTime(VALID_ENDTIME_CS2101)
                .withCategories(VALID_CATEGORY_CS2101).withDescription(VALID_DESCRIPTION_CS2101)
                .withTags(VALID_TAG_CS2101);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     * @throws DataConversionException
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel)
                    throws IOException, IllegalValueException, DataConversionException {
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
     * @throws DataConversionException
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel)
                    throws IOException, IllegalValueException, DataConversionException {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the task book, filtered task list and selected task in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     * @throws DataConversionException
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) throws DataConversionException {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TaskBook expectedTaskBook = new TaskBook(actualModel.getTaskBook());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());
        Task expectedSelectedTask = actualModel.getSelectedTask();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException | IllegalValueException | IOException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedTaskBook, actualModel.getTaskBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
            assertEquals(expectedSelectedTask, actualModel.getSelectedTask());
            assertEquals(expectedCommandHistory, actualCommandHistory);
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
