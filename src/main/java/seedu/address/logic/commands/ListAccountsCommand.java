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

    public static final String COMMAND_WORD = "listaccounts";

    public static final String COMMAND_ALIAS = "lAc";

    public static final String MESSAGE_SUCCESS = "Listed all accounts";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        List<Account> listOfAccounts = model.getFilteredAccountList();
        String outputList = getListOfAccounts(listOfAccounts);
        return new CommandResult(outputList);
    }

    private String getListOfAccounts (List<Account> accounts) {
        String outputList = "";
        outputList += MESSAGE_SUCCESS + "\n";
        outputList += "Accounts: \n";
        int index = 0;
        for (Account account : accounts) {
            index++;
            outputList += index + ". " + account.getUsername() + "\n";
        }
        return outputList;
    }

}
