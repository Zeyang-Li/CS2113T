package seedu.address.storage;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.Username;


/**
 * JAXB-friendly version of the Account.
 */
public class JsonAdaptedAccount {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Account's %s field is missing!";

    public String username;
    public String password;


    /**
     * Constructs an {@code XmlAdaptedItem} with the given item details.
     */
    public JsonAdaptedAccount(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }


    /**
     * Converts a given Account into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedItem
     */
    public JsonAdaptedAccount(Account source) {
        username = source.getUsername().fullUsername;
        password = source.getPassword().toString();
    }


    /**
     * Converts this jaxb-friendly adapted account object into the model's Account object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted account
     */
    public Account toModelType() throws IllegalValueException {

        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Username.class
                    .getSimpleName()));
        }

        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }

        final Username modelUsername = new Username(username);

        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class
                    .getSimpleName()));
        }

        if (!Password.isValidPassword(password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final Password modelPassword = new Password(password);

        return new Account(modelUsername, modelPassword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JsonAdaptedAccount)) {
            return false;
        }

        JsonAdaptedAccount otherAccount = (JsonAdaptedAccount) other;
        return Objects.equals(username, otherAccount.username)
                && Objects.equals(password, otherAccount.password);
    }

}
