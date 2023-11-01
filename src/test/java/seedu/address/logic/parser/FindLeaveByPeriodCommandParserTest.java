package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_START;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindLeaveByPeriodCommand;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.LeaveInPeriodPredicate;

public class FindLeaveByPeriodCommandParserTest {
    private final FindLeaveByPeriodCommandParser parser = new FindLeaveByPeriodCommandParser();
    private static final String START_DATE_INPUT = "2023-10-30";
    private static final String END_DATE_INPUT = "2023-10-31";

    private static final Date START_DATE = Date.of(START_DATE_INPUT);
    private static final Date END_DATE = Date.of(END_DATE_INPUT);

    @Test
    public void parse_validArgs_returnsFindLeaveByPeriodCommand() {

        // both start and end dates supplied
        String startAndEndInput = generateUserInput(true, true);
        FindLeaveByPeriodCommand expectedStartAndEndCommand = new FindLeaveByPeriodCommand(
                new LeaveInPeriodPredicate(START_DATE, END_DATE));
        assertParseSuccess(parser, startAndEndInput, expectedStartAndEndCommand);

        // only start date supplied
        String startInput = generateUserInput(true, false);
        FindLeaveByPeriodCommand expectedStartCommand = new FindLeaveByPeriodCommand(
                new LeaveInPeriodPredicate(START_DATE, null));
        assertParseSuccess(parser, startInput, expectedStartCommand);

        // only end date supplied
        String endInput = generateUserInput(false, true);
        FindLeaveByPeriodCommand expectedEndCommand = new FindLeaveByPeriodCommand(
                new LeaveInPeriodPredicate(null, END_DATE));
        assertParseSuccess(parser, endInput, expectedEndCommand);

        // no dates supplied
        String noDateInput = generateUserInput(false, false);
        FindLeaveByPeriodCommand expectedNoDateCommand = new FindLeaveByPeriodCommand(
                new LeaveInPeriodPredicate(null, null));
        assertParseSuccess(parser, noDateInput, expectedNoDateCommand);

        // multiple whitespaces between characters
        String whitespaceInput = " \n " + PREFIX_LEAVE_START + START_DATE_INPUT + " \n "
                + " \t " + PREFIX_LEAVE_END + END_DATE_INPUT + " \t ";
        FindLeaveByPeriodCommand expectedWhitespaceCommand = new FindLeaveByPeriodCommand(
                new LeaveInPeriodPredicate(START_DATE, END_DATE));
        assertParseSuccess(parser, whitespaceInput, expectedWhitespaceCommand);
    }

    private String generateUserInput(boolean hasStartDate, boolean hasEndDate) {
        String startDateStr = hasStartDate ? " " + PREFIX_LEAVE_START + START_DATE_INPUT
                : "";
        String endDateStr = hasEndDate ? " " + PREFIX_LEAVE_END + END_DATE_INPUT
                : "";
        return startDateStr + endDateStr;
    }

    @Test
    public void parse_duplicateArgs_throwsParseException() {
        String startDateStr = " " + PREFIX_LEAVE_START + START_DATE_INPUT;
        String duplicateStartInput = startDateStr + startDateStr;
        assertParseFailure(parser, duplicateStartInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_START));

        String endDateStr = " " + PREFIX_LEAVE_END + END_DATE_INPUT;
        String duplicateEndInput = endDateStr + endDateStr;
        assertParseFailure(parser, duplicateEndInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LEAVE_END));
    }
}
