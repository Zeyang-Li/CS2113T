package seedu.address.logic.commands;

import static seedu.address.logic.commands.ClearCommand.MESSAGE_INVALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AccountList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAccountList;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;
import seedu.address.model.task.Task;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private String[] argument;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTaskBook(), new UserPrefs(), new AccountList());
        expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), model.getAccountList());
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        expectedModel.setLoggedInUser(admin);
        this.argument = new String[] {""};
    }

    private ReadOnlyAccountList getTypicalAccountList() {
        return null;
    }

    @Test
    public void execute_listAllTask_success()
            throws IOException, IllegalValueException, DataConversionException {
        assertCommandSuccess(new ListCommand(argument), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS1, expectedModel);
    }

    /*@Test
    public void execute_listTdTask_success()
            throws IOException, IllegalValueException, DataConversionException {
        this.argument = new String[] {"td"};
        Predicate<Task> predicate = task -> meetRequirementTd(task);
        expectedModel.updateFilteredTaskList(predicate);
        expectedModel.commitTaskBook();

        assertCommandSuccess(new ListCommand(argument), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS2, expectedModel);
    }

    @Test
    public void execute_listDateTask_Date_success()
            throws IOException, IllegalValueException, DataConversionException {
        this.argument = new String[] {"01-01-11"};
        String specifiedDate = argument[0];
        assertCommandSuccess(new ListCommand(argument), model, commandHistory,
                String.format(MESSAGE_SUCCESS3, specifiedDate),expectedModel);
    }

    @Test
    public void execute_listCategoryTask1_success()
            throws IOException, IllegalValueException, DataConversionException {
        this.argument = new String[] {"a"};
        String specifiedCategory = argument[0];
        assertCommandSuccess(new ListCommand(argument), model, commandHistory,
                String.format(MESSAGE_SUCCESS4, categoryString(specifiedCategory)),expectedModel);
    }

    @Test
    public void execute_listCategoryTask2_success()
            throws IOException, IllegalValueException, DataConversionException {
        this.argument = new String[] {"c"};
        String specifiedCategory = argument[0];
        assertCommandSuccess(new ListCommand(argument), model, commandHistory,
                String.format(MESSAGE_SUCCESS4, categoryString(specifiedCategory)),expectedModel);
    }*/

    @Test
    public void invalidInput_failure()
            throws DataConversionException {

        assertCommandFailure(new ListCommand(new String[] {"abc"}), model, commandHistory,
                MESSAGE_INVALID_DATE);

        assertCommandFailure(new ListCommand(new String[] {"aa-bb-cc"}), model, commandHistory,
                MESSAGE_INVALID_DATE);

        assertCommandFailure(new ListCommand(new String[] {"38-01-11"}), model, commandHistory,
                MESSAGE_INVALID_DATE);

        assertCommandFailure(new ListCommand(new String[] {"01-13-11"}), model, commandHistory,
                MESSAGE_INVALID_DATE);

        assertCommandFailure(new ListCommand(new String[] {"01-01-1111"}), model, commandHistory,
                MESSAGE_INVALID_DATE);

        assertCommandFailure(new ListCommand(new String[] {"aa-bb-cc-dd"}), model, commandHistory,
                MESSAGE_INVALID_DATE);

        assertCommandFailure(new ListCommand(new String[] {"001-01-11"}), model, commandHistory,
                MESSAGE_INVALID_DATE);

    }

}
