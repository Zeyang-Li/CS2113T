package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Exports the listed tasks in the taskbook to a json file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_PARAMETERS = "Parameters: FILENAME (must end with .json) [CATEGORY]\n";
    public static final String COMMAND_EXAMPLE = "Example: 1. " + COMMAND_WORD + " export.json\n"
            + "2. " + COMMAND_WORD + " academic.json a\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the tasks listed "
            + COMMAND_PARAMETERS
            + COMMAND_EXAMPLE;

    public static final String MESSAGE_EXPORT_SUCCESS = "Exported tasks to %1$s";
    public static final String MESSAGE_EXPORT_A_SUCCESS = "Exported all academic tasks to %1$s";
    public static final String MESSAGE_EXPORT_C_SUCCESS = "Exported all CCA tasks to %1$s";
    public static final String MESSAGE_EXPORT_E_SUCCESS = "Exported all entertainment tasks to %1$s";
    public static final String MESSAGE_EXPORT_R_SUCCESS = "Exported all errand tasks to %1$s";
    public static final String MESSAGE_EXPORT_O_SUCCESS = "Exported all other tasks to %1$s";
    public static final String MESSAGE_FAILURE = "Export failed!";
    public static final String MESSAGE_CATEGORY_FAILURE = "Invalid category!";
    public static final String MESSAGE_FAILURE_EMPTY_AB = "There is nothing to export!";

    private final Path filePath;
    private String category = "not specified category";

    public ExportCommand(Path filePath) {
        requireNonNull(filePath);

        this.filePath = filePath;
    }
    public ExportCommand(Path filePath, String category) {
        this.filePath = filePath;
        this.category = category;
        requireNonNull(filePath);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (category.equals("a")) {
            return getCommandResult(model, MESSAGE_EXPORT_A_SUCCESS);
        } else if (category.equals("c")) {
            return getCommandResult(model, MESSAGE_EXPORT_C_SUCCESS);
        } else if (category.equals("e")) {
            return getCommandResult(model, MESSAGE_EXPORT_E_SUCCESS);
        } else if (category.equals("r")) {
            return getCommandResult(model, MESSAGE_EXPORT_R_SUCCESS);
        } else if (category.equals("o")) {
            return getCommandResult(model, MESSAGE_EXPORT_O_SUCCESS);
        } else if (category.equals("not specified category")) {
            return getCommandResult(model, MESSAGE_EXPORT_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_CATEGORY_FAILURE);
        }
    }

    private CommandResult getCommandResult(Model model, String messageExportESuccess) throws CommandException {

        if (!category.equals("not specified category")) {
            Predicate<Task> predicate = task -> meetRequirement(task);
            model.updateFilteredTaskList(predicate);
        }
        try {
            model.exportFilteredTaskBook(filePath);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_FAILURE_EMPTY_AB);
        }

        return new CommandResult(String.format(messageExportESuccess, filePath));
    }

    private boolean meetRequirement(Task task) {
        String cateInTask = task.getCategories().toString();
        if (cateInTask.equals(category)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean equals(Object other) {
        // same object
        if (other == this) {
            return true;
        }

        // handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        // checks state
        ExportCommand e = (ExportCommand) other;
        return filePath.equals(e.filePath);
    }
}
