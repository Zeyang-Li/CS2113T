package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Set a reminder of the nearest tasks for user, those tasks can be specified by task category.
 */
public class RemindCommand extends Command {
    public static final String COMMAND_WORD = "remind";
    public static final String MESSAGE_USAGE = "remind: give reminds on specified requirement\n"
            + "Parameters:\n"
            + "1. start/ddl: Tasketch will give a reminding task list ordered by task start time or deadline.\n"
            + "2. a/e/c/r/o start/ddl: Tasketch will give a reminding task list of specified category\n"
            + "of tasks ordered by start time or deadline.\n";
    private String arguments;
    public ObservableList<Task> shownTaskList;

    /**
     * Constructor of RemindCommand.
     */
    public RemindCommand(String userInput) {
        this.arguments = userInput;
    }

    /**
     * Access function of arguments.
     */
    public String getArguments() {
        return this.arguments;
    }

    /**
     * A boolean function to verify user's input
     */
    private boolean isValidCategory(String input) {
        if (!input.equals("a") && !input.equals("e")
                && !input.equals("c") && !input.equals("r") && !input.equals("o")) {

            return false;
        } else {

            return true;
        }
    }

    /**
     * A boolean function to verify user's input
     */
    private boolean isValidTime(String input) {
        if (!input.equals("start") && !input.equals("ddl")) {

            return false;
        } else {

            return true;
        }
    }

    /**
     * A predicate function decide which task to choose
     */
    private boolean meetRequirement(Task task, String givenCategory) {
        if (task.getCategories().value.equals(givenCategory)) {

            return true;
        } else {

            return false;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException, ParseException {

        requireNonNull(model);
        shownTaskList = model.getAllTaskList();
        String trimmedArguments = arguments.trim();
        String[] splitedInput = trimmedArguments.split("\\s");

        if (splitedInput.length == 1) {
            if (!isValidTime(splitedInput[0])) {

                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
            } else if (splitedInput[0].equals("start")) {
                model.sortByStart();

            } else {

                model.sortByEnd();

            }
            model.setRemindList(shownTaskList);
            model.commitTaskBook();

        } else if (splitedInput.length == 2) {

            if (!isValidCategory(splitedInput[0]) || !isValidTime(splitedInput[1])) {

                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
            } else {

                if (splitedInput[1].equals("start")) {
                    model.sortByStart();

                } else {
                    model.sortByEnd();

                }
            }

            model.setRemindList(shownTaskList);
            model.commitTaskBook();
            Predicate<Task> predicate = task -> meetRequirement(task, splitedInput[0]);
            model.updateFilteredTaskList(predicate);

        } else {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
        }

        return new CommandResult("Reminder shown!");
    }
}
