package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyDaysKeeper;
import seedu.address.model.DaysKeeper;
import seedu.address.model.day.Day;

/**
 * An Immutable daysKeeper that is serializable to JSON format.
 */
@JsonRootName(value = "dayskeeper")
class JsonSerializableDaysKeeper {

    public static final String MESSAGE_DUPLICATE_DAY = "Days list contains duplicate day(s).";

    private final List<JsonAdaptedDay> days = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableDaysKeeper} with the given days.
     */
    @JsonCreator
    public JsonSerializableDaysKeeper(@JsonProperty("days") List<JsonAdaptedDay> days) {
        this.days.addAll(days);
    }

    /**
     * Converts a given {@code ReadOnlyDaysKeeper} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableDaysKeeper}.
     */
    public JsonSerializableDaysKeeper(ReadOnlyDaysKeeper source) {
        days.addAll(source.getDayList().stream().map(JsonAdaptedDay::new).collect(Collectors.toList()));
    }

    /**
     * Converts this days keeper into the model's {@code DaysKeeper} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DaysKeeper toModelType() throws IllegalValueException {
        DaysKeeper daysKeeper = new DaysKeeper();
        for (JsonAdaptedDay jsonAdaptedDay : days) {
            Day day = jsonAdaptedDay.toModelType();
            if (daysKeeper.hasDay(day)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DAY);
            }
            daysKeeper.addDay(day);
        }
        return daysKeeper;
    }

}
