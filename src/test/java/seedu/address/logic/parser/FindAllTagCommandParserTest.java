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
        tagList.add(new Tag("full time"));
        tagList.add(new Tag("remote"));
        // no leading and trailing whitespaces
        FindAllTagCommand expectedFindCommand =
                new FindAllTagCommand(new TagsContainAllTagsPredicate(tagList));
        assertParseSuccess(parser, " t/full time t/remote ", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n t/full time \n \t t/remote  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("full time"));
        assertThrows(ParseException.class, () -> parser.parse("t/full time a/remote"));
    }


}
