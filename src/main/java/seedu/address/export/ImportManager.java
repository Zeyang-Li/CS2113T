package seedu.address.export;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAccountList;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.storage.JsonFileStorage;
import seedu.address.storage.JsonSerializableAccountList;
import seedu.address.storage.JsonSerializableTaskBook;

/**
 * Manages importing of TaskBook data.
 */
public class ImportManager implements Import {

    private static final Logger logger = LogsCenter.getLogger(ImportManager.class);

    private Path importPath;

    public ImportManager(Path filePath) {
        this.importPath = filePath;
    }

    public Path getImportPath() {
        return importPath;
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(importPath);
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("TaskBook file " + filePath + " not found");
            return Optional.empty();
        }

        Optional<JsonSerializableTaskBook> jsonTaskBook = JsonFileStorage.loadDataFromSaveFile(filePath);
        try {
            if (jsonTaskBook.isPresent()) {
                return Optional.of(jsonTaskBook.get().toModelType());
            } else {
                return Optional.empty();
            }
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public Optional<ReadOnlyAccountList> readAccountList() throws DataConversionException, IOException {
        return readAccountList(importPath);
    }

    private Optional<ReadOnlyAccountList> readAccountList(Path importPath)
            throws FileNotFoundException, DataConversionException {
        requireNonNull(importPath);

        if (!Files.exists(importPath)) {
            logger.info("AccountList file " + importPath + " not found");
            return Optional.empty();
        }

        Optional<JsonSerializableAccountList> jsonAccountList = JsonFileStorage.loadAccountListFromSaveFile(importPath);
        try {
            if (jsonAccountList.isPresent()) {
                return Optional.of(jsonAccountList.get().toModelType());
            } else {
                return Optional.empty();
            }
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + importPath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }
}
