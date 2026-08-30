package hms.gui;

import hms.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JavaFX GUI front-end for the Hospital Management System.
 *
 * This class is purely a presentation layer: all data and business rules
 * (capacities, validation, entity behaviour) live in the existing
 * hms.HospitalData model and hms.* entity classes, which are reused
 * unchanged from the console version. This demonstrates the Open/Closed
 * Principle again -- the GUI was added without modifying any core class.
 */
public class HMSApp extends Application {

    private final HospitalData data = new HospitalData();
    private BorderPane root;
    private Label statusBar;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        root.setTop(buildHeader());
        root.setLeft(buildSideMenu());
        root.setCenter(buildWelcomePane());

        statusBar = new Label("Ready.");
        statusBar.setPadding(new Insets(6, 10, 6, 10));
        root.setBottom(statusBar);

        Scene scene = new Scene(root, 950, 620);
        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
        stage.show();
    }

    // ---------------- Header ----------------

    private VBox buildHeader() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy  HH:mm:ss");
        Label title = new Label("Welcome to the HMS");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        Label dateTime = new Label(LocalDateTime.now().format(fmt));
        dateTime.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");

        VBox box = new VBox(4, title, dateTime);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-background-color: #2c3e50;");
        title.setStyle(title.getStyle() + " -fx-text-fill: white;");
        dateTime.setStyle("-fx-font-size: 13px; -fx-text-fill: #cccccc;");
        return box;
    }

    // ---------------- Side menu ----------------

    private VBox buildSideMenu() {
        VBox menu = new VBox(8);
        menu.setPadding(new Insets(15));
        menu.setPrefWidth(190);
        menu.setStyle("-fx-background-color: #ecf0f1;");

        String[] labels = {
                "Doctors", "Patients", "Medical", "Laboratories",
                "Facilities", "Staff", "Appointments (Extension)"
        };
        for (String label : labels) {
            Button btn = new Button(label);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> openSection(label));
            menu.getChildren().add(btn);
        }

        Button home = new Button("Home");
        home.setMaxWidth(Double.MAX_VALUE);
        home.setStyle("-fx-font-weight: bold;");
        home.setOnAction(e -> {
            root.setCenter(buildWelcomePane());
            setStatus("Returned to Home.");
        });
        menu.getChildren().add(0, home);
        menu.getChildren().add(1, new Separator());

        return menu;
    }

    private void openSection(String label) {
        switch (label) {
            case "Doctors" -> root.setCenter(buildDoctorPane());
            case "Patients" -> root.setCenter(buildPatientPane());
            case "Medical" -> root.setCenter(buildMedicalPane());
            case "Laboratories" -> root.setCenter(buildLabPane());
            case "Facilities" -> root.setCenter(buildFacilityPane());
            case "Staff" -> root.setCenter(buildStaffPane());
            case "Appointments (Extension)" -> root.setCenter(buildAppointmentPane());
        }
        setStatus("Viewing: " + label);
    }

    private void setStatus(String msg) {
        statusBar.setText(msg);
    }

    private VBox buildWelcomePane() {
        Label info = new Label(
                "Select a section from the left menu to manage doctors, patients,\n" +
                "medicines, laboratories, facilities, staff, or appointments.");
        info.setStyle("-fx-font-size: 14px;");
        VBox box = new VBox(info);
        box.setPadding(new Insets(30));
        return box;
    }

    // ---------------- Doctor pane ----------------

    private VBox buildDoctorPane() {
        TableView<Doctor> table = new TableView<>();
        table.getColumns().addAll(
                col("ID", "id"), col("Name", "name"), col("Specialist", "specialist"),
                col("Work Time", "workTime"), col("Qualification", "qualification"), col("Room", "room"));
        table.getItems().addAll(data.getDoctors());

        TextField id = new TextField(); id.setPromptText("ID");
        TextField name = new TextField(); name.setPromptText("Name");
        TextField specialist = new TextField(); specialist.setPromptText("Specialist");
        TextField workTime = new TextField(); workTime.setPromptText("Work Time");
        TextField qualification = new TextField(); qualification.setPromptText("Qualification");
        TextField room = new TextField(); room.setPromptText("Room (number)");

        Button addBtn = new Button("Add Doctor");
        Label feedback = new Label();
        addBtn.setOnAction(e -> {
            try {
                if (data.getDoctors().size() >= HospitalData.DOCTOR_CAP) {
                    throw new InvalidInputException("Doctor capacity (" + HospitalData.DOCTOR_CAP + ") reached.");
                }
                int roomNum = parseIntField(room.getText(), "Room");
                Doctor d = Doctor.newDoctor(id.getText().trim(), name.getText().trim(),
                        specialist.getText().trim(), workTime.getText().trim(),
                        qualification.getText().trim(), roomNum);
                data.getDoctors().add(d);
                table.getItems().add(d);
                clearFields(id, name, specialist, workTime, qualification, room);
                feedback.setStyle("-fx-text-fill: green;");
                feedback.setText("Doctor added successfully.");
                setStatus("Added doctor " + d.getId());
            } catch (InvalidInputException ex) {
                feedback.setStyle("-fx-text-fill: red;");
                feedback.setText("Error: " + ex.getMessage());
            }
        });

        FlowPane form = new FlowPane(8, 8, id, name, specialist, workTime, qualification, room, addBtn);
        form.setPadding(new Insets(10));

        VBox box = new VBox(10, sectionTitle("Doctors"), table, form, feedback);
        VBox.setVgrow(table, Priority.ALWAYS);
        box.setPadding(new Insets(15));
        return box;
    }

    // ---------------- Patient pane ----------------

    private VBox buildPatientPane() {
        TableView<Patient> table = new TableView<>();
        table.getColumns().addAll(
                col("ID", "id"), col("Name", "name"), col("Disease", "disease"),
                col("Sex", "sex"), col("Admit Status", "admitStatus"), col("Age", "age"));
        table.getItems().addAll(data.getPatients());

        TextField id = new TextField(); id.setPromptText("ID");
        TextField name = new TextField(); name.setPromptText("Name");
        TextField disease = new TextField(); disease.setPromptText("Disease");
        TextField sex = new TextField(); sex.setPromptText("Sex");
        TextField admitStatus = new TextField(); admitStatus.setPromptText("Admit Status");
        TextField age = new TextField(); age.setPromptText("Age (number)");

        Button addBtn = new Button("Add Patient");
        Label feedback = new Label();
        addBtn.setOnAction(e -> {
            try {
                if (data.getPatients().size() >= HospitalData.PATIENT_CAP) {
                    throw new InvalidInputException("Patient capacity (" + HospitalData.PATIENT_CAP + ") reached.");
                }
                int ageNum = parseIntField(age.getText(), "Age");
                Patient p = Patient.newPatient(id.getText().trim(), name.getText().trim(),
                        disease.getText().trim(), sex.getText().trim(),
                        admitStatus.getText().trim(), ageNum);
                data.getPatients().add(p);
                table.getItems().add(p);
                clearFields(id, name, disease, sex, admitStatus, age);
                feedback.setStyle("-fx-text-fill: green;");
                feedback.setText("Patient added successfully.");
                setStatus("Added patient " + p.getId());
            } catch (InvalidInputException ex) {
                feedback.setStyle("-fx-text-fill: red;");
                feedback.setText("Error: " + ex.getMessage());
            }
        });

        FlowPane form = new FlowPane(8, 8, id, name, disease, sex, admitStatus, age, addBtn);
        form.setPadding(new Insets(10));

        VBox box = new VBox(10, sectionTitle("Patients"), table, form, feedback);
        VBox.setVgrow(table, Priority.ALWAYS);
        box.setPadding(new Insets(15));
        return box;
    }

    // ---------------- Medical pane ----------------

    private VBox buildMedicalPane() {
        TableView<Medical> table = new TableView<>();
        table.getColumns().addAll(
                col("Name", "name"), col("Manufacturer", "manufacturer"),
                col("Expiry", "expiryDate"), col("Cost (RM)", "cost"), col("Stock", "count"));
        table.getItems().addAll(data.getMedicals());

        TextField name = new TextField(); name.setPromptText("Name");
        TextField manufacturer = new TextField(); manufacturer.setPromptText("Manufacturer");
        TextField expiry = new TextField(); expiry.setPromptText("Expiry (YYYY-MM-DD)");
        TextField cost = new TextField(); cost.setPromptText("Cost (RM)");
        TextField count = new TextField(); count.setPromptText("Stock Count");

        Button addBtn = new Button("Add Medicine");
        Label feedback = new Label();
        addBtn.setOnAction(e -> {
            try {
                if (data.getMedicals().size() >= HospitalData.MEDICAL_CAP) {
                    throw new InvalidInputException("Medical capacity (" + HospitalData.MEDICAL_CAP + ") reached.");
                }
                int costNum = parseIntField(cost.getText(), "Cost");
                int countNum = parseIntField(count.getText(), "Count");
                Medical m = Medical.newMedical(name.getText().trim(), manufacturer.getText().trim(),
                        expiry.getText().trim(), costNum, countNum);
                data.getMedicals().add(m);
                table.getItems().add(m);
                clearFields(name, manufacturer, expiry, cost, count);
                feedback.setStyle("-fx-text-fill: green;");
                feedback.setText("Medicine added successfully.");
                setStatus("Added medicine " + m.getName());
            } catch (InvalidInputException ex) {
                feedback.setStyle("-fx-text-fill: red;");
                feedback.setText("Error: " + ex.getMessage());
            }
        });

        TextField searchField = new TextField();
        searchField.setPromptText("Search by name...");
        Button searchBtn = new Button("Find");
        Label searchResult = new Label();
        searchBtn.setOnAction(e -> {
            String keyword = searchField.getText().trim();
            long matches = data.getMedicals().stream()
                    .filter(m -> m.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .count();
            searchResult.setText(matches + " medicine(s) matched \"" + keyword + "\".");
            table.getItems().setAll(data.getMedicals().stream()
                    .filter(m -> keyword.isBlank() || m.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .toList());
        });

        HBox searchBox = new HBox(8, searchField, searchBtn, searchResult);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(0, 10, 0, 10));

        FlowPane form = new FlowPane(8, 8, name, manufacturer, expiry, cost, count, addBtn);
        form.setPadding(new Insets(10));

        VBox box = new VBox(10, sectionTitle("Medical"), table, searchBox, form, feedback);
        VBox.setVgrow(table, Priority.ALWAYS);
        box.setPadding(new Insets(15));
        return box;
    }

    // ---------------- Lab pane ----------------

    private VBox buildLabPane() {
        TableView<Lab> table = new TableView<>();
        table.getColumns().addAll(col("Lab/Test", "lab"), col("Cost (RM)", "cost"));
        table.getItems().addAll(data.getLabs());

        TextField lab = new TextField(); lab.setPromptText("Lab/Test Name");
        TextField cost = new TextField(); cost.setPromptText("Cost (RM)");

        Button addBtn = new Button("Add Lab");
        Label feedback = new Label();
        addBtn.setOnAction(e -> {
            try {
                if (data.getLabs().size() >= HospitalData.LAB_CAP) {
                    throw new InvalidInputException("Lab capacity (" + HospitalData.LAB_CAP + ") reached.");
                }
                int costNum = parseIntField(cost.getText(), "Cost");
                Lab l = Lab.newLab(lab.getText().trim(), costNum);
                data.getLabs().add(l);
                table.getItems().add(l);
                clearFields(lab, cost);
                feedback.setStyle("-fx-text-fill: green;");
                feedback.setText("Lab added successfully.");
                setStatus("Added lab " + l.getLab());
            } catch (InvalidInputException ex) {
                feedback.setStyle("-fx-text-fill: red;");
                feedback.setText("Error: " + ex.getMessage());
            }
        });

        FlowPane form = new FlowPane(8, 8, lab, cost, addBtn);
        form.setPadding(new Insets(10));

        VBox box = new VBox(10, sectionTitle("Laboratories"), table, form, feedback);
        VBox.setVgrow(table, Priority.ALWAYS);
        box.setPadding(new Insets(15));
        return box;
    }

    // ---------------- Facility pane ----------------

    private VBox buildFacilityPane() {
        ListView<String> list = new ListView<>();
        data.getFacilities().forEach(f -> list.getItems().add(f.getFacility()));

        TextField facility = new TextField(); facility.setPromptText("Facility Name");
        Button addBtn = new Button("Add Facility");
        Label feedback = new Label();
        addBtn.setOnAction(e -> {
            try {
                if (data.getFacilities().size() >= HospitalData.FACILITY_CAP) {
                    throw new InvalidInputException("Facility capacity (" + HospitalData.FACILITY_CAP + ") reached.");
                }
                Facility f = Facility.newFacility(facility.getText().trim());
                data.getFacilities().add(f);
                list.getItems().add(f.getFacility());
                clearFields(facility);
                feedback.setStyle("-fx-text-fill: green;");
                feedback.setText("Facility added successfully.");
                setStatus("Added facility " + f.getFacility());
            } catch (InvalidInputException ex) {
                feedback.setStyle("-fx-text-fill: red;");
                feedback.setText("Error: " + ex.getMessage());
            }
        });

        HBox form = new HBox(8, facility, addBtn);
        form.setPadding(new Insets(10));

        VBox box = new VBox(10, sectionTitle("Facilities"), list, form, feedback);
        VBox.setVgrow(list, Priority.ALWAYS);
        box.setPadding(new Insets(15));
        return box;
    }

    // ---------------- Staff pane ----------------

    private VBox buildStaffPane() {
        TableView<Staff> table = new TableView<>();
        table.getColumns().addAll(
                col("ID", "id"), col("Name", "name"), col("Designation", "designation"),
                col("Sex", "sex"), col("Salary (RM)", "salary"));
        table.getItems().addAll(data.getStaffList());

        TextField id = new TextField(); id.setPromptText("ID");
        TextField name = new TextField(); name.setPromptText("Name");
        TextField designation = new TextField(); designation.setPromptText("Designation");
        TextField sex = new TextField(); sex.setPromptText("Sex");
        TextField salary = new TextField(); salary.setPromptText("Salary (RM)");

        Button addBtn = new Button("Add Staff");
        Label feedback = new Label();
        addBtn.setOnAction(e -> {
            try {
                if (data.getStaffList().size() >= HospitalData.STAFF_CAP) {
                    throw new InvalidInputException("Staff capacity (" + HospitalData.STAFF_CAP + ") reached.");
                }
                int salaryNum = parseIntField(salary.getText(), "Salary");
                Staff s = Staff.newStaff(id.getText().trim(), name.getText().trim(),
                        designation.getText().trim(), sex.getText().trim(), salaryNum);
                data.getStaffList().add(s);
                table.getItems().add(s);
                clearFields(id, name, designation, sex, salary);
                feedback.setStyle("-fx-text-fill: green;");
                feedback.setText("Staff added successfully.");
                setStatus("Added staff " + s.getId());
            } catch (InvalidInputException ex) {
                feedback.setStyle("-fx-text-fill: red;");
                feedback.setText("Error: " + ex.getMessage());
            }
        });

        FlowPane form = new FlowPane(8, 8, id, name, designation, sex, salary, addBtn);
        form.setPadding(new Insets(10));

        VBox box = new VBox(10, sectionTitle("Staff"), table, form, feedback);
        VBox.setVgrow(table, Priority.ALWAYS);
        box.setPadding(new Insets(15));
        return box;
    }

    // ---------------- Appointment pane (extension) ----------------

    private VBox buildAppointmentPane() {
        TableView<Appointment> table = new TableView<>();
        TableColumn<Appointment, String> apptIdCol = new TableColumn<>("Appointment ID");
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        TableColumn<Appointment, String> patientCol = new TableColumn<>("Patient");
        patientCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getPatient().getName()));
        TableColumn<Appointment, String> doctorCol = new TableColumn<>("Doctor");
        doctorCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getDoctor().getName()));
        TableColumn<Appointment, String> dtCol = new TableColumn<>("Date/Time");
        dtCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        TableColumn<Appointment, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.getColumns().addAll(apptIdCol, patientCol, doctorCol, dtCol, statusCol);
        table.getItems().addAll(data.getAppointments());

        TextField apptId = new TextField(); apptId.setPromptText("Appointment ID");
        ComboBox<Patient> patientBox = new ComboBox<>();
        patientBox.getItems().addAll(data.getPatients());
        patientBox.setPromptText("Select Patient");
        ComboBox<Doctor> doctorBox = new ComboBox<>();
        doctorBox.getItems().addAll(data.getDoctors());
        doctorBox.setPromptText("Select Doctor");
        TextField dateTime = new TextField(); dateTime.setPromptText("Date/Time (e.g. 2026-09-01 10:00)");

        Button addBtn = new Button("Book Appointment");
        Label feedback = new Label();
        addBtn.setOnAction(e -> {
            try {
                Patient p = patientBox.getValue();
                Doctor d = doctorBox.getValue();
                if (p == null || d == null) {
                    throw new InvalidInputException("Please select both a patient and a doctor.");
                }
                Appointment appt = Appointment.newAppointment(
                        apptId.getText().trim(), p, d, dateTime.getText().trim(), "Scheduled");
                data.getAppointments().add(appt);
                table.getItems().add(appt);
                apptId.clear();
                dateTime.clear();
                patientBox.setValue(null);
                doctorBox.setValue(null);
                feedback.setStyle("-fx-text-fill: green;");
                feedback.setText("Appointment booked successfully.");
                setStatus("Booked appointment " + appt.getAppointmentId());
            } catch (InvalidInputException ex) {
                feedback.setStyle("-fx-text-fill: red;");
                feedback.setText("Error: " + ex.getMessage());
            }
        });

        FlowPane form = new FlowPane(8, 8, apptId, patientBox, doctorBox, dateTime, addBtn);
        form.setPadding(new Insets(10));

        VBox box = new VBox(10, sectionTitle("Appointments (Extension)"), table, form, feedback);
        VBox.setVgrow(table, Priority.ALWAYS);
        box.setPadding(new Insets(15));
        return box;
    }

    // ---------------- Helpers ----------------

    private <T> TableColumn<T, ?> col(String title, String property) {
        TableColumn<T, Object> c = new TableColumn<>(title);
        c.setCellValueFactory(new PropertyValueFactory<>(property));
        return c;
    }

    private Label sectionTitle(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        return l;
    }

    private void clearFields(TextField... fields) {
        for (TextField f : fields) f.clear();
    }

    /**
     * Parses an integer field, throwing InvalidInputException (rather than
     * letting NumberFormatException propagate) so the GUI's single catch
     * block can present a consistent, user-friendly error message.
     */
    private int parseIntField(String text, String fieldName) throws InvalidInputException {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException ex) {
            throw new InvalidInputException(fieldName + " must be a whole number.");
        }
    }
}
