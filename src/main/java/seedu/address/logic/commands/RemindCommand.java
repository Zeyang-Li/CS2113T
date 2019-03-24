package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

public class RemindCommand extends Command{
    public static final String COMMAND_WORD = "remind";
    public static final String MESSAGE_USAGE = "remind: give reminds on specified requirement\n"
            + "Parameters:\n"
            + "1. start/ddl: Tasketch will give a reminding task list ordered by task start time or deadline.\n"
            + "2. a/e/c/r/o start/ddl: Tasketch will give a reminding task list of specified category\n"
            + "of tasks ordered by start time or deadline.\n";
    public String arguments;

    public RemindCommand(String userInput) {
        this.arguments = userInput;
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
    private boolean meetRequirement(Task task, String givenVategory) {
        if (task.getCategory().equals(givenVategory)) {

            return true;
        } else {

            return false;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException, ParseException {

        requireNonNull(model);
        List<Task> tasks = model.getFilteredTaskList();

        String trimmedArguments = arguments.trim();
        String[] splitedInput = trimmedArguments.split("\\s");
        List<Task> originalTasks = model.getFilteredTaskList();
        List<Task> sortedTasks = new ArrayList<Task>();

        if (splitedInput.length == 1) {
            if (!isValidTime(splitedInput[0])) {

                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
            } else if (splitedInput[0].equals("start")) {

                model.sortByStart();
            } else {

                model.sortByEnd();
            }

            model.commitTaskBook();
        } else if (splitedInput.length == 2) {

            if (!isValidCategory(splitedInput[0]) || !isValidTime(splitedInput[1])) {

                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
            } else {

                List<Task> categoriedTasks =
                        CategoryExtractor.findTaskOfSpecifiedCategory(originalTasks, splitedInput[0]);
                if (splitedInput[1].equals("start")) {

                    model.sortByStart();

                } else {

                    model.sortByEnd();
                }
            }

            model.commitTaskBook();
            Predicate<Task> predicate = task -> meetRequirement(task, splitedInput[0]);
            model.updateFilteredTaskList(predicate);

        } else {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
        }

        return new CommandResult("Reminder shown!");
    }
}
