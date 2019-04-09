package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;


/**
 * Stores taskbook data in an Json file
 */
public class JsonFileStorage {
    /**
     * Saves the given taskbook data to the specified file.
     */
    public static void saveDataToFile(Path file, JsonSerializableTaskBook taskBook)
            throws FileNotFoundException {
        try {
            JsonUtil.saveJsonFile(taskBook, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores AccountList data in an Json file
     */
    public static void saveAccountListToFile(Path file, JsonSerializableAccountList accountList)
            throws FileNotFoundException {
        try {
            JsonUtil.saveJsonFile(accountList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns task book in the file or an empty address book
     */
    public static Optional<JsonSerializableTaskBook> loadDataFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        return JsonUtil.readJsonFile(file, JsonSerializableTaskBook.class);

    }

    public static Optional<JsonSerializableAccountList> loadAccountListFromSaveFile(Path file)
            throws DataConversionException,
            FileNotFoundException {
        return JsonUtil.readJsonFile(file, JsonSerializableAccountList.class);
    }

}

