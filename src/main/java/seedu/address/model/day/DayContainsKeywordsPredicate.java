package seedu.address.model.day;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Day}'s {@code date} matches any of the keywords given.
 */
public class DayContainsKeywordsPredicate implements Predicate<Day> {
    private final List<String> keywords;

    public DayContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Day day) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(day.getDate().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DayContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DayContainsKeywordsPredicate) other).keywords)); // state check
    }

}
