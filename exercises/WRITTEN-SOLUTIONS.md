# CSC207 written exercise solutions

## Chapter 3: UML to Java

Each declaration belongs in its own file if made public. Constructors in subclasses
must call `super(balance)` because Account has no no-argument constructor.

```java
abstract class Account {
  protected double balance;

  public Account(double balance) {
    this.balance = balance;
  }

  public void deposit(double amount) {
    balance += amount;
  }

  public abstract boolean withdraw(double amount);
}

interface InterestBearing {
  void addInterest();
}

class ChequingAccount extends Account {
  private double overdraftLimit;

  public ChequingAccount(double balance) {
    super(balance);
  }

  public boolean withdraw(double amount) {
    // Implementation omitted: the question asks for declarations.
    throw new UnsupportedOperationException();
  }
}

class SavingsAccount extends Account implements InterestBearing {
  private double rate;

  public SavingsAccount(double balance) {
    super(balance);
  }

  public boolean withdraw(double amount) {
    throw new UnsupportedOperationException();
  }

  public void addInterest() {
    balance += balance * rate;
  }
}
```

The completed Java-to-UML diagram is in
[vehicles-uml-starter.puml](../plantuml/exercises/vehicles-uml-starter.puml).
Solid generalization arrows represent `extends`; the dashed realization arrow
represents `ElectricCar implements Chargeable`.

## Chapter 8: Library noun–verb analysis

Candidate classes: Item, LoanableItem, Book, DVD, ReferenceItem, Member,
Librarian, Hold and Catalogue. Borrowable describes the borrowing capability.
Title, catalogue number, member number, loan period and late fee are attributes;
three weeks and one week are attribute values. Library is the overall system,
so a separate Library object adds no responsibility to this small model.

Item owns identifying information. Catalogue indexes items by unique catalogue
number and rejects duplicates when adding an item. Member owns member identity
and late-fee state. Member.login checks the supplied member number against the
member record; the specification does not define a password scheme.
Librarian coordinates adding catalogue items and waiving a member's late fee.
LoanableItem owns its current borrower and ordered holds; it enforces that borrowing
requires an available item, and placing a hold requires an item currently on loan.
Hold remembers the requesting member and target item. Member delegates borrowing
and hold placement to Borrowable; ReferenceItem exposes neither operation.

The completed diagram is in
[library-uml-starter.puml](../plantuml/exercises/library-uml-starter.puml).
Book and DVD are small subclasses with loan periods of 21 and 7 days. A single
LoanableItem class with a media-type field would also be reasonable because the
only specified difference is the loan period. We use subclasses to explicitly
show the named categories in this exercise; borrowing logic stays in their parent.
Member and Librarian do not share a parent: the specification supplies no shared
identity fields or behavior to extract. Add a Person superclass if later
requirements introduce shared person data.

### Hold scenario

1. Member logs in using its member number and selects an item by catalogue number.
2. Catalogue.findItem returns that item, or the operation reports an unknown item.
3. Member.placeHold delegates to the selected Borrowable; reference-only items
   cannot participate because they do not implement that interface.
4. LoanableItem checks isOnLoan using its borrower field. An available item causes
   hold placement to be rejected without changing the hold queue.
5. LoanableItem creates a Hold referring to the member and itself and appends it
   to its ordered holds. The member receives confirmation of the recorded hold.

LoanableItem is responsible for knowing whether it is on loan. This keeps the
availability check and borrowing update with the state they use. An alternative
is a Loan or circulation service that records active loans: that is better for
loan history, due dates and persistent storage, but requires collaborating with
that service for every availability check. An item's borrower field and a
separate active-loan record must not become independent sources of truth.

### Study-room reservation use case

Name: Reserve a study room. Primary actor: Student.

Preconditions: The student is signed in and has selected a room, date and time.
Trigger: The student submits the reservation request.
Success outcome: One reservation for the selected room and time is recorded for
that student, and the system displays confirmation.

1. Student submits the selected room, date and time.
2. System validates the requested time and checks current availability.
3. System records the reservation only if the room is still available, preventing
   another reservation for the same room at an overlapping time.
4. System displays confirmation identifying the room, date and time.

Extension at step 3: Another reservation makes the room unavailable. The system
records no reservation for this request, reports unavailability and lets the
student select another room or time.

Extension at step 2: The requested time is in the past or otherwise invalid.
The system explains the invalid time and lets the student revise it. No
reservation is created or changed.

## Chapter 9: Report responsibilities

Report violates the Single Responsibility Principle: formatting, file output
and email delivery have separate reasons to change. Keep report generation in
Report, and move output to ReportFileWriter and ReportEmailer. A ReportOutput
interface lets calling code depend on the output abstraction (DIP).

## Chapters 10–11: Architecture review questions

- Policies include business rules such as enrolment limits and actions such as
  reserving a room. UI event listeners are low-level adapters; entities embody
  high-level domain policies.
- High-level policy should not depend on low-level details because changes to a
  UI or database should not require changes to business rules.
- The Controller depends on InputBoundary, implemented by the Interactor.
  The Interactor depends on OutputBoundary and the data-access interface,
  implemented by Presenter and DataAccess respectively. Those interfaces belong
  to the use-case layer, so dependencies point inward while runtime calls can
  pass outward through implementations.
- InputBoundary.execute typically returns void in the illustrated architecture:
  results are delivered through OutputBoundary rather than returned to Controller.
- Main creates DataAccess, ViewModel and Presenter first, injects those into
  Interactor, injects Interactor into Controller, then connects Controller and
  ViewModel to View. Main selects concrete implementations and assembles objects.
- SRP separates presentation, orchestration and persistence. OCP permits adding
  interface implementations. LSP requires interchangeable implementations to
  preserve their contracts. ISP keeps use-case boundaries focused. DIP directs
  references toward domain and use-case abstractions.
