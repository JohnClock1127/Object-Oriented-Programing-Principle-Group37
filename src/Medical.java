import java.util.Scanner;

public class Medical {
    private String name;
    private String manufacturer;
    private String expiryDate;
    private int cost;
    private int count;

    public Medical() {
    }

    public Medical(String name, String manufacturer, String expiryDate, int cost, int count) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.cost = cost;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public int getCost() {
        return cost;
    }

    public int getCount() {
        return count;
    }

    // Prompts the user to enter the medical's details.
    public void newMedical(Scanner sc) {
        System.out.print("Enter medicine name: ");
        name = sc.nextLine();
        System.out.print("Enter manufacturer: ");
        manufacturer = sc.nextLine();
        System.out.print("Enter expiry date: ");
        expiryDate = sc.nextLine();
        System.out.print("Enter cost: ");
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid whole number for cost: ");
            sc.next();
        }
        cost = sc.nextInt();
        System.out.print("Enter number of units: ");
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid whole number for units: ");
            sc.next();
        }
        count = sc.nextInt();
        sc.nextLine();
    }

    // Shows the content of the medical
    public void findMedical() {
        System.out.println(name + " " + manufacturer + " " + expiryDate + " " + cost);
    }
}