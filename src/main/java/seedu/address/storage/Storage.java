package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.AccountListChangedEvent;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAccountList;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskBookStorage, UserPrefsStorage, AccountListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getTaskBookFilePath();

    @Override
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;


    @Override
    Optional<ReadOnlyAccountList> readAccountList() throws DataConversionException, IOException;


    @Override
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException;

    @Override
    void saveAccountList(ReadOnlyAccountList accountList) throws IOException, Exception;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskBookChangedEvent(TaskBookChangedEvent abce);

    /**
     * Saves the current version of the Account List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     * @throws Exception
     */
    void handleAccountListChangedEvent(AccountListChangedEvent accountList) throws Exception;
}
