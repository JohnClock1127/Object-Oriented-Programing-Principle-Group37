import java.util.Scanner;

public class Facility {
    private String facility;

    public Facility() {
    }

    public Facility(String facility) {
        this.facility = facility;
    }

    public String getFacility() {
        return facility;
    }

    // Prompts the user to enter the facility's details.
    public void newFacility(Scanner sc) {
        System.out.print("Enter facility: ");
        facility = sc.nextLine();
    }

    // Shows the content of the facility
    public void showFacility() {
        System.out.println(facility);
    }
}