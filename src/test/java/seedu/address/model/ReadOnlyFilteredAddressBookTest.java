package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class ReadOnlyFilteredAddressBookTest {
    @Test
    public void getPersonList() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ReadOnlyFilteredAddressBook addressBook = new ReadOnlyFilteredAddressBook(model);

        List<Person> persons = addressBook.getPersonList();
        assertEquals(persons, getTypicalPersons());
    }
}
