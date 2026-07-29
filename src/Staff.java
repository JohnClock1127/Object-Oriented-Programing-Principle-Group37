import java.util.Scanner;

public class Staff {
    private String id;
    private String name;
    private String designation;
    private String sex;
    private int salary;

    public Staff() {
    }

    public Staff(String id, String name, String designation, String sex, int salary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.sex = sex;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getSex() {
        return sex;
    }

    public int getSalary() {
        return salary;
    }

    // Prompts the user to enter the staff's details.
    public void newStaff(Scanner sc) {
        System.out.print("Enter staff ID: ");
        id = sc.nextLine();
        System.out.print("Enter staff name: ");
        name = sc.nextLine();
        System.out.print("Enter staff designation: ");
        designation = sc.nextLine();
        System.out.print("Enter staff sex: ");
        sex = sc.nextLine();
        System.out.print("Enter staff salary: ");
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid whole number for salary: ");
            sc.next();
        }
        salary = sc.nextInt();
        sc.nextLine();
    }

    // Shows the content of the staff
    public void showStaffInfo() {
        System.out.println(id + " " + name + " " + designation + " " + sex + " " + salary);
    }
}