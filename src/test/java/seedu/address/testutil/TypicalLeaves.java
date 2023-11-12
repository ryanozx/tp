package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import seedu.address.model.LeavesBook;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Title;

/**
 * A utility class containing a list of {@code Leave} objects to be used in tests.
 */
public class TypicalLeaves {

    public static final Date DEFAULT_START = Date.of("2020-01-01");
    public static final Date DEFAULT_END = Date.of("2020-01-02");
    public static final Date DEFAULT_START_2 = Date.of("2020-01-03");
    public static final Date DEFAULT_END_2 = Date.of("2020-01-04");
    public static final Leave ALICE_LEAVE = new Leave(ALICE, new Title("Alice's Maternity Leave"),
            Range.createNonNullRange(DEFAULT_START, DEFAULT_END),
            new Description("Alice's Maternity Leave Description"));
    public static final Leave BOB_LEAVE = new Leave(BOB, new Title("Bob's Paternity Leave"),
            Range.createNonNullRange(DEFAULT_START, DEFAULT_END),
            new Description("Bob's Paternity Leave Description"));
    public static final Leave ALICE_LEAVE_2 = new Leave(ALICE, new Title("Alice's Maternity Leave 2"),
            Range.createNonNullRange(DEFAULT_START_2, DEFAULT_END_2));
    public static final Leave BENSON_LEAVE = new Leave(BENSON, new Title("Benson's Paternity Leave"),
            Range.createNonNullRange(DEFAULT_START, DEFAULT_END),
            new Description("Benson's Paternity Leave Description"));
    public static final Leave BENSON_LEAVE_2 = new Leave(BENSON, new Title("Benson's Paternity Leave 2"),
            Range.createNonNullRange(DEFAULT_START_2, DEFAULT_END_2));

    private TypicalLeaves() {} // prevents instantiation

    public static Leave[] getTypicalLeaves() {
        return new Leave[] {ALICE_LEAVE, BENSON_LEAVE, ALICE_LEAVE_2};
    }

    public static LeavesBook getTypicalLeavesBook() {
        LeavesBook lb = new LeavesBook();
        for (Leave leave : getTypicalLeaves()) {
            lb.addLeave(leave);
        }
        return lb;
    }
}
