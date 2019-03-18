package seedu.address.logic.commands;

import java.util.*;
import java.text.*;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;

public class ListtdCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String MESSAGE_USAGE = "list : list specified tasks" +
            "\nParameters:" +
            "\n1. no parameters: list        list all tasks" +
            "\n2. td: list td      list all the tasks of today" +
            "\n3. DATE: list 25-03-19    list all the tasks which are on 25th March, 2019";
    public static final String MESSAGE_SUCCESS = "Listed all tasks of today.";

    private boolean meetRequirement(Task task){

        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YY");
        String dateInString = formatter.format(currentDate);

        final String taskDate = task.getStartDate().value;
        if (taskDate.equals(dateInString)) {
            return true;
        }
        return false;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        Predicate<Task> predicate = task -> meetRequirement(task);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
