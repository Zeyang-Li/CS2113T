package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyDaysKeeper;

/**
 * A class to access DaysKeeper data stored as a json file on the hard disk.
 */
public class JsonDaysKeeperStorage implements DaysKeeperStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonDaysKeeperStorage.class);

    private Path filePath;

    public JsonDaysKeeperStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDaysKeeperFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDaysKeeper> readDaysKeeper() throws DataConversionException {
        return readDaysKeeper(filePath);
    }

    /**
     * Similar to {@link #readDaysKeeper()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDaysKeeper> readDaysKeeper(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableDaysKeeper> jsonDaysKeeper = JsonUtil.readJsonFile(
                filePath, JsonSerializableDaysKeeper.class);
        if (!jsonDaysKeeper.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDaysKeeper.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDaysKeeper(ReadOnlyDaysKeeper daysKeeper) throws IOException {
        saveDaysKeeper(daysKeeper, filePath);
    }

    /**
     * Similar to {@link #saveDaysKeeper(ReadOnlyDaysKeeper)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveDaysKeeper(ReadOnlyDaysKeeper daysKeeper, Path filePath) throws IOException {
        requireNonNull(daysKeeper);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDaysKeeper(daysKeeper), filePath);
    }

}
