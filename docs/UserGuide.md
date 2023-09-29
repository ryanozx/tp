---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# AB-3 User Guide

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `addressbook.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**How to read each command's description**<br>

The description for each command will contain the following

### **[Action]**: [Command name]

* **What it does:** <br> 
| a short description of what the command helps you to do

* **Format:** <br> 
| contained within a `code block`. It specifies how the command should be formatted. You should follow the specified format to ensure the command performs the desired action. Refer to [How to interpret command formats](#how-to-interpret-command-formats) for more details.

* **Examples:** <br>
| gives a few examples of how the command is used and describes the outcome of the command.

* **Acceptable Values:** <br> 
| describes any restrictions on the type of values used in a command field. Values for these fields must satisfy the restrictions for the command to be acceptable.

* **Warning:** <br>
| describes any potential dangers of using a particular command that may lead to unexpected side effects. You should pay attention to the warnings to avoid performing a costly operation.

* **Expected output on success:** <br>
|  describes what you should see if the command is accepted. Changes can be of one of two types - either through visual elements (stated as GUI changes) or via messages.

* **Expected output on failure:** <br>
| describes what you should see if the command is rejected.

* **For advanced users:** <br>
| describes actions that users familiar with the application can use to provide greater convenience. Be careful when performing these actions, especially if you are a new user!

</box>

<box type="info" seamless>

#### **How to interpret command formats:**

Example command format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...`

This command is used to add an employee to the address book. Let's examine how we can use this command.

| **Command component examples** | **What they mean** |
| --- | --- |
| add | The name of the command. It is the first word of the command. You must specify the command that you intend to use. |
| n/NAME | Command fields. We use these fields to supply further information that the command requires, in this case, the employee's name. Fields are seperated by a space. Some fields require a field prefix, while other fields do not have a field prefix. Let's look at the field in greater detail. |
| n/ | Field prefix. It contains a letter followed by a forward slash (/). If the field has a field prefix, the prefix must be specified at the start of the field. Each of these fields has a unique prefix, which can be found by referring to the command format (e.g. n/ for the name field, p/ for the phone number field). Additionally, fields with field prefixes can be written in any order, so long as they are written after fields without field prefixes. |
| NAME | Field name. For field names that contain more than one word, they are seperated by an underscore, e.g. PHONE_NUMBER. We can replace this with information specific to our scenario. For instance, if we add an employee named *John Smith*, we will type in the field as `n/John Smith`, where *John Smith* replaces NAME. |
| [t/TAG] | Optional command fields. Fields wrapped in square brackets i.e. [] are optional and need not be filled in for the command to succeed. Nevertheless, if you want to supply this field to the command, follow the format specified within the square brackets. For instance, if we want to provide a tag (e.g. *manager*) an optional field, we will key in the field as `t/manager`. |
| ... | The ellipsis following a field indicates that we can supply any number of that particular field, with each field seperated by a space. In the example, [t/TAG]... means we can provide any number of tags in the command. For instance, if we want to provide the tags *manager* and *sales*, we can key them in as `t/manager t/sales`.

**Notes**

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

* **What it Does:** <br>
| Shows a message explaning how to access the help page.

* **Format:**<br> 
| `help`

* **Examples:** 

| S/N | Command information |
| --- | --- | 
| 1 | **Command:** `help` <br> **Output:** Displays a message showing how to access the help page |

* **Acceptable Values:** <br>
| This command does not have any parameters

* **Expected Output on Success:** <br>
| A message appears showing how to access the help page

![help message](images/helpMessage.png)

* **Expected Output on Failure:** <br>
| Error messages: N/A (no expected failure)

### Adding a new employee or adding employees to set up HRMate initially: `add`

* **What it Does:** <br>
| Uses employee fields from the command to add an employee

* **Format:** <br>
| `add n/NAME p/PHONE_NUMBER e/EMAIL_ADDRESS a/HOME_ADDRESS [t/TAG]...`

* **Examples:** <br>

| S/N | Command information |
| --- | --- |
| 1 | **Command:** `add n/John Doe p/98765432 e/johnd@example.com a/John Street, block 123 #01-01` <br> **Output:** Adds an employee with the following details into the address book: <br> - Name: John Doe <br> - Phone number: 98765432 <br> - Email address: johnd@example.com <br> - Home address: John Street, block 123 #01-01 |
| 2 | **Command:** `add n/Betsy Crowe e/betsycrowe@example.com a/Newgate Prison p/91234567 t/criminal t/friend` <br> **Output:** Adds an employee with the following details into the address book: <br> - Name: Betsy Crowe <br> - Phone number: 91234567 <br> - Email address: betsycrowe@example.com <br> - Home address: Newgate Prison <br> - Tags: criminal, friend (Note: we can add multiple tags to the same record) |

* **Acceptable Values:** <br>
| A non-empty input for name, phone number, email address, home address and each optional tag

* **Expected Output on Success:** <br>
| GUI Changes: the created user is shown in a dedicated interface section <br>
| Message shown to user: "Successfully created employee"

* **Expected Output on Failure:** <br>
| `Missing argument error` -  if a compulsory argument is missing (compulsory arguments are NAME, EMAIL, PHONE_NUMBER and HOME_ADDRESS) <br>
| `FIELD must be of format...` - if at least one of the field inputs violates the field's format requirements. Follow the displayed error message to fix the format error.

* **For advanced users:** <br>
| `add` takes in any number of tags, as an employee can have any number of tags

### Listing all employees: `list`

* **What it Does:** <br>
| Shows a list of all employees in HRMate

* **Format:** <br>
| `list`

* **Examples:** 

| S/N | Command information |
| --- | --- |
| 1 | **Command:** `list` <br> **Output:** Shows a list of all employees in the address book |

* **Acceptable Values:** <br>
| This command has no parameters

* **Expected Output on Success:** <br>
| GUI Changes: A list of employees is dispplayed in a dedicated interface section

* **Expected Output on Failure:** <br>
| N/A (no expected failures)

### Editing an employee's personal information: `edit`

* **What it Does:** <br>
| Edits an existing person in HRMate based on their index on the [list](#listing-all-employees-list) command

* **Format:** <br>
| `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL_ADDRESS] [a/HOME_ADDRESS] [t/TAG]...`

* **Examples:**

| S/N | Command information |
| --- | --- |
| 1 | **Command:** `edit 1 p/91234567 e/johndoe@example.com` <br> **Output:** Edits the employee with index 1 to have the below changes: <br> - Phone number: 91234567 <br> - Email: johndoe@example.com <br> - No changes to the other fields |
| 2 | **Command:** `edit 2 n/Betsy Crower t/` <br> **Output:**: Edits the employee with index 2 to have the below changes: <br> - Name: Betsy Crower <br> - Tags: (all removed) <br> - No changes to the other fields |
| 3 | **Command:** `edit 3 t/friend` <br> **Output:** Edits the employee with index 3 to have the below changes: <br> - Tags: friend (all other existing tags removed) <br> - No changes to the other fields |

* **Acceptable Values:** <br>
| The specified `INDEX` must match with an employee shown in the displayed person list<br>
| At least one employee field must be changes (`edit 1` would be invalid as no employee fields are changed) <br>
| Only fields specified will be modified; fields not specified in the command will not be modified. A field is specified by including its associated field prefix in the command (i.e. `n/` for name, `p/` for phone number etc.)
| For the name, phone number, email address and home address fields: If these fields are included in the command, they cannot be left empty (e.g. `n/` is not allowed as the new name cannot be empty)

* **Warning:** <br>
| If the tag prefix is specified, all existing tags currently associated with the employee will be removed and replaced with the new tags specified in the command, if any (see examples 2 and 3 above). Therefore to avoid losing any tags associated with the employee, we recommend using the [add-tag](#) and [remove-tag](#) commands instead for greater control.

* **Expected output on failure:** <br>
| `At least one field to edit must be provided` - if no fields are provided <br>
| `The person index provided is invalid` - if the index provided does not match with any employee in the displayed person list<br>
| `FIELD must be of format...` - if at least one of the field inputs violates the field's format requirements. Follow the displayed error message to fix the format error.

* **For advanced users:** <br>
| You can remove all the tags of an employee with `edit INDEX t/` (see example 2 above)

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
