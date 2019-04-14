package seedu.address.logic.suggestion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.AddAccountCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteAccountCommand;
import seedu.address.logic.commands.EditAccountCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindAccountCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListAccountsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.TimelineCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.suggestions.Suggestion;
import seedu.address.logic.suggestions.WrongCommandSuggestion;

public class WrongCommandSuggestionTest {
    private Suggestion wrongCommandSuggestion = new WrongCommandSuggestion();
    private ArrayList<String> outputCommandList;

    @Before
    public void setup() {
        outputCommandList = new ArrayList<>();
    }

    @Test
    public void getSuggestion_noSuggestions() {
        // "abcDEFGH" input is not similar to any command
        assertNull(wrongCommandSuggestion.getSuggestions("abcDEFGH"));
    }

    @Test
    public void getSuggestion_oneSuggestion() {
        // "histary" input is similar to history command and has only one suggestion available
        outputCommandList.add(HistoryCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("histary"));
    }

    @Test
    public void getSuggestion_multipleSuggestions() {
        outputCommandList.add(RedoCommand.COMMAND_WORD);
        outputCommandList.add(UndoCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("wido"));
    }

    @Test
    public void getSuggestion_multipleSuggestions2() {
        outputCommandList.add(EditCommand.COMMAND_WORD);
        outputCommandList.add(ExitCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("ecot"));
    }

    @Test
    public void getSuggestion_multipleSuggestions3() {
        outputCommandList.add(ExportCommand.COMMAND_WORD);
        outputCommandList.add(ImportCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("aaport"));
    }

    @Test
    public void getSuggestion_checkOccClearSuggestion() {
        outputCommandList.add(ClearCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("raelc"));
    }

    @Test
    public void getSuggestion_checkOccListSuggestion() {
        outputCommandList.add(ListCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("tsil"));
    }

    @Test
    public void getSuggestion_checkOccTimelineSuggestion() {
        outputCommandList.add(TimelineCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("tiimelne"));
    }

    @Test
    public void getSuggestion_checkOccFindAccSuggestion() {
        outputCommandList.add(FindAccountCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("fdniountAcc"));
    }

    @Test
    public void getSuggestion_checkOccListAccSuggestion() {
        outputCommandList.add(ListAccountsCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("listountsAcc"));
    }

    @Test
    public void getSuggestion_checkOccAddAccSuggestion() {
        outputCommandList.add(AddAccountCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("ddaountAcc"));
    }

    @Test
    public void getSuggestion_checkOccDeleteAccSuggestion() {
        outputCommandList.add(DeleteAccountCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("teledeountAcc"));
    }

    @Test
    public void getSuggestion_checkOccEditAccSuggestion() {
        outputCommandList.add(EditAccountCommand.COMMAND_WORD);
        assertEquals(outputCommandList, wrongCommandSuggestion.getSuggestions("etidountAcc"));
    }
}
