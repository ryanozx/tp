package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_STATUS_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_DATE_END_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_DATE_START_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_EARLY_DATE_END_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_LATE_DATE_START_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DATE_END;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DATE_START;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_TITLE_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LEAVE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LEAVE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddLeaveCommand;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Title;
import seedu.address.testutil.LeaveBuilder;

public class AddLeaveCommandParserTest {
    private AddLeaveCommandParser parser = new AddLeaveCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // without description
        Leave expectedLeave = new LeaveBuilder().withTitle(VALID_LEAVE_TITLE)
                .withStart(Date.of(VALID_LEAVE_DATE_START)).withEnd(Date.of(VALID_LEAVE_DATE_END))
                .withDescription("NONE").build();
        Range dateRange = Range.createNonNullRange(expectedLeave.getStart(), expectedLeave.getEnd());

        assertParseSuccess(parser, " 1" + VALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                + VALID_LEAVE_END_DATE_DESC, new AddLeaveCommand(INDEX_FIRST_LEAVE, expectedLeave.getTitle(),
                dateRange, expectedLeave.getDescription()));


        // with description
        Leave expectedLeaveWithDescription = new LeaveBuilder().withTitle(VALID_LEAVE_TITLE)
                .withStart(Date.of(VALID_LEAVE_DATE_START)).withEnd(Date.of(VALID_LEAVE_DATE_END))
                .withDescription(VALID_LEAVE_DESCRIPTION).build();
        Range dateRangeWithDescription = Range.createNonNullRange(
                expectedLeaveWithDescription.getStart(), expectedLeaveWithDescription.getEnd());

        assertParseSuccess(parser, " 2" + VALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_DESCRIPTION_DESC,
                new AddLeaveCommand(INDEX_SECOND_LEAVE, expectedLeaveWithDescription.getTitle(),
                        dateRangeWithDescription , expectedLeaveWithDescription.getDescription()));
    }

    @Test
    public void parse_extraStatusPrefix_failure() {

        String expectedMessageWithoutDescription = String.format(MESSAGE_NO_STATUS_PREFIX,
                AddLeaveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " 3" + VALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                        + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_DESCRIPTION_DESC + " " + PREFIX_LEAVE_STATUS,
                expectedMessageWithoutDescription);
    }

    @Test
    public void parse_duplicatedFieldPrefix_failure() {
        String validExpectedLeaveString = " 3" + VALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_DESCRIPTION_DESC;

        // multiple title
        assertParseFailure(parser, VALID_LEAVE_TITLE_DESC + validExpectedLeaveString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_TITLE));

        // multiple startDate
        assertParseFailure(parser, VALID_LEAVE_START_DATE_DESC + validExpectedLeaveString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_DATE_START));

        // multiple endDate
        assertParseFailure(parser, VALID_LEAVE_END_DATE_DESC + validExpectedLeaveString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_DATE_END));

        // multiple description
        assertParseFailure(parser, VALID_LEAVE_DESCRIPTION_DESC + validExpectedLeaveString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_DESCRIPTION));

        // multiple fields repeated
        assertParseFailure(parser, VALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                        + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_DESCRIPTION_DESC + validExpectedLeaveString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_TITLE, PREFIX_LEAVE_DATE_START,
                        PREFIX_LEAVE_DATE_END, PREFIX_LEAVE_DESCRIPTION));

        // invalid value followed by valid value

        // invalid title
        assertParseFailure(parser, validExpectedLeaveString + INVALID_LEAVE_TITLE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_TITLE));

        // invalid startDate
        assertParseFailure(parser, validExpectedLeaveString + INVALID_LEAVE_DATE_START_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_DATE_START));

        // invalid endDate
        assertParseFailure(parser, validExpectedLeaveString + INVALID_LEAVE_DATE_END_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_DATE_END));

        // invalid description
        assertParseFailure(parser, validExpectedLeaveString + INVALID_LEAVE_DESCRIPTION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_DESCRIPTION));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLeaveCommand.MESSAGE_USAGE);

        // missing index
        assertParseFailure(parser, VALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                        + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_DESCRIPTION_DESC, expectedMessage);

        // missing title prefix
        assertParseFailure(parser, " 3" + VALID_LEAVE_START_DATE_DESC
                        + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_DESCRIPTION_DESC, expectedMessage);

        // missing startDate prefix
        assertParseFailure(parser, " 3" + VALID_LEAVE_TITLE_DESC
                        + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_DESCRIPTION_DESC, expectedMessage);

        // missing endDate prefix
        assertParseFailure(parser, " 3" + VALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                        + VALID_LEAVE_DESCRIPTION_DESC, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " 3" + VALID_LEAVE_TITLE + VALID_LEAVE_DATE_START
                                + VALID_LEAVE_DATE_END + VALID_LEAVE_DESCRIPTION, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid title
        assertParseFailure(parser, " 3" + INVALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_DESCRIPTION_DESC, Title.MESSAGE_CONSTRAINTS);

        // invalid startDate
        assertParseFailure(parser, " 3" + VALID_LEAVE_TITLE_DESC + INVALID_LEAVE_DATE_START_DESC
                + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_DESCRIPTION_DESC, Date.MESSAGE_CONSTRAINTS);

        // invalid endDate
        assertParseFailure(parser, " 3" + VALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                + INVALID_LEAVE_DATE_END_DESC + VALID_LEAVE_DESCRIPTION_DESC, Date.MESSAGE_CONSTRAINTS);

        // invalid endDate which is earlier than the startDate
        assertParseFailure(parser, " 3" + VALID_LEAVE_TITLE_DESC + INVALID_LEAVE_LATE_DATE_START_DESC
                + INVALID_LEAVE_EARLY_DATE_END_DESC + VALID_LEAVE_DESCRIPTION_DESC, Range.MESSAGE_INVALID_END_DATE);

        // invalid description
        assertParseFailure(parser, " 3" + VALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                + VALID_LEAVE_END_DATE_DESC + INVALID_LEAVE_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, " 3" + INVALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                        + VALID_LEAVE_END_DATE_DESC + INVALID_LEAVE_DESCRIPTION_DESC,
                Title.MESSAGE_CONSTRAINTS);
    }
    @Test
    public void parse_invalidPreamble_failure() {
        String validExpectedLeaveString = VALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_DESCRIPTION_DESC;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLeaveCommand.MESSAGE_USAGE);

        // negative index
        assertParseFailure(parser, "-5" + validExpectedLeaveString, expectedMessage);

        // zero index
        assertParseFailure(parser, "0" + validExpectedLeaveString, expectedMessage);

        // duplicated index
        assertParseFailure(parser, "1 1" + validExpectedLeaveString, expectedMessage);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", expectedMessage);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", expectedMessage);
    }

}
