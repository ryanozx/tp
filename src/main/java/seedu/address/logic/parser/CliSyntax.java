package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_PERSON_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PERSON_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_PERSON_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_PERSON_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_PERSON_TAG = new Prefix("t/");
    public static final Prefix PREFIX_LEAVE_TITLE = new Prefix("title/");
    public static final Prefix PREFIX_LEAVE_DATE_START = new Prefix("start/");
    public static final Prefix PREFIX_LEAVE_DATE_END = new Prefix("end/");
    public static final Prefix PREFIX_LEAVE_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_LEAVE_STATUS = new Prefix("s/");
}
