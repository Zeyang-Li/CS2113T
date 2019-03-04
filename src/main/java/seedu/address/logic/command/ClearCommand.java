package seedu.address.logic.command;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
/**
 * Clears data in Tasketch.
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Tasketch been completely cleared!";

    /**
     * This is the method execute
     * @return
     */
    public CommandResult execute() {
        //To be further implemented.
        // to add the feature to clear tasks on the daily basis and monthly basis
        //Please refer to the original command
        return new seedu.address.logic.command.CommandResult(MESSAGE_SUCCESS);

    }
}
