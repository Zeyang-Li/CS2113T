package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AccountList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;

public class ExportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private Path exportFilePath;
    private Path wrongFileType;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTaskBook(), new UserPrefs(), new AccountList());
        expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);

        exportFilePath = Paths.get("src", "test", "data", "sandbox", "testExportCommand.json");
        wrongFileType = Paths.get("tryWrong.xml");
    }

    @Test
    public void execute() throws DataConversionException, IllegalValueException, IOException {
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS, exportFilePath);
        ExportCommand expectedCommand = new ExportCommand(exportFilePath);

        assertCommandSuccess(expectedCommand, model, new CommandHistory(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyTaskbook_throwsCommandException() throws Exception {
        ExportCommand exportCommand = new ExportCommand(exportFilePath);
        model.resetData(new TaskBook());

        thrown.expect(CommandException.class);
        thrown.expectMessage(ExportCommand.MESSAGE_FAILURE_EMPTY_AB);
        exportCommand.execute(model, new CommandHistory());
    }

    @Test
    public void execute_Taskbook_WithCategoryA() throws Exception {

        expectedModel = model;
        ExportCommand exportCommand = new ExportCommand(exportFilePath, "a");
        exportCommand.execute(model, new CommandHistory());

        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_A_SUCCESS, exportFilePath);
        ExportCommand expectedCommand = new ExportCommand(exportFilePath,"a");

        assertCommandSuccess(expectedCommand, model, new CommandHistory(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_Taskbook_WithCategoryE() throws Exception {

        expectedModel = model;
        ExportCommand exportCommand = new ExportCommand(exportFilePath, "e");
        exportCommand.execute(model, new CommandHistory());

        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_E_SUCCESS, exportFilePath);
        ExportCommand expectedCommand = new ExportCommand(exportFilePath,"e");

        assertCommandSuccess(expectedCommand, model, new CommandHistory(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_Taskbook_WithCategorR() throws Exception {

        expectedModel = model;
        ExportCommand exportCommand = new ExportCommand(exportFilePath, "r");
        exportCommand.execute(model, new CommandHistory());

        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_R_SUCCESS, exportFilePath);
        ExportCommand expectedCommand = new ExportCommand(exportFilePath,"r");

        assertCommandSuccess(expectedCommand, model, new CommandHistory(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_Taskbook_WithCategoryO() throws Exception {

        expectedModel = model;
        ExportCommand exportCommand = new ExportCommand(exportFilePath, "o");
        exportCommand.execute(model, new CommandHistory());

        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_O_SUCCESS, exportFilePath);
        ExportCommand expectedCommand = new ExportCommand(exportFilePath,"o");

        assertCommandSuccess(expectedCommand, model, new CommandHistory(), expectedMessage, expectedModel);
    }


}
