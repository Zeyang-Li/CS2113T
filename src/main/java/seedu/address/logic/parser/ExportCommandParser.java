package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Path;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ExportCommand} object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    private static final String MESSAGE_INVALID_CATEGORY_FORMAT = "Invalid category!";

    /**
     * Parses the given {@code args} of arguments in the context of the {@code ImportCommand}
     * and returns a {@code ImportCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExportCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        String[] args = userInput.trim().split("\\s+");
        if (args.length == 2) {
            if (args[1].equals("a") || args[1].equals("c") || args[1].equals("e")
                    || args[1].equals("r") || args[1].equals("o")) {
                try {
                    Path filePath = ParserUtil.parseFilename(args[0]);
                    return new ExportCommand(filePath, args[1]);
                } catch (ParseException pe) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE), pe);
                }
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_CATEGORY_FORMAT, ExportCommand.MESSAGE_USAGE));
            }
        } else if (args.length == 1) {
            try {
                Path filePath = ParserUtil.parseFilename(args[0]);
                return new ExportCommand(filePath);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE), pe);
            }
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
    }
}
