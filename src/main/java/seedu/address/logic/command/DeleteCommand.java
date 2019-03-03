package seedu.address.logic.command;

import java.util.List;

//To be replaced:
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import static java.util.Objects.requireNonNull;

/**
 * This command delete a task.
 */
public class DeleteCommand {
    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Task: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * This is the method execute
     * @param model
     * @param history
     * @return
     * @throws CommandException
     */
    public seedu.address.logic.command.CommandResult execute(Model model, CommandHistory history) 
            throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.command.DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((seedu.address.logic.command.DeleteCommand) other).targetIndex)); // state check
    }
}
