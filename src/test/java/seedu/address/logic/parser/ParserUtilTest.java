package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.InvalidIndexException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Status.StatusType;
import seedu.address.model.leave.Title;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_TITLE = "Leave Title";
    private static final String VALID_DESCRIPTION = "Leave Description";
    private static final String VALID_START_DATE = "2020-01-01";
    private static final String VALID_END_DATE = "2020-01-02";
    private static final String VALID_STATUS = StatusType.APPROVED.toString();

    private static final String INVALID_TITLE = "B@d T!tl3";

    private static final String INVALID_START_DATE = "2020/01/01";
    private static final String INVALID_END_DATE = "2020/01/02";

    private static final String START_DATE_LATE = VALID_END_DATE;
    private static final String END_DATE_EARLY = VALID_START_DATE;

    private static final String INVALID_DESCRIPTION = "*** ***";
    private static final String INVALID_STATUS = "STATUS LOST";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIndex(null));
    }
    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        // no numeric index at all
        assertThrows(ParseException.class, ParserUtil.MESSAGE_INVALID_INDEX, () ->
                ParserUtil.parseIndex("abc"));

        // "10a" is not numeric; even though we could potentially extract "10" out of it
        // but it's better not to, to avoid dealing with cases like "1a2b3c4a"
        assertThrows(ParseException.class, ParserUtil.MESSAGE_INVALID_INDEX, () ->
                ParserUtil.parseIndex("10a"));

        // numeric index must come at the start
        assertThrows(ParseException.class, ParserUtil.MESSAGE_INVALID_INDEX, () ->
                ParserUtil.parseIndex("abc 10"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsInvalidIndexException() {
        // reject negative indices
        assertThrows(InvalidIndexException.class, InvalidIndexException.MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex("-1"));
        // reject zero index
        assertThrows(InvalidIndexException.class, InvalidIndexException.MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex("0"));

        // reject indices beyond Integer.MAX_VALUE
        String exceedIntMaxInput = Long.toString((long) Integer.MAX_VALUE + 1);
        assertThrows(ParseException.class, ParserUtil.MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex(exceedIntMaxInput));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex(WHITESPACE + "1" + WHITESPACE));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress(null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail(null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTitle_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle(null));
    }

    @Test
    public void parseTitle_validTitleNoWhitespace_returnsTitle() throws ParseException {
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(VALID_TITLE));
    }

    @Test
    public void parseTitle_validTitleWithWhitespace_returnsTitle() throws ParseException {
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(WHITESPACE + VALID_TITLE + WHITESPACE));
    }

    @Test
    public void parseTitle_invalidTitle_throwsParseException() throws ParseException {
        assertThrows(ParseException.class, Title.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseTitle(INVALID_TITLE));
    }

    @Test
    public void parseNonNullRange_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNonNullRange(null, VALID_START_DATE));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNonNullRange(VALID_START_DATE, null));
    }

    @Test
    public void parseNonNullRange_invalidDate_throwsException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNonNullRange(INVALID_START_DATE, VALID_END_DATE));
        assertThrows(ParseException.class, () -> ParserUtil.parseNonNullRange(VALID_START_DATE, INVALID_END_DATE));
    }

    @Test
    public void parseNonNullRange_endBeforeStart_throwsException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNonNullRange(START_DATE_LATE, END_DATE_EARLY));
    }

    @Test
    public void parseNonNullRange_validDates_returnRange() throws Exception {
        Range expected = Range.createNonNullRange(Date.of(VALID_START_DATE), Date.of(VALID_END_DATE));
        assertEquals(ParserUtil.parseNonNullRange(VALID_START_DATE, VALID_END_DATE), expected);
    }

    @Test
    public void parseDate_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
    }

    @Test
    public void parseDate_validDateNoWhitespace_returnsDate() throws Exception {
        Date expected = Date.of(VALID_START_DATE);
        assertEquals(ParserUtil.parseDate(VALID_START_DATE), expected);
    }

    @Test
    public void parseDate_validDateWithWhitespace_returnsDate() throws Exception {
        Date expected = Date.of(VALID_START_DATE);
        assertEquals(ParserUtil.parseDate(WHITESPACE + VALID_START_DATE + WHITESPACE), expected);
    }

    @Test
    public void parseDate_invalidDate_throwsParseException() throws Exception {
        assertThrows(ParseException.class, Date.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseDate(INVALID_START_DATE));
    }

    @Test
    public void parseNullableRange_invalidDate_throwsException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNullableRange(INVALID_START_DATE, VALID_END_DATE));
        assertThrows(ParseException.class, () -> ParserUtil.parseNullableRange(VALID_START_DATE, INVALID_END_DATE));
    }

    @Test
    public void parseNullableRange_endBeforeStart_throwsException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNullableRange(START_DATE_LATE, END_DATE_EARLY));
    }

    @Test
    public void parseNullableRange_validDates_returnRange() throws Exception {
        Range expected;

        // start and end date present
        expected = Range.createNullableRange(Date.of(VALID_START_DATE), Date.of(VALID_END_DATE));
        assertEquals(ParserUtil.parseNullableRange(VALID_START_DATE, VALID_END_DATE), expected);

        // start date present
        expected = Range.createNullableRange(Date.of(VALID_START_DATE), null);
        assertEquals(ParserUtil.parseNullableRange(VALID_START_DATE, null), expected);

        // end date present
        expected = Range.createNullableRange(null, Date.of(VALID_END_DATE));
        assertEquals(ParserUtil.parseNullableRange(null, VALID_END_DATE), expected);

        // start and end date not present
        expected = Range.createNullableRange(null, null);
        assertEquals(ParserUtil.parseNullableRange(null, null), expected);
    }

    @Test
    public void parseDescription_nullDescription_throwsException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription(null));
    }

    @Test
    public void parseDescription_validDescriptionNoWhitespace_returnsDescription() throws Exception {
        Description expected = new Description(VALID_DESCRIPTION);
        assertEquals(ParserUtil.parseDescription(VALID_DESCRIPTION), expected);
    }

    @Test
    public void parseDescription_validDescriptionWithWhitespace_returnsDescription() throws Exception {
        Description expected = new Description(VALID_DESCRIPTION);
        assertEquals(ParserUtil.parseDescription(WHITESPACE + VALID_DESCRIPTION + WHITESPACE), expected);
    }

    @Test
    public void parseDescription_invalidDescription_throwsParseException() {
        assertThrows(ParseException.class, Description.MESSAGE_CONSTRAINTS, () ->
                ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseStatus_nullStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStatus(null));
    }

    @Test
    public void parseStatus_validStatusNoWhitespace_returnsStatus() throws Exception {
        Status expected = Status.of(VALID_STATUS);
        assertEquals(ParserUtil.parseStatus(VALID_STATUS), expected);
    }

    @Test
    public void parseStatus_validStatusWithWhitespace_returnsStatus() throws Exception {
        Status expected = Status.of(VALID_STATUS);
        assertEquals(ParserUtil.parseStatus(WHITESPACE + VALID_STATUS + WHITESPACE), expected);
    }

    @Test
    public void parseStatus_invalidStatus_throwsParseException() {
        assertThrows(ParseException.class, Status.MESSAGE_CONSTRAINTS, () -> ParserUtil.parseStatus(INVALID_STATUS));
    }
}
