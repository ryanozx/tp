package seedu.address.storage;

import java.util.List;

import seedu.address.commons.util.CsvFile;
import seedu.address.commons.util.CsvUtil;

/**
 * An Immutable LeavesBook that is serializable to CSV format.
 */
public class CsvSerializableLeavesBook extends SerializableLeavesBook<CsvAdaptedLeave> {
    public CsvSerializableLeavesBook(List<CsvAdaptedLeave> leaves) {
        this.leaves.addAll(leaves);
    }

    /**
     * Creates a CsvFile from the list of AdaptedLeaves in the serializableLeaveBook
     * @return CsvFile containing records of all persons in this address book
     */
    public CsvFile saveLeavesBook() {
        CsvFile leavesBookFile = new CsvFile(CsvAdaptedLeave.getHeader(), CsvUtil.DELIMITER);
        leaves.forEach(leavesBookFile::addRow);
        return leavesBookFile;
    }
}
