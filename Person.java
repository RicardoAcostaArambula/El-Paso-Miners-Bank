/**
 * The {@code Person} class represents an individual with a name, address, and Social Security Number (SSN).
 * It provides constructors to create a person object and getter/setter methods to access and modify the attributes.
 * 
 * @author Sebastian Nares, Ricado Acosta
 */
public class Person {
    private String name;
    private String address;
    private int ssn;

    /**
     * Default constructor that initializes a new {@code Person} object without any attributes.
     */
    public Person() {
    }

    /**
     * Constructs a new {@code Person} object with the specified name.
     * 
     * @param name The name of the person.
     */
    public Person(String name) {
        this.name = name;
    }

    /**
     * Constructs a new {@code Person} object with the specified name and address.
     * 
     * @param name    The name of the person.
     * @param address The address of the person.
     */
    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /**
     * Constructs a new {@code Person} object with the specified name, address, and SSN.
     * 
     * @param name    The name of the person.
     * @param address The address of the person.
     * @param ssn     The Social Security Number of the person.
     */
    public Person(String name, String address, int ssn) {
        this.name = name;
        this.address = address;
        this.ssn = ssn;
    }

    /* Setters */

    /**
     * Sets the name of the person.
     * 
     * @param name The new name of the person.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the address of the person.
     * 
     * @param address The new address of the person.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the Social Security Number of the person.
     * 
     * @param ssn The new Social Security Number of the person.
     */
    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    /* Getters */

    /**
     * Gets the name of the person.
     * 
     * @return The name of the person.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the address of the person.
     * 
     * @return The address of the person.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Gets the Social Security Number of the person.
     * 
     * @return The Social Security Number of the person.
     */
    public int getSsn() {
        return this.ssn;
    }
}
