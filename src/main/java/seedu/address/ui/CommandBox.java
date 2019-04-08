package seedu.address.ui;

import static seedu.address.logic.commands.CommandFormatString.ADDACCOUNT_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.ADD_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.CLEAR_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.DELETEACCOUNT_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.DELETE_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.EDITACCOUNT_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.EDIT_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.EXPORT_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.FINDACCOUNT_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.FIND_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.IMPORT_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.LIST_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.LOGIN_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.MONTH_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.REMIND_COMMAND_CATEGORY_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.REMIND_COMMAND_FORMATSTRING;
import static seedu.address.logic.commands.CommandFormatString.TIMELINE_COMMAND_FORMATSTRING;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddAccountCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;

import seedu.address.logic.commands.CommandResult;
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

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String NO_MATCHED_COMMAND = "No matched command!";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private static final String[] CommandList;

    static {
        CommandList = new String[] {
            AddCommand.COMMAND_WORD,
            AddAccountCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            DeleteAccountCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            EditAccountCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            ExportCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            FindAccountCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            HistoryCommand.COMMAND_WORD,
            ImportCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD,
            ListAccountsCommand.COMMAND_WORD,
            LoginCommand.COMMAND_WORD,
            LoginStatusCommand.COMMAND_WORD,
            LogoutCommand.COMMAND_WORD,
            MonthCommand.COMMAND_WORD,
            RemindCommand.COMMAND_WORD,
            RedoCommand.COMMAND_WORD,
            ShowTimeCommand.COMMAND_WORD,
            TimelineCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD,
        };
    }

    private final CommandExecutor commandExecutor;
    private final List<String> history;
    private ListElementPointer historySnapshot;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, List<String> history) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.history = history;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = new ListElementPointer(history);
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     * @throws Exception
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) throws Exception {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            keyEvent.consume();
            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        case TAB:
            keyEvent.consume();
            if (Arrays.asList(CommandList).contains(commandTextField.getText())
                    || commandTextField.getText().equals(RemindCommand.COMMAND_WORD
                            + " " + REMIND_COMMAND_FORMATSTRING)) {
                showParameterForCommand(commandTextField.getText());
            } else {
                autoCompleteInputCommand();
            }
            break;

        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Show the format string of parameter for commands in Command line.
     */
    private void showParameterForCommand(String text) {
        String completedtext = null;
        switch (text) {
        case "add":
            completedtext = text + " " + ADD_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "addAccount":
            completedtext = text + " " + ADDACCOUNT_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "clear":
            completedtext = text + " " + CLEAR_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "delete":
            completedtext = text + " " + DELETE_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "deleteAccount":
            completedtext = text + " " + DELETEACCOUNT_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "edit":
            completedtext = text + " " + EDIT_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "editAccount":
            completedtext = text + " " + EDITACCOUNT_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "export":
            completedtext = text + " " + EXPORT_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "find":
            completedtext = text + " " + FIND_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "findAccount":
            completedtext = text + " " + FINDACCOUNT_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "import":
            completedtext = text + " " + IMPORT_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "list":
            completedtext = text + " " + LIST_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "login":
            completedtext = text + " " + LOGIN_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "month":
            completedtext = text + " " + MONTH_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "remind":
            completedtext = text + " " + REMIND_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case "timeline":
            completedtext = text + " " + TIMELINE_COMMAND_FORMATSTRING;
            replaceText(completedtext);
            break;
        case RemindCommand.COMMAND_WORD + " " + REMIND_COMMAND_FORMATSTRING:
            completedtext = RemindCommand.COMMAND_WORD + " " + REMIND_COMMAND_CATEGORY_FORMATSTRING;
            replaceText(completedtext);
            break;

        default:
        }
    }

    private void autoCompleteInputCommand() throws Exception {
        String text = commandTextField.getText();
        String completedtext = getCompletedtext(text);
        replaceText(completedtext);
    }

    private String getCompletedtext(String text) {
        return getMostSimilarCommand(text, CommandList);
    }

    /**
     * Get the most similar command with the text field input.
     */
    private String getMostSimilarCommand(String text, String[] commandlist) {
        int length = text.length();
        float highestRatio = 0;
        String highestRatioCommand = null;
        for (String commands: commandlist) {
            if (length <= commands.length() && text.equals(commands.substring(0, length))) {
                return commands;
            } else if (highestRatio == 0 || highestRatio < getSimilarityRatio(text, commands)) {
                highestRatio = getSimilarityRatio(text, commands);
                highestRatioCommand = commands;
            }
            //System.out.println(getSimilarityRatio(text, commands));
        }

        if (highestRatio < 0.5) {
            return NO_MATCHED_COMMAND;
        }
        return highestRatioCommand;
    }

    private float getSimilarityRatio(String text, String commands) {
        int max = Math.max(text.length(), commands.length());
        return 1 - (float) compare(text, commands) / max;
    }

    /**
     * Compare input text with the command to get the similarity of them.
     */
    private float compare(String text, String command) {
        int[][] difference;
        int textLength = text.length();
        int commandLength = command.length();
        char ch1;
        char ch2;
        int temp;
        if (textLength == 0) {
            return commandLength;
        }
        if (commandLength == 0) {
            return textLength;
        }
        difference = new int[textLength + 1][commandLength + 1];
        for (int i = 0; i <= textLength; i++) {
            difference[i][0] = i;
        }
        for (int j = 0; j <= commandLength; j++) {
            difference[0][j] = j;
        }
        for (int i = 1; i <= textLength; i++) {
            ch1 = text.charAt(i - 1);
            for (int j = 1; j <= commandLength; j++) {
                ch2 = command.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                difference[i][j] = min(difference[i - 1][j] + 1,
                                       difference[i][j - 1] + 1, difference[i - 1][j - 1] + temp);
            }
        }
        return difference[textLength][commandLength];
    }

    private int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() throws IOException, IllegalValueException {
        try {
            commandExecutor.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            initHistory();
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles the tap button pressed event.
     */
    @FXML
    private void handleCommandtapped() throws IOException, IllegalValueException {
        try {
            commandExecutor.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            initHistory();
            setStyleToIndicateCommandFailure();
        }
    }
    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = new ListElementPointer(history);
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, IllegalValueException, IOException;
    }

}
