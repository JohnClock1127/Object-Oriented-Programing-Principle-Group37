package hms;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * HospitalManagement is the main control class of the system.
 * It owns the data collections (capacity-bounded ArrayLists, per the
 * spec's "arrays/ArrayLists" requirement with fixed capacities) and
 * drives the main menu loop, delegating all entity-specific behaviour
 * to the respective classes (Doctor, Patient, Staff, Medical, Lab,
 * Facility, Appointment). This keeps HospitalManagement responsible
 * only for orchestration (Single Responsibility Principle).
 *
 * ---------------------------------------------------------------
 * TEAM MEMBERS - replace the placeholder names/IDs below with your
 * actual group members. The first entry of Doctors, Patients, and
 * Staff MUST use a team member's name + last 3 digits of their
 * student ID, per the assignment requirement.
 * ---------------------------------------------------------------
 */
public class HospitalManagement {

    // Capacities as specified in the assignment
    private static final int DOCTOR_CAP = HospitalData.DOCTOR_CAP;
    private static final int PATIENT_CAP = HospitalData.PATIENT_CAP;
    private static final int LAB_CAP = HospitalData.LAB_CAP;
    private static final int FACILITY_CAP = HospitalData.FACILITY_CAP;
    private static final int MEDICAL_CAP = HospitalData.MEDICAL_CAP;
    private static final int STAFF_CAP = HospitalData.STAFF_CAP;

    private final HospitalData data = new HospitalData();
    private final List<Doctor> doctors = data.getDoctors();
    private final List<Patient> patients = data.getPatients();
    private final List<Lab> labs = data.getLabs();
    private final List<Facility> facilities = data.getFacilities();
    private final List<Medical> medicals = data.getMedicals();
    private final List<Staff> staffList = data.getStaffList();
    private final List<Appointment> appointments = data.getAppointments(); // extension

    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        HospitalManagement hms = new HospitalManagement();
        hms.run();
    }

    public void run() {
        printWelcome();
        mainMenu();
        sc.close();
        System.out.println("Thank you for using the Hospital Management System. Goodbye!");
    }

    private void printWelcome() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy  HH:mm:ss");
        System.out.println("====================================================");
        System.out.println("            Welcome to the HMS");
        System.out.println("   " + LocalDateTime.now().format(fmt));
        System.out.println("====================================================");
    }

    // ================= MAIN MENU =================

    private void mainMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n================ MAIN MENU ================");
            System.out.println("1. Doctors");
            System.out.println("2. Patients");
            System.out.println("3. Medical");
            System.out.println("4. Laboratories");
            System.out.println("5. Facilities");
            System.out.println("6. Staff");
            System.out.println("7. Appointments (Extension)");
            System.out.println("0. Exit");
            System.out.println("=============================================");

            int choice = readMenuChoice(0, 7);
            switch (choice) {
                case 1 -> doctorMenu();
                case 2 -> patientMenu();
                case 3 -> medicalMenu();
                case 4 -> labMenu();
                case 5 -> facilityMenu();
                case 6 -> staffMenu();
                case 7 -> appointmentMenu();
                case 0 -> exit = true;
            }
        }
    }

    /**
     * Robust menu-choice reader. Demonstrates runtime exception handling:
     * catches InputMismatchException (non-numeric input) and re-prompts
     * instead of crashing.
     */
    private int readMenuChoice(int min, int max) {
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                if (choice < min || choice > max) {
                    System.out.printf("Please enter a number between %d and %d.%n", min, max);
                    continue;
                }
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: please enter a whole number.");
            }
        }
    }

    private String readNonEmptyLine(String prompt) {
        String value;
        while (true) {
            System.out.print(prompt);
            value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    // ================= DOCTOR MENU =================

    private void doctorMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n------------- DOCTOR MENU -------------");
            System.out.println("1. Add New Doctor");
            System.out.println("2. Display All Doctors");
            System.out.println("3. Search Doctor by Specialist");
            System.out.println("0. Back to Main Menu");

            int choice = readMenuChoice(0, 3);

            switch (choice) {
                case 1 -> addDoctor();
                case 2 -> displayDoctors();
                case 3 -> searchDoctorBySpecialist();
                case 0 -> back = true;
            }
        }
    }

    private void addDoctor() {
        if (doctors.size() >= DOCTOR_CAP) {
            System.out.println("Cannot add more doctors: capacity (" + DOCTOR_CAP + ") reached.");
            return;
        }
        try {
            String id = readNonEmptyLine("Enter Doctor ID: ");
            String name = readNonEmptyLine("Enter Doctor Name: ");
            String specialist = readNonEmptyLine("Enter Specialist Field: ");
            String workTime = readNonEmptyLine("Enter Work Time (e.g. 9am-5pm): ");
            String qualification = readNonEmptyLine("Enter Qualification: ");
            int room = readInt("Enter Room Number: ");

            Doctor d = Doctor.newDoctor(id, name, specialist, workTime, qualification, room);
            doctors.add(d);
            System.out.println("Doctor added successfully!");
        } catch (InvalidInputException e) {
            System.out.println("Failed to add doctor: " + e.getMessage());
        }
    }

    private void displayDoctors() {
        System.out.println("\n================ DOCTOR LIST ================");
        if (doctors.isEmpty()) {
            System.out.println("No doctors registered.");
        } else {
            for (Doctor d : doctors) {
                d.showDoctorInfo();
            }
        }
    }
    
    private void searchDoctorBySpecialist() {
        System.out.println("\n========== SEARCH DOCTOR BY SPECIALIST ==========");

        if (doctors.isEmpty()) {
            System.out.println("No doctors registered.");
            return;
        }

        // Display available specialists
        java.util.List<String> specialists = new java.util.ArrayList<>();

        for (Doctor d : doctors) {
            if (!specialists.contains(d.getSpecialist())) {
                specialists.add(d.getSpecialist());
            }
        }

        System.out.println("Available Specialists:");
        for (int i = 0; i < specialists.size(); i++) {
            System.out.println((i + 1) + ". " + specialists.get(i));
        }

        int choice = readMenuChoice(1, specialists.size());
        String selectedSpecialist = specialists.get(choice - 1);

        System.out.println("\nDoctors in " + selectedSpecialist + ":");

        boolean found = false;

        for (Doctor d : doctors) {
            if (d.getSpecialist().equalsIgnoreCase(selectedSpecialist)) {
                d.showDoctorInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No doctor found.");
        }
    }

    // ================= PATIENT MENU =================

    private void patientMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n------------- PATIENT MENU -------------");
            System.out.println("1. Add New Patient");
            System.out.println("2. Display All Patients");
            System.out.println("3. Search Patient by Disease");
            System.out.println("0. Back to Main Menu");

            int choice = readMenuChoice(0, 3);

            switch (choice) {
                case 1 -> addPatient();
                case 2 -> displayPatients();
                case 3 -> searchPatientByDisease();
                case 0 -> back = true;
            }
        }
    }

    private void addPatient() {
        if (patients.size() >= PATIENT_CAP) {
            System.out.println("Cannot add more patients: capacity (" + PATIENT_CAP + ") reached.");
            return;
        }
        try {
            String id = readNonEmptyLine("Enter Patient ID: ");
            String name = readNonEmptyLine("Enter Patient Name: ");
            String disease = readNonEmptyLine("Enter Disease/Condition: ");
            String sex = readNonEmptyLine("Enter Sex: ");
            String admitStatus = readNonEmptyLine("Enter Admit Status (Admitted/Outpatient): ");
            int age = readInt("Enter Age: ");

            Patient p = Patient.newPatient(id, name, disease, sex, admitStatus, age);
            patients.add(p);
            System.out.println("Patient added successfully!");
        } catch (InvalidInputException e) {
            System.out.println("Failed to add patient: " + e.getMessage());
        }
    }

    private void displayPatients() {
        System.out.println("\n================ PATIENT LIST ================");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
        } else {
            for (Patient p : patients) {
                p.showPatientInfo();
            }
        }
    }
    
    private void searchPatientByDisease() {
        System.out.println("\n========== SEARCH PATIENT BY DISEASE ==========");

        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }

        // Display available diseases
        java.util.List<String> diseases = new java.util.ArrayList<>();

        for (Patient p : patients) {
            if (!diseases.contains(p.getDisease())) {
                diseases.add(p.getDisease());
            }
        }

        System.out.println("Available Diseases:");
        for (int i = 0; i < diseases.size(); i++) {
            System.out.println((i + 1) + ". " + diseases.get(i));
        }

        int choice = readMenuChoice(1, diseases.size());
        String selectedDisease = diseases.get(choice - 1);

        System.out.println("\nPatients with " + selectedDisease + ":");

        boolean found = false;

        for (Patient p : patients) {
            if (p.getDisease().equalsIgnoreCase(selectedDisease)) {
                p.showPatientInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No patient found.");
        }
    }

    // ================= MEDICAL MENU =================

    private void medicalMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n------------- MEDICAL MENU -------------");
            System.out.println("1. Add New Medicine");
            System.out.println("2. Display All Medicines");
            System.out.println("3. Find Medicine by Name");
            System.out.println("0. Back to Main Menu");
            int choice = readMenuChoice(0, 3);
            switch (choice) {
                case 1 -> addMedical();
                case 2 -> displayMedicals();
                case 3 -> findMedicalPrompt();
                case 0 -> back = true;
            }
        }
    }

    private void addMedical() {
        if (medicals.size() >= MEDICAL_CAP) {
            System.out.println("Cannot add more medicines: capacity (" + MEDICAL_CAP + ") reached.");
            return;
        }
        try {
            String name = readNonEmptyLine("Enter Medicine Name: ");
            String manufacturer = readNonEmptyLine("Enter Manufacturer: ");
            String expiryDate = readNonEmptyLine("Enter Expiry Date (YYYY-MM-DD): ");
            int cost = readInt("Enter Cost (RM): ");
            int count = readInt("Enter Stock Count: ");

            Medical m = Medical.newMedical(name, manufacturer, expiryDate, cost, count);
            medicals.add(m);
            System.out.println("Medicine added successfully!");
        } catch (InvalidInputException e) {
            System.out.println("Failed to add medicine: " + e.getMessage());
        }
    }

    private void displayMedicals() {
        System.out.println("\n================ MEDICAL LIST ================");
        if (medicals.isEmpty()) {
            System.out.println("No medicines registered.");
        } else {
            for (Medical m : medicals) {
                m.showMedicalInfo();
            }
        }
    }

    private void findMedicalPrompt() {
        String keyword = readNonEmptyLine("Enter medicine name (or part of it) to search: ");
        Medical.findMedical(medicals, keyword);
    }

    // ================= LAB MENU =================

    private void labMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n------------- LABORATORY MENU -------------");
            System.out.println("1. Add New Lab");
            System.out.println("2. Display All Labs");
            System.out.println("0. Back to Main Menu");
            int choice = readMenuChoice(0, 2);
            switch (choice) {
                case 1 -> addLab();
                case 2 -> Lab.labList(labs);
                case 0 -> back = true;
            }
        }
    }

    private void addLab() {
        if (labs.size() >= LAB_CAP) {
            System.out.println("Cannot add more labs: capacity (" + LAB_CAP + ") reached.");
            return;
        }
        try {
            String lab = readNonEmptyLine("Enter Lab/Test Name: ");
            int cost = readInt("Enter Cost (RM): ");
            Lab l = Lab.newLab(lab, cost);
            labs.add(l);
            System.out.println("Lab added successfully!");
        } catch (InvalidInputException e) {
            System.out.println("Failed to add lab: " + e.getMessage());
        }
    }

    // ================= FACILITY MENU =================

    private void facilityMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n------------- FACILITY MENU -------------");
            System.out.println("1. Add New Facility");
            System.out.println("2. Display All Facilities");
            System.out.println("0. Back to Main Menu");
            int choice = readMenuChoice(0, 2);
            switch (choice) {
                case 1 -> addFacility();
                case 2 -> Facility.showFacility(facilities);
                case 0 -> back = true;
            }
        }
    }

    private void addFacility() {
        if (facilities.size() >= FACILITY_CAP) {
            System.out.println("Cannot add more facilities: capacity (" + FACILITY_CAP + ") reached.");
            return;
        }
        try {
            String facility = readNonEmptyLine("Enter Facility Name: ");
            Facility f = Facility.newFacility(facility);
            facilities.add(f);
            System.out.println("Facility added successfully!");
        } catch (InvalidInputException e) {
            System.out.println("Failed to add facility: " + e.getMessage());
        }
    }

    // ================= STAFF MENU =================

    private void staffMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n------------- STAFF MENU -------------");
            System.out.println("1. Add New Staff");
            System.out.println("2. Display All Staff");
            System.out.println("0. Back to Main Menu");
            int choice = readMenuChoice(0, 2);
            switch (choice) {
                case 1 -> addStaff();
                case 2 -> displayStaff();
                case 0 -> back = true;
            }
        }
    }

    private void addStaff() {
        if (staffList.size() >= STAFF_CAP) {
            System.out.println("Cannot add more staff: capacity (" + STAFF_CAP + ") reached.");
            return;
        }
        try {
            String id = readNonEmptyLine("Enter Staff ID: ");
            String name = readNonEmptyLine("Enter Staff Name: ");
            String designation = readNonEmptyLine("Enter Designation: ");
            String sex = readNonEmptyLine("Enter Sex: ");
            int salary = readInt("Enter Salary (RM): ");

            Staff s = Staff.newStaff(id, name, designation, sex, salary);
            staffList.add(s);
            System.out.println("Staff added successfully!");
        } catch (InvalidInputException e) {
            System.out.println("Failed to add staff: " + e.getMessage());
        }
    }

    private void displayStaff() {
        System.out.println("\n================ STAFF LIST ================");
        if (staffList.isEmpty()) {
            System.out.println("No staff registered.");
        } else {
            for (Staff s : staffList) {
                s.showStaffInfo();
            }
        }
    }

    // ================= APPOINTMENT MENU (EXTENSION) =================

    private void appointmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n------------- APPOINTMENT MENU (Extension) -------------");
            System.out.println("1. Book New Appointment");
            System.out.println("2. Display All Appointments");
            System.out.println("0. Back to Main Menu");
            int choice = readMenuChoice(0, 2);
            switch (choice) {
                case 1 -> addAppointment();
                case 2 -> displayAppointments();
                case 0 -> back = true;
            }
        }
    }

    private void addAppointment() {
        if (patients.isEmpty() || doctors.isEmpty()) {
            System.out.println("Need at least one patient and one doctor to book an appointment.");
            return;
        }
        try {
            String apptId = readNonEmptyLine("Enter Appointment ID: ");

            displayPatients();
            String patientId = readNonEmptyLine("Enter Patient ID from the list above: ");
            Patient chosenPatient = data.findPatientById(patientId);
            if (chosenPatient == null) {
                throw new InvalidInputException("No patient found with ID: " + patientId);
            }

            displayDoctors();
            String doctorId = readNonEmptyLine("Enter Doctor ID from the list above: ");
            Doctor chosenDoctor = data.findDoctorById(doctorId);
            if (chosenDoctor == null) {
                throw new InvalidInputException("No doctor found with ID: " + doctorId);
            }

            String dateTime = readNonEmptyLine("Enter Date/Time (e.g. 2026-09-01 10:00): ");

            Appointment appt = Appointment.newAppointment(apptId, chosenPatient, chosenDoctor, dateTime, "Scheduled");
            appointments.add(appt);
            System.out.println("Appointment booked successfully!");
        } catch (InvalidInputException e) {
            System.out.println("Failed to book appointment: " + e.getMessage());
        }
    }

    private void displayAppointments() {
        System.out.println("\n================ APPOINTMENT LIST ================");
        if (appointments.isEmpty()) {
            System.out.println("No appointments booked.");
        } else {
            for (Appointment a : appointments) {
                a.showAppointmentInfo();
            }
        }
    }

}
