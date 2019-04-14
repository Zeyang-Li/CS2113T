package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;


/**
 * Clears the Tasketch.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_CLEAR_SUCCESS = "Tasketch has been cleared!";
    public static final String MESSAGE_USAGE = "clear : clear tasks\n"
            + "Parameters: none or DATE or before\n"
            + "Example: " + COMMAND_WORD + "\n"
            + COMMAND_WORD + " 18-03-19\n"
            + COMMAND_WORD + " 03-19\n"
            + COMMAND_WORD + " before\n";
    public static final String COMMAND_PARAMETERS = "Parameters: none or DATE or before\n";
    public static final String MESSAGE_CLEARDATE_SUCCESS = "clear %1$d tasks which start at %2$s";
    public static final String MESSAGE_CLEARYD_SUCCESS = "clear %1$d tasks which have already ended on %2$s";
    public static final String MESSAGE_LOGIN = "Please login first";
    public static final String MESSAGE_INVALID_DATE = "Date is not valid";
    private String specificDate;
    private int count = 0;
    private List<Task> tasksToBeDeleted = new ArrayList<Task>();


    /**
     * Creates an ClearDateCommand to clear tasks on the specified {@code specific_date}
     */
    public ClearCommand(String specificDate) {
        this.specificDate = specificDate;
    }

    /**
     * Boolean function that help predicate to select specific tasks.
     */

    public boolean checkDate(String checkingDate, String dateInTask) {
        final String[] specificDateList = checkingDate.split("-");
        if (specificDateList.length == 3) {
            if (checkingDate.equals(dateInTask)) {
                return true;
            } else {
                return false;
            }
        } else if (specificDateList.length == 2) {
            final String[] startDateInString = dateInTask.split("-");
            if (specificDateList[0].equals(startDateInString[1]) && specificDateList[1].equals(startDateInString[2])) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * check whether is numberic
     * @param str
     * @return
     */
    public static boolean isNumberic(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    /**
     * Boolean function that help predicate to check valid date format.
     */
    public boolean isValidDate(String checkingDate) {
        final String[] specificDateList = checkingDate.split("-");

        if (specificDateList.length == 3) {
            char[] days=specificDateList[0].toCharArray();
            char[] months=specificDateList[1].toCharArray();
            char[] years=specificDateList[2].toCharArray();

            if (days.length == 2 && months.length == 2 && years.length == 2) {
                if (isNumberic(specificDateList[0]) && isNumberic(specificDateList[1])
                        && isNumberic(specificDateList[2])) {
                    final Integer day = Integer.parseInt(specificDateList[0]);
                    final Integer month = Integer.parseInt(specificDateList[1]);
                    if (day <= 31 && day > 0 && month > 0 && month <= 12) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } else if (specificDateList.length == 2) {
            char[] months=specificDateList[0].toCharArray();
            char[] years=specificDateList[1].toCharArray();

            if (months.length == 2 && years.length == 2) {
                if (isNumberic(specificDateList[0]) && isNumberic(specificDateList[1])) {
                    final Integer month = Integer.parseInt(specificDateList[0]);
                    if (month > 0 && month <= 12) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }

        }
        return false;
    }

    /**
     * Boolean function that checks whether the task is ended before today.
     */
    public boolean checkBeforeDate(String checkingDate, String dateInTask) {

        final String[] specificDateList = checkingDate.split("-");
        final String[] taskDateList = dateInTask.split("-");

        final Integer day = Integer.parseInt(taskDateList[0]);
        final Integer month = Integer.parseInt(taskDateList[1]);
        final Integer year = Integer.parseInt(taskDateList[2]);

        final Integer specificYear = Integer.parseInt(specificDateList[2]);
        final Integer specificMonth = Integer.parseInt(specificDateList[1]);
        final Integer specificDay = Integer.parseInt(specificDateList[0]);

        if ((specificYear > year)
                || (specificMonth > month && specificYear == year)
                || (specificDay >= day && specificYear == year && specificMonth == month)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Boolean function that checks whether the task starts from that specific day.
     */
    public boolean checkStartDate(Task task) {
        final String startDate = task.getStartDate().value;
        return checkDate(this.specificDate, startDate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        if (this.specificDate.equals("")) {

            model.setTaskBook(new TaskBook());
            model.commitTaskBook();
            return new CommandResult(MESSAGE_CLEAR_SUCCESS);

        } else if (this.specificDate.equals("before")) {

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YY");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, -24);
            this.specificDate = formatter.format(calendar.getTime());

            List<Task> lastShownList = model.getFilteredTaskList();
            for (Task task : lastShownList) {
                if (checkBeforeDate(this.specificDate, task.getEndDate().value)) {
                    count++;
                    tasksToBeDeleted.add(task);
                }
            }
            model.deleteTaskList(tasksToBeDeleted);
            model.commitTaskBook();
            return new CommandResult(String.format(MESSAGE_CLEARYD_SUCCESS, count, specificDate));

        } else if (isValidDate(this.specificDate)){

            if(isValidDate(this.specificDate)) {
                List<Task> lastShownList = model.getFilteredTaskList();
                for (Task task : lastShownList) {
                    if (checkStartDate(task)) {
                        count++;
                        tasksToBeDeleted.add(task);
                    }
                }
                model.deleteTaskList(tasksToBeDeleted);
                model.commitTaskBook();
                return new CommandResult(String.format(MESSAGE_CLEARDATE_SUCCESS, count, specificDate));
            } else {
                return new CommandResult(MESSAGE_INVALID_DATE);
            }


        } else {
            throw new CommandException(MESSAGE_INVALID_DATE);
        }
    }
}
