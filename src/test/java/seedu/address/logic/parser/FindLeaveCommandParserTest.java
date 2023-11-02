package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindLeaveCommand;


public class FindLeaveCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLeaveCommand.MESSAGE_USAGE);
    private FindLeaveCommandParser parser = new FindLeaveCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLeaveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        Index validIndex = Index.fromOneBased(10);
        FindLeaveCommand expectedCommand = new FindLeaveCommand(validIndex);
        assertParseSuccess(parser, "10", expectedCommand);
    }

    @Test
    public void parse_invalidTags_throwsParseException() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
    }
}
