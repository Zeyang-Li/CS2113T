package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Lists all tasks today in tasketch to the user.
 */
public class ListtdCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String MESSAGE_USAGE = "list : list specified tasks\n"
            + "Parameters:\n"
            + "1. no parameters: list        list all tasks\n"
            + "2. td: list td       list all the tasks of today\n"
            + "3. DATE: list 25-03-19    "
            + "list all the tasks which are before/after 25th March, 2019\n";
    public static final String MESSAGE_SUCCESS = "Listed all tasks of today.";

    /**
     * A boolean function that help predicate to select specific tasks.
     */
    private boolean meetRequirement(Task task) {

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
