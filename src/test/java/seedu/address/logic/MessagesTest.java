package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.EMPLOYEE_HEADER;
import static seedu.address.logic.Messages.END_HEADER;
import static seedu.address.logic.Messages.START_HEADER;
import static seedu.address.logic.Messages.STATUS_HEADER;
import static seedu.address.logic.Messages.TITLE_HEADER;
import static seedu.address.testutil.LeaveBuilder.DEFAULT_DESCRIPTION;
import static seedu.address.testutil.LeaveBuilder.DEFAULT_END;
import static seedu.address.testutil.LeaveBuilder.DEFAULT_PERSON;
import static seedu.address.testutil.LeaveBuilder.DEFAULT_START;
import static seedu.address.testutil.LeaveBuilder.DEFAULT_STATUS;
import static seedu.address.testutil.LeaveBuilder.DEFAULT_TITLE;

import org.junit.jupiter.api.Test;

import seedu.address.model.leave.Leave;
import seedu.address.testutil.LeaveBuilder;

public class MessagesTest {
    @Test
    public void format_leave() {
        // with description
        String expectedOutput = EMPLOYEE_HEADER + DEFAULT_PERSON.getName()
                + TITLE_HEADER + DEFAULT_TITLE
                + START_HEADER + DEFAULT_START
                + END_HEADER + DEFAULT_END
                + STATUS_HEADER + DEFAULT_STATUS;
        Leave leaveWithDescription = new LeaveBuilder()
                .withEmployee(DEFAULT_PERSON)
                .withTitle(DEFAULT_TITLE.toString())
                .withStart(DEFAULT_START)
                .withEnd(DEFAULT_END)
                .withStatus(DEFAULT_STATUS.getStatusType())
                .withDescription(DEFAULT_DESCRIPTION.toString())
                .build();
        assertEquals(Messages.format(leaveWithDescription), expectedOutput);

        // without description
        expectedOutput = EMPLOYEE_HEADER + DEFAULT_PERSON.getName()
                + TITLE_HEADER + DEFAULT_TITLE
                + START_HEADER + DEFAULT_START
                + END_HEADER + DEFAULT_END
                + STATUS_HEADER + DEFAULT_STATUS;
        Leave leaveWithoutDescription = new LeaveBuilder()
                .withEmployee(DEFAULT_PERSON)
                .withTitle(DEFAULT_TITLE.toString())
                .withStart(DEFAULT_START)
                .withEnd(DEFAULT_END)
                .withStatus(DEFAULT_STATUS.getStatusType())
                .withDescription("")
                .build();
        assertEquals(Messages.format(leaveWithoutDescription), expectedOutput);
    }
}
