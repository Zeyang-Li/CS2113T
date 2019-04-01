package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's category in the task book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCategories(String)}
 */
public class Categories {

    public static final String CAT_ACADEMIC = "a";
    public static final String CAT_CCA = "c";
    public static final String CAT_ENTERTAINMENT = "e";
    public static final String CAT_ERRAND = "r";
    public static final String CAT_OTHER = "o";

    public static final String MESSAGE_CONSTRAINTS =
            "Category should contains 1 alphabet\n"
            + "Should be a (academic), c (cca), e (entertainment), r (errand), o (other)";
    public static final String VALIDATION_REGEX = "[acero]";
    public final String value;
    public final String fullName;

    /**
     * Constructs a {@code Categories}.
     *
     * @param categories A valid categories.
     */
    public Categories(String categories) {
        requireNonNull(categories);
        checkArgument(isValidCategories(categories), MESSAGE_CONSTRAINTS);
        value = categories;
        if (value.equalsIgnoreCase(CAT_ACADEMIC)) {
            fullName = "Academic";
        } else if (value.equalsIgnoreCase(CAT_CCA)) {
            fullName = "Cca";
        } else if (value.equalsIgnoreCase(CAT_ENTERTAINMENT)) {
            fullName = "Entertainment";
        } else if (value.equalsIgnoreCase(CAT_ERRAND)) {
            fullName = "Errand";
        } else if (value.equalsIgnoreCase(CAT_OTHER)) {
            fullName = "Other";
        } else {
            fullName = "Invalid";
        }
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidCategories(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Categories // instanceof handles nulls
                && value.equals(((Categories) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
