package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Month command will change the current month shown on calendar.
 */
public class MonthCommand extends Command {
    public static final String COMMAND_WORD = "month";

    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the current calendar to "
            + "next month or previous month.\n"
            + "Parameters: [+ / -]...\n"
            + "Example: " + COMMAND_WORD + " +";
    public static final String COMMAND_PARAMETERS = "Parameters: [+ / -]...\n";

    public static final String MESSAGE_SUCCESS = "Viewing %s month's calendar!";
    public static final String MESSAGE_ILLEGAL = "Please type in + or - to indicate!";
    public static final String MESSAGE_LOGIN = "Please login first";

    private String parameter = " ";

    public MonthCommand(String c) throws ParseException {
        c = c.replaceAll("\\s","");
        //System.out.println(c);
        if (c.equals("+")) {
            this.parameter = "next";
        } else if (c.equals("-")) {
            this.parameter = "previous";
        } else if (c.replaceAll("\\s", "").equals("")) {
            this.parameter = "this";
        } else {
            parameter = MESSAGE_ILLEGAL;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException, ParseException {
        requireNonNull(model);

        if (parameter.equals(MESSAGE_ILLEGAL)) {
            return new CommandResult(MESSAGE_ILLEGAL);
        }

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        model.setMonth(parameter);
        return new CommandResult(String.format(MESSAGE_SUCCESS, parameter));
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
