package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.isValidPath;
import static seedu.address.commons.util.FileUtil.isValidXmlFilename;
import static seedu.address.model.Filetype.isValidFiletype;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Filetype;
import seedu.address.model.account.Password;
import seedu.address.model.account.Username;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Categories;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.StartTime;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_FILENAME = "Filename is invalid.";
    public static final String MESSAGE_INVALID_EXTENSION = "Filename must end with \".xml\".";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String startDate} into a {@code StartDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code startDate} is invalid.
     */
    public static StartDate parseStartDate(String startDate) throws ParseException {
        requireNonNull(startDate);
        String trimmedStartDate = startDate.trim();
        if (!StartDate.isValidStartDate(trimmedStartDate)) {
            throw new ParseException(StartDate.MESSAGE_CONSTRAINTS);
        }
        return new StartDate(trimmedStartDate);
    }

    /**
     * Parses a {@code String startTime} into a {@code StartTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code startTime} is invalid.
     */
    public static StartTime parseStartTime(String startTime) throws ParseException {
        requireNonNull(startTime);
        String trimmedStartTime = startTime.trim();
        if (!StartTime.isValidStartTime(trimmedStartTime)) {
            throw new ParseException(StartTime.MESSAGE_CONSTRAINTS);
        }
        return new StartTime(trimmedStartTime);
    }

    /**
     * Parses a {@code String endDate} into a {@code EndDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code endDate} is invalid.
     */
    public static EndDate parseEndDate(String endDate) throws ParseException {
        requireNonNull(endDate);
        String trimmedEndDate = endDate.trim();
        if (!EndDate.isValidEndDate(trimmedEndDate)) {
            throw new ParseException(EndDate.MESSAGE_CONSTRAINTS);
        }
        return new EndDate(trimmedEndDate);
    }

    /**
     * Parses a {@code String endTime} into a {@code EndTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code endTime} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static EndTime parseEndTime(String endTime) throws ParseException {
        requireNonNull(endTime);
        String trimmedEndTime = endTime.trim();
        if (!EndTime.isValidEndTime(trimmedEndTime)) {
            throw new ParseException(EndTime.MESSAGE_CONSTRAINTS);
        }
        return new EndTime(trimmedEndTime);
    }

    /**
     * Parses a {@code String category} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static Categories parseCategories(String categories) throws ParseException {
        requireNonNull(categories);
        String trimmedCategories = categories.trim();
        if (!Categories.isValidCategories(trimmedCategories)) {
            throw new ParseException(Categories.MESSAGE_CONSTRAINTS);
        }
        return new Categories(trimmedCategories);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code String filetype} into a {@code Filetype}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String filetype} is invalid.
     */
    public static Filetype parseFiletype(String filetype) throws ParseException {
        requireNonNull(filetype);

        String trimmedFiletype = filetype.trim();
        if (!isValidFiletype(trimmedFiletype)) {
            throw new ParseException(Filetype.MESSAGE_FILETYPE_CONSTRAINTS);
        }
        return new Filetype(trimmedFiletype);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a String with white space into a String without white space.
     */
    public static String parseCategory(String s) {
        requireNonNull(s);
        String trimmedCategory = s.trim();
        return trimmedCategory;
    }

    /**
     * Parses a {@code String filename} into a {@code Path}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code filename} is invalid.
     */
    public static Path parseFilename(String filename) throws ParseException {
        requireNonNull(filename);

        String trimmedFilename = filename.trim();
        if (!isValidPath(trimmedFilename)) {
            throw new ParseException(MESSAGE_INVALID_FILENAME);
        }
        if (!isValidXmlFilename(trimmedFilename)) {
            throw new ParseException(MESSAGE_INVALID_EXTENSION);
        }
        return Paths.get("data", trimmedFilename);
    }

    /**
     * Parses a {@code String username} into a {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code username} is invalid.
     */
    public static Username parseUsername(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new ParseException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return new Username(trimmedUsername);
    }



    /**
     * Parses a {@code String password} into a {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code password} is invalid.
     */
    public static Password parsePassword(String password) throws ParseException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return new Password(trimmedPassword);
    }
}
