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
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTaskBook(), new UserPrefs(), new AccountList());
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
    }

    @Test
    public void execute_newTask_success() throws DataConversionException, IllegalValueException, IOException {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());
        expectedModel.addTask(validTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(new AddCommand(validTask), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws DataConversionException {
        Task taskInList = model.getTaskBook().getTaskList().get(0);
        assertCommandFailure(new AddCommand(taskInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_TASK);
    }

}
