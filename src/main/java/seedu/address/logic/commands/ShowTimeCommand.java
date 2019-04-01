package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DAYS;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.day.Day;

/**
 * Lists all tasks in the task book to the user.
 */
public class ShowTimeCommand extends Command {

    public static final String COMMAND_WORD = "showtime";
    public static final String COMMAND_ALIAS = "st";
    public static final String MESSAGE_USAGE = "showtime : show each category's time of specified days\n"
            + "Parameters:\n"
            + "1. no parameters: showtime        show all days\n"
            + "2. DATE: showtime 25-03-19    "
            + "show the day 25th March, 2019\n";
    public static final String MESSAGE_SUCCESS1 = "Shown all days";
    public static final String MESSAGE_SUCCESS2 = "Listed day on %1$s";
    public static final String COMMAND_PARAMETERS = "Parameters:\n"
            + "1. no parameters: showtime        show all days\n"
            + "2. DATE: showtime 25-03-19    "
            + "show the day 25th March, 2019\n";
    private String[] arguments;
    private String specifiedDate;

    public ShowTimeCommand(String[] arguments) {
        this.arguments = arguments;
    }

    /**
     * A boolean function used to decide predicate for list DATE command.
     */
    private boolean meetRequirementDate(Day day) {

        String[] dateInfo = specifiedDate.split("-");
        if (dateInfo.length == 3) {

            final String taskDate = day.getDate().value;
            if (taskDate.equals(specifiedDate)) {
                return true;
            } else {
                return false;
            }
        } else if (dateInfo.length == 2) {

            final String taskDate = day.getDate().value;
            String[] splitDate = taskDate.split("-");

            if (splitDate[0].equals(dateInfo[0]) && splitDate[1].equals(dateInfo[1])) {
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
        if (arguments[0].equals("")) {

            model.updateFilteredDayList(PREDICATE_SHOW_ALL_DAYS);
            return new CommandResult(MESSAGE_SUCCESS1);
        } else {

            specifiedDate = arguments[0];
            Predicate<Day> predicate = day -> meetRequirementDate(day);
            model.updateFilteredDayList(predicate);
            return new CommandResult(String.format(MESSAGE_SUCCESS2, specifiedDate));
        }
    }

}

