package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDate;

import seedu.address.model.leave.Leave;
/**
 * A utility class containing a list of {@code Leave} objects to be used in tests.
 */
public class TypicalLeaves {

    public static final LocalDate DEFAULT_START = LocalDate.of(2020, 1, 1);
    public static final LocalDate DEFAULT_END = LocalDate.of(2020, 1, 2);
    public static final LocalDate DEFAULT_START_2 = LocalDate.of(2020, 1, 3);
    public static final LocalDate DEFAULT_END_2 = LocalDate.of(2020, 1, 4);

    public static final Leave ALICE_LEAVE = new Leave(ALICE, "Alice's Maternity Leave", DEFAULT_START, DEFAULT_END,
            "Alice's Maternity Leave Description");
    public static final Leave BOB_LEAVE = new Leave(BOB, "Bob's Paternity Leave", DEFAULT_START, DEFAULT_END,
            "Bob's Paternity Leave Description");
    public static final Leave ALICE_LEAVE_2 = new Leave(ALICE, "Alice's Maternity Leave 2", DEFAULT_START_2,
            DEFAULT_END_2);

    private TypicalLeaves() {} // prevents instantiation

    public static Leave[] getTypicalLeaves() {
        return new Leave[] {ALICE_LEAVE, BOB_LEAVE, ALICE_LEAVE_2};
    }
}
