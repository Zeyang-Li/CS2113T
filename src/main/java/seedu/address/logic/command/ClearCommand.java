package seedu.address.logic.command;

import seedu.address.logic.command.CommandResult;

/**
 * Clears data in Tasketch.
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Tasketch been cleared!";
    /* @Override */
    public CommandResult execute() {
        //To be further implemented.
        //Please refer to the original command
        return new seedu.address.logic.command.CommandResult(MESSAGE_SUCCESS);

    }
}
