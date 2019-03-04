package seedu.address.logic.command;

//To be replaced:
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Execute this command to exit the program.
 */
public class ExitTasketchCommand extends TasketchCommand {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Tasketch as requested ...";

    /**
     * This is the method execute
     * @param model
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    public seedu.address.logic.commands.CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
