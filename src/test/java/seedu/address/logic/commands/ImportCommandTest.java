package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalTasks.ENT;
import static seedu.address.testutil.TypicalTasks.HOME;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AccountList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;
import seedu.address.testutil.TaskBookBuilder;

public class ImportCommandTest {
    private static final int TASKS_ADDED = 2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private Path importFilePath;

    @Before
    public void setUp() throws IOException, IllegalValueException {
        model = new ModelManager(new TaskBookBuilder().build(), new UserPrefs(), new AccountList());
        expectedModel = new ModelManager(new TaskBookBuilder().withTask(HOME).withTask(ENT).build(),
                new UserPrefs(), new AccountList());
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        expectedModel.setLoggedInUser(admin);
        importFilePath = Paths.get("src", "test", "data", "sandbox", "testImportCommand.json");
        expectedModel.exportFilteredTaskBook(importFilePath);
    }

    @Test
    public void constructor_nullImportFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null);
    }

    @Test
    public void execute() throws CommandException {
        CommandResult commandResult = new ImportCommand(importFilePath).execute(model, new CommandHistory());
        String expectedMessage = String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS, TASKS_ADDED);

        ReadOnlyTaskBook taskBook = model.getTaskBook();
        ReadOnlyTaskBook expectedTaskBook = expectedModel.getTaskBook();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedTaskBook.getTaskList(), taskBook.getTaskList());
    }

    @Test
    public void equals() {
        ImportCommand standardCommand = new ImportCommand(importFilePath);
        final Path differentFilePath = Paths.get("src", "test", "data", "testImportCommandDifferent.json");

        // same value -> returns true
        ImportCommand commandWithSameArgument = new ImportCommand(importFilePath);
        assertTrue(standardCommand.equals(commandWithSameArgument));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null value -> returns false
        assertFalse(standardCommand.equals(null));

        // different filename -> returns false
        assertFalse(standardCommand.equals(new ImportCommand(differentFilePath)));
    }
}
