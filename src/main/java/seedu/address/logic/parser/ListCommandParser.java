package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Used for parsing user input for list command
 */
public class ListCommandParser {

    /**
     * Parse function
     */
    public String parse(String userInput) throws ParseException {
        String[] arguments = userInput.trim().split("\\s+");
        if (arguments.length == 1) {
            if (arguments[0].equals("")) {
                return "ListCommand";
            } else if (arguments[0].equals("td")) {
                return "ListtdCommand";
            } else if (isValidDateFormat(arguments[0])) {
                return arguments[0];
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Check if user input is of the correct format
     */
    public boolean isValidDateFormat(String str) {
        String[] str_split = str.split("-");
        if (str_split.length == 2 || str_split.length == 3) {
            return true;
        } else {
            return false;
        }
    }

}
