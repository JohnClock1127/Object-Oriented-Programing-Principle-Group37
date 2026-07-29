import java.util.Scanner;

public class HospitalManagement {

    // Fixed size arrays as required
    private final Doctor[] doctors = new Doctor[25];
    private final Patient[] patients = new Patient[100];
    private final Lab[] labs = new Lab[20];
    private final Facility[] facilities = new Facility[20];
    private final Medical[] medicals = new Medical[100];
    private final Staff[] staffs = new Staff[100];

    // Counters that track how many slots are currently filled
    private int doctorCount = 0;
    private int patientCount = 0;
    private int labCount = 0;
    private int facilityCount = 0;
    private int medicalCount = 0;
    private int staffCount = 0;

    private final Scanner scanner = new Scanner(System.in);

    public HospitalManagement() {
        loadExistingDoctors();
        loadExistingPatients();
        loadExistingLabs();
        loadExistingFacilities();
        loadExistingMedicals();
        loadExistingStaffs();
    }

    // ---------- Preloaded sample data (minimum 3 objects each) ----------

    private void loadExistingDoctors() {
        doctors[doctorCount++] = new Doctor("D001", "Dr. Sarah Lim", "Cardiology", "9am-5pm", "MBBS, MD", 101);
        doctors[doctorCount++] = new Doctor("D002", "Dr. Ahmad Faiz", "Orthopedics", "8am-4pm", "MBBS, MS", 102);
        doctors[doctorCount++] = new Doctor("D003", "Dr. Priya Nair", "Pediatrics", "10am-6pm", "MBBS, MRCPCH", 103);
    }

    private void loadExistingPatients() {
        patients[patientCount++] = new Patient("P001", "Tan Wei Ming", "Hypertension", "Male", "Admitted", 45);
        patients[patientCount++] = new Patient("P002", "Nurul Huda", "Fractured Arm", "Female", "Discharged", 29);
        patients[patientCount++] = new Patient("P003", "Ramesh Kumar", "Diabetes", "Male", "Admitted", 60);
    }

    private void loadExistingLabs() {
        labs[labCount++] = new Lab("Blood Test", 50);
        labs[labCount++] = new Lab("X-Ray Scan", 120);
        labs[labCount++] = new Lab("Urine Test", 30);
    }

    private void loadExistingFacilities() {
        facilities[facilityCount++] = new Facility("General Ward");
        facilities[facilityCount++] = new Facility("Intensive Care Unit");
        facilities[facilityCount++] = new Facility("Private Room");
    }

    private void loadExistingMedicals() {
        medicals[medicalCount++] = new Medical("Paracetamol", "Duopharma", "2027-05-01", 1, 500);
        medicals[medicalCount++] = new Medical("Amoxicillin", "Pfizer", "2026-11-15", 2, 300);
        medicals[medicalCount++] = new Medical("Insulin", "Novo Nordisk", "2026-08-20", 30, 150);
    }

    private void loadExistingStaffs() {
        staffs[staffCount++] = new Staff("S001", "Chong Mei Ling", "Nurse", "Female", 2800);
        staffs[staffCount++] = new Staff("S002", "Farid Hassan", "Receptionist", "Male", 2200);
        staffs[staffCount++] = new Staff("S003", "Lee Chee Kong", "Pharmacist", "Male", 3200);
    }

    // ---------- Menu driven flow ----------

    public void run() {
        int choice;
        do {
            printMainMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    handleDoctorMenu();
                    break;
                case 2:
                    handlePatientMenu();
                    break;
                case 3:
                    handleLabMenu();
                    break;
                case 4:
                    handleFacilityMenu();
                    break;
                case 5:
                    handleMedicalMenu();
                    break;
                case 6:
                    handleStaffMenu();
                    break;
                case 0:
                    System.out.println("Exiting Hospital Management System. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void printMainMenu() {
        System.out.println("\n===== Hospital Management System =====");
        System.out.println("1. Doctor Management");
        System.out.println("2. Patient Management");
        System.out.println("3. Lab Management");
        System.out.println("4. Facility Management");
        System.out.println("5. Medical Stock Management");
        System.out.println("6. Staff Management");
        System.out.println("0. Exit");
    }

    private void handleDoctorMenu() {
        System.out.println("\n-- Doctor Management --");
        System.out.println("1. View all doctors");
        System.out.println("2. Add a new doctor");
        int option = readInt("Enter your choice: ");
        if (option == 1) {
            System.out.println("\n--- Doctor List (" + doctorCount + ") ---");
            for (int i = 0; i < doctorCount; i++) {
                doctors[i].showDoctorInfo();
            }
        } else if (option == 2) {
            if (doctorCount < doctors.length) {
                Doctor doctor = new Doctor();
                doctor.newDoctor(scanner);
                doctors[doctorCount++] = doctor;
                System.out.println("Doctor added successfully.");
            } else {
                System.out.println("Doctor list is full. Cannot add more doctors.");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void handlePatientMenu() {
        System.out.println("\n-- Patient Management --");
        System.out.println("1. View all patients");
        System.out.println("2. Add a new patient");
        int option = readInt("Enter your choice: ");
        if (option == 1) {
            System.out.println("\n--- Patient List (" + patientCount + ") ---");
            for (int i = 0; i < patientCount; i++) {
                patients[i].showPatientInfo();
            }
        } else if (option == 2) {
            if (patientCount < patients.length) {
                Patient patient = new Patient();
                patient.newPatient(scanner);
                patients[patientCount++] = patient;
                System.out.println("Patient added successfully.");
            } else {
                System.out.println("Patient list is full. Cannot add more patients.");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void handleLabMenu() {
        System.out.println("\n-- Lab Management --");
        System.out.println("1. View all labs");
        System.out.println("2. Add a new lab");
        int option = readInt("Enter your choice: ");
        if (option == 1) {
            System.out.println("\n--- Lab List (" + labCount + ") ---");
            for (int i = 0; i < labCount; i++) {
                labs[i].labList();
            }
        } else if (option == 2) {
            if (labCount < labs.length) {
                Lab lab = new Lab();
                lab.newLab(scanner);
                labs[labCount++] = lab;
                System.out.println("Lab added successfully.");
            } else {
                System.out.println("Lab list is full. Cannot add more labs.");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void handleFacilityMenu() {
        System.out.println("\n-- Facility Management --");
        System.out.println("1. View all facilities");
        System.out.println("2. Add a new facility");
        int option = readInt("Enter your choice: ");
        if (option == 1) {
            System.out.println("\n--- Facility List (" + facilityCount + ") ---");
            for (int i = 0; i < facilityCount; i++) {
                facilities[i].showFacility();
            }
        } else if (option == 2) {
            if (facilityCount < facilities.length) {
                Facility facility = new Facility();
                facility.newFacility(scanner);
                facilities[facilityCount++] = facility;
                System.out.println("Facility added successfully.");
            } else {
                System.out.println("Facility list is full. Cannot add more facilities.");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void handleMedicalMenu() {
        System.out.println("\n-- Medical Stock Management --");
        System.out.println("1. View all medical stock");
        System.out.println("2. Add a new medical item");
        int option = readInt("Enter your choice: ");
        if (option == 1) {
            System.out.println("\n--- Medical Stock List (" + medicalCount + ") ---");
            for (int i = 0; i < medicalCount; i++) {
                medicals[i].findMedical();
            }
        } else if (option == 2) {
            if (medicalCount < medicals.length) {
                Medical medical = new Medical();
                medical.newMedical(scanner);
                medicals[medicalCount++] = medical;
                System.out.println("Medical item added successfully.");
            } else {
                System.out.println("Medical list is full. Cannot add more items.");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void handleStaffMenu() {
        System.out.println("\n-- Staff Management --");
        System.out.println("1. View all staff");
        System.out.println("2. Add a new staff member");
        int option = readInt("Enter your choice: ");
        if (option == 1) {
            System.out.println("\n--- Staff List (" + staffCount + ") ---");
            for (int i = 0; i < staffCount; i++) {
                staffs[i].showStaffInfo();
            }
        } else if (option == 2) {
            if (staffCount < staffs.length) {
                Staff staff = new Staff();
                staff.newStaff(scanner);
                staffs[staffCount++] = staff;
                System.out.println("Staff added successfully.");
            } else {
                System.out.println("Staff list is full. Cannot add more staff.");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    // Input

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid whole number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public static void main(String[] args) {
        HospitalManagement app = new HospitalManagement();
        app.run();
        app.scanner.close();
    }
}