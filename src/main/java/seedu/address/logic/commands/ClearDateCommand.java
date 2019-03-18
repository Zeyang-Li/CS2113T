package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Finds and clear all tasks on specific date in Tasketch.
 */

public class ClearDateCommand extends Command{

    public static final String COMMAND_WORD = "clear";

    public static final String COMMAND_ALIAS = "c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds and clear all tasks on one specific date\n"
            + "Parameters: DATE\n"
            + "Example: " + COMMAND_WORD + " 18-03-19";

    public static final String MESSAGE_SUCCESS = "clear %1$d tasks on %2$s";
//    public static final String MESSAGE_FAILURE = "There are no tasks on that day";
    private String specific_date;
    private int count;

    /**
     * Creates an ClearDateCommand to clear tasks on the specified {@code specific_date}
     */
    public ClearDateCommand(String specific_date) {
        this.specific_date = specific_date;
    }

    public boolean checkDate(Task task) {
        final String[] specificDate = specific_date.split("-");
        final String startDate = task.getStartDate().value;
        if (specificDate.length == 3) {
            if (specific_date.equals(startDate)) {
                return true;
            } else {
                return false;
            }
        } else if (specificDate.length == 2) {
            final String[] startDateInString = startDate.split("-");
            if(specificDate[0].equals(startDateInString[1]) && specificDate[1].equals(startDateInString[2])) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        count = 0;
        List<Task> lastShownList = model.getFilteredTaskList();
        List<Task> TasksToBeDeleted = new ArrayList<Task>();
        for(Task task : lastShownList) {
            if (checkDate(task)) {
                count++;
                TasksToBeDeleted.add(task);
            }
        }

//        if (count == 0) {
//            throw new CommandException(MESSAGE_FAILURE);
//        }

        model.deleteTaskList(TasksToBeDeleted);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, count, specific_date));
    }

}
