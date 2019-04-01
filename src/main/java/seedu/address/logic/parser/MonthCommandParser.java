package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.MonthCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for changing calendar view.
 */
public class MonthCommandParser implements Parser<MonthCommand> {

    /**
     * parse method.
     * @param args
     * @return
     * @throws ParseException
     */
    public MonthCommand parse(String args) throws ParseException {
        try {
            return new MonthCommand(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MonthCommand.MESSAGE_ILLEGAL), pe);
        }
    }
}
