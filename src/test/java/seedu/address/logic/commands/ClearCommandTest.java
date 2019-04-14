package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.CS2100;
import static seedu.address.testutil.TypicalTasks.CS2110;
import static seedu.address.testutil.TypicalTasks.SAMESTARTDATEWITHCS2110;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
import seedu.address.model.task.Task;


public class ClearCommandTest {

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
    public void emptyTaskBook_success() throws DataConversionException, IllegalValueException, IOException {

        Model expectedModel = new ModelManager();
        expectedModel.commitTaskBook();

        assertCommandSuccess(new ClearCommand(""), emptyModel, commandHistory, ClearCommand.MESSAGE_CLEAR_SUCCESS,
                expectedModel);
    }

    @Test
    public void nonEmptyTaskBook_success()
            throws DataConversionException, IllegalValueException, IOException {

        Model expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());
        expectedModel.resetData(new TaskBook());
        expectedModel.commitTaskBook();

        assertCommandSuccess(new ClearCommand(""), model, commandHistory, ClearCommand.MESSAGE_CLEAR_SUCCESS,
                expectedModel);
    }

    @Test
    public void clearBefore_nonEmptyTaskBook_success()
            throws DataConversionException, IllegalValueException, IOException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YY");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, -24);
        String specificDate = formatter.format(calendar.getTime());

        Model expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());

        List<Task> tasksToBeCleared = new ArrayList<>(Arrays.asList(CS2110, SAMESTARTDATEWITHCS2110, CS2100));
        expectedModel.deleteTaskList(tasksToBeCleared);

        expectedModel.commitTaskBook();

        assertCommandSuccess(new ClearCommand("before"), model, commandHistory,
                String.format(ClearCommand.MESSAGE_CLEARYD_SUCCESS, 3, specificDate), expectedModel);
    }

    @Test
    public void clearSpecificDate_nonEmptyTaskBook_success()
            throws DataConversionException, IllegalValueException, IOException {

        Model expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());

        List<Task> tasksToBeCleared = new ArrayList<>(Arrays.asList(CS2110, SAMESTARTDATEWITHCS2110));
        expectedModel.deleteTaskList(tasksToBeCleared);

        expectedModel.commitTaskBook();

        assertCommandSuccess(new ClearCommand(CS2110.getStartDate().value), model, commandHistory,
                String.format(ClearCommand.MESSAGE_CLEARDATE_SUCCESS, 2, CS2110.getStartDate().value), expectedModel);
    }

    @Test
    public void clearSpecificDate2_nonEmptyTaskBook_success()
            throws DataConversionException, IllegalValueException, IOException {

        Model expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());
        expectedModel.deleteTask(CS2100);
        expectedModel.commitTaskBook();

        assertCommandSuccess(new ClearCommand(CS2100.getStartDate().value), model, commandHistory,
                String.format(ClearCommand.MESSAGE_CLEARDATE_SUCCESS, 1, CS2100.getStartDate().value), expectedModel);
    }

    @Test
    public void clearSpecificMonth_nonEmptyTaskBook_success()
            throws DataConversionException, IllegalValueException, IOException {

        Model expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());

        List<Task> tasksToBeCleared = new ArrayList<>(Arrays.asList(CS2110, SAMESTARTDATEWITHCS2110));
        expectedModel.deleteTaskList(tasksToBeCleared);

        expectedModel.commitTaskBook();

        assertCommandSuccess(new ClearCommand("01-11"), model, commandHistory,
                String.format(ClearCommand.MESSAGE_CLEARDATE_SUCCESS, 2, "01-11"), expectedModel);
    }

    @Test
    public void clearSpecificMonth2_nonEmptyTaskBook_success()
            throws DataConversionException, IllegalValueException, IOException {

        Model expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());
        expectedModel.deleteTask(CS2100);
        expectedModel.commitTaskBook();

        assertCommandSuccess(new ClearCommand("03-03"), model, commandHistory,
                String.format(ClearCommand.MESSAGE_CLEARDATE_SUCCESS, 1, "03-03"), expectedModel);
    }

    @Test
    public void DateWithNoTask_nonEmptyTaskBook_success()
            throws DataConversionException, IllegalValueException, IOException {

        Model expectedModel = new ModelManager(model.getTaskBook(), new UserPrefs(), new AccountList());

        expectedModel.commitTaskBook();

        assertCommandSuccess(new ClearCommand("01-01-00"), model, commandHistory,
                String.format(ClearCommand.MESSAGE_CLEARDATE_SUCCESS, 0, "01-01-00"), expectedModel);
    }

    @Test
    public void invalidDate_nonEmptyTaskBook_success()
            throws DataConversionException {

        assertCommandFailure(new ClearCommand("aabb"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("aa-bb"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("aa-bb-cc"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("34-01-99"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("0a-0b-0c"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("01-14-99"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("01-01-2334"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("aa-bb-cc-dd"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("00-00-12"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("01-00-99"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("012-12-10"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("12-012-20"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);

        assertCommandFailure(new ClearCommand("12-12-112"), model, commandHistory,
                ClearCommand.MESSAGE_INVALID_DATE);
    }


}
