package hms;

/**
 * Represents a doctor working at the hospital.
 */
public class Doctor extends Person {

    private String specialist;
    private String workTime;
    private String qualification;
    private int room;

    public Doctor(String id, String name, String specialist, String workTime,
                  String qualification, int room) {
        super(id, name);
        this.specialist = specialist;
        this.workTime = workTime;
        this.qualification = qualification;
        this.room = room;
    }

    public static Doctor newDoctor(String id, String name, String specialist, String workTime,
                                    String qualification, int room) throws InvalidInputException {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new InvalidInputException("Doctor ID and name cannot be empty.");
        }
        if (room < 0) {
            throw new InvalidInputException("Room number cannot be negative.");
        }
        return new Doctor(id, name, specialist, workTime, qualification, room);
    }

    @Override
    public void showInfo() {
        showDoctorInfo();
    }

    public void showDoctorInfo() {
        System.out.println("--------------------------------------------------");
        System.out.printf("ID            : %s%n", id);
        System.out.printf("Name          : %s%n", name);
        System.out.printf("Specialist    : %s%n", specialist);
        System.out.printf("Work Time     : %s%n", workTime);
        System.out.printf("Qualification : %s%n", qualification);
        System.out.printf("Room          : %d%n", room);
        System.out.println("--------------------------------------------------");
    }

    // Getters / setters
    public String getSpecialist() { return specialist; }
    public void setSpecialist(String specialist) { this.specialist = specialist; }

    public String getWorkTime() { return workTime; }
    public void setWorkTime(String workTime) { this.workTime = workTime; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public int getRoom() { return room; }
    public void setRoom(int room) { this.room = room; }

    @Override
    public String toString() {
        return String.format("%-6s %-20s %-15s %-12s %-10s Room:%d",
                id, name, specialist, workTime, qualification, room);
    }
}
