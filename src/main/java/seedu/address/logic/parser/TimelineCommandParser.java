package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.TimelineCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This is the Timeline parser.
 */
public class TimelineCommandParser implements Parser<TimelineCommand> {

    /**
     * parse method.
     * @param args
     * @return
     * @throws ParseException
     */
    public TimelineCommand parse(String args) throws ParseException {
        try {
            return new TimelineCommand(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimelineCommand.MESSAGE_ILLEGAL), pe);
        }
    }
}
