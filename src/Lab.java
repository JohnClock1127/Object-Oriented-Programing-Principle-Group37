import java.util.Scanner;

public class Lab {
    private String lab;
    private int cost;

    public Lab() {
    }

    public Lab(String lab, int cost) {
        this.lab = lab;
        this.cost = cost;
    }

    public String getLab() {
        return lab;
    }

    public int getCost() {
        return cost;
    }

    // Prompts the user to enter the lab's details.
    // A Scanner is passed in rather than created here, so the caller
    // owns and closes the single shared Scanner for the whole program.
    public void newLab(Scanner sc) {
        System.out.print("Enter lab facility: ");
        lab = sc.nextLine();
        System.out.print("Enter cost: ");
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid whole number for cost: ");
            sc.next();
        }
        cost = sc.nextInt();
        sc.nextLine();
    }

    // Shows the content of the lab
    public void labList() {
        System.out.println(lab + " " + cost);
    }
}