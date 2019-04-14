package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AccountList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;

public class TimelineCommandTest {
    private Model model;
    private Model emptyModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTaskBook(), new UserPrefs(), new AccountList());
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
    }

    @Before
    public void setUpEmptyModel() {
        emptyModel = new ModelManager(new TaskBook(), new UserPrefs(), new AccountList());
        Username admin = new Username("admin");
        emptyModel.setLoggedInUser(admin);
    }

    @Test
    public void execute_timeline_success()
            throws DataConversionException, IllegalValueException, IOException {
        String input = "01-04-19";
        assertCommandSuccess(new TimelineCommand(input), model, commandHistory,
                String.format(TimelineCommand.MESSAGE_SUCCESS, input), model);
    }

    @Test
    public void execute_timeline_invalid_day()
            throws DataConversionException, IllegalValueException {
        String invalidInput = "31-04-19";

        assertCommandFailure(new TimelineCommand(invalidInput), model, commandHistory,
                TimelineCommand.MESSAGE_ILLEGAL_VALUE);
    }

    @Test
    public void execute_timeline_invalid_month()
            throws DataConversionException, IllegalValueException {
        String invalidInput = "01-13-19";

        assertCommandFailure(new TimelineCommand(invalidInput), model, commandHistory,
                TimelineCommand.MESSAGE_ILLEGAL_VALUE);
    }

    @Test
    public void execute_timeline_invalid_year()
            throws DataConversionException, IllegalValueException {
        String invalidInput = "01-04-100";

        assertCommandFailure(new TimelineCommand(invalidInput), model, commandHistory,
                TimelineCommand.MESSAGE_ILLEGAL);
    }
}
