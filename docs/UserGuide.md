<frontmatter>
  layout: default.md
  pageNav: 3
  title: "User Guide"
</frontmatter>

# HRMate User Guide

HRMate is a **desktop app for managing employee records, optimized for use via a Command Line Interface** (CLI) while
still having the benefits of a Graphical User Interface (GUI). If you can type fast, HRMate can get your contact management
tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print></page-nav-print>

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `hrmate.jar` from [to be updated]().

3. Copy the file to the folder you want to use as the _home folder_ for your HRMate.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar hrmate.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.
   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.
   * `delete 3` : Deletes the 3rd contact shown in the current list.
   * `clear` : Deletes all contacts.
   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

## How to interpret command formats

Example command format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...`

This command is used to add an employee to the address book. Let's examine how we can use this command.

| **Command component examples** | **What they mean**                                                                                                                                                                                                                                                                                                                                                                                                                                               |
|--------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| add                            | The name of the command. It is the first word of the command. You must specify the command that you intend to use.                                                                                                                                                                                                                                                                                                                                               |
| n/NAME                         | Command fields. We use these fields to supply further information that the command requires, in this case, the employee's name. Fields are separated by a space. Some fields require a field prefix, while other fields do not have a field prefix. Let's look at the field in greater detail.                                                                                                                                                                   |
| n/                             | Field prefix. It contains a letter followed by a forward slash (/). If the field has a field prefix, the prefix must be specified at the start of the field. Each of these fields has a unique prefix, which can be found by referring to the command format (e.g. n/ for the name field, p/ for the phone number field). Additionally, fields with field prefixes can be written in any order, so long as they are written after fields without field prefixes. |
| NAME                           | Field name. For field names that contain more than one word, they are separated by an underscore, e.g. PHONE_NUMBER. We can replace this with information specific to our scenario. For instance, if we add an employee named *John Smith*, we will type in the field as `n/John Smith`, where *John Smith* replaces NAME.                                                                                                                                       |
| [t/TAG]                        | Optional command fields. Fields wrapped in square brackets i.e. [] are optional and need not be filled in for the command to succeed. Nevertheless, if you want to supply this field to the command, follow the format specified within the square brackets. For instance, if we want to provide a tag (e.g. *manager*) an optional field, we will key in the field as `t/manager`.                                                                              |
| ...                            | The ellipsis following a field indicates that we can supply any number of that particular field, with each field separated by a space. In the example, [t/TAG]... means we can provide any number of tags in the command. For instance, if we want to provide the tags *manager* and *sales*, we can key them in as `t/manager t/sales`.                                                                                                                         |
**Notes**
* Parameters can be in any order <br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also
  acceptable.
* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be
  ignored <br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.
--------------------------------------------------------------------------------------------------------------------
## Features

### Getting help
Should you need any help, you can access this online user guide (this document) with the `help` [command](#glossary).

#### Getting help : `help`

1. Type in the following command in the [command box](#glossary): `help`. 
2. Press "Enter" on your keyboard.
3. The following pop up should show:
![help menu](images/help-menu.png)

4. Click on the "copy link" button to copy the link to the user guide, and paste into any [web browser](#glossary) to access the user guide. Alternatively, click the red button at the corner of the window to close the pop up.

### Adding a new employee
When a new employee joins, you can use the `add` [command](#glossary) to add their information into HRMate.

#### Add an employee: `add`

1. Type in the following [command](#glossary) in the [command box](#glossary): `add n/NAME p/PHONE_NUMBER e/EMAIL_ADDRESS a/HOME_ADDRESS [t/TAG]...`, replace `NAME` with employee name, `PHONE_NUMBER` with employee phone number `EMAIL_ADDRESS` with employee email address and `HOME_ADDRESS` with employee home address. `[t/TAG]...` is an optional field with one or more [tags](#glossary), with `TAG` being the name of the tag.
 - For instance, if you have an employee name John Doe with phone number 98765432, email johnd@example.com, home address John Street, block 123 #01-01 and tags full-time and remote, type in the command `add n/John Doe p/98765432 e/johnd@example.com a/John Street, block 123 #01-01 t/full-time t/remote`
 - If your employee does not require any tags, using the same information as above, the command would be `add n/John Doe p/98765432 e/johnd@example.com a/John Street, block 123 #01-01`
2. Press "enter" on your keyboard and you should see the person information at the end of the [employee list](#glossary). 

Here are the potential error messages that you may receive and here's how to fix them:

| Error message | Why it happens | Fix |
|---------------|----------------|-----|
| `Invalid command format!` | The command you input does not follow the specified format | Ensure the command you entered follows the following format: `add n/NAME p/PHONE_NUMBER e/EMAIL_ADDRESS a/HOME_ADDRESS [t/TAG]...`, replacing `NAME` with employee name, `PHONE_NUMBER` with employee phone number, `EMAIL_ADDRESS` with employee email address, `HOME_ADDRESS` with employee home address. `[t/TAG]...` is optional, with `[t/TAG]` representing one or more tags and `TAG` being the tag name.
| `FIELD should FORMAT` where `FIELD` is an input like `Names` or `Phone numbers` and `FORMAT` contains additional information about the field's format. | The input does not follow the format prescribed. For example, the entered phone number might contain alphabets. | Follow the on screen message to fix the field in question. For example, `Phone numbers should only contain numbers, and it should be at least 3 digits long` means that the input phone number does not follow the prescribed format. |
| `This employee already exists in the address book` | The provided employee name is already found in HRMate | Use another name for the employee. For example, if trying to add another John Doe, use the name John Doe (HR) to differentiate between the existing John Doe. HRMate does this name checking to prevent unintentional duplicate employee entries.

### Listing all employees: `list`

* **What It Does:**
  * Shows a list of all employees in HRMate
* **Format:**
  * `list`
* **Examples:**

| S/N | Command information                                                                        |
|-----|--------------------------------------------------------------------------------------------|
| 1   | **Command:** `list` <br><br> **Output:** Shows a list of all employees in the address book |
* **Acceptable Values:**
  * This command has no parameters
* **Expected Output on Success:**
  * GUI Changes: A list of employees is displayed in a dedicated interface section
* **Expected Output on Failure:**
  * N/A (no expected failure)

### Editing employee records
HRMate offers different [commands](#glossary) for editing employee records. `add-tag` and `delete-tag` would add and remove an employee's [tags](#glossary) while `edit` is for editing name, phone number, email address, home address and tags.

#### Adding tags to employees : `add-tag`

1. Find the employee under the [employee list](#glossary).

<box type="info" seamless>
If the employee is not found, consider using [list](#glossary) or any [find commands]() to locate the employee in the employee list.
</box>

2. Type in the following [command](#glossary) in the [command box](#glossary) `add-tag INDEX t/TAG...` where `INDEX` is the [index](#glossary) of the employee in the list currently, `TAG` is the name of the [tag](#glossary) to be added and `t/TAG...` representing one or more tags. 
 - For instance, if you want to add the tags full-time and remote to the employee indexed 2, type `add-tag 2 t/full-time t/remote` to the command box.
3. Press "enter" on your keyboard and you should see the input tags added to the employee specified.

Here are the potential error messages that you may receive and here's how to fix them:

| Error message | Why it happens | Fix |
|---------------|----------------|-----|
| `Invalid command format!` | The command you input does not follow the specfied format | Ensure the command you entered follows the following format: `add-tag INDEX t/TAG...`, replacing INDEX with the index of the employee currently, `TAG` is the name of the tag to be added and `t/TAG...` representing one or more tags. |
| `At least one tag must be provided` | No tags were provided | Add tags to the command in the command box. Note that the tags must have a t/ prefix. For example, to add the tag full-time, use `t/full-time`.
| `The person index provided is invalid` | The index specified does not refer to any employee | Double check if the index appears in the employee list. Alternatively, use `list` or any find commands to locate the employee in the employee list. Afterwards, use the correct employee index in the `add-tag` command. |
| `The employee already has some of the tags` | The employee already has some of the tags which you are trying to add | Remove the tags the employee has from the input command. For example, for an employee who already has the full-time tag, the command `add-tag 2 t/full-time t/remote` would not work. Instead try `add-tag 2 t/remote`.|
| `Tags names only allows alphanumeric characters, spaces, and dashes.` | The tags input contains illegal characters | Remove the illegal characters from the input. |

#### Removing tags from employees : `delete-tag`
1. Get the [index](#glossary) of the employee in the [employee list](#glossary).

<box type="info" seamless>
If the employee is not found, consider using [list]() or any [find commands]() to locate the employee in the employee list.
</box>

2. Type in the following [command](#glossary) in the [command box](#glossary) `delete-tag INDEX t/TAG...` where `INDEX` is the index of the employee in the list currently, `TAG` is the name of the [tag](#glossary) to be deleted and `t/TAG...` representing one or more tags. 
 - For instance, if you want to remove the tags full-time and remote to the employee indexed 2, type `delete-tag 2 t/full-time t/remote` to the command box.
3. Press "enter" on your keyboard and you should see the input tags removed from the employee specified.

Here are the potential error messages you may receive and here's how to fix them:

| Error message | Why it happens | Fix |
|---------------|----------------|-----|
| `Invalid command format!` | The command you input does not follow the specfied format | Ensure the command you entered follows the following format: `delete-tag INDEX t/TAG...`, replacing INDEX with the index of the employee currently, `TAG` is the name of the tag to be deleted and `t/TAG...` representing one or more tags. |
| `At least one tag must be provided` | No tags were provided | Add tags to the command in the command box. Note that the tags must have a t/prefix. For example, to remove the tag full-time, use `t/full-time` |
| `The person index provided is invalid` | The index specified does not refer to any employee | Double check if the index appears in the employee list. Alternatively, use `list` or any find commands to locate the employee in the employee list. Afterwards, use the correct employee index in the `delete-tag` command. |
| `Some of the tags are not found on this employee.` | The employee does not have some of the tags you are trying to delete | Remove the tags not found on the employee from the input command. For example, for an employee without the tag full-time, the command `delete-tag 2 t/full-time t/remote` does not work. Instead try `delete-tag 2 t/remote`.|
| `Tags names only allows alphanumeric characters, spaces, and dashes.` | The tags input contains illegal characters | Remove the illegal characters from the input. |

#### Editing the name, phone number, email address, home address or tags of employees : `edit`
1. Get the [index](#glossary) of the employee under the [employee list](#glossary).

<box type="info" seamless>
If the employee is not found, consider using [list]() or any [find commands]() to locate the employee in the employee list.
</box>

2. Type in the following [command](#glossary) in the [command box](#glossary) `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL_ADDRESS] [a/HOME_ADDRESS] [t/TAG]...` where `INDEX` is the index of the employee in the list currently, `[n/NAME]`, `[p/PHONE_NUMBER]`, `[e/EMAIL_ADDRESS]`, `[a/HOME_ADDRESS]` are optional fields which require changing, replacing `NAME` with employee name, `PHONE_NUMBER` with employee phone number, `EMAIL_ADDRESS` with employee email address and `HOME_ADDRESS` with employee home address. `[t/TAG]...` is an optional field representing one or more [tags](#glossary) where `TAG` is the tag name. Note that at least one field to edit must be present and only the fields present will be edited.
 - For example, to change the phone number, email address and tags of the employee indexed 2 to 98765432, johndoe@example.com and full-time and remote, type in the command `edit 2 p/98765432 e/johndoe@example.com t/full-time t/remote`. Note that the name and home address will remain unchanged.
 - In another example, to change the home address of the employee indexed 1 to John street, block 123 #01-01 and remove all tags from the employee, type in the command `edit 1 a/John street, block 123 #01-01 t/`. Note that the name, phone number and email_address will remain unchanged.


<box type="warning" seamless>
If the tag prefix is specified, all existing tags under the employee will be removed and replaced with the new tags in the command.
In the first example, the employee will have all tags removed and replaced by 2 tags: full-time and remote.
In the second example, the employee will have all tags removed. No tags will be added since no tags are specified.
Therefore, to avoid unintentionally losing any information while editing tags, we recommend using the <a href="#adding-tags-to-employees--add-tag">add tag</a> and <a href="#removing-tags-from-employees--delete-tag">delete-tag</a> commands instead for editing tag
</box>

3. Press "enter" on your keyboard and you should see the changes applied to the employee.

| Error message | Why it happens | Fix |
|---------------|----------------|-----|
| `Invalid command format!` | The command you input does not follow the specified format | Ensure the command you entered follows the following format: `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL_ADDRESS] [a/HOME_ADDRESS] [t/TAG]...` where `INDEX` is the index of the employee in the list currently, `[n/NAME]`, `[p/PHONE_NUMBER]`, `[e/EMAIL_ADDRESS]`, `[a/HOME_ADDRESS]` are optional fields which require changing, replacing `NAME` with employee name, `PHONE_NUMBER` with employee phone number, `EMAIL_ADDRESS` with employee email address and `HOME_ADDRESS` with employee home address. `[t/TAG]...` is an optional field representing one or more tags where `TAG` is the tag name. |
| `FIELD should FORMAT` where `FIELD` is an input like `Names` or `Phone numbers` and `FORMAT` contains additional information about the field's format. | The input does not follow the format prescribed. For example, the entered phone number might contain alphabets. | Follow the on screen message to fix the field in question. For example, `Phone numbers should only contain numbers, and it should be at least 3 digits long` means that the input phone number does not follow the prescribed format. |
| `The person index provided is invalid` | The index specified does not refer to any employee | Double check if the index appears in the employee list. Alternatively, use `list` or any find commands to locate the employee in the employee list. Afterwards, use the correct employee index in the `edit` command. |
| `At least one field to edit must be provided` | The command you input does not contain any fields to edit | Check if there is any input fields in the command inputted. An input like `edit 1` is not accepted as there is no edits to be made. |
| `This employee already exists in the address book` | The provided employee name is already found in HRMate | Use another name for the employee. For example, if trying to add another John Doe, use the name John Doe (HR) to differentiate between the existing John Doe. HRMate does this name checking to prevent unintentional duplicate employee entries.

* **For advanced users:**
  * You can remove all the tags of an employee with `edit INDEX t/` (see warning above)

### Viewing All Tags: `view-tag`

* **What It Does:**
  * Shows a list of all tags currently in use within the address book, which helps HR managers quickly identify different categories of employees.

* **Format:**
  * `view-tag`
* **Examples:**

| S/N | Command information                                                                                                         |
|-----|-----------------------------------------------------------------------------------------------------------------------------|
| 1   | **Command:** `view-tag` <br><br> **Output:** Shows a list of all tags assigned to at least one employee in the address book |

* **Acceptable Values:**
  * This command doesn't require any parameters.
* **Expected Output on Success:**
  * GUI Changes: A list of tags is displayed in a dedicated interface section.
  * Message shown to the user: "Successfully fetched all tags."
* **Expected Output on Failure:**
  * Error messages: N/A (since no parameters are involved, the command will not fail due to invalid input)

### Finding employees with all tags in a group of tags: `find-all-tag`

* **What It Does:**
  * Filters and displays individuals from a dataset who match all the specified tags (case-sensitive) exactly. It helps users narrow their search to find records meeting all the specified criteria.

* **Format:**
  * `find-all-tag t/TAG [t/MORE_TAGS]...`
* **Examples:**


| S/N | Command information                                                                                                                      |
|-----|------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | **Command:** `find-all-tag t/remote t/full time` <br><br> **Output:** Shows a list of employees who are both full-time and work remotely |

* **Acceptable Values:**
  * Tags: Any predefined tags available in the address book (e.g. remote, contract, fullTime)
  * Only full words will be matched, e.g. “remote” will not match “remotely”
* **Expected Output on Success:**
  * GUI Changes: A list of employees who match some specified tags will be shown in a dedicated interface section
  * Message shown to the user: "[Number of matched employees] employees matched: "
* **Expected Output on Failure:**
  * `Invalid command format!` - Missing parameters
  * `Tags names only allows alphanumeric characters, spaces, and dashes.` - Invalid parameter inputs

### Finding employees with at least one tag in a group of tags: `find-some-tag`


* **What It Does:**
  * Filters and displays individuals from a dataset who match at least one of the specified tags (case-sensitive). It provides flexibility by showing records that meet any of the criteria provided.

* **Format:**
  * `find-some-tag t/TAG [t/MORE_TAGS]...`
* **Examples:**

| S/N | Command information                                                                                                                                                           |
|-----|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | **Command:** `find-some-tag t/fullTime t/remote ` <br><br> **Output:** Shows a list of employees who either full-time or work remotely or both full-time and working remotely |

* **Acceptable Values:**
  * Tags: Any predefined tags available in the address book (e.g. remote, contract, fullTime)
  * Only full words will be matched, e.g. “remote” will not match “remotely”
* **Expected Output on Success:**
  * GUI Changes: A list of employees who match some of the specified tags will be shown in a dedicated interface section
  * Message shown to the user: "[Number of matched employees] employees matched: "
* **Expected Output on Failure:**
  * `Invalid command format!` - Missing parameters
  * `Tags names only allows alphanumeric characters, spaces, and dashes.` - Invalid parameter inputs

### Locating an employee in HRMate by name: `find`

* **What It Does:**
  * Finds all employees whose name contains at least one word in the search query (case-insensitive). Only name
    containing the whole word are matched.
* **Format:**
  * `find SEARCH_QUERY...`
* **Examples:**

| S/N | Command information                                                                                                                                                                                                                  |
|-----|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | **Command:** `find martinez` <br><br> **Output:** Returns a list of employees whose names contain "*martinez*" e.g. "*Emiliano <u>Martinez</u>*" and "*Lisandro <u>Martinez</u>*"                                                    |
| 2   | **Command:** `find harry redknapp` <br><br> **Output:** Returns a list of employees whose names contain either "*harry*" or "*redknapp*" e.g. "*<u>Harry</u> Kane*", "*Jamie <u>Redknapp</u>*", and "*<u>Harry</u> <u>Redknapp</u>*" |
| 3   | **Command:** `find martin` <br><br> **Output:** Returns a list of employees whose names contain "*martin*" e.g. "*<u>Martin</u> Odegaard*" but not "*Gabriel Martinelli*" (only full word matches are displayed)                     |
* **Acceptable Values:**
  * Search query: A non-empty input is required. Inputs can contain one or more words.
* **Expected Output on Success:**
  * GUI Changes: A list of employees whose names contain at least one of the words
  * Message shown to user: "[Number of matches] employees matched:"
* **Expected Output on Failure:**
  * `Invalid Command Format` - if an empty input is provided
* **For advanced users:**
  * Employees are displayed in the order in which they are arranged in the address book

### Removing an employee Record : `delete`

* **What It Does:**
  * Removes an existing employee from HRMate, so that the employee’s record is no longer available in HRMate.
* **Format:**
  * `delete EMPLOYEE_LIST_INDEX`
* **Examples:**

| S/N | Command information                                                                                                                                                                                                                         |
|-----|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | **Command:** `delete 1` <br><br> **Output:** Deletes the record belonging to the employee whose index is 1. For instance, if the record belongs to “*David de Gea*”, then *de Gea*’s record is removed from HRMate and no longer available. |
| 2   | **Command:** `delete 5` <br><br> **Output:**: Deletes the record belonging to the employee whose index is 5. For instance, if the record belongs to “*Carlos Puyol*”, then *Puyol*’s record is removed from HRMate and no longer available. |
* **Acceptable Values:**
  * The specified `EMPLOYEE_LIST_INDEX` must correspond to an index number shown in the address book. It must be a valid number, larger than zero, and cannot exceed the number of entries in the address book.
* **Expected Output on Success:**
  * GUI Changes: The record of the employee whose index is specified will no longer appear in the list of employees
  * Message shown to user: "[Employee name]'s record deleted"
* **Expected Output on Failure:**
  * `Employee List Index does not exist` - An invalid index was specified
  * `Employee List Index not specified` - No index was specified by the user

### Restarting HRMate by clearing all existing records : `clear`

* **What It Does:**
  * Removes every entry from HRMate, such that the address book in HRMate is empty. A possible use case of this command
    is to remove all sample records in HRMate so that you can fill it with employee records instead.
* **Format:**
  * `clear`
* **Examples:**

| S/N | Command information                                                                          |
|-----|----------------------------------------------------------------------------------------------|
| 1   | **Command:** `clear` <br><br> **Output:** Deletes every employee record in the address book. |
* **Acceptable Values:**
  * This command does not require any parameters.
* **Warning:**
  <box type="warning" seamless>
    Be careful when using this command - the records are irretrievable once deleted.
  </box>
* **Expected Value on Success:**
  * GUI Changes: All existing records are no longer visible in the address book
  * Message shown to the user: "Address book has been cleared"
* **Expected Value on Failure:**
  * N/A (no expected failure)

### Adding a leave record: `add-leave`
* **What It Does:**
  * Adds a leave record in HRMate based on employee index

* **Format:**
  * `add-leave INDEX title/TITLE start/START_DATE end/END_DATE [d/DESCRIPTION]`
* Examples:

| S/N | Command information                                                                                                                                                                                                                                                                                                                                                                                         |
|-----|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | **Command:** `add-leave 1 title/Sample Leave 1 start/2023-11-01 end/2023-11-01` <br><br> **Output:** Adds a leave with the following details into the leaves book: <ul><li>Employee: John Doe (employee with index 1)</li> <li>Title: Sample Leave 1</li> <li>Start: 2023-11-01</li> <li>End: 2023-11-01</li> <li>Description: NONE</li> <li>Status: PENDING</li></ul>                                      |
| 2   | **Command:** `add-leave 2 title/Sample Leave 2 start/2023-11-01 end/2023-11-02 d/Sample Description` <br><br> **Output:** Adds a leave with the following details into the leaves book: <ul><li>Employee: Steve Nash (employee with index 2)</li> <li>Title: Sample Leave 2</li> <li>Start: 2023-11-01</li> <li>End: 2023-11-02</li> <li>Description: Sample Description</li> <li>Status: PENDING</li></ul> |

* **Acceptable value:**
  * The specified `INDEX` must match with a employee record shown in the displayed employee list.
  * Description field is optional, the rest of field cannot be left empty.
  * For start and end: they must be in the format `yyyy-mm-dd` (like 2023-11-01 for 2023 November 1st). Additionally, the start date must be before or equals to the end date.
  * For status: it is PENDING status in default when a leave is first added.

* **Expected output on failure:**
  * `Date should be valid and in a format of "yyyy-MM-dd"` - if the format of date is invalid or no date input after "start/" and/or "end/"
  * `The leave index provided is invalid` - if the index provided does not match with any employee record in the displayed leave list
  * `Invalid command format!... ` - if any of the required field missing
  * `Leave titles should only contain...` - if the title input is empty or invalid
  * `End date cannot be before start date` - the end date provided is before the current or provided start date


### Editing a leave record: `edit-leave`
* **What It Does:**
  * Edits an existing leave record in HRMate based on their index

* **Format:**
  * `edit-leave INDEX [title/TITLE] [start/START_DATE] [end/END_DATE] [d/DESCRIPTION] [s/STATUS]`
    
* Examples:

| S/N | Command information |
|-----|------|
| 1   | **Command:** `edit-leave 1 title/medical leave start/2023-11-01` <br> **Output**: Edits the leave record with index 1 to have the below changes: <ul><li>Title: medical leave</li><li>Start date: 2023 November 1st</li><li>No changes to the other fields</li></ul> |
| 2   | **Command:** `edit-leave 2 end/2023-11-02 d/not free until 11pm s/APPROVED` <br> **Output**: Edits the leave record with index 2 to have the below changes: <ul><li>End date: 2023 November 2nd</li><li>Description: not free until 11pm</li><li>Status: APPROVED</li></ul> |

* **Acceptable value:**
  * The specified `INDEX` must match with a leave record shown in the displayed leave list.
  * At least one leave record's field must be changed (`edit-leave 1` would be invalid as no leave record field are changed)
  * Only fields specified will be modified; fields not specified in the command will not be modified. A field is specified by including its associated field prefix in the command (i.e. `title/` for title, `start/` for start date etc.)
  * For title: if this field is included in the command, it cannot be left empty (i.e. `title/` is not allowed)
  * For start and end: if these fields are included in the command, they must be in the format yyyy-mm-dd (like 2023-11-01 for 2023 November 1st). Additionally, the start date must be before or equals to the end date.
  * For status: if this field is included in the command, it must be either APPROVED, PENDING or REJECTED

* **Expected output on failure:**
  * `At least one field to edit must be provided` - if no fields are provided
  * `The leave index provided is invalid` - if the index provided does not match with any leave record in the displayed leave list
  * `FIELD must be of format...` - if at least one of the field inputs violates the field's format requirements. Follow the displayed error message to fix the format error.
  * `end date cannot be before start date` - the end date provided is before the current or provided start date

### Removing a leave record: `delete-leave`

* **What It Does:**
  * Removes an existing leave record form HRMate, so that the leave's record is no longer available in HRMate.
* **Format:**
  * `delete-leave LEAVE_LIST_INDEX`
* **Examples:**

| S/N | Command information                                                                                                                                                                                                                                                    |
|-----|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | **Command:** `delete-leave 1` <br><br> **Output:** Deletes the leave record with index 1. For instance, if the leave record titled "medical leave" with employee "David de Gea" is indexed 1, then "medical leave" is removed from HRMate and no longer available.     |
| 2   | **Command:** `delete-leave 2` <br><br> **Output:** Deletes the leave record with index 2. For instance, if the leave record titled "childcare leave" with employee "Carlos Puyol" is indexed 2, then "childcare leave" is removed from HRMate and no longer available. |

* **Acceptable Values:**
  * The specified `LEAVE_LIST_INDEX` must correspond to an index number shown in the leave book. It must be a valid number, larger than zero, and cannot exceed the number of entries in the leave book.
* **Expected Output on Success:**
  * GUI Changes: The leave record of the specified index will no longer appear in the list of leave records.
  * Message shown to user: "[leave title] deleted"
* **Expected Output on Failure:**
  * `Leave list index does not exist` - An invalid index was specified.
  * `Employee list index not specified` - No index was specified by the user.

### Closes the application : `exit`

* **What It Does:**
  * Closes and exits the application.
* **Format:**
  * `exit`
* **Examples:**

| S/N | Command information                                               |
|-----|-------------------------------------------------------------------|
| 1   | **Command:** `exit` <br><br> **Output:** Closes and exits HRMate. |
* **Acceptable Values:**
  * This command does not require any parameters.
* **Expected Value on Success:**
  * GUI Changes: The application window closes.
* **Expected Value on Failure:**
  * N/A (no expected failure)

### Approve leave record by index: `approve-leave`

* **What It Does:**
  * Approve a leave record by the given index
  * **Format:**
  * `approve-leave INDEX`
* **Examples:**

| S/N | Command information                                                                                                                                                                     |
|-----|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | **Command:** `approve-leave 1` <br><br> **Output:** Approve the leave record of index 1|

* **Acceptable Values:**
  * The index should be a positive number starting from 1 and within the range of the leave list.
* **Expected Output on Success:**
  * GUI Changes: The approved leave will be marked as approved with green highlight.
  * Message shown to user: "Approved Leave:” with the information of the approved leave
* **Expected Output on Failure:**
  * `Leave previously approved: `with the information of the previously approved leave - the leave given by the index was previously approved
  * `The leave index provided is invalid:  - the index is out of bounds

### Reject leave record by index: `reject-leave`

* **What It Does:**
  * Reject a leave record by the given index
  * **Format:**
  * `reject-leave INDEX`
* **Examples:**

| S/N | Command information                                                                   |
|-----|---------------------------------------------------------------------------------------|
| 1   | **Command:** `reject-leave 1` <br><br> **Output:** Reject the leave record of index 1 |

* **Acceptable Values:**
  * The index should be a positive number starting from 1 and within the range of the leave list.
* **Expected Output on Success:**
  * GUI Changes: The rejected leave will be marked as rejected with red highlight.
  * Message shown to user: "Rejected Leave:” with the information of the rejected leave
* **Expected Output on Failure:**
  * `Leave previously Rejected: `with the information of the previously rejected leave - the leave given by the index was previously rejected
  * `The leave index provided is invalid:`  - the index is out of bounds

### Find leave records by time period: `find-leave-range`

* **What It Does:**
  * Finds all leave records in HRMate in a given time period. There are 4 possible scenarios:
  * 1) The time period has a start date and an end date (inclusive) - all leaves with at least one day that falls within
      this period will be displayed
    2) The time period only has a start date (inclusive) - all leaves that either occur on the start date or will occur
      after the start date will be displayed
    3) The time period only has an end date (inclusive) - all leaves that either occur on the end date or will finish
      before the end date will be displayed
    4) The time period does not have a start date or end date - all leaves will be returned
* **Format:**
  * `find-leave-range [start/START_DATE] [end/END_DATE]`
* **Examples:**

| S/N | Command information                                                                                                                                                                     |
|-----|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | **Command:** `find-leave-range start/2023-10-27 end/2023-11-03` <br><br> **Output:** Returns all leaves that have at least one day in the period of 2023-10-27 and 2023-11-03 inclusive |
| 2   | **Command:** `find-leave-range start/2023-10-27` <br><br> **Output:** Returns all leaves that have at least one day either on or after 2023-10-27                                       |
| 3   | **Command:** `find-leave-range end/2023-11-03` <br><br> **Output:** Returns all leaves that have at least one day on or before 2023-11-03                                               |
| 4   | **Command:** `find-leave-range` <br><br> **Output:** Returns all leaves                                                                                                                 |

* **Acceptable Values:**
  * The dates provided for START_DATE and END_DATE must be of the format `yyyy-MM-DD`
  * If both end date and start date are provided, the end date cannot occur before the start date
* **Expected Output on Success:**
  * GUI Changes: All leave applications that fall within the queried time period will be returned
  * Message shown to user: "[number of leaves matched] leaves listed!"
* **Expected Output on Failure:**
  * `The end date is earlier than the start date!` - the start date cannot occur after the end date

### Find leave records by leave status: `find-leave-status`

* **What It Does:**
  * Finds all leave records in HRMate that have a particular status (either APPROVED/PENDING/REJECTED), so that you can
  * see which leaves still require approval
* **Format:**
  * `find-leave-status STATUS`
* **Examples:**

| S/N | Command information                                                                                               |
|-----|-------------------------------------------------------------------------------------------------------------------|
| 1   | **Command:** `find-leave-status APPROVED` <br><br> **Output:** Returns all leaves that have already been approved |
| 2   | **Command:** `find-leave-status PENDING` <br><br> **Output:** Returns all leaves that are still pending approval  |
| 3   | **Command:** `find-leave-status REJECTED` <br><br> **Output:** Returns all leaves that have been rejected         |

* **Acceptable Values:**
  * Status must be either "APPROVED", "PENDING", or "REJECTED". If the value is not in uppercase, the command will still be
    accepted, but all other values are rejected.
* **Expected Output on Success:**
  * GUI Changes: All leave applications that have the requested status will be returned
  * Message shown to user: "[number of leaves matched] leaves listed!"
* **Expected Output on Failure:**
  * `Command should only contain one of the following words: APPROVED / PENDING / REJECTED` - you provided an invalid status,
    try either `APPROVED`, `PENDING`, or `REJECTED` instead

### View all leaves: `find-all-leave`

* **What It Does:**
  * Shows all leave records in HRMate
* **Format:**
  * `find-all-leave`
* **Examples:**

| S/N | Command information                                                                      |
|-----|------------------------------------------------------------------------------------------|
| 1   | **Command:** `find-all-leave` <br><br> **Output:** Returns all leaves in the application |

* **Expected Output on Success:**
  * GUI Changes: All leave applications will be returned
  * Message shown to user: "Current # of Leave Request(s): [Number of leave applications]"
  * If there are no leave applications on the app, the message shown to the user is: "There are currently no leave requests."
* **Expected Output on Failure:**
  * None, this command is not expected to fail

### Find leave records belonging to an employee: `find-leave`

* **What It Does:**
  * Finds all leave records that are associated with an employee, so that you can see the person's leave history and 
  * upcoming leaves
* **Format:**
  * `find-leave INDEX`
* **Examples:**

| S/N | Command information                                                                                                                                                                                              |
|-----|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | **Command:** `find-leave 1` <br><br> **Output:** Returns all leaves associated with the employee with index 1. For instance, if the employee "John Smith" has employee index 1, John's leaves will be displayed. |

* **Acceptable Values:**
  * The specified `INDEX` must match with a employee record shown in the displayed employee list.
* **Expected Output on Success:**
  * GUI Changes: All leave applications associated with the requested employee will be returned.
  * Message shown to user: "[number of leaves matched] leaves listed!"
* **Expected Output on Failure:**
  * `The person index provided is invalid` - The index you provided does not match with any known employee. To resolve this,
    make sure that the employee that you are requesting for is currently displayed in the employee list. Use the number to
    the left of the employee's name as the index
  * `Invalid command format` - There was no employee index provided, please make sure that you supply a number as the indexh 
--------------------------------------------------------------------------------------------------------------------

### Importing/Exporting employee records
It's painful having to add in each employee into HRMate manually. That's why HRMate provides import and export commands,
so you can bring in all your records from Excel with just a single command!

With the import and export commands, HRMate can read and save files in [CSV](#Glossary) format, which is supported
by major spreadsheet applications such as Microsoft Excel. 


#### Importing employee records : `import`

Here's how you can bring over your records from Excel:

1. Export your Excel save file in CSV format. Ensure that the separator is set to be a semicolon(`;`), and that you have
the following fields: Name, Phone, Email, Address. You may also include a Tags column if you wish.
   * You may skip this step if you already have a CSV file (e.g. you are importing a previously exported CSV file generated by
   HRMate)
2. In HRMate, type in the following command in the command box: `import`
3. In the file dialog that opens up, go to where you saved your exported CSV file, click on it, and click on the Open button.
4. You should see your employee records show up in HRMate, along with the message "Employee records have been imported from [your file name]!"

Here are some possible error messages you might encounter and here's how you can fix them:

| Error Message                                                       | Why it happens                                                               | Fix                                                                               |
|---------------------------------------------------------------------|------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| Employee records were not imported                                  | You did not select a file in the file dialog                                 | Retype the command, and make sure to select a CSV file when the file dialog opens |
| Records in file [file name] could not be imported, import cancelled | Your file likely contains illegal characters or is corrupted                 | Ensure that your data fulfils the following constraints (to be added)             |
| No valid records found in file [file name], import cancelled        | Your file either is empty or does not contain a single valid employee record | Ensure that your file is non-empty and fulfils the abovementioned constraints     |                                                     


#### Exporting employee records : `export`

Not only can you bring your data into HRMate, you can also bring your data out of HRMate. HRMate's export feature allows you
to export either the entire set of employee records, or employee records with a particular filter applied (e.g. only export
all full-time employees, which are tagged with "Full time"). You can then either store your exported CSV file for future use,
open it in a different application, or send it to another employee for them to import!

Here's how you can export your data out of HRMate:

1. In HRMate, type in the following command in the command box: `export [file name]`, replacing `[file name]` with the name
you will like to give your file. Your files will be saved in CSV format automatically.
   - For instance, if you would like to save your file as  `employees.csv`, type in the command `export employees`
2. You should see the message "Employee records have been saved to [file name]!"
3. To retrieve your exported file, go to the folder in which HRMate is stored in your File Explorer (if using Windows) or
Finder (if using Mac OS). From there, click on the `export` folder.
4. You should see your file in the `export` folder.

Here are potential error messages that you may receive and here's how to fix them:

| Error Message                                                       | Why it happens                                   | Fix                                                                                                                                                                                                                   |
|---------------------------------------------------------------------|--------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Employee records could not be saved!                                | You do not have the permission to write the file | Try renaming your file name when typing out the command, especially if the previous name refers to an existing file. If not, move HRMate to a different folder where you can create files, and run the command again. |      

--------------------------------------------------------------------------------------------------------------------

### Importing/Exporting leave records
The import and export feature extends to importing and exporting leaves. This allows you to generate lists of leave
applications that can be opened in other major spreadsheet applications such as Microsoft Excel.


#### Importing employee records : `import-leave`

Here's how you can bring over your leave records from Excel:

1. Export your Excel save file in CSV format. Ensure that the separator is set to be a semicolon(`;`), and that you have
   the following fields: Title, Employee, Start, End, Description, Status.
  * You may skip this step if you already have a CSV file (e.g. you are importing a previously exported CSV file generated by
    HRMate)
2. In HRMate, type in the following command in the command box: `import-leave`
3. In the file dialog that opens up, go to where you saved your exported CSV file, click on it, and click on the Open button.
4. You should see your leave records show up in HRMate, along with the message "Leave records have been imported from [your file name]!"

Here are some possible error messages you might encounter and here's how you can fix them:

| Error Message                                                       | Why it happens                                                            | Fix                                                                               |
|---------------------------------------------------------------------|---------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| Leave records were not imported                                     | You did not select a file in the file dialog                              | Retype the command, and make sure to select a CSV file when the file dialog opens |
| Records in file [file name] could not be imported, import cancelled | Your file likely contains illegal characters or is corrupted              | Ensure that your data fulfils the following constraints (to be added)             |
| No valid records found in file [file name], import cancelled        | Your file either is empty or does not contain a single valid leave record | Ensure that your file is non-empty and fulfils the abovementioned constraints     |                                                     


#### Exporting leave records : `export-leave`

Not only can you bring your data into HRMate, you can also bring your data out of HRMate. HRMate's export feature allows you
to export either the entire set of leave application records, or leave records with a particular filter applied (e.g. only export
all leaves in a given time period). You can then either store your exported CSV file for future use,
open it in a different application, or send it to another employee for them to import!

Here's how you can export your data out of HRMate:

1. In HRMate, type in the following command in the command box: `export-leave [file name]`, replacing `[file name]` with the name
   you will like to give your file. Your files will be saved in CSV format automatically.
  - For instance, if you would like to save your file as  `today.csv`, type in the command `export today`
2. You should see the message "Leave records have been saved to [file name]!"
3. To retrieve your exported file, go to the folder in which HRMate is stored in your File Explorer (if using Windows) or
   Finder (if using Mac OS). From there, click on the `export` folder.
4. You should see your file in the `export` folder.

Here are potential error messages that you may receive and here's how to fix them:

| Error Message                     | Why it happens                                   | Fix                                                                                                                                                                                                                   |
|-----------------------------------|--------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Leave records could not be saved! | You do not have the permission to write the file | Try renaming your file name when typing out the command, especially if the previous name refers to an existing file. If not, move HRMate to a different folder where you can create files, and run the command again. |      

--------------------------------------------------------------------------------------------------------------------



### Saving the data

HRMate data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

HRMate data are saved automatically as a JSON file `[JAR file location]/data/hrmate.json`. Advanced users are welcome to update data directly by editing that data file.

* **Warning:**
  <box type="warning" seamless>
    If your changes to the data file makes its format invalid, HRMate will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.
  </box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous HRMate home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                    | Format, Examples                                                                                                                                                      |
|---------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**                   | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Add Leave**             | `add-leave INDEX title/TITLE start/START_DATE end/END_DATE [d/DESCRIPTION]`<br> e.g., `add-leave 1 title/Sample Leave 1 start/2023-11-01 end/2023-11-01`              |
| **Add Tag**               | `add-tag EMPLOYEE_LIST_INDEX TAG`<br> e.g., `add-tag 3 remote`                                                                                                        |
| **Approve Leave**         | `approve-leave 1` <br> e.g., `approve-leave 1`                                                                                                                        |
| **Clear**                 | `clear`                                                                                                                                                               |
| **Delete**                | `delete EMPLOYEE_LIST_INDEX`<br> e.g., `delete 3`                                                                                                                     |
| **Delete Leave**          | `delete-leave LEAVE_LIST_INDEX`<br> e.g., `delete-leave 1`                                                                                                            |
| **Delete Tag**            | `delete-tag EMPLOYEE_LIST_INDEX TAG`<br> e.g., `delete-tag 3 remote`                                                                                                  |
| **Edit**                  | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                           |
| **Edit Leave**            | `edit-leave INDEX [title/TITLE] [start/START_DATE] [end/END_DATE] [d/DESCRIPTION] [s/STATUS]`<br> e.g., `edit-leave 1 title/medical leave start/2023-11-01`           |
| **Exit**                  | `exit`                                                                                                                                                                |
| **Export Contacts**       | `export FILE_NAME`                                                                                                                                                    |           
| **Export Leaves**         | `export-leaves FILE_NAME`                                                                                                                                             |           
| **Find**                  | `find SEARCH_QUERY...​`<br> e.g., `find James Jake`                                                                                                                   |
| **Find All Tags**         | `find-all-tag [t/TAG]...`<br> e.g.,`find-all-tag t/remote t/full-time`                                                                                                |
| **Find Some Tags**        | `find-some-tag [t/TAG]...`<br> e.g.,`find-some-tag t/remote t/full-time`                                                                                              |
| **Find Leaves by Period** | `find-leave-range [start/START_DATE] [end/END_DATE]`                                                                                                                  | 
| **Find Leaves by Status** | `find-leave-status STATUS`                                                                                                                                            |
| **Find All Leaves**       | `find-all-leave`                                                                                                                                                      |
| **Find All Tags**         | `find-all-tag [t/TAG]...`<br> e.g.,`find-all-tag t/remote t/full-time`                                                                                                |
| **Find Leaves**           | `find-leave INDEX`<br> e.g., `fin-leave 1`                                                                                                                            |
| **Find Leaves by Period** | `find-leave-range [start/START_DATE] [end/END_DATE]`                                                                                                                  | 
| **Find Leaves by Status** | `find-leave-status STATUS`                                                                                                                                            |
| **Find Some Tags**        | `find-some-tag [t/TAG]...`<br> e.g.,`find-some-tag t/remote t/full-time`                                                                                              |
| **Help**                  | `help`                                                                                                                                                                |
| **Import Contacts**       | `import`                                                                                                                                                              |
| **Import Leaves**         | `import-leaves`                                                                                                                                                       |
| **List**                  | `list`                                                                                                                                                                |
| **Reject Leave**          | `reject-leave 1` <br> e.g., `reject-leave 1`                                                                                                                          |
| **View tag**              | `view-tag`                                                                                                                                                            |
--------------------------------------------------------------------------------------------------------------------

## Glossary

| Term | Meaning                                                                                                                         |
|------|---------------------------------------------------------------------------------------------------------------------------------|
| CSV  | A text file format that uses commas to separate values. It is supported by a wide range of software, including Microsoft Excel. |
| Command | A line of instructions that you input. Refer to []() for a picture |
| Command box | A box for you to input commands. Refer to []() for a picture. |
| Employee list | The employee list is the list of employees on the left side of the application. Refer to []() for a picture. |
| Prefix | A letter or phrase before an input. Refer to []() for more details |
| Tag  | A text phrase used to categorise employees by. A tag can be a position (intern, senior), department (HR, tech) or any category (full-time, remote). |
| Index | The number labelling each employee in the employee list. Refer to []() for a picture. |
| Web browser | An application to serve the web like Internet Explorer, Google Chrome or Firefox. In fact, you are probably using one to access this guide right now! |
