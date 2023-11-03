package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaves.BENSON_LEAVE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.CsvFile;
import seedu.address.commons.util.CsvUtil;
import seedu.address.model.LeavesBook;

public class CsvSerializableLeavesBookTest {
    private static final CsvAdaptedLeave aliceAL = new CsvAdaptedLeave(ALICE_LEAVE);
    private static final CsvAdaptedLeave bensonAL = new CsvAdaptedLeave(BENSON_LEAVE);

    @Test
    public void constructor_containsLeaves() throws Exception {
        List<CsvAdaptedLeave> leaves = generateLeavesList();

        CsvSerializableLeavesBook csvBook = new CsvSerializableLeavesBook(leaves);
        LeavesBook leavesBook = csvBook.toModelType(getTypicalAddressBook());

        assertTrue(leavesBook.hasLeave(ALICE_LEAVE));
        assertTrue(leavesBook.hasLeave(BENSON_LEAVE));
    }

    private List<CsvAdaptedLeave> generateLeavesList() {
        List<CsvAdaptedLeave> leaves = new ArrayList<>();
        leaves.add(aliceAL);
        leaves.add(bensonAL);
        return leaves;
    }

    @Test
    public void saveAddressBook_successfulCreateFile() {
        List<CsvAdaptedLeave> leaves = generateLeavesList();
        CsvSerializableLeavesBook csvBook = new CsvSerializableLeavesBook(leaves);
        CsvFile csvFile = csvBook.saveLeavesBook();

        List<String> rowStrings = csvFile.getRows()
                .map(CsvFile.CsvRow::printRow)
                .collect(Collectors.toList());

        List<String> expectedStrings = leaves.stream().map(leave ->
                String.join(CsvUtil.DELIMITER, leave.getCsvValues())).collect(Collectors.toList());

        assertEquals(rowStrings, expectedStrings);
    }
}
