package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindAllTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagsContainAllTagsPredicate;
import seedu.address.model.tag.Tag;

public class FindAllTagCommandParserTest {

    private FindAllTagCommandParser parser = new FindAllTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAllTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("fullTime"));
        tagList.add(new Tag("remote"));
        // no leading and trailing whitespaces
        FindAllTagCommand expectedFindCommand =
                new FindAllTagCommand(new TagsContainAllTagsPredicate(tagList));
        assertParseSuccess(parser, "fullTime remote", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n fullTime \n \t remote  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("full-time"));
    }


}
