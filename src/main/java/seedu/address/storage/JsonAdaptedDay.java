package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.day.Academic;
import seedu.address.model.day.Cca;
import seedu.address.model.day.Date;
import seedu.address.model.day.Day;
import seedu.address.model.day.Entertainment;
import seedu.address.model.day.Errand;
import seedu.address.model.day.Other;
import seedu.address.model.day.Week;

/**
 * Jackson-friendly version of {@link Day}.
 */
class JsonAdaptedDay {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Date's %s field is missing!";

    private final String date;
    private final String weekStart;
    private final String weekEnd;
    private final String academicTime;
    private final String academicLimit;
    private final String ccaTime;
    private final String ccaLimit;
    private final String entertainmentTime;
    private final String entertainmentLimit;
    private final String errandTime;
    private final String errandLimit;
    private final String otherTime;
    private final String otherLimit;

    /**
     * Constructs a {@code JsonAdaptedDay) with the given day details.
     */
    @JsonCreator
    public JsonAdaptedDay(@JsonProperty("date") String date, @JsonProperty("week start") String weekStart,
                          @JsonProperty("week end") String weekEnd, @JsonProperty("academic time") String academicTime,
                          @JsonProperty("academic limit") String academicLimit,
                          @JsonProperty("cca time") String ccaTime, @JsonProperty("cca limit") String ccaLimit,
                          @JsonProperty("entertainment time") String entertainmentTime,
                          @JsonProperty("entertainment limit") String entertainmentLimit,
                          @JsonProperty("errand time") String errandTime,
                          @JsonProperty("errand limit") String errandLimit,
                          @JsonProperty("other time") String otherTime, @JsonProperty("other limit") String otherLimit)
    {
        this.date = date;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.academicTime = academicTime;
        this.academicLimit = academicLimit;
        this.ccaTime = ccaTime;
        this.ccaLimit = ccaLimit;
        this.entertainmentTime = entertainmentTime;
        this.entertainmentLimit = entertainmentLimit;
        this.errandTime = errandTime;
        this.errandLimit = errandLimit;
        this.otherTime = otherTime;
        this.otherLimit = otherLimit;
    }

    /**
     * Converts a given {@code Day} into this class for Jackson use.
     */
    public JsonAdaptedDay(Day source) {
        date = source.getDate().value;
        weekStart = source.getWeek().getStart();
        weekEnd = source.getWeek().getEnd();
        academicTime = source.getAcademic().getTime();
        academicLimit = source.getAcademic().getLimit();
        ccaTime = source.getCca().getTime();
        ccaLimit = source.getCca().getLimit();
        entertainmentTime = source.getEntertainment().getTime();
        entertainmentLimit = source.getEntertainment().getLimit();
        errandTime = source.getErrand().getTime();
        errandLimit = source.getErrand().getLimit();
        otherTime = source.getOther().getTime();
        otherLimit = source.getOther().getLimit();
    }

    /**
     * Converts this Jackson-friendly adapted day object into the model's {@code Day} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted day.
     */
    public Day toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (weekStart == null && weekEnd == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Week.class.getSimpleName()));
        }
        if (!Week.isValidStart(weekStart)) {
            throw new IllegalValueException(Week.MESSAGE_START_CONSTRAINTS);
        }
        if (!Week.isValidEnd(weekEnd)) {
            throw new IllegalValueException(Week.MESSAGE_END_CONSTRAINTS);
        }
        final Week modelWeek = new Week(weekStart, weekEnd);

        if (academicTime == null && academicLimit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Academic.class.getSimpleName()));
        }
        if (!Academic.isValidTime(academicTime)) {
            throw new IllegalValueException(Academic.MESSAGE_TIME_CONSTRAINTS);
        }
        if (!Academic.isValidLimit(academicLimit)) {
            throw new IllegalValueException(Academic.MESSAGE_LIMIT_CONSTRAINTS);
        }
        final Academic modelAcademic = new Academic(academicTime, academicLimit);

        if (ccaTime == null && ccaLimit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Cca.class.getSimpleName()));
        }
        if (!Cca.isValidTime(ccaTime)) {
            throw new IllegalValueException(Cca.MESSAGE_TIME_CONSTRAINTS);
        }
        if (!Cca.isValidLimit(ccaLimit)) {
            throw new IllegalValueException(Cca.MESSAGE_LIMIT_CONSTRAINTS);
        }
        final Cca modelCca = new Cca(ccaTime, ccaLimit);

        if (entertainmentTime == null && entertainmentLimit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Entertainment.class.getSimpleName()));
        }
        if (!Entertainment.isValidTime(entertainmentTime)) {
            throw new IllegalValueException(Entertainment.MESSAGE_TIME_CONSTRAINTS);
        }
        if (!Entertainment.isValidLimit(entertainmentLimit)) {
            throw new IllegalValueException(Entertainment.MESSAGE_LIMIT_CONSTRAINTS);
        }
        final Entertainment modelEntertainment = new Entertainment(entertainmentTime, entertainmentLimit);

        if (errandTime == null && errandLimit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Errand.class.getSimpleName()));
        }
        if (!Errand.isValidTime(errandTime)) {
            throw new IllegalValueException(Errand.MESSAGE_TIME_CONSTRAINTS);
        }
        if (!Errand.isValidLimit(errandLimit)) {
            throw new IllegalValueException(Errand.MESSAGE_LIMIT_CONSTRAINTS);
        }
        final Errand modelErrand = new Errand(errandTime, errandLimit);

        if (otherTime == null && otherLimit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Other.class.getSimpleName()));
        }
        if (!Other.isValidTime(otherTime)) {
            throw new IllegalValueException(Other.MESSAGE_TIME_CONSTRAINTS);
        }
        if (!Other.isValidLimit(otherLimit)) {
            throw new IllegalValueException(Other.MESSAGE_LIMIT_CONSTRAINTS);
        }
        final Other modelOther = new Other(otherTime, otherLimit);

        return new Day(modelDate, modelWeek, modelAcademic, modelEntertainment, modelCca, modelErrand, modelOther);
    }

}
