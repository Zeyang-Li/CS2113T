package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DAYS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

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
    public static final String MESSAGE_SUCCESS = "Shown all days";
    public static final String COMMAND_PARAMETERS = "Parameters:\n"
            + "1. no parameters: showtime        show all days\n"
            + "2. DATE: showtime 25-03-19    "
            + "show the day 25th March, 2019\n";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDayList(PREDICATE_SHOW_ALL_DAYS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}

