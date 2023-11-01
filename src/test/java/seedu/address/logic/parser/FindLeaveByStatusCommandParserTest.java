package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindLeaveByStatusCommand;
import seedu.address.model.leave.LeaveHasStatusPredicate;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Status.StatusType;

public class FindLeaveByStatusCommandParserTest {
    private final FindLeaveByStatusCommandParser parser = new FindLeaveByStatusCommandParser();

    @Test
    public void parse_validArgs_returnsFindLeaveByStatusCommand() {
        // approved
        String approvedInput = "APPROVED";
        FindLeaveByStatusCommand expectedApprovedCommand = new FindLeaveByStatusCommand(
                generatePredicate(StatusType.APPROVED));
        assertParseSuccess(parser, approvedInput, expectedApprovedCommand);
        // approved in lowercase
        String approvedLowercaseInput = "approved";
        assertParseSuccess(parser, approvedLowercaseInput, expectedApprovedCommand);
        // approved with whitespace
        String approvedWhitespaceInput = "\tAPPROVED\t";
        assertParseSuccess(parser, approvedWhitespaceInput, expectedApprovedCommand);

        // pending
        String pendingInput = "PENDING";
        FindLeaveByStatusCommand expectedPendingCommand = new FindLeaveByStatusCommand(
                generatePredicate(StatusType.PENDING));
        assertParseSuccess(parser, pendingInput, expectedPendingCommand);
        // approved in lowercase
        String pendingLowercaseInput = "pending";
        assertParseSuccess(parser, pendingLowercaseInput, expectedPendingCommand);
        // approved with whitespace
        String pendingWhitespaceInput = "\tPENDING\t";
        assertParseSuccess(parser, pendingWhitespaceInput, expectedPendingCommand);

        // rejected
        String rejectedInput = "REJECTED";
        FindLeaveByStatusCommand expectedRejectedCommand = new FindLeaveByStatusCommand(
                generatePredicate(StatusType.REJECTED));
        assertParseSuccess(parser, rejectedInput, expectedRejectedCommand);
        // approved in lowercase
        String rejectedLowercaseInput = "rejected";
        assertParseSuccess(parser, rejectedLowercaseInput, expectedRejectedCommand);
        // approved with whitespace
        String rejectedWhitespaceInput = "\tREJECTED\t";
        assertParseSuccess(parser, rejectedWhitespaceInput, expectedRejectedCommand);
    }

    private LeaveHasStatusPredicate generatePredicate(StatusType status) {
        return new LeaveHasStatusPredicate(Status.of(status));
    }

    @Test
    public void parse_invalidArgs_throwsException() {
        // empty string
        assertParseFailure(parser, "", FindLeaveByStatusCommand.MESSAGE_FAILED);
        // non-status word
        assertParseFailure(parser, "badStatus", FindLeaveByStatusCommand.MESSAGE_FAILED);
        // multiple statuses
        assertParseFailure(parser, "APPROVED APPROVED", FindLeaveByStatusCommand.MESSAGE_FAILED);
    }
}
