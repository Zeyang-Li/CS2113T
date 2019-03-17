package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Add a task to Tasketch
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Tasketch. "
            + "Parameters: "
            + PREFIX_NAME + "TASK NAME "
            + PREFIX_STARTDATE + "START_DATE (omit colon) "
            + PREFIX_STARTTIME + "START_TIME (omit colon) "
            + PREFIX_ENDDATE + "END_DATE (omit colon) "
            + PREFIX_ENDTIME + "END_TIME (omit colon) "
            + PREFIX_DESCRIPTION + "CONTENT "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Revise CS2113T "
            + PREFIX_STARTDATE + "03-15-19 "
            + PREFIX_STARTTIME + "14.00 "
            + PREFIX_ENDDATE + "03-15-19 "
            + PREFIX_ENDTIME + "17.00 "
            + PREFIX_DESCRIPTION + "Class diagram "
            + PREFIX_TAG + "urgent "
            + PREFIX_TAG + "duesoon\n";

    public static final String MESSAGE_SUCCESS = "New Task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This Task already exists in Tasketch";
    private Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public AddCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addTask(toAdd);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
