package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Range;
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

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
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

    //=========== LeavesBook ================================================================================

    /**
     * Parses a {@code String title} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Title parseTitle(String title) {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String start} {@code String end} into an {@code Range}. Both start and
     * end must be non-null.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param start Non-null string containing the start date
     * @param end Non-null string containing the end date
     * @throws ParseException if the given {@code start} and {@code end} is invalid, or if
     *      the end date is before the start date
     */
    public static Range parseNonNullRange(String start, String end) throws ParseException {
        requireNonNull(end);
        requireNonNull(start);

        try {
            String trimmedStart = start.trim();
            String trimmedEnd = end.trim();

            Date startDate = Date.of(trimmedStart);
            Date endDate = Date.of(trimmedEnd);

            return Range.createNonNullRange(startDate, endDate);
        } catch (DateTimeParseException e) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        } catch (EndBeforeStartException e) {
            throw new ParseException(Range.MESSAGE_INVALID_END_DATE);
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
            Date startDate = hasStart ? Date.of(start.trim()) : null;
            Date endDate = hasEnd ? Date.of(end.trim()) : null;

            return Range.createNullableRange(startDate, endDate);
        } catch (DateTimeParseException e) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        } catch (EndBeforeStartException e) {
            throw new ParseException(Range.MESSAGE_INVALID_END_DATE);
        }
    }

    /**
     * Parses a {@code String description} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Description parseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new Description(trimmedDescription);
    }
}
