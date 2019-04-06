package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddAccountCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAccountCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditAccountCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindAccountCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListAccountsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LoginStatusCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.MonthCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemindCommand;
import seedu.address.logic.commands.ShowTimeCommand;
import seedu.address.logic.commands.TimelineCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.suggestions.WrongCommandSuggestion;


//The command created for Tasketch:

/**
 * Parses user input.
 */
public class TaskBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case AddAccountCommand.COMMAND_WORD:
        case AddAccountCommand.COMMAND_ALIAS:
            return new AddAccountCommandParser().parse(arguments);

        case DeleteAccountCommand.COMMAND_WORD:
        case DeleteAccountCommand.COMMAND_ALIAS:
            return new DeleteAccountCommandParser().parse(arguments);

        case EditAccountCommand.COMMAND_WORD:
        case EditAccountCommand.COMMAND_ALIAS:
            return new EditAccountCommandParser().parse(arguments);

        case FindAccountCommand.COMMAND_WORD:
        case FindAccountCommand.COMMAND_ALIAS:
            return new FindAccountCommandParser().parse(arguments);

        case ListAccountsCommand.COMMAND_WORD:
        case ListAccountsCommand.COMMAND_ALIAS:
            return new ListAccountsCommand();

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
        	return new LogoutCommand();

        case LoginStatusCommand.COMMAND_WORD:
            return new LoginStatusCommand();

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommandParser().parse(arguments);

        case MonthCommand.COMMAND_WORD:
        case MonthCommand.COMMAND_ALIAS:
            return new MonthCommandParser().parse(arguments);

        case TimelineCommand.COMMAND_WORD:
        case TimelineCommand.COMMAND_ALIAS:
            return new TimelineCommandParser().parse(arguments);

        case RemindCommand.COMMAND_WORD:
            return new RemindCommand(arguments);

        case ShowTimeCommand.COMMAND_WORD:
        case ShowTimeCommand.COMMAND_ALIAS:
            return new ShowTimeCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        default:
            List<String> listOfCommands = new WrongCommandSuggestion().getSuggestions(commandWord);
            if (listOfCommands == null) {
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND + "\n" + WrongCommandSuggestion.NO_SUGGESTION);
            } else {
                String suggestionsToString = StringUtil.join(listOfCommands, ", ");
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND + '\n'
                        + String.format(WrongCommandSuggestion.SUGGESTION_HEADER, suggestionsToString));
            }
        }
    }

}
