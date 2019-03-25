package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String MESSAGE_USAGE = "list : list specified tasks\n"
            + "Parameters:\n"
            + "1. no parameters: list        list all tasks\n"
            + "2. td: list td       list all the tasks of today\n"
            + "3. DATE: list 25-03-19    "
            + "list all the tasks which are before/after 25th March, 2019\n";
    public static final String MESSAGE_SUCCESS1 = "Listed all tasks";
    public static final String MESSAGE_SUCCESS2 = "Listed all tasks of today.";
    public static final String MESSAGE_SUCCESS3 = "Listed tasks on %1$s";
    public static final String COMMAND_PARAMETERS = "Parameters:\n"
            + "1. no parameters: list        list all tasks\n"
            + "2. td: list td       list all the tasks of today\n"
            + "3. DATE: list 25-03-19    "
            + "list all the tasks which are before/after 25th March, 2019\n";
    private String[] arguments;
    private String specifiedDate;

    public ListCommand(String[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (arguments[0].equals("")) {

            model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
            return new CommandResult(MESSAGE_SUCCESS1);
        } else if (arguments[0].equals("td")) {

            Predicate<Task> predicate = task -> meetRequirementTd(task);
            model.updateFilteredTaskList(predicate);
            return new CommandResult(MESSAGE_SUCCESS2);
        } else {

            specifiedDate = arguments[0];
            Predicate<Task> predicate = task -> meetRequirementDate(task);
            model.updateFilteredTaskList(predicate);
            return new CommandResult(String.format(MESSAGE_SUCCESS3, specifiedDate));
        }

    }

    /**
     * A boolean function used to decide predicate for list td command.
     */
    private boolean meetRequirementTd(Task task) {

        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YY");
        String dateInString = formatter.format(currentDate);

        final String taskDate = task.getStartDate().value;
        if (taskDate.equals(dateInString)) {
            return true;
        }
        return false;
    }

    /**
     * A boolean function used to decide predicate for list DATE command.
     */
    private boolean meetRequirementDate(Task task) {

        String[] dateInfo = specifiedDate.split("-");
        if (dateInfo.length == 3) {

            final String taskDate = task.getStartDate().value;
            if (taskDate.equals(specifiedDate)) {
                return true;
            } else {
                return false;
            }
        } else if (dateInfo.length == 2) {

            final String taskDate = task.getStartDate().value;
            String[] splitDate = taskDate.split("-");

            if (splitDate[1].equals(dateInfo[0]) && splitDate[2].equals(dateInfo[1])) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}


