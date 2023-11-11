package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LEAVE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ApproveLeaveCommand;

public class ApproveLeaveCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApproveLeaveCommand.MESSAGE_USAGE);

    private ApproveLeaveCommandParser parser = new ApproveLeaveCommandParser();

    @Test
    public void parse_validIndex_success() {
        String userInput = String.valueOf(INDEX_FIRST_LEAVE.getOneBased());
        ApproveLeaveCommand expectedCommand = new ApproveLeaveCommand(INDEX_FIRST_LEAVE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_missingIndex_failure() {
        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_nonNumericIndex_failure() {
        // non-numeric indices
        assertParseFailure(parser, "1a", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "abc", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "abc 10", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_outOfBoundIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // exceed Integer.MAX_VALUE
        assertParseFailure(parser, "2147483648", MESSAGE_INVALID_FORMAT);
    }
}
