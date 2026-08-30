package hms;

import java.util.List;

/**
 * Represents a laboratory / lab test service offered by the hospital.
 */
public class Lab {

    private String lab;
    private int cost;

    public Lab(String lab, int cost) {
        this.lab = lab;
        this.cost = cost;
    }

    public static Lab newLab(String lab, int cost) throws InvalidInputException {
        if (lab == null || lab.isBlank()) {
            throw new InvalidInputException("Lab name cannot be empty.");
        }
        if (cost < 0) {
            throw new InvalidInputException("Lab cost cannot be negative.");
        }
        return new Lab(lab, cost);
    }

    /**
     * Prints every lab currently stored in the given list.
     */
    public static void labList(List<Lab> labs) {
        System.out.println("--------------------------------------------------");
        if (labs.isEmpty()) {
            System.out.println("No labs registered.");
        } else {
            int i = 1;
            for (Lab l : labs) {
                System.out.printf("%2d. %-25s RM%d%n", i++, l.lab, l.cost);
            }
        }
        System.out.println("--------------------------------------------------");
    }

    public String getLab() { return lab; }
    public void setLab(String lab) { this.lab = lab; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    @Override
    public String toString() {
        return String.format("%-25s RM%d", lab, cost);
    }
}
