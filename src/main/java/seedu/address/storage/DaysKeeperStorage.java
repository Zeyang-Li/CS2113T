package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDaysKeeper;

/**
 * Represents a storage for {@link seedu.address.model.DaysKeeper}.
 */
public interface DaysKeeperStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDaysKeeperFilePath();

    /**
     * Returns DaysKeeper data as a {@link ReadOnlyDaysKeeper}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDaysKeeper> readDaysKeeper() throws DataConversionException, IOException;

    /**
     * @see #getDaysKeeperFilePath()
     */
    Optional<ReadOnlyDaysKeeper> readDaysKeeper(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDaysKeeper} to the storage.
     * @param daysKeeper cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDaysKeeper(ReadOnlyDaysKeeper daysKeeper) throws IOException;

    /**
     * @see #saveDaysKeeper(ReadOnlyDaysKeeper)
     */
    void saveDaysKeeper(ReadOnlyDaysKeeper daysKeeper, Path filePath) throws IOException;

}
