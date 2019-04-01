package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.day.Date;
import seedu.address.model.day.Day;
import seedu.address.model.day.UniqueDayList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Wraps all data at the task-book level
 * Duplicates are not allowed (by .isSameTask comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList tasks;
    private final UniqueDayList days;
    private Map<Date, Day> dayMap;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        days = new UniqueDayList();
        dayMap = new HashMap<>();
    }

    public TaskBook() {}

    /**
     * Creates an TaskBook using the Tasks in the {@code toBeCopied}
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setDatas(List<Task> tasks, List<Day> days) {
        this.tasks.setTasks(tasks);
        this.days.setDays(days);
        this.dayMap = new HashMap<>();
        resetDayMap(tasks);
        indicateModified();
    }

    /**
     * Reset the dayMap after reset data
     * @param tasks
     */
    public void resetDayMap(List<Task> tasks) {
        dayMap.clear();
        days.clear();
        for (int i = 0; i <= tasks.size() - 1; i++) {
            Task t = tasks.get(i);
            String dateS = t.getStartDate().toString();
            String dateE = t.getEndDate().toString();
            if (dateS.equalsIgnoreCase(dateE)) {
                Date date = new Date(dateS);
                if (!dayMap.containsKey(date)) {
                    dayMap.put(date, new Day(date));
                    Day a = dayMap.get(date);
                    days.add(a);
                }
                Day d = dayMap.get(date);
                dayMap.remove(date, d);
                days.remove(d);
                d.addCategory(t);
                dayMap.put(date, d);
                addDay(d);
            }
        }
    }

    /**
     * Resets the existing data of this {@code TaskBook} with {@code newData}.
     */
    public void resetData(ReadOnlyTaskBook newData) {
        requireNonNull(newData);

        setDatas(newData.getTaskList(), newData.getDayList());
    }

    //// task-level operations

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task book.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Returns true if a day with the same identity as {@code day} exists in the task book.
     */
    public boolean hasDay(Day day) {
        requireNonNull(day);
        return days.contains(day);
    }


    /**
     * Adds a task to the task book.
     * The task must not already exist in the task book.
     */
    public void addTask(Task t) {
        tasks.add(t);
        String dateS = t.getStartDate().toString();
        String dateE = t.getEndDate().toString();
        if (dateS.equalsIgnoreCase(dateE)) {
            Date date = new Date(dateS);
            if (!dayMap.containsKey(date)) {
                dayMap.put(date, new Day(date));
                Day a = dayMap.get(date);
                days.add(a);
            }
            Day d = dayMap.get(date);
            dayMap.remove(date, d);
            days.remove(d);
            d.addCategory(t);
            dayMap.put(date, d);
            addDay(d);
            return;
        }
        indicateModified();
    }

    /**
     * Add day into uniqueDayList
     * @param d
     */
    public void addDay(Day d) {
        days.add(d);
        indicateModified();
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the task book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the task book.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);
        tasks.setTask(target, editedTask);
        String targetDateS = target.getStartDate().toString();
        String targetDateE = target.getEndDate().toString();
        String editedDateS = editedTask.getStartDate().toString();
        String editedDateE = editedTask.getEndDate().toString();

        if (targetDateE.equalsIgnoreCase(targetDateS) && !editedDateE.equalsIgnoreCase(editedDateS)) {
            Date date = new Date(targetDateS);
            if (!dayMap.containsKey(date)) {
            }
            Day d = dayMap.get(date);
            days.remove(d);
            dayMap.remove(date, d);
            d.removeCategory(target);
            if (!d.isDayEmpty()) {
                days.add(d);
                dayMap.put(date, d);
            }
            indicateModified();
        } else if (!targetDateE.equalsIgnoreCase(targetDateS) && editedDateE.equalsIgnoreCase(editedDateS)) {
            Date date = new Date(editedDateS);
            if (!dayMap.containsKey(date)) {
                dayMap.put(date, new Day(date));
                Day a = dayMap.get(date);
                dayMap.remove(date, a);
                days.add(a);
                dayMap.put(date, a);
            }
            Day d = dayMap.get(date);
            dayMap.remove(date);
            days.remove(d);
            d.addCategory(editedTask);
            addDay(d);
            dayMap.put(date, d);
            indicateModified();
        } else if (targetDateE.equalsIgnoreCase(targetDateS) && editedDateE.equalsIgnoreCase(editedDateS)) {
            Date targetStartDate = new Date(targetDateS);
            Date editedStartDate = new Date(editedDateS);

            if (!targetStartDate.equals(editedStartDate)) {
                if (!dayMap.containsKey(editedStartDate)) {
                    dayMap.put(editedStartDate, new Day(editedStartDate));
                }
                Day eD = dayMap.get(editedStartDate);
                days.add(eD);
                days.remove(eD);
                eD.addCategory(editedTask);
                days.add(eD);

                Day tD = dayMap.get(targetStartDate);
                days.remove(tD);
                tD.removeCategory(target);
                days.add(tD);

                indicateModified();
                return;
            }
            Day tD = dayMap.get(targetStartDate);
            days.remove(tD);
            tD.editCategory(target, editedTask);
            addDay(tD);
            indicateModified();
        }
    }

    /**
     * Removes {@code key} from this {@code TaskBook}.
     * {@code key} must exist in the task book.
     */
    public void removeTask(Task key) {
        String dateS = key.getStartDate().toString();
        String dateE = key.getEndDate().toString();
        if (dateS.equalsIgnoreCase(dateE)) {
            Date date = new Date(dateS);
            if (!dayMap.containsKey(date)) {
            }
            Day d = dayMap.get(date);
            days.remove(d);
            d.removeCategory(key);
            if (!d.isDayEmpty()) {
                days.add(d);
            }
            tasks.remove(key);
            indicateModified();
            return;
        }
        tasks.remove(key);
        indicateModified();
    }

    /**
     * Sort tasks in the storage by given comparator.
     */
    public void sortTaskByDate(Comparator<Task> comparator) {
        tasks.sortByDate(comparator);
        indicateModified();
    }


    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the task book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asUnmodifiableObservableList().size() + " tasks" + days.asUnmodifiableObservableList().size()
                + " days";
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Day> getDayList() {
        return days.asUnmodifiableObservableList();
    }

    @Override
    public Map<Date, Day> getDayMap() {
        return this.dayMap;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && tasks.equals(((TaskBook) other).tasks));
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }
}
