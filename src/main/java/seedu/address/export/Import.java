package seedu.address.export;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAccountList;
import seedu.address.model.ReadOnlyTaskBook;


/**
 * The API of the Import component.
 */
public interface Import {

    /**
     * Returns the taskBook from the xml file specified.
     * @throws DataConversionException if the file is not in the correct format.
     * @throws FileNotFoundException if the file does not exist
     */
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    /**
     * Similar to {@link #readTaskBook()}
     * @param filePath location of the data. Cannot be null
     */
    Optional<ReadOnlyTaskBook> readTaskBook(Path filePath) throws DataConversionException, FileNotFoundException;

    Optional<ReadOnlyAccountList> readAccountList() throws DataConversionException, IOException;

}
