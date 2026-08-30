package hms;

import java.util.List;

/**
 * Represents a hospital facility (e.g. ICU, X-Ray Room, Ambulance Bay).
 */
public class Facility {

    private String facility;

    public Facility(String facility) {
        this.facility = facility;
    }

    public static Facility newFacility(String facility) throws InvalidInputException {
        if (facility == null || facility.isBlank()) {
            throw new InvalidInputException("Facility name cannot be empty.");
        }
        return new Facility(facility);
    }

    /**
     * Prints every facility in the given list.
     */
    public static void showFacility(List<Facility> facilities) {
        System.out.println("--------------------------------------------------");
        if (facilities.isEmpty()) {
            System.out.println("No facilities registered.");
        } else {
            int i = 1;
            for (Facility f : facilities) {
                System.out.printf("%2d. %s%n", i++, f.facility);
            }
        }
        System.out.println("--------------------------------------------------");
    }

    public String getFacility() { return facility; }
    public void setFacility(String facility) { this.facility = facility; }

    @Override
    public String toString() {
        return facility;
    }
}
