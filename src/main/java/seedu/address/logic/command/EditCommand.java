package seedu.address.logic.command;

//To be replaced:
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import seedu.address.commons.core.index.Index;


/**
 * Edit the details of a task.
 * Topic, start time, duration, description.
 */
public class EditCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + "-t " + "TOPIC] "
            + "[" + "-d " + "DATE] "
            + "[" + "-s" + "START_TIME] "
            + "[" + "-dur " + "DURATION] "
            + "[" + "-des " + "DESCRIPTION]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "-s " + "1600 "
            + "-dur " + "3";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in Tasketch.";

    //private final Index index;
    //private final EditPersonDescriptor editPersonDescriptor;

}
