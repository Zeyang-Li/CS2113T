package seedu.address.logic.commands;

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

public class MonthCommandTest {
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
    public void execute_next_month_success()
            throws DataConversionException, IllegalValueException, IOException {
        String input = "+";
        String output = "next";
        assertCommandSuccess(new MonthCommand(input), model, commandHistory,
                String.format(MonthCommand.MESSAGE_SUCCESS, output), model);
    }

    @Test
    public void execute_prevous_month_success()
            throws DataConversionException, IllegalValueException, IOException {
        String input = "-";
        String output = "previous";
        assertCommandSuccess(new MonthCommand(input), model, commandHistory,
                String.format(MonthCommand.MESSAGE_SUCCESS, output), model);
    }

    @Test
    public void execute_this_month_success()
            throws DataConversionException, IllegalValueException, IOException {
        String input = "";
        String output = "this";
        assertCommandSuccess(new MonthCommand(input), model, commandHistory,
                String.format(MonthCommand.MESSAGE_SUCCESS, output), model);
    }
}
