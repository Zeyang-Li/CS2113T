package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.TaskBook;



/**
 * Clears the Tasketch.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Tasketch has been cleared!";
    public static final String MESSAGE_USAGE = "clear : clear tasks\n"
            + "Parameters: none or DATE\n"
            + "Example: " + COMMAND_WORD + "\n"
            + COMMAND_WORD + " 18-03-19\n"
            + COMMAND_WORD + " 03-19\n";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setTaskBook(new TaskBook());
        model.commitTaskBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
