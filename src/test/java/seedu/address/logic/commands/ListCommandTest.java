package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAccountList;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;

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
        model = new ModelManager(getTypicalTaskBook(), new UserPrefs(), getTypicalAccountList());
        expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), model.getAccountList());
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        expectedModel.setLoggedInUser(admin);
    }

	private ReadOnlyAccountList getTypicalAccountList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Test
    public void execute_listIsNotFiltered_showsSameList() throws IOException, IllegalValueException {
        assertCommandSuccess(new ListCommand(argument), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS1, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() throws IOException, IllegalValueException {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        assertCommandSuccess(new ListCommand(argument), model, commandHistory,
                ListCommand.MESSAGE_SUCCESS1, expectedModel);
    }
}
