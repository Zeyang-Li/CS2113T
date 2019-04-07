package seedu.address.export;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.task.Task;

/**
 * The API of the Export component.
 */
public interface Export {
    /**
     * Saves the filteredTasks to the storage.
     * @throws IOException if there was any problem writing to the file.
     * @throws IllegalValueException if the current Taskbook is empty.
     */
    void saveFilteredTasks() throws IOException, IllegalValueException;

    /**
     * @see #saveFilteredTasks()
     */
    void saveFilteredTasks(ObservableList<Task> filteredTasks, Path filePath)
            throws IOException, IllegalValueException;

	void saveFilteredAccountList(ObservableList<Account> filteredAccountList, Path filePath)
			throws IOException, IllegalValueException;

	void saveFilteredAccountList() throws IOException, IllegalValueException;

}
