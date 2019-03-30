package seedu.address.storage;

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

/**
 * Jackson-friendly version of {@link Day}.
 */
class JsonAdaptedDay {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Date's %s field is missing!";

    private final String date;
    private final String academic;
    private final String cca;
    private final String entertainment;
    private final String errand;
    private final String other;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedDay(@JsonProperty("date") String date, @JsonProperty("AcademicTime") String academic,
                          @JsonProperty("ccaTime") String cca,
                          @JsonProperty("entertainmentTime") String entertainment,
                          @JsonProperty("errandTime") String errand, @JsonProperty("otherTime") String other) {

        this.date = date;
        this.academic = academic;
        this.cca = cca;
        this.entertainment = entertainment;
        this.errand = errand;
        this.other = other;
    }

    /**
     * Converts a given {@code Day} into this class for Jackson use.
     */
    public JsonAdaptedDay(Day source) {
        date = source.getDate().value;
        academic = source.getAcademic().time;
        cca = source.getCca().time;
        entertainment = source.getEntertainment().time;
        errand = source.getErrand().time;
        other = source.getOther().time;
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

        if (academic == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Academic.class.getSimpleName()));
        }
        if (!Academic.isValidTime(academic)) {
            throw new IllegalValueException(Academic.MESSAGE_CONSTRAINTS);
        }
        final Academic modelAcademic = new Academic(academic);

        if (cca == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Cca.class.getSimpleName()));
        }
        if (!Cca.isValidTime(cca)) {
            throw new IllegalValueException(Cca.MESSAGE_CONSTRAINTS);
        }
        final Cca modelCca = new Cca(cca);

        if (entertainment == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Entertainment.class.getSimpleName()));
        }
        if (!Entertainment.isValidTime(entertainment)) {
            throw new IllegalValueException(Entertainment.MESSAGE_CONSTRAINTS);
        }
        final Entertainment modelEntertainment = new Entertainment(entertainment);

        if (errand == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Errand.class.getSimpleName()));
        }
        if (!Errand.isValidTime(errand)) {
            throw new IllegalValueException(Errand.MESSAGE_CONSTRAINTS);
        }
        final Errand modelErrand = new Errand(errand);

        if (other == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Other.class.getSimpleName()));
        }
        if (!Other.isValidTime(other)) {
            throw new IllegalValueException(Other.MESSAGE_CONSTRAINTS);
        }
        final Other modelOther = new Other(other);

        return new Day(modelDate, modelAcademic, modelEntertainment, modelCca, modelErrand, modelOther);
    }

}
