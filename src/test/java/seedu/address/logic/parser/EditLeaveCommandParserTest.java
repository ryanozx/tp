package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_DATE_END_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_DATE_END_EARLY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_DATE_START_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_DATE_START_LATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEAVE_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DATE_END;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DATE_START;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_STATUS_APPROVED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_TITLE_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LEAVE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LEAVE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_LEAVE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditLeaveCommand;
import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Title;
import seedu.address.testutil.EditLeaveDescriptorBuilder;

public class EditLeaveCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditLeaveCommand.MESSAGE_USAGE);

    private final EditLeaveCommandParser parser = new EditLeaveCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, VALID_LEAVE_STATUS_DESC, MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "1", EditLeaveCommand.MESSAGE_NOT_EDITED);

        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        assertParseFailure(parser, "abc", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "10a", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "abc 10", MESSAGE_INVALID_FORMAT);
    }


    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_LEAVE_STATUS_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_LEAVE_STATUS_DESC, MESSAGE_INVALID_FORMAT);

        // non-numeric index
        assertParseFailure(parser, "abc" + VALID_LEAVE_STATUS_DESC, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "10a" + VALID_LEAVE_STATUS_DESC, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "abc 10" + VALID_LEAVE_STATUS_DESC, MESSAGE_INVALID_FORMAT);

        // out-of-bound index
        String intMaxPlusOne = Long.toString((long) Integer.MAX_VALUE + 1);
        assertParseFailure(parser, intMaxPlusOne + TAG_DESC_FRIEND, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + TAG_DESC_FRIEND, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string" + TAG_DESC_FRIEND, MESSAGE_INVALID_FORMAT);
    }

    @Test public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_LEAVE_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_LEAVE_STATUS_DESC, Status.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_LEAVE_DATE_START_DESC, Date.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_LEAVE_DATE_END_DESC, Date.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_LEAVE_TITLE_DESC, Title.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + INVALID_LEAVE_TITLE_DESC + VALID_LEAVE_STATUS_DESC,
                Title.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_LEAVE;
        String userInput = targetIndex.getOneBased() + VALID_LEAVE_TITLE_DESC + VALID_LEAVE_DESCRIPTION_DESC
            + VALID_LEAVE_START_DATE_DESC + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_STATUS_DESC;
        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder().withTitle(VALID_LEAVE_TITLE)
            .withDescription(VALID_LEAVE_DESCRIPTION).withStart(Date.of(VALID_LEAVE_DATE_START))
            .withEnd(Date.of(VALID_LEAVE_DATE_END)).withStatus(Status.of(VALID_LEAVE_STATUS_APPROVED)).build();
        EditLeaveCommand expectedCommand = new EditLeaveCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_LEAVE;
        String userInput = targetIndex.getOneBased() + VALID_LEAVE_DESCRIPTION_DESC + VALID_LEAVE_TITLE_DESC;
        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder().withTitle(VALID_LEAVE_TITLE)
            .withDescription(VALID_LEAVE_DESCRIPTION).build();
        EditLeaveCommand expectedCommand = new EditLeaveCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_LEAVE;
        String userInput = targetIndex.getOneBased() + VALID_LEAVE_TITLE_DESC;
        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder().withTitle(VALID_LEAVE_TITLE).build();
        EditLeaveCommand expectedCommand = new EditLeaveCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + VALID_LEAVE_DESCRIPTION_DESC;
        descriptor = new EditLeaveDescriptorBuilder().withDescription(VALID_LEAVE_DESCRIPTION).build();
        expectedCommand = new EditLeaveCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + VALID_LEAVE_START_DATE_DESC;
        descriptor = new EditLeaveDescriptorBuilder().withStart(Date.of(VALID_LEAVE_DATE_START)).build();
        expectedCommand = new EditLeaveCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + VALID_LEAVE_END_DATE_DESC;
        descriptor = new EditLeaveDescriptorBuilder().withEnd(Date.of(VALID_LEAVE_DATE_END)).build();
        expectedCommand = new EditLeaveCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + VALID_LEAVE_STATUS_DESC;
        descriptor = new EditLeaveDescriptorBuilder().withStatus(Status.of(VALID_LEAVE_STATUS_APPROVED)).build();
        expectedCommand = new EditLeaveCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_LEAVE;
        String userInput = targetIndex.getOneBased() + INVALID_LEAVE_TITLE_DESC + VALID_LEAVE_TITLE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_TITLE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + VALID_LEAVE_TITLE_DESC + INVALID_LEAVE_TITLE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_TITLE));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + VALID_LEAVE_TITLE_DESC + VALID_LEAVE_STATUS_DESC
            + VALID_LEAVE_START_DATE_DESC + VALID_LEAVE_TITLE_DESC + VALID_LEAVE_STATUS_DESC
            + VALID_LEAVE_START_DATE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_TITLE,
                    PREFIX_LEAVE_STATUS, PREFIX_LEAVE_DATE_START));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_LEAVE_TITLE_DESC + INVALID_LEAVE_STATUS_DESC
            + INVALID_LEAVE_DATE_START_DESC + INVALID_LEAVE_TITLE_DESC + INVALID_LEAVE_STATUS_DESC
            + INVALID_LEAVE_DATE_START_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_TITLE,
                    PREFIX_LEAVE_STATUS, PREFIX_LEAVE_DATE_START));
    }

    @Test
    public void parse_endBeforeStart_throwsError() {
        Index targetIndex = INDEX_FIRST_LEAVE;
        // ensure that order is thrown regardless of whether start or end is processed first
        String userInput = targetIndex.getOneBased() + VALID_LEAVE_TITLE_DESC + INVALID_LEAVE_DATE_END_EARLY_DESC
                + INVALID_LEAVE_DATE_START_LATE_DESC;
        assertParseFailure(parser, userInput, Range.MESSAGE_END_BEFORE_START_ERROR);

        userInput = targetIndex.getOneBased() + VALID_LEAVE_TITLE_DESC + INVALID_LEAVE_DATE_START_LATE_DESC
                + INVALID_LEAVE_DATE_END_EARLY_DESC;
        assertParseFailure(parser, userInput, Range.MESSAGE_END_BEFORE_START_ERROR);
    }

    @Test
    public void parse_emptyDescription_success() {
        Index targetIndex = INDEX_FIRST_LEAVE;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_EMPTY;
        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder()
            .withDescription(Description.DESCRIPTION_PLACEHOLDER).build();
        EditLeaveCommand expectedCommand = new EditLeaveCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}


