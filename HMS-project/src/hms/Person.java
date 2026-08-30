package hms;

/**
 * Abstract base class capturing attributes common to every person-like
 * entity in the hospital (Staff, Doctor, Patient). Pulling id/name up
 * here follows the Open/Closed Principle: new person-like classes can
 * be added later by extending Person without modifying this class.
 */
public abstract class Person {

    protected String id;
    protected String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Every subclass must define how it prints its own info.
     * Kept abstract so each class has a single responsibility:
     * knowing how to describe itself.
     */
    public abstract void showInfo();
}
