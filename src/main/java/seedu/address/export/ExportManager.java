package seedu.address.export;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.task.Task;
import seedu.address.storage.JsonFileStorage;
import seedu.address.storage.JsonSerializableTaskBook;


/**
 * Manages exporting of AddressBook data.
 */
public class ExportManager implements Export {

    private static final String MESSAGE_NOTHING_TO_EXPORT = "There is nothing to export!";
    private static final Logger logger = LogsCenter.getLogger(seedu.address.export.ExportManager.class);

    private ObservableList<Task> filteredTasks;
    private Path exportPath;

    public ExportManager(ObservableList<Task> filteredTasks, Path filePath) {
        this.filteredTasks = filteredTasks;
        this.exportPath = filePath;
    }

    public Path getExportFilePath() {
        return exportPath;
    }


    /**
     * Saves the {@code filteredTasks} to the {@code exportPath}.
     * @throws IOException if there was any problem writing to the file.
     * @throws IllegalValueException if the current taskbook is empty.
     */
    @Override
    public void saveFilteredTasks() throws IOException, IllegalValueException {
        saveFilteredTasks(filteredTasks, exportPath);
    }

    /**
     * Similar to {@link #saveFilteredTasks()}
     * @param filteredTasks cannot be null.
     * @param filePath file path of the data. Cannot be null
     */
    @Override
    public void saveFilteredTasks(ObservableList<Task> filteredTasks, Path filePath)
            throws IOException, IllegalValueException {
        requireNonNull(filteredTasks);
        requireNonNull(filePath);

        if (filteredTasks.size() <= 0) {
            logger.warning("There is nothing to export!");
            throw new IllegalValueException(MESSAGE_NOTHING_TO_EXPORT);
        }

        if (FileUtil.isFileExists(filePath)) {
            logger.fine("File exists. Overwriting output file: " + filePath.toString());
        } else {
            logger.fine("Initializing output file: " + filePath.toString());
            FileUtil.createIfMissing(filePath);
        }
        JsonFileStorage.saveDataToFile(filePath, new JsonSerializableTaskBook(filteredTasks));
    }
}
