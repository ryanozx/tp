package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.MICHAEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.TagsContainAllTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code FindSomeTagCommand }.
 */
public class FindAllTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());

    @Test
    public void equals() {
        List<Tag> tagList1 = new ArrayList<>();
        List<Tag> tagList2 = new ArrayList<>();
        tagList1.add(new Tag("full time"));
        tagList1.add(new Tag("remote"));
        tagList2.add(new Tag("part time"));
        tagList2.add(new Tag("remote"));
        TagsContainAllTagsPredicate firstPredicate =
                new TagsContainAllTagsPredicate(tagList1);
        TagsContainAllTagsPredicate secondPredicate =
                new TagsContainAllTagsPredicate(tagList2);

        FindAllTagCommand findFirstCommand = new FindAllTagCommand(firstPredicate);
        FindAllTagCommand findSecondCommand = new FindAllTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindAllTagCommand findFirstCommandCopy = new FindAllTagCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different tags -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noTagsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagsContainAllTagsPredicate predicate = preparePredicate(new String[]{"test for tag"});
        FindAllTagCommand command = new FindAllTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_twoKeywords_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        TagsContainAllTagsPredicate predicate = preparePredicate(new String[]{"remote", "full time"});
        FindAllTagCommand command = new FindAllTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MICHAEL), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("full time"));
        TagsContainAllTagsPredicate predicate = new TagsContainAllTagsPredicate(tagList);
        FindAllTagCommand findAllTagCommand = new FindAllTagCommand(predicate);
        String expected = FindAllTagCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findAllTagCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private TagsContainAllTagsPredicate preparePredicate(String[] args) {
        List<Tag> tagList = new ArrayList<>();
        for (String keyword : args) {
            Tag tag = new Tag(keyword);
            tagList.add(tag);
        }
        return new TagsContainAllTagsPredicate(tagList);
    }
}
