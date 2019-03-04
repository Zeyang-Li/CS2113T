package seedu.address.logic.command.exceptions;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class TasketchCommandExceprions extends Exception{

    public TasketchCommandExceprions(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public TasketchCommandExceprions(String message, Throwable cause) {
        super(message, cause);
    }
}

