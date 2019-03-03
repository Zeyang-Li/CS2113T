package seedu.address.logic.command;

//To be replaced:
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;


/**
 * This command will list all tasks.
 */
public class ListCommand {
    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    /**
     * This is the execute method.
     * @param model
     * @return
     */
    public seedu.address.logic.command.CommandResult execute(Model model) {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
