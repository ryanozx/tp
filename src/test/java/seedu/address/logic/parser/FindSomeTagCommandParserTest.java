package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.FindSomeTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagsContainSomeTagsPredicate;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.List;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

public class FindSomeTagCommandParserTest {

    private FindSomeTagCommandParser parser = new FindSomeTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSomeTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("fullTime"));
        tagList.add(new Tag("remote"));
        // no leading and trailing whitespaces
        FindSomeTagCommand expectedFindCommand =
                new FindSomeTagCommand(new TagsContainSomeTagsPredicate(tagList));
        assertParseSuccess(parser, "fullTime remote", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n fullTime \n \t remote  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("full-time"));
    }


}
