package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;

import java.util.List;



import seedu.address.logic.CommandHistory;

import seedu.address.model.Model;

import seedu.address.model.account.Account;

/**
 * Lists all accounts in the account list to the user.
 */
public class ListAccountsCommand extends Command {

    public static final String COMMAND_WORD = "listAccounts";

    public static final String COMMAND_ALIAS = "lAc";

    public static final String MESSAGE_SUCCESS = "Listed all accounts";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        List<Account> ListOfAccounts = model.getFilteredAccountList();
        String OutputList = getListOfAccounts(ListOfAccounts);
        return new CommandResult(OutputList);
    }

    private String getListOfAccounts (List<Account> accounts) {
        String OutputList = "";
        OutputList += MESSAGE_SUCCESS + "\n";

        OutputList += "Accounts: \n";
        int index = 0;
        for (Account account : accounts) {
        	index++;
            OutputList += index + ". " + account.getUsername() + "\n";
        }
        return OutputList;
    }

}
