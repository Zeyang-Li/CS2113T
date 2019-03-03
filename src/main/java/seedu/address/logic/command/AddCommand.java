package seedu.address.logic.command;


//To be replaced:
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Add a task to Tasketch
 */
public class AddCommand extends Command{
    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Tasketch. "
            + "Parameters: "
            + "-t" + "TOPIC "
            + "-d " + "DATE (default today) "
            + "-s" + "START_TIME (omit colon) "
            + "-dur" + "DURATION (hour) "
            + "-des" + "CONTENT "
            + "[" + "-tag" + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "-t" + "Revise CS2113T "
            + "-d " + "0315"
            + "-s" + "1400 "
            + "-dur" + "2 "
            + "-des" + "Class diagram "
            + "-tag" + "urgent"
            + "-tag" + "due soon...\n";

    public static final String MESSAGE_SUCCESS = "New Task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This Task already exists in Tasketch";
    public Person toAdd;

    /* @Override */
    public CommandResult execute(Model model) throws CommandException {

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
    public AddCommand(Person person) {
        toAdd = person;
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.command.AddCommand // instanceof handles nulls
                && toAdd.equals(((seedu.address.logic.command.AddCommand) other).toAdd));
    }
}
