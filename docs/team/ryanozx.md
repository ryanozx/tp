<frontmatter>
  layout: default.md
  title: "Ryan's Project Portfolio Page"
</frontmatter>

### Project: HRMate

HRMate is a desktop address book application that aims to streamline HR processes, by offering an intuitive, CLI-based
contact management system with specialised functionalities for HR tasks. It has a GUI created with JavaFX, and is
written in Java with about 10 kLoC.

Given below are my contributions to the project.

* **Code contributions:** [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=ryanozx&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)
* **Community**
  * Reviewed PRs [#56](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/56), [#62](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/62)
* **Documentation**
  * User Guide:
    * Add documentation for the features `find`, `delete`, `clear`, `exit`, `import`, `export`
  * Developer Guide:
    * Add implementation and user stories for the features `import` and `export`
  * Managed conversion of documentation into MarkBind format
* **Enhancements to existing features**
  * Add method in FileUtil to support writing a stream of lines into a file ([#58](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/58))
* **New Feature**: Import and export commands ([#53](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/53), [#58](https://github.com/AY2324S1-CS2103T-W11-1/tp/pull/58))
  * Provides ability for users to import and export HRMate data in CSV format for greater portability
  * This feature provides greater interchangeability with other spreadsheet applications like Excel, and allows users to save
    filtered results that can be either stored elsewhere or sent to others
  * Highlights: 
    * Created abstract classes for AdaptedPerson and SerializableAddressBook
    * Created CsvUtil to handle the reading and writing of CSV files
    * Created CsvFile to support the ability to query CSV files for values in a specified column by column name
* **Project Management**
  * Set up Issues tracker in the repo
  * Set up milestones v1.1 - v1.4 and assigned issues to rest of the team
