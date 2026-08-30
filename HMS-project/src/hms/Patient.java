package hms;

/**
 * Represents a patient admitted to or visiting the hospital.
 */
public class Patient extends Person {

    private String disease;
    private String sex;
    private String admitStatus;
    private int age;

    public Patient(String id, String name, String disease, String sex,
                    String admitStatus, int age) {
        super(id, name);
        this.disease = disease;
        this.sex = sex;
        this.admitStatus = admitStatus;
        this.age = age;
    }

    public static Patient newPatient(String id, String name, String disease, String sex,
                                      String admitStatus, int age) throws InvalidInputException {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new InvalidInputException("Patient ID and name cannot be empty.");
        }
        if (age < 0 || age > 150) {
            throw new InvalidInputException("Age must be between 0 and 150.");
        }
        return new Patient(id, name, disease, sex, admitStatus, age);
    }

    @Override
    public void showInfo() {
        showPatientInfo();
    }

    public void showPatientInfo() {
        System.out.println("--------------------------------------------------");
        System.out.printf("ID            : %s%n", id);
        System.out.printf("Name          : %s%n", name);
        System.out.printf("Disease       : %s%n", disease);
        System.out.printf("Sex           : %s%n", sex);
        System.out.printf("Admit Status  : %s%n", admitStatus);
        System.out.printf("Age           : %d%n", age);
        System.out.println("--------------------------------------------------");
    }

    // Getters / setters
    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getAdmitStatus() { return admitStatus; }
    public void setAdmitStatus(String admitStatus) { this.admitStatus = admitStatus; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return String.format("%-6s %-20s %-15s %-6s %-12s Age:%d",
                id, name, disease, sex, admitStatus, age);
    }
}
