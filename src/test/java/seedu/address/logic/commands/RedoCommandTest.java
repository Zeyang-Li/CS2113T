package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstTask;
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

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs(), new AccountList());
    private final Model expectedModel = new ModelManager(getTypicalTaskBook(), new UserPrefs(), new AccountList());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        deleteFirstTask(model);
        deleteFirstTask(model);
        model.undoTaskBook();
        model.undoTaskBook();

        expectedModel.setLoggedInUser(admin);
        deleteFirstTask(expectedModel);
        deleteFirstTask(expectedModel);
        expectedModel.undoTaskBook();
        expectedModel.undoTaskBook();
    }

    @Test
    public void execute() throws DataConversionException, IllegalValueException, IOException {
        // multiple redoable states in model
        expectedModel.redoTaskBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoTaskBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
