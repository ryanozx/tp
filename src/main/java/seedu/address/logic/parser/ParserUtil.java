package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.InvalidIndexException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Title;
import seedu.address.model.leave.exceptions.EndBeforeStartException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not an integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();

        // Once trimmed, first check the completely invalid formats, ie empty string, or non-integer
        // Then check if is a non-zero unsigned integer

        if (!StringUtil.isInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new InvalidIndexException();
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
        try {
            String trimmedName = name.trim();
            return new Name(trimmedName);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        try {
            String trimmedPhone = phone.trim();
            return new Phone(trimmedPhone);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        try {
            String trimmedAddress = address.trim();
            return new Address(trimmedAddress);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        try {
            String trimmedEmail = email.trim();
            return new Email(trimmedEmail);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        try {
            String trimmedTag = tag.trim();
            return new Tag(trimmedTag);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     *
     * @throws ParseException if at least one of the tags in {@code Collection<String> tags} is invalid.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    //=========== LeavesBook ================================================================================

    /**
     * Parses a {@code String title} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if a title cannot be constructed due to illegal characters
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        try {
            String trimmedTitle = title.trim();
            return new Title(trimmedTitle);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String start} {@code String end} into an {@code Range}. Both start and
     * end must be non-null.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param start Non-null string containing the start date
     * @param end Non-null string containing the end date
     * @throws NullPointerException if either start or end is empty
     * @throws ParseException if the given {@code start} and {@code end} is invalid, or if
     *      the end date is before the start date
     */
    public static Range parseNonNullRange(String start, String end) throws NullPointerException, ParseException {
        requireNonNull(end);
        requireNonNull(start);

        try {
            String trimmedStart = start.trim();
            String trimmedEnd = end.trim();

            Date startDate = parseDate(trimmedStart);
            Date endDate = parseDate(trimmedEnd);

            return Range.createNonNullRange(startDate, endDate);
        } catch (EndBeforeStartException e) {
            throw new ParseException(Range.MESSAGE_END_BEFORE_START_ERROR);
        }
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param date Non-null string containing the date
     * @throws ParseException if the given {@code start} and {@code end} is invalid, or if
     *      the end date is before the start date
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        try {
            String trimmedDate = date.trim();
            return Date.of(trimmedDate);
        } catch (DateTimeParseException e) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String start} {@code String end} into an {@code Range}.
     * Start and end can be null.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param start String containing the start date / null
     * @param end String containing the end date / null
     * @throws ParseException if the given {@code start} and {@code end} is invalid, or if
     *      the end date is before the start date
     */
    public static Range parseNullableRange(String start, String end) throws ParseException {
        try {
            boolean hasStart = start != null;
            boolean hasEnd = end != null;
            Date startDate = hasStart ? parseDate(start.trim()) : null;
            Date endDate = hasEnd ? parseDate(end.trim()) : null;

            return Range.createNullableRange(startDate, endDate);
        } catch (EndBeforeStartException e) {
            throw new ParseException(Range.MESSAGE_END_BEFORE_START_ERROR);
        }
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if description is not valid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        try {
            String trimmedDescription = description.trim();
            if (trimmedDescription.isEmpty()) {
                trimmedDescription = Description.DESCRIPTION_PLACEHOLDER;
            }
            return new Description(trimmedDescription);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String status} into a {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if status does not match any valid statuses.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        try {
            String trimmedStatus = status.trim();
            return Status.of(trimmedStatus);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
    }
}
