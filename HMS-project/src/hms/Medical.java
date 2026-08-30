package hms;

/**
 * Represents a medicine stocked by the hospital pharmacy.
 * Does not extend Person since medicines have no id/name-as-identity
 * semantics in the spec (attributes are name, manufacturer, expiryDate,
 * cost, count) -- kept as its own root class for single responsibility.
 */
public class Medical {

    private String name;
    private String manufacturer;
    private String expiryDate;
    private int cost;
    private int count;

    public Medical(String name, String manufacturer, String expiryDate, int cost, int count) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.cost = cost;
        this.count = count;
    }

    public static Medical newMedical(String name, String manufacturer, String expiryDate,
                                      int cost, int count) throws InvalidInputException {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("Medicine name cannot be empty.");
        }
        if (cost < 0 || count < 0) {
            throw new InvalidInputException("Cost and count cannot be negative.");
        }
        return new Medical(name, manufacturer, expiryDate, cost, count);
    }

    /**
     * Searches a list of Medical items by name (case-insensitive,
     * partial match) and prints matches. Returns the number found.
     */
    public static int findMedical(java.util.List<Medical> medicals, String keyword) {
        int found = 0;
        System.out.println("--------------------------------------------------");
        for (Medical m : medicals) {
            if (m.name.toLowerCase().contains(keyword.toLowerCase())) {
                m.showMedicalInfo();
                found++;
            }
        }
        if (found == 0) {
            System.out.println("No medicine found matching: " + keyword);
        }
        System.out.println("--------------------------------------------------");
        return found;
    }

    public void showMedicalInfo() {
        System.out.printf("Name: %-15s Manufacturer: %-15s Expiry: %-10s Cost: RM%-5d Stock: %d%n",
                name, manufacturer, expiryDate, cost, count);
    }

    // Getters / setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    @Override
    public String toString() {
        return String.format("%-15s %-15s %-10s RM%-5d Stock:%d",
                name, manufacturer, expiryDate, cost, count);
    }
}
