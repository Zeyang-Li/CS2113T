package seedu.address.export;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.opencsv.CSVWriter;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ModelManager;
import seedu.address.model.task.Task;

/**
 * The writer for CSV file of the app.
 */
public class CsvWriter {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static final int INDEX_TASK_NAME = 0;
    private static final int INDEX_TASK_STARTDATE = 1;
    private static final int INDEX_TASK_STARTTIME = 2;
    private static final int INDEX_TASK_ENDDATE = 3;
    private static final int INDEX_TASK_ENDTIME = 4;
    private static final int INDEX_TASK_DESCRIPTION = 5;
    private static final int INDEX_TASK_CATEGRORIES = 6;

    private final String[] header = { "Name", "StartDate", "StartTime", "EndDate", "EndTime", "Description", "Categories" };
    private final ObservableList<Task> listOfTasks;
    private final Path outputFilepath;
    private final Task task;

    public CsvWriter(Task task, Path outputFilepath) {
        requireAllNonNull(task, outputFilepath);

        if (!FileUtil.isFileExists(outputFilepath)) {
            try {
                FileUtil.createFile(outputFilepath);
            } catch (IOException e) {
                logger.severe("Error creating output file: " + outputFilepath.toString());
            }
        } else {
            logger.fine("Initializing with output file: " + outputFilepath.toString());
        }

        this.task = task;
        this.listOfTasks = null;
        this.outputFilepath = outputFilepath;
    }

    public CsvWriter(ObservableList<Task> tasks, Path outputFilepath) {
        requireAllNonNull(tasks);

        if (!FileUtil.isFileExists(outputFilepath)) {
            try {
                FileUtil.createFile(outputFilepath);
            } catch (IOException e) {
                logger.severe("Error creating output file: " + outputFilepath.toString());
            }
        } else {
            logger.fine("Initializing with output file: " + outputFilepath.toString());
        }

        this.listOfTasks = tasks;
        this.task = null;
        this.outputFilepath = outputFilepath;
    }

    /**
     * Writes to the file as defined in {@code outputFilepath}.
     */
    public void write() throws IOException {
        try {

            File file = new File(String.valueOf(outputFilepath));
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);

            writer.writeNext(header);

            if (listOfTasks != null) {
                writeMultiplePersons(writer);
            } else if (task != null) {
                writeSinglePerson(writer);
            }

            writer.close();

        } catch (IOException e) {
            throw new IOException();
        }
    }

    /**
     * Writes the {@code listOfPersons} to the csv file.
     */
    private void writeMultiplePersons(CSVWriter writer) {
        List<String[]> data = new ArrayList<>();
        listOfTasks.forEach(person -> {
            String[] personDetails = convertToStringArray(task);
            data.add(personDetails);
        });

        writer.writeAll(data);
    }

    /**
     * Writes the {@code person} to the csv file.
     */
    private void writeSinglePerson(CSVWriter writer) {
        String[] personDetails = convertToStringArray(task);

        writer.writeNext(personDetails);
    }


    /**
     * Returns a string array that contains the details of a {@code task}.
     * @param task {@code Task} to be saved to the string array.
     * @return A string array containing the name, phone, address, and email of
     *          the {@code person}.
     */
    private String[] convertToStringArray(Task task) {
        String[] taskDetails = new String[header.length];
        taskDetails[INDEX_TASK_NAME] = task.getName().toString();
        taskDetails[INDEX_TASK_STARTDATE] = task.getStartDate().toString();
        taskDetails[INDEX_TASK_STARTTIME] = task.getStartTime().toString();
        taskDetails[INDEX_TASK_ENDDATE] = task.getEndDate().toString();
        taskDetails[INDEX_TASK_ENDTIME] = task.getEndTime().toString();
        taskDetails[INDEX_TASK_DESCRIPTION] = task.getDescription().toString();
        taskDetails[INDEX_TASK_CATEGRORIES] = task.getCategories().toString();
        return taskDetails;
    }

    public Path getOutputFilepath() {
        return outputFilepath;
    }
}
