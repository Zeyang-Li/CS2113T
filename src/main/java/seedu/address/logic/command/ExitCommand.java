package seedu.address.logic.command;

//To be replaced:
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Execute this command to exit the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Tasketch as requested ...";

    //@Override
    public seedu.address.logic.command.CommandResult execute(Model model, CommandHistory history) {
        // to be implement
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
