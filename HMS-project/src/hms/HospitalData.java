package hms;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the capacity-bounded collections for every entity in the system
 * and the shared seeding logic. Both the console UI (HospitalManagement)
 * and the JavaFX GUI (hms.gui.HMSApp) build on top of this same data
 * layer, so business rules (capacities, validation via newX() factory
 * methods) live in exactly one place.
 */
public class HospitalData {

    public static final int DOCTOR_CAP = 25;
    public static final int PATIENT_CAP = 100;
    public static final int LAB_CAP = 20;
    public static final int FACILITY_CAP = 20;
    public static final int MEDICAL_CAP = 100;
    public static final int STAFF_CAP = 100;

    private final List<Doctor> doctors = new ArrayList<>(DOCTOR_CAP);
    private final List<Patient> patients = new ArrayList<>(PATIENT_CAP);
    private final List<Lab> labs = new ArrayList<>(LAB_CAP);
    private final List<Facility> facilities = new ArrayList<>(FACILITY_CAP);
    private final List<Medical> medicals = new ArrayList<>(MEDICAL_CAP);
    private final List<Staff> staffList = new ArrayList<>(STAFF_CAP);
    private final List<Appointment> appointments = new ArrayList<>();

    public HospitalData() {
        seedInitialData();
    }

    private void seedInitialData() {
        try {
            // --- Doctors (first entry = team member 1) ---
            doctors.add(Doctor.newDoctor("D001", "Ooi Sim Joo (id 123)", "Cardiology", "9am-5pm", "MBBS", 101));
            doctors.add(Doctor.newDoctor("D002", "Dr. Sarah Lim", "Pediatrics", "8am-4pm", "MD", 102));
            doctors.add(Doctor.newDoctor("D003", "Dr. Ahmad Faiz", "Orthopedics", "10am-6pm", "MBBS, MS", 103));
            doctors.add(Doctor.newDoctor("D004", "Dr. Priya Raj", "Neurology", "9am-5pm", "MD, DM", 104));
            doctors.add(Doctor.newDoctor("D005", "Dr. Wei Ming", "General Surgery", "7am-3pm", "MBBS, MS", 105));

            // --- Patients (first entry = team member 1) ---
            patients.add(Patient.newPatient("P001", "Ooi Sim Joo (id 123)", "Flu", "Female", "Outpatient", 22));
            patients.add(Patient.newPatient("P002", "Alice Tan", "Fracture", "Female", "Admitted", 34));
            patients.add(Patient.newPatient("P003", "Kumar Selvam", "Diabetes", "Male", "Outpatient", 55));
            patients.add(Patient.newPatient("P004", "Nur Aisyah", "Asthma", "Female", "Admitted", 19));
            patients.add(Patient.newPatient("P005", "James Wong", "Hypertension", "Male", "Outpatient", 61));

            // --- Staff (first entry = team member 1) ---
            staffList.add(Staff.newStaff("S001", "Member1 (id 001)", "Receptionist", "Male", 2200));
            staffList.add(Staff.newStaff("S002", "Lee Mei Ling", "Nurse", "Female", 3200));
            staffList.add(Staff.newStaff("S003", "Ravi Chandran", "Pharmacist", "Male", 3500));
            staffList.add(Staff.newStaff("S004", "Farah Hanim", "Lab Technician", "Female", 2800));
            staffList.add(Staff.newStaff("S005", "Tan Boon Kiat", "Security Guard", "Male", 2000));

            // --- Medicals ---
            medicals.add(Medical.newMedical("Paracetamol", "Duopharma", "2027-05-01", 5, 500));
            medicals.add(Medical.newMedical("Amoxicillin", "Pfizer", "2026-12-15", 15, 300));
            medicals.add(Medical.newMedical("Ibuprofen", "GSK", "2027-02-20", 8, 400));
            medicals.add(Medical.newMedical("Metformin", "Hovid", "2027-08-10", 12, 250));
            medicals.add(Medical.newMedical("Cetirizine", "Sanofi", "2026-11-30", 6, 350));

            // --- Labs ---
            labs.add(Lab.newLab("Blood Test", 50));
            labs.add(Lab.newLab("X-Ray", 80));
            labs.add(Lab.newLab("MRI Scan", 350));
            labs.add(Lab.newLab("Urine Test", 30));
            labs.add(Lab.newLab("ECG", 60));

            // --- Facilities ---
            facilities.add(Facility.newFacility("Intensive Care Unit (ICU)"));
            facilities.add(Facility.newFacility("Emergency Room"));
            facilities.add(Facility.newFacility("X-Ray Room"));
            facilities.add(Facility.newFacility("Operation Theatre"));
            facilities.add(Facility.newFacility("Pharmacy Counter"));

        } catch (InvalidInputException e) {
            System.out.println("Failed to seed initial data: " + e.getMessage());
        }
    }

    public List<Doctor> getDoctors() { return doctors; }
    public List<Patient> getPatients() { return patients; }
    public List<Lab> getLabs() { return labs; }
    public List<Facility> getFacilities() { return facilities; }
    public List<Medical> getMedicals() { return medicals; }
    public List<Staff> getStaffList() { return staffList; }
    public List<Appointment> getAppointments() { return appointments; }

    public Patient findPatientById(String id) {
        for (Patient p : patients) if (p.getId().equalsIgnoreCase(id)) return p;
        return null;
    }

    public Doctor findDoctorById(String id) {
        for (Doctor d : doctors) if (d.getId().equalsIgnoreCase(id)) return d;
        return null;
    }
}
