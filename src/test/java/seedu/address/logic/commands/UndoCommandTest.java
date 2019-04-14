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

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs(), new AccountList());
    private final Model expectedModel = new ModelManager(getTypicalTaskBook(), new UserPrefs(), new AccountList());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        deleteFirstTask(model);
        deleteFirstTask(model);

        expectedModel.setLoggedInUser(admin);
        deleteFirstTask(expectedModel);
        deleteFirstTask(expectedModel);
    }

    @Test
    public void execute() throws DataConversionException, IllegalValueException, IOException {
        // multiple undoable states in model
        expectedModel.undoTaskBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoTaskBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
