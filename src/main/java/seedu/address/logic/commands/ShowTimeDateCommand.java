package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.day.Day;

/**
 * Lists all tasks on an specific date in tasketch to the user.
 */
public class ShowTimeDateCommand extends Command {
    public static final String COMMAND_WORD = "showtime";
    public static final String COMMAND_ALIAS = "st";
    public static final String MESSAGE_USAGE = "showtime : show each category's time of specified days\n"
            + "Parameters:\n"
            + "1. no parameters: showtime        show all days\n"
            + "2. DATE: showtime 25-03-19    "
            + "show the day 25th March, 2019\n";
    public static final String MESSAGE_SUCCESS = "Listed days on %1$s";
    private String specifiedDate;

    /**
     * Constructor for ListDateCommand
     */
    public ShowTimeDateCommand(String date) {
        this.specifiedDate = date;
    }

    /**
     * A boolean function that help predicate to select specific tasks.
     */
    private boolean meetRequirement(Day day) {

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

        Predicate<Day> predicate = day -> meetRequirement(day);
        model.updateFilteredDayList(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, specifiedDate));
    }
}
