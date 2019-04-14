package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;

public class RemindCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTaskBook(), new UserPrefs(), new AccountList());
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
    }

    @Test
    public void remindStart_success() throws DataConversionException, IllegalValueException, IOException {

        expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());
        expectedModel.commitTaskBook();

        assertCommandSuccess(new RemindCommand("start"), model, commandHistory, RemindCommand.MESSAGE_REMIND_SUCCESS,
                expectedModel);
    }

    @Test
    public void remindDDL_success() throws DataConversionException, IllegalValueException, IOException {

        expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());
        expectedModel.commitTaskBook();

        assertCommandSuccess(new RemindCommand("ddl"), model, commandHistory, RemindCommand.MESSAGE_REMIND_SUCCESS,
                expectedModel);
    }

    @Test
    public void remindCategoryStart1_success() throws DataConversionException, IllegalValueException, IOException {

        expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());
        expectedModel.commitTaskBook();

        assertCommandSuccess(new RemindCommand("a start"), model, commandHistory, RemindCommand.MESSAGE_REMIND_SUCCESS,
                expectedModel);
    }

    @Test
    public void remindCategoryStart2_success() throws DataConversionException, IllegalValueException, IOException {

        expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());
        expectedModel.commitTaskBook();

        assertCommandSuccess(new RemindCommand("c start"), model, commandHistory, RemindCommand.MESSAGE_REMIND_SUCCESS,
                expectedModel);
    }

    @Test
    public void remindCategoryDDL1_success() throws DataConversionException, IllegalValueException, IOException {

        expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());
        expectedModel.commitTaskBook();

        assertCommandSuccess(new RemindCommand("a ddl"), model, commandHistory, RemindCommand.MESSAGE_REMIND_SUCCESS,
                expectedModel);
    }

    @Test
    public void remindCategoryDDL2_success() throws DataConversionException, IllegalValueException, IOException {

        expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());
        expectedModel.commitTaskBook();

        assertCommandSuccess(new RemindCommand("c ddl"), model, commandHistory, RemindCommand.MESSAGE_REMIND_SUCCESS,
                expectedModel);
    }

    @Test
    public void invalidInput_failure()
            throws DataConversionException {

        assertCommandFailure(new RemindCommand(""), model, commandHistory,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));

        assertCommandFailure(new RemindCommand(" "), model, commandHistory,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));

        assertCommandFailure(new RemindCommand("asdas"), model, commandHistory,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));

        assertCommandFailure(new RemindCommand("asd aslf"), model, commandHistory,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));

        assertCommandFailure(new RemindCommand("k start"), model, commandHistory,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));

        assertCommandFailure(new RemindCommand("a sss"), model, commandHistory,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));

    }
}
