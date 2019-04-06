package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AccountListChangedEvent;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAccountList;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Tasketch data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskBookStorage taskBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private AccountListStorage accountListStorage;

    public StorageManager(TaskBookStorage taskBookStorage, UserPrefsStorage userPrefsStorage, AccountListStorage
            accountListStorage) {
        super();
        this.taskBookStorage = taskBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.accountListStorage = accountListStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TaskBook methods ==============================

    @Override
    public Path getTaskBookFilePath() {
        return taskBookStorage.getTaskBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(taskBookStorage.getTaskBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskBookStorage.readTaskBook(filePath);
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, taskBookStorage.getTaskBookFilePath());
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskBookStorage.saveTaskBook(taskBook, filePath);
    }
    @Override
    public void backupTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        taskBookStorage.backupTaskBook(taskBook);
    }


    @Override
    @Subscribe
    public void handleTaskBookChangedEvent(TaskBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskBook(event.data);
        } catch (IOException e) {
            new DataSavingExceptionEvent(e);
        }
    }


    @Override
    public Path getAccountListFilePath() {
        return accountListStorage.getAccountListFilePath();
    }

    @Override
    public Optional<ReadOnlyAccountList> readAccountList() throws DataConversionException, IOException {
        return readAccountList(accountListStorage.getAccountListFilePath());
    }


    @Override
    public Optional<ReadOnlyAccountList> readAccountList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return accountListStorage.readAccountList(filePath);
    }

    @Override
    public void saveAccountList(ReadOnlyAccountList accountList) throws Exception {
        saveAccountList(accountList, accountListStorage.getAccountListFilePath());
    }

    @Override
    public void saveAccountList(ReadOnlyAccountList accountList, Path filePath) throws Exception {
        logger.fine("Attempting to write to data file: " + filePath);
        accountListStorage.saveAccountList(accountList, filePath);
    }

    @Override
    @Subscribe
    public void handleAccountListChangedEvent(AccountListChangedEvent event) throws Exception {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAccountList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

	private void raise(DataSavingExceptionEvent dataSavingExceptionEvent) {
		// TODO Auto-generated method stub
		
	}



}
