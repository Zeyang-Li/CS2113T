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
    public static final String COMMAND_PARAMETERS = "Parameters: [date]...\n"
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

        parameter = parameter.replaceAll("\\s+", "");
        if (parameter.length() != 8) {
            throw new ParseException(MESSAGE_ILLEGAL);
        }
        try {
            validDate();
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }

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

    /**
     * Checks whether the date entered is valid.
     * @return
     */
    private boolean validDate() throws CommandException, ParseException {
        boolean check = true;
        if (parameter == "") {
            return true;
        }
        try {
            //System.out.println(parameter.split("-")[0].replaceAll("0", ""));
            int day = Integer.parseInt(parameter.split("-")[0].replaceAll("0", ""));
            //System.out.println(day);
            //System.out.println(parameter.split("-")[1]);
            int month = Integer.parseInt(parameter.split("-")[1].replaceAll("0", ""));
            //System.out.println(parameter.split("-")[2]);
            int year = Integer.parseInt(parameter.split("-")[2].replaceAll("0", ""));
            if (year > 25) {
                throw new CommandException("Oops do not make plans so early!");
            }
            year += 2000;
            if (month < 0 || month > 12) {
                throw new CommandException("Incorrect month number! month should be within 1-12.");
            }
            switch (month) {
            case 1:
                if (day < 0 || day > 31) {
                    check = false;
                }
                break;
            case 2:
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    if (day < 0 || day > 29) {
                        check = false;
                    }
                } else {
                    if (day < 0 || day > 28) {
                        check = false;
                    }
                }
                break;
            case 3:
                if (day < 0 || day > 31) {
                    check = false;
                }
                break;
            case 4:
                if (day < 0 || day > 30) {
                    check = false;
                }
                break;
            case 5:
                if (day < 0 || day > 31) {
                    check = false;
                }
                break;
            case 6:
                if (day < 0 || day > 30) {
                    check = false;
                }
                break;
            case 7:
                if (day < 0 || day > 31) {
                    check = false;
                }
                break;
            case 8:
                if (day < 0 || day > 31) {
                    check = false;
                }
                break;
            case 9:
                if (day < 0 || day > 30) {
                    check = false;
                }
                break;
            case 10:
                if (day < 0 || day > 31) {
                    check = false;
                }
                break;
            case 11:
                if (day < 0 || day > 30) {
                    check = false;
                }
                break;
            case 12:
                if (day < 0 || day > 31) {
                    check = false;
                }
                break;
            default:
                check = false;
                break;
            }
            if (!check) {
                throw new ParseException("Incorrect day value! [dd-mm-yy]");
            }
        } catch (Exception e) {
            throw new ParseException("Incorrect date value! [dd-mm-yy]");
        }
        return true;
    }
}
