package seedu.address.logic.commands;

import java.util.*;
import java.text.*;
import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String MESSAGE_USAGE = "list : list specified tasks" +
            "Parameters:" +
            "1. no parameters: list        list all tasks" +
            "2. td/tmr: list td/tmr       list all the tasks of today/tommorow" +
            "3. before/after DATE: list before/after 25-03-19    list all the tasks which are before/after 25th March, 2019";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static ListCommand listAllTask() {
        return new ListCommand();
    }

}

