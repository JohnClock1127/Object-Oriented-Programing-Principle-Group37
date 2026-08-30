package hms;

/**
 * EXTENSION CLASS (Section 3.3 requirement).
 *
 * Represents an appointment linking a Patient to a Doctor at a given time.
 * This class demonstrates the Open/Closed Principle in practice: it was
 * added to the system without modifying Patient.java, Doctor.java, or any
 * other existing core class. It simply references their public getters
 * (getId/getName) and plugs into HospitalManagement via one new ArrayList
 * and one new menu option, following the same newX()/showXInfo() pattern
 * already used by the rest of the system.
 */
public class Appointment {

    private String appointmentId;
    private Patient patient;
    private Doctor doctor;
    private String dateTime;
    private String status; // e.g. "Scheduled", "Completed", "Cancelled"

    public Appointment(String appointmentId, Patient patient, Doctor doctor,
                        String dateTime, String status) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = status;
    }

    public static Appointment newAppointment(String appointmentId, Patient patient, Doctor doctor,
                                              String dateTime, String status) throws InvalidInputException {
        if (appointmentId == null || appointmentId.isBlank()) {
            throw new InvalidInputException("Appointment ID cannot be empty.");
        }
        if (patient == null || doctor == null) {
            throw new InvalidInputException("Appointment requires a valid patient and doctor.");
        }
        return new Appointment(appointmentId, patient, doctor, dateTime, status);
    }

    public void showAppointmentInfo() {
        System.out.println("--------------------------------------------------");
        System.out.printf("Appointment ID : %s%n", appointmentId);
        System.out.printf("Patient        : %s (%s)%n", patient.getName(), patient.getId());
        System.out.printf("Doctor         : %s (%s)%n", doctor.getName(), doctor.getId());
        System.out.printf("Date/Time      : %s%n", dateTime);
        System.out.printf("Status         : %s%n", status);
        System.out.println("--------------------------------------------------");
    }

    public String getAppointmentId() { return appointmentId; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public String getDateTime() { return dateTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("%-6s %-15s -> Dr.%-15s %-16s [%s]",
                appointmentId, patient.getName(), doctor.getName(), dateTime, status);
    }
}
