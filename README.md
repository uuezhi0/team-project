# Team Project

Please keep this up-to-date with information about your project throughout the term.

The readme should include information such as:
- a summary of what your application is all about
- a list of the user stories, along with who is responsible for each one
- information about the API(s) that your project uses 
- screenshots or animations demonstrating current functionality

By keeping this README up-to-date,
your team will find it easier to prepare for the final presentation
at the end of the term.

# Project Name: [PROJECT NAME]
## Team Information
### Team Members
- Emma Yuezhi Liu
- Ayesha Abdul-Cader
- Maria Eduarda Melo Queiroz
- David Shen
- Yutong Rao
---
## Project Description
### Information/Utility
Students often know what they want to read about but not where it’s shelved, and Library of Congress call numbers are
confusing if you’ve never used them. Shelf Finder takes a topic or call number and tells you exactly which Robarts books
match, where they are, and whether a copy is on the shelf right now. It’s for U of T students and researchers,
especially first-years and anyone new to Robarts, and for library staff answering “where would I find…” questions.

---
## User Stories
### User Story 1
“As a user I want to input a call number and see which classifications it falls under”
### User Story 2
“As a user, I want to enter a call number like “PS3537” and see the Robarts books shelved there, so I can browse a section the way I would in person.”
### User Story 3
“As a user, I want to see which floor a book’s call number is on, so I can go straight to the right part of the stacks.”
### User Story 4
“As a user, I want to see how many copies are on the shelf right now, so I don’t make a wasted trip.”
### User Story 5
“As a user, I want to save books to a list that’s still there next time I open the app, so I can collect sources over several visits.”

---
## Use Cases
### Use Case 1: Explain a Call Number
**Lead:** Maria Eduarda Melo Queiroz (Duda)
 
**Main Flow:**
1. The student enters a call number (e.g. QA76.73).
2. The system checks that it is a valid Library of Congress call number.
3. The system shows the subjects the call number belongs to, from the broadest class to the most specific (e.g. *Q: Science → QA: Mathematics → QA76: Computer science*).

**Alternative Flows:**
- The call number isn't valid (e.g. a Dewey number) → The system says so and shows an example.
- No specific subject is known for that call number → The system shows the broadest subject it can identify and says no more specific subject is available.
### Use Case 2: Search by Topic
**Lead:** David Shen
 
**Main Flow:**
1. The student enters a topic.
2. The system searches the U of T Libraries catalogue for books on that topic.
3. The system keeps only books held in the Robarts stacks.
4. The system shows the matching books with title, author, year and call number, in shelf order.

**Alternative Flows:**
- The topic is empty → The system asks for a topic.
- No Robarts books match → The system says so and suggests a broader topic.
- The catalogue can't be reached → The system says so and asks the student to try again.
### Use Case 3: Find It in Robarts
**Lead:** Ayesha Abdul-Cader
 
**Main Flow:**
1. The student selects a book from search results, or enters a call number.
2. The system finds the section of the Robarts stacks whose call number range includes it.
3. The system shows the floor, the colour of the section, and the call number range shelved there.

**Alternative Flows:**
- The call number isn't a valid Library of Congress call number (e.g. a Dewey number) → The system says it can't place that call number and shows an example.
- The call number comes before every section in the floor table → The system says the location is unknown and suggests asking at the service desk.
### Use Case 4: Save a Book to a Reading List
**Lead:** Emma Yuezhi Liu
 
**Main Flow:**
1. The student selects a book from search results and asks to save it.
2. The system adds the book to the reading list and stores the list.
3. The system confirms the book was saved.
4. When the student reopens the app, the saved books are still in the reading list.

**Alternative Flows:**
- The book is already in the reading list → The system says so, and the list is unchanged.
- The list can't be stored → The system says the book wasn't saved and that the student may try again.

(Add additional use cases as needed.)
---
## Minimal Viable Product (MVP)
| Team Member | MVP Use Case |
|-------------|--------------|
| Name | Use Case |
| Name | Use Case |
| Name | Use Case |
---
## Entities
### Entity 1
### Entity 2
(Add additional entities as needed.)
---
## API Usage

**API Name:** University of Toronto Libraries catalogue, through the Alma SRU (Search/Retrieve via URL) service
 
**Documentation:** [Ex Libris Alma: SRU/SRW Search](https://knowledge.exlibrisgroup.com/Alma/Product_Documentation/010Alma_Online_Help_(English)/090Integrations_with_External_Systems/030Resource_Management/190SRU_SRW_Search)
 
**Base URL:** `https://ca01.alma.exlibrisgroup.com/view/sru/01UTORONTO_INST`
 
**Example request** (subject search, first 3 records, MARCXML):
```
https://ca01.alma.exlibrisgroup.com/view/sru/01UTORONTO_INST?version=1.2&operation=searchRetrieve&recordSchema=marcxml&query=alma.subjects=climate&maximumRecords=3
```
 
**Access:** No API key is needed. UTL confirmed there are no rate limits, attribution requirements or end date. Ayesha is the team's contact with UTL.
 
**How it is used in the program:**
- **Search by Topic** sends a subject query and builds a `Book` from each record held in the Robarts stacks.
- **Find It in Robarts** takes a book's Robarts call number from those records and looks it up in our own floor table (`floors.csv`), which isn't part of the API.
- **Save a Book to a Reading List** stores each book's catalogue ID (MMS ID) so the saved book can be identified later.
Each response is a list of MARC21 records in XML. The fields we read are:
 
| MARC field | Meaning | Used for |
|---|---|---|
| `001` | MMS ID (the record's unique ID) | `Book` ID, saved reading list |
| `245` $a | Title | Displayed in results |
| `100` $a | Main author | Displayed in results |
| `264` $c (or `260` $c) | Publication year | Displayed in results |
| `AVA` | Physical holdings, one field per library/location | Robarts filter, call number |
 
Within each `AVA` field:
 
| Subfield | Meaning | Example |
|---|---|---|
| $b | Library code | `ROBARTS` |
| $j | Location code | `STACKS` |
| $d | Call number at that library | `PR4579 .J64 2005` |
 
A book counts as being in the Robarts stacks when it has an `AVA` field with `$b = ROBARTS` and `$j = STACKS`. We take the call number from that field's `$d` rather than from the `050` field, because the copy a library holds can have a different call number from the one in the catalogue record.
 
E-books carry an `AVE` field instead of `AVA` and are skipped, since they aren't on a shelf.

---
## Design Diagrams
### UML Class Diagram
### Sequence Diagrams
---
## How to Run
[Instructions for running the program.]
---
## Notes
[Any additional project information.]