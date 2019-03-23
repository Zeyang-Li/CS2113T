package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDaysKeeper;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskBookStorage, DaysKeeperStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getTaskBookFilePath();

    @Override
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    @Override
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException;

    @Override
    Path getDaysKeeperFilePath();

    @Override
    Optional<ReadOnlyDaysKeeper> readDaysKeeper() throws DataConversionException, IOException;

    @Override
    void saveDaysKeeper(ReadOnlyDaysKeeper daysKeeper) throws IOException;

}
