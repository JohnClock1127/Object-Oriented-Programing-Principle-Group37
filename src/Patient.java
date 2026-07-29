import java.util.Scanner;

public class Patient {
    private String id;
    private String name;
    private String disease;
    private String sex;
    private String admitStatus;
    private int age;

    public Patient() {
    }

    public Patient(String id, String name, String disease, String sex, String admitStatus, int age) {
        this.id = id;
        this.name = name;
        this.disease = disease;
        this.sex = sex;
        this.admitStatus = admitStatus;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisease() {
        return disease;
    }

    public String getSex() {
        return sex;
    }

    public String getAdmitStatus() {
        return admitStatus;
    }

    public int getAge() {
        return age;
    }

    // Prompts the user to enter the patient's details.
    // A Scanner is passed in rather than created here, so the caller
    // owns and closes the single shared Scanner for the whole program.
    public void newPatient(Scanner sc) {
        System.out.print("Enter patient ID: ");
        id = sc.nextLine();
        System.out.print("Enter patient name: ");
        name = sc.nextLine();
        System.out.print("Enter disease: ");
        disease = sc.nextLine();
        System.out.print("Enter sex: ");
        sex = sc.nextLine();
        System.out.print("Enter admit status: ");
        admitStatus = sc.nextLine();
        System.out.print("Enter age: ");
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid whole number for age: ");
            sc.next();
        }
        age = sc.nextInt();
        sc.nextLine();
    }

    // Shows the content of the patient
    public void showPatientInfo() {
        System.out.println(id + " " + name + " " + disease + " " + sex + " " + admitStatus);
    }
}