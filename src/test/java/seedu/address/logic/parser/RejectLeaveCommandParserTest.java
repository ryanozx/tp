package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LEAVE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RejectLeaveCommand;

public class RejectLeaveCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RejectLeaveCommand.MESSAGE_USAGE);

    private final RejectLeaveCommandParser parser = new RejectLeaveCommandParser();

    @Test
    public void parse_emptyIndex_failure() {
        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "    ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        Index targetIndex = INDEX_FIRST_LEAVE;
        String userInput = String.valueOf(targetIndex.getOneBased());
        RejectLeaveCommand expectedCommand = new RejectLeaveCommand(targetIndex);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nonNumericIndex_failure() {
        // non-numeric indices
        assertParseFailure(parser, "1a", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "abc", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "abc 10", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // exceed Integer.MAX_VALUE
        assertParseFailure(parser, "2147483648", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
    }
}
