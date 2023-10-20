package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.CsvFile;
import seedu.address.commons.util.CsvUtil;
import seedu.address.model.AddressBook;

public class CsvSerializableAddressBookTest {

    private static CsvAdaptedPerson aliceAP = new CsvAdaptedPerson(ALICE);
    private static CsvAdaptedPerson bensonAP = new CsvAdaptedPerson(BENSON);
    @Test
    public void constructor_containsPersons() throws Exception {
        List<CsvAdaptedPerson> persons = generatePersonsList();

        CsvSerializableAddressBook csvBook = new CsvSerializableAddressBook(persons);
        AddressBook addressBook = csvBook.toModelType();

        assertTrue(addressBook.hasPerson(ALICE));
        assertTrue(addressBook.hasPerson(BENSON));
    }

    private List<CsvAdaptedPerson> generatePersonsList() {
        List<CsvAdaptedPerson> persons = new ArrayList<>();
        persons.add(aliceAP);
        persons.add(bensonAP);
        return persons;
    }

    @Test
    public void saveAddressBook_successfulCreateFile() {
        List<CsvAdaptedPerson> persons = generatePersonsList();
        CsvSerializableAddressBook csvBook = new CsvSerializableAddressBook(persons);
        CsvFile csvFile = csvBook.saveAddressBook();

        List<String> rowStrings = csvFile.getRows()
                .map(CsvFile.CsvRow::printRow)
                .collect(Collectors.toList());

        List<String> expectedStrings = persons.stream().map(person ->
                String.join(CsvUtil.DELIMITER, person.getValues())).collect(Collectors.toList());

        assertEquals(rowStrings, expectedStrings);
    }
}
