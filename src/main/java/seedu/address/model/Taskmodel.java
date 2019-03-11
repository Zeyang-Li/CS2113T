package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

public interface Taskmodel {

	List<Task> getFilteredTaskList();

	boolean hasTask(Task editedTask);

	void setTask(Task taskToEdit, Task editedTask);

	void updateFilteredTaskList(Predicate<Person> predicateShowAllPersons);

	void commitAddressBook();

}
