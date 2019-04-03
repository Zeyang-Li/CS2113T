package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * This is the Timeline command.
 */
public class TimelineCommand extends Command {

    public static final String COMMAND_WORD = "timeline";

    public static final String COMMAND_ALIAS = "time";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Arrange the tasks of the specific date "
            + "into a timeline and displays them according to categories.\n"
            + "Parameters: [date]...\n"
            + "Example: " + COMMAND_WORD + "15-03-19";
    public static final String COMMAND_PARAMETERS = COMMAND_WORD + ": Arrange the tasks of the specific date "
            + "into a timeline and displays them according to categories.\n"
            + "Parameters: [date]...\n"
            + "Example: " + COMMAND_WORD + "15-03-19";
    public static final String MESSAGE_SUCCESS = "Timeline on %1$s has been successfully arranged!";
    public static final String MESSAGE_ILLEGAL = "Incorrect date format. [dd-mm-yys]";

    private String parameter;

    public TimelineCommand(String args) throws ParseException {
        this.parameter = args;
    }

    /**
     * This filters the tasks.
     * @param task
     * @param d
     * @return
     */
    private boolean filter(Task task, String d) {
        if (task.getStartDate().value.equals(d)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException, ParseException {
        requireNonNull(model);
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YY");
        String formattedToday = formatter.format(today);
        //System.out.println(formattedToday);

        if (parameter.equals("")) {
            Predicate<Task> predicate = task -> filter(task, formattedToday);
            //model.updateFilteredTaskList(predicate);
            return new CommandResult(String.format(MESSAGE_SUCCESS, formattedToday));
        } else {
            Predicate<Task> predicate = task -> filter(task, this.parameter);
            //model.updateFilteredTaskList(predicate);
            return new CommandResult(String.format(MESSAGE_SUCCESS, parameter));
        }
    }
}
