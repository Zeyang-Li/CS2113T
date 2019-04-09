package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Lists all tasks in the task book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String MESSAGE_LOGIN = "Please login first";
    public static final String MESSAGE_USAGE = "list : list specified tasks\n"
            + "Parameters:\n"
            + "1. no parameters: list        list all tasks\n"
            + "2. td: list td       list all the tasks of today\n"
            + "3. DATE: list 25-03-19    "
            + "list all the tasks which are before/after 25th March, 2019\n"
            + "4. category: list a/c/e/r/o";
    public static final String MESSAGE_SUCCESS1 = "Listed all tasks";
    public static final String MESSAGE_SUCCESS2 = "Listed all tasks of today.";
    public static final String MESSAGE_SUCCESS3 = "Listed tasks on %1$s";
    public static final String MESSAGE_SUCCESS4 = "Listed all tasks of %1$s category";
    public static final String COMMAND_PARAMETERS = "Parameters:\n"
            + "1. no parameters: list        list all tasks\n"
            + "2. td: list td       list all the tasks of today\n"
            + "3. DATE: list 25-03-19    "
            + "list all the tasks which are before/after 25th March, 2019\n"
            + "4. category: list a      list all tasks of academic category";
    private String[] arguments;
    private String specifiedDate;
    private String specifiedCategory;

    public ListCommand(String[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        if (arguments[0].equals("")) {

            model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
            return new CommandResult(MESSAGE_SUCCESS1);
        } else if (arguments[0].equals("td")) {

            Predicate<Task> predicate = task -> meetRequirementTd(task);
            model.updateFilteredTaskList(predicate);
            //ObservableList<Task> test = model.getFilteredTaskList();
            return new CommandResult(MESSAGE_SUCCESS2);
        } else if (isCategory(arguments[0])) {

            specifiedCategory = arguments[0];
            Predicate<Task> predicate = task -> meetRequirementCategory(task);
            model.updateFilteredTaskList(predicate);
            return new CommandResult(String.format(MESSAGE_SUCCESS4, categoryString(specifiedCategory)));
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
     * A boolean function used to decide predicate for list category command.
     */
    private boolean isCategory(String str) {
        if (str.equals("a") || str.equals("c") || str.equals("e") || str.equals("r") || str.equals("o")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return a string that to be printed.
     */
    private String categoryString(String str) {
        if (str.equals("a")) {
            return "academic";
        } else if (str.equals("c")) {
            return "CCA";
        } else if (str.equals("e")) {
            return "entertainment";
        } else if (str.equals("r")) {
            return "errand";
        } else {
            return "other";
        }
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

    /**
     * A boolean function used to decide predicate for list category command.
     */
    private boolean meetRequirementCategory(Task task) {
        String taskCategory = task.getCategories().value;
        if (taskCategory.equals(specifiedCategory)) {
            return true;
        } else {
            return false;
        }
    }

}


