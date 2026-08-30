# Hospital Management System (HMS)

A Java console + JavaFX GUI application for managing Doctors, Patients,
Medical stock, Laboratories, Facilities, and Staff.

## Project structure

```
HMS/
  src/
    hms/
      InvalidInputException.java   Custom checked exception (input validation)
      Person.java                  Abstract base class (id, name, showInfo())
      Staff.java                   extends Person
      Doctor.java                  extends Person
      Patient.java                 extends Person
      Medical.java                 medicine stock
      Lab.java                     lab/test services
      Facility.java                hospital facilities
      Appointment.java             EXTENSION class (links Patient + Doctor)
      HospitalData.java            shared data model (ArrayLists + seeding)
      HospitalManagement.java      console app (has main method)
      gui/
        HMSApp.java                JavaFX GUI app (has its own main method)
```

## ⚠️ Before you submit / record

Replace every `"Member1 (id 001)"` placeholder in **`HospitalData.java`**
(used by both the console app and the GUI) with your real team member
name(s) and the last three digits of their student ID, e.g.:

```java
doctors.add(Doctor.newDoctor("D001", "Chong Cheng Hann (id 730)", ...));
patients.add(Patient.newPatient("P001", "Chong Cheng Hann (id 730)", ...));
staffList.add(Staff.newStaff("S001", "Chong Cheng Hann (id 730)", ...));
```

If you have more than one team member, you can also swap `D002`/`P002`/`S002`,
etc. for other members' names so everyone is represented in the seed data
(the assignment only strictly requires the *first* entry in each list, but
it's a nice touch to show up on camera pointing at "their" record).

## How to run in Eclipse

### Console version (no extra setup needed)
1. `File > Import > Existing Projects into Workspace` (or create a new
   Java Project and copy the `src/hms` folder in, excluding `gui/`).
2. Right-click `HospitalManagement.java` → `Run As > Java Application`.

### JavaFX GUI version
JavaFX is no longer bundled with the JDK (since Java 11+), so you need
the JavaFX SDK separately:

1. Download the JavaFX SDK for your OS from
   https://gluonhq.com/products/javafx/ (choose the SDK, not jmods,
   matching your JDK version, e.g. JavaFX 21 for JDK 21).
2. Unzip it somewhere, e.g. `C:\javafx-sdk-21\` or `/opt/javafx-sdk-21/`.
3. In Eclipse: right-click the project → `Build Path > Add External
   Archives...` → add every `.jar` in the SDK's `lib` folder.
4. Right-click `HMSApp.java` → `Run As > Java Application`, then edit
   the run configuration's **Arguments** tab → **VM arguments**, and add:
   ```
   --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml
   ```
   (adjust the path to wherever you unzipped the SDK).
5. Run again — the GUI window should appear.

*Tip: if your Eclipse has the "e(fx)clipse" plugin installed, it can
generate this run configuration for you automatically via
`New > Other > JavaFX Project`.*

## Command-line run (outside Eclipse)

```bash
# Console version
javac -d out $(find src/hms -maxdepth 1 -name "*.java")
java -cp out hms.HospitalManagement

# GUI version (adjust path to your JavaFX SDK)
javac -d out --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -cp src $(find src -name "*.java")
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -cp out hms.gui.HMSApp
```

## Design notes (for your UML / OOP explanation in the video)

- **Person** (abstract) — holds `id`, `name`, and the abstract
  `showInfo()`. `Staff`, `Doctor`, and `Patient` all **extend** `Person`,
  so shared identity fields and behaviour live in one place
  (avoids duplication, supports the Open/Closed Principle: new
  person-like classes can be added by extending `Person` without
  touching existing code).
- **Medical, Lab, Facility** — standalone classes (no natural "identity"
  concept shared with Person), each following the same
  `newX()` static factory + `showXInfo()` / list-printing pattern for
  consistency across the codebase.
- **Appointment** (the required extension class) — composed of a
  `Patient` reference and a `Doctor` reference, demonstrating a
  **has-a** relationship. It was added with **zero modifications** to
  any existing class — only new files were created, plus one new
  ArrayList and one new menu branch, illustrating the Open/Closed
  Principle in practice.
- **InvalidInputException** — a custom checked exception used across
  every `newX()` factory method to reject invalid input (blank names,
  negative numbers, missing selections). Both the console app and the
  GUI catch it and print a friendly message instead of crashing —
  this is the "runtime Exception Handling for input data validation"
  requirement.
- **HospitalData** — the single source of truth for all six
  capacity-bounded ArrayLists (`Doctor` 25, `Patient` 100, `Lab` 20,
  `Facility` 20, `Medical` 100, `Staff` 100) plus the seed data. Both
  `HospitalManagement` (console) and `HMSApp` (GUI) build on top of it,
  so business rules live in exactly one place (Single Responsibility).
- **HospitalManagement / HMSApp** — pure orchestration/presentation
  layers. They contain no business logic of their own; they only wire
  user input to the entity classes' factory methods and print/render
  the results.

### Suggested UML relationships to describe on camera
- `Doctor --|> Person`, `Patient --|> Person`, `Staff --|> Person` (inheritance)
- `Appointment --> Patient`, `Appointment --> Doctor` (association / has-a)
- `HospitalData --* Doctor/Patient/Staff/Medical/Lab/Facility/Appointment` (composition — owns the lists)
- `HospitalManagement --> HospitalData`, `HMSApp --> HospitalData` (dependency)
- All `newX()` methods `throws InvalidInputException`

## Video recording checklist (10 min max)

1. **Introduction (2 min)** — each member states name, student ID,
   project title, and their task (e.g. "I implemented the Doctor and
   Appointment classes and the console menu").
2. **Program Description (≈7 min)** — run the console app, show the
   welcome banner with date/time, walk through each of the 6 (7 with
   the extension) menu options, add a new entry in at least 2–3
   sections, trigger a validation error on purpose (e.g. leave a name
   blank, or type letters for age) to show exception handling working,
   then optionally launch the JavaFX GUI and repeat briefly.
3. **UML Class Diagram (≈1 min)** — show a diagram (see relationships
   above) and briefly name each class's attributes, methods, and how
   they connect.

## Extension checklist (Section 3.3)

- ✅ New class added with minimal core changes: `Appointment.java`
- ✅ Runtime exception handling: `InvalidInputException` used in every
  `newX()` factory + caught in both UIs
- ✅ JavaFX GUI: `hms/gui/HMSApp.java`
