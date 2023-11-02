package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.DAVID;
import static seedu.address.testutil.TypicalPersons.MICHAEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.TagsContainSomeTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code FindSomeTagCommand }.
 */
public class FindSomeTagCommandTest {
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
        TagsContainSomeTagsPredicate firstPredicate =
                new TagsContainSomeTagsPredicate(tagList1);
        TagsContainSomeTagsPredicate secondPredicate =
                new TagsContainSomeTagsPredicate(tagList2);

        FindSomeTagCommand findFirstCommand = new FindSomeTagCommand(firstPredicate);
        FindSomeTagCommand findSecondCommand = new FindSomeTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindSomeTagCommand findFirstCommandCopy = new FindSomeTagCommand(firstPredicate);
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
        TagsContainSomeTagsPredicate predicate = preparePredicate(new String[]{"tagForTest"});
        FindSomeTagCommand command = new FindSomeTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeywords_multiplePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        TagsContainSomeTagsPredicate predicate = preparePredicate(new String[]{"remote"});
        FindSomeTagCommand command = new FindSomeTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MICHAEL, DAVID), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("full time"));
        TagsContainSomeTagsPredicate predicate = new TagsContainSomeTagsPredicate(tagList);
        FindSomeTagCommand findSomeTagCommand = new FindSomeTagCommand(predicate);
        String expected = FindSomeTagCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findSomeTagCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private TagsContainSomeTagsPredicate preparePredicate(String[] args) {
        List<Tag> tagList = new ArrayList<>();
        for (String keyword : args) {
            Tag tag = new Tag(keyword);
            tagList.add(tag);
        }
        return new TagsContainSomeTagsPredicate(tagList);
    }
}
