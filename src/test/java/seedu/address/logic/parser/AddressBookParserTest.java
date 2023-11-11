package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FULL_TIME;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_REMOTE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DATE_END;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DATE_START;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LEAVE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddLeaveCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.ApproveLeaveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLeaveCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditLeaveCommand;
import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportContactCommand;
import seedu.address.logic.commands.ExportLeaveCommand;
import seedu.address.logic.commands.FindAllLeaveCommand;
import seedu.address.logic.commands.FindAllTagCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindLeaveByPeriodCommand;
import seedu.address.logic.commands.FindLeaveByStatusCommand;
import seedu.address.logic.commands.FindLeaveCommand;
import seedu.address.logic.commands.FindSomeTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportContactCommand;
import seedu.address.logic.commands.ImportLeaveCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RejectLeaveCommand;
import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.LeaveHasStatusPredicate;
import seedu.address.model.leave.LeaveInPeriodPredicate;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Status.StatusType;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagsContainAllTagsPredicate;
import seedu.address.model.person.TagsContainSomeTagsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditLeaveDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.LeaveBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addLeave() throws Exception {
        Leave leave = new LeaveBuilder().withTitle(VALID_LEAVE_TITLE)
                .withStart(Date.of(VALID_LEAVE_DATE_START)).withEnd(Date.of(VALID_LEAVE_DATE_END))
                .withDescription(VALID_LEAVE_DESCRIPTION).build();
        AddLeaveCommand command = (AddLeaveCommand) parser.parseCommand(AddLeaveCommand.COMMAND_WORD + " "
                + INDEX_FIRST_LEAVE.getOneBased() + VALID_LEAVE_TITLE_DESC + VALID_LEAVE_START_DATE_DESC
                + VALID_LEAVE_END_DATE_DESC + VALID_LEAVE_DESCRIPTION_DESC);
        Range dateRange = Range.createNonNullRange(leave.getStart(), leave.getEnd());
        assertEquals(new AddLeaveCommand(INDEX_FIRST_LEAVE, leave.getTitle(),
                dateRange, leave.getDescription()), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editLeave() throws Exception {
        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder().withTitle(VALID_LEAVE_TITLE).build();
        EditLeaveCommand command = (EditLeaveCommand) parser.parseCommand(EditLeaveCommand.COMMAND_WORD + " "
                + INDEX_FIRST_LEAVE.getOneBased() + VALID_LEAVE_TITLE_DESC);
        assertEquals(new EditLeaveCommand(INDEX_FIRST_LEAVE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exportContact() throws Exception {
        String testFileName = "testExportFile";
        Path testFilePath = Paths.get(ExportContactCommand.EXPORT_DEST, "testExportFile.csv");
        ExportContactCommand command = (ExportContactCommand) parser.parseCommand(
                ExportContactCommand.COMMAND_WORD + " " + testFileName);
        assertEquals(new ExportContactCommand(testFilePath), command);
    }

    @Test
    public void parseCommand_exportLeave() throws Exception {
        String testFileName = "testExportFile";
        Path testFilePath = Paths.get(ExportLeaveCommand.EXPORT_DEST, "testExportFile.csv");
        ExportLeaveCommand command = (ExportLeaveCommand) parser.parseCommand(
                ExportLeaveCommand.COMMAND_WORD + " " + testFileName);
        assertEquals(new ExportLeaveCommand(testFilePath), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findAllTag() throws Exception {
        List<Tag> keywords = Arrays.asList(new Tag("remote"), new Tag("full time"));
        FindAllTagCommand command = (FindAllTagCommand) parser.parseCommand(
                FindAllTagCommand.COMMAND_WORD + " " + TAG_DESC_REMOTE + TAG_DESC_FULL_TIME);
        assertEquals(new FindAllTagCommand(new TagsContainAllTagsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findSomeTag() throws Exception {
        List<Tag> keywords = Arrays.asList(new Tag("remote"), new Tag("full time"));
        FindSomeTagCommand command = (FindSomeTagCommand) parser.parseCommand(
                FindSomeTagCommand.COMMAND_WORD + " " + TAG_DESC_REMOTE + TAG_DESC_FULL_TIME);
        assertEquals(new FindSomeTagCommand(new TagsContainSomeTagsPredicate(keywords)), command);
    }
    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_import() throws Exception {
        assertTrue(parser.parseCommand(ImportContactCommand.COMMAND_WORD) instanceof ImportContactCommand);
        assertTrue(parser.parseCommand(ImportContactCommand.COMMAND_WORD + " 3") instanceof ImportContactCommand);
    }

    @Test
    public void parseCommand_importLeave() throws Exception {
        assertTrue(parser.parseCommand(ImportLeaveCommand.COMMAND_WORD) instanceof ImportLeaveCommand);
        assertTrue(parser.parseCommand(ImportLeaveCommand.COMMAND_WORD + " 3") instanceof ImportLeaveCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addTag() throws Exception {
        AddTagCommand command = (AddTagCommand) parser.parseCommand(
                AddTagCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " " + TAG_DESC_FRIEND);
        assertEquals(new AddTagCommand(INDEX_FIRST_PERSON, List.of(new Tag(VALID_TAG_FRIEND))), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_deleteTag() throws Exception {
        assertTrue(parser.parseCommand(DeleteTagCommand.COMMAND_WORD + " 1 t/friends") instanceof DeleteTagCommand);
        assertTrue(parser.parseCommand(DeleteTagCommand.COMMAND_WORD + " 1 t/friends t/colleagues")
                instanceof DeleteTagCommand);
        assertTrue(parser.parseCommand(DeleteTagCommand.COMMAND_WORD + " 1 t/friends t/colleagues t/family")
                instanceof DeleteTagCommand);
    }

    @Test
    public void parseCommand_viewTag() throws Exception {
        assertTrue(parser.parseCommand(ViewTagCommand.COMMAND_WORD) instanceof ViewTagCommand);
    }

    @Test
    public void parseCommand_approveLeave() throws Exception {
        assertTrue(parser.parseCommand(ApproveLeaveCommand.COMMAND_WORD + " 1") instanceof ApproveLeaveCommand);
    }

    @Test
    public void parseCommand_deleteLeave() throws Exception {
        DeleteLeaveCommand deleteLeaveCommand = (DeleteLeaveCommand) parser.parseCommand(
                DeleteLeaveCommand.COMMAND_WORD + " 1");
        assertEquals(deleteLeaveCommand, new DeleteLeaveCommand(INDEX_FIRST_LEAVE));
    }

    @Test
    public void parseCommand_findAllLeaveCommand() throws Exception {
        assertTrue(parser.parseCommand(FindAllLeaveCommand.COMMAND_WORD) instanceof FindAllLeaveCommand);
        assertTrue(parser.parseCommand(FindAllLeaveCommand.COMMAND_WORD + " 3") instanceof FindAllLeaveCommand);
    }
    @Test
    public void parseCommand_findLeave() throws Exception {
        assertTrue(parser.parseCommand(FindLeaveCommand.COMMAND_WORD + " 1") instanceof FindLeaveCommand);
    }
    @Test
    public void parseCommand_findLeaveByPeriod() throws Exception {
        String startDate = "2023-10-30";
        String endDate = "2023-10-31";

        LeaveInPeriodPredicate expectedPredicate = new LeaveInPeriodPredicate(
                Range.createNonNullRange(Date.of(startDate), Date.of(endDate)));
        String userInput = FindLeaveByPeriodCommand.COMMAND_WORD + VALID_LEAVE_START_DATE_DESC
            + VALID_LEAVE_END_DATE_DESC;
        assertTrue(parser.parseCommand(userInput) instanceof FindLeaveByPeriodCommand);
        assertEquals(parser.parseCommand(userInput), new FindLeaveByPeriodCommand(expectedPredicate));
    }

    @Test
    public void parseCommand_findLeaveByStatus() throws Exception {
        LeaveHasStatusPredicate expectedPredicate = new LeaveHasStatusPredicate(
                Status.of(StatusType.APPROVED));
        String userInput = FindLeaveByStatusCommand.COMMAND_WORD + " "
                + StatusType.APPROVED;
        assertTrue(parser.parseCommand(userInput) instanceof FindLeaveByStatusCommand);
        assertEquals(parser.parseCommand(userInput), new FindLeaveByStatusCommand(expectedPredicate));
    }

    @Test
    public void parseCommand_rejectLeave() throws Exception {
        assertTrue(parser.parseCommand(RejectLeaveCommand.COMMAND_WORD + " 1") instanceof RejectLeaveCommand);
    }
}
