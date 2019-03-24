package seedu.address.logic.suggestions;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;


/**
 * Suggests a command with the closest match to the inputted string.
 */
public class WrongCommandSuggestion implements Suggestion {
    public static final String SUGGESTION_HEADER = "Do you mean: %1$s?";
    public static final String NO_SUGGESTION = "No suggestions available.Try 'help'";
    private static final int WORD_DISTANCE_LIMIT = 3;

    private static final String[] CommandList;

    // Initialising the CommandList Array
    static {
        CommandList = new String[] {
                AddCommand.COMMAND_WORD,
                ClearCommand.COMMAND_WORD,
                DeleteCommand.COMMAND_WORD,
                EditCommand.COMMAND_WORD,
                ExitCommand.COMMAND_WORD,
                FindCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD,
                HistoryCommand.COMMAND_WORD,
                ListCommand.COMMAND_WORD,
                RedoCommand.COMMAND_WORD,
                UndoCommand.COMMAND_WORD,
        };
    }

    /**
     * Parses the command input and passes it to the getNearestCommand for comparison of commands.
     * Returns formatted string of the suggestion header and closest matched command, else returns nothing.
     * @param userCommand A {@code String} object of the user's command input
     * @return A {@code String} object containing the suggestion header and suggested similar command.
     */
    public List<String> getSuggestions(String userCommand) {
        String userCommandInLowerCase = userCommand.toLowerCase();
        List<String> suggestedCommand = getNearestCommands(userCommandInLowerCase);
        return suggestedCommand;
    }

    private List<String> getNearestCommands(String userCommand) {
        ArrayList<String>[] commandEditDistances = new ArrayList[WORD_DISTANCE_LIMIT];

        for (int i = 0; i < WORD_DISTANCE_LIMIT; i++) {
            commandEditDistances[i] = new ArrayList<>();
        }

        for (String commands: CommandList) {
            int distance = new StringSimilarity().editDistance(userCommand, commands);

            if (distance < WORD_DISTANCE_LIMIT) {
                commandEditDistances[1].add(commands);
            }
        }

        for (ArrayList<String> suggestedCommands: commandEditDistances) {
            if (!suggestedCommands.isEmpty()) {
                return suggestedCommands;
            }
        }

        return null;
    }
}
