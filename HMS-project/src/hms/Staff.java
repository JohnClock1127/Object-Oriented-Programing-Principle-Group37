package hms;

/**
 * Represents an administrative staff member of the hospital.
 * Single Responsibility: this class only holds staff data and
 * knows how to create/display itself.
 */
public class Staff extends Person {

    private String designation;
    private String sex;
    private int salary;

    public Staff(String id, String name, String designation, String sex, int salary) {
        super(id, name);
        this.designation = designation;
        this.sex = sex;
        this.salary = salary;
    }

    /**
     * Factory-style helper that prompts nothing itself; it simply builds
     * a new Staff object from already-validated data. Kept as required
     * by the assignment spec (newStaff()).
     */
    public static Staff newStaff(String id, String name, String designation, String sex, int salary)
            throws InvalidInputException {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new InvalidInputException("Staff ID and name cannot be empty.");
        }
        if (salary < 0) {
            throw new InvalidInputException("Salary cannot be negative.");
        }
        return new Staff(id, name, designation, sex, salary);
    }

    @Override
    public void showInfo() {
        showStaffInfo();
    }

    public void showStaffInfo() {
        System.out.println("--------------------------------------------------");
        System.out.printf("ID           : %s%n", id);
        System.out.printf("Name         : %s%n", name);
        System.out.printf("Designation  : %s%n", designation);
        System.out.printf("Sex          : %s%n", sex);
        System.out.printf("Salary       : RM %d%n", salary);
        System.out.println("--------------------------------------------------");
    }

    // Getters / setters
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }

    @Override
    public String toString() {
        return String.format("%-6s %-20s %-15s %-6s RM%d", id, name, designation, sex, salary);
    }
}
