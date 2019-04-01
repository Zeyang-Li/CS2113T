package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports all persons in the address book to a file.
 */
public class ExportAllCommand extends Command {

    public static final String COMMAND_WORD = "exportall";

    public static final String COMMAND_EXAMPLE = "Example: " + COMMAND_WORD;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports all the tasks in the task book.\n"
            + COMMAND_EXAMPLE;

    public static final String MESSAGE_ARGUMENTS = "Filetype: %1$s";
    public static final String MESSAGE_SUCCESS = "Exported all tasks.";
    private static final String MESSAGE_FAILURE = "Export failed!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            model.exportTaskBook();
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }


}
