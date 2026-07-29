import java.util.Scanner;

public class Doctor {
    private String id;
    private String name;
    private String specialist;
    private String workTime;
    private String qualification;
    private int room;

    public Doctor() {
    }

    public Doctor(String id, String name, String specialist, String workTime, String qualification, int room) {
        this.id = id;
        this.name = name;
        this.specialist = specialist;
        this.workTime = workTime;
        this.qualification = qualification;
        this.room = room;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialist() {
        return specialist;
    }

    public String getWorkTime() {
        return workTime;
    }

    public String getQualification() {
        return qualification;
    }

    public int getRoom() {
        return room;
    }

    // Prompts the user to enter the doctor's details.
    public void newDoctor(Scanner sc) {
        System.out.print("Enter doctor ID: ");
        id = sc.nextLine();
        System.out.print("Enter doctor name: ");
        name = sc.nextLine();
        System.out.print("Enter specialization: ");
        specialist = sc.nextLine();
        System.out.print("Enter work time: ");
        workTime = sc.nextLine();
        System.out.print("Enter qualification: ");
        qualification = sc.nextLine();
        System.out.print("Enter room number: ");
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid whole number for room: ");
            sc.next();
        }
        room = sc.nextInt();
        sc.nextLine();
    }

    // Shows the content of the doctor
    public void showDoctorInfo() {
        System.out.println(id + " " + name + " " + specialist + " " + workTime + " " + qualification);
    }
}