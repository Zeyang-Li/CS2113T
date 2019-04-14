package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Used for parsing user input for clear command
 */

public class ClearCommandParser implements Parser<ClearCommand> {
    /**
     * Parse function
     */
    public ClearCommand parse(String userInput) throws ParseException {
        String[] arguments = userInput.trim().split("\\s+");
        if (arguments.length == 1) {
            if (arguments[0].equals("")) {
                return new ClearCommand("");
            } else if (arguments[0].equals("before")) {
                return new ClearCommand("before");
            } else if (isValidDateFormat(arguments[0])) {
                return new ClearCommand(arguments[0]);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Check if user input is of the correct format
     */
    public boolean isValidDateFormat(String str) {
        String[] strSplited = str.split("-");
        if (strSplited.length == 2 || strSplited.length == 3) {
            return true;
        } else {
            return false;
        }
    }



}
