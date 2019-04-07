package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AccountList;
import seedu.address.model.ReadOnlyAccountList;
import seedu.address.model.account.Account;


/**
 * An Immutable AccountList that is serializable to Json format
 */
@JsonRootName(value = "Source")
public class JsonSerializableAccountList {
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Account list contains duplicate account(s).";

    private final List<JsonAdaptedAccount> accounts = new ArrayList<>();


    /**
     * Constructs a {@code JsonSerializableAccountList} with the given accounts.
     */
    @JsonCreator
    public JsonSerializableAccountList(@JsonProperty("accounts") List<JsonAdaptedAccount> accounts) {
        this.accounts.addAll(accounts);
    }

    /**
     * Conversion
     */
    public JsonSerializableAccountList(ReadOnlyAccountList src) {
        accounts.addAll(src.getAccountList().stream().map(JsonAdaptedAccount::new).collect(Collectors.toList()));
    }

    /**
     * Conversion with filtered accounts instead of the whole AccountList.
     */
    public JsonSerializableAccountList(ObservableList<Account> filteredAccountList) {
        accounts.addAll(filteredAccountList.stream().map(JsonAdaptedAccount::new).collect(Collectors.toList()));
    }

    /**
     * Converts account list into the model's {@code AccountList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedAccount}.
     */
    public AccountList toModelType() throws IllegalValueException {
        AccountList accountList = new AccountList();
        for (JsonAdaptedAccount p : accounts) {
            Account account = p.toModelType();
            if (accountList.hasAccount(account)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            accountList.addAccount(account);
        }
        return accountList;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JsonSerializableAccountList)) {
            return false;
        }
        return accounts.equals(((JsonSerializableAccountList) other).accounts);
    }
}
