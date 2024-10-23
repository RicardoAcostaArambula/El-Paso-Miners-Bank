/**
 * The {@code Employee} class extends the {@code Person} class and represents an employee in the system.
 * It contains attributes related to the employee's identification and managerial status.
 * The class provides methods to set and retrieve employee details.
 * 
 * @author Sebastian Nares, Ricardo Acosta
 */
public class Employee extends Person {
    // Attributes
    private String employee_id;
    private boolean is_a_manager;

    /**
     * Default constructor for {@code Employee}.
     * Calls the no-argument constructor of the {@code Person} class.
     */
    public Employee() {
        super(); 
    }

    /**
     * Constructs an {@code Employee} object with the specified name, employee ID, and managerial status.
     * 
     * @param name         The name of the employee.
     * @param employee_id  The ID of the employee.
     * @param is_a_manager {@code true} if the employee is a manager, {@code false} otherwise.
     */
    public Employee(String name, String employee_id, boolean is_a_manager) {
        super(name); 
        this.employee_id = employee_id;
        this.is_a_manager = is_a_manager;
    }

    /**
     * Constructs an {@code Employee} object with the specified name, address, social security number, 
     * employee ID, and managerial status.
     * 
     * @param name         The name of the employee.
     * @param address      The address of the employee.
     * @param ssn          The social security number of the employee.
     * @param employee_id  The ID of the employee.
     * @param is_a_manager {@code true} if the employee is a manager, {@code false} otherwise.
     */
    public Employee(String name, String address, int ssn, String employee_id, boolean is_a_manager) {
        super(name, address, ssn); 
        this.employee_id = employee_id;
        this.is_a_manager = is_a_manager;
    }

    /**
     * Sets the employee ID.
     * 
     * @param employee_id The new employee ID to be set.
     */
    public void set_employee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    /**
     * Sets whether the employee is a manager.
     * 
     * @param is_a_manager {@code true} if the employee is a manager, {@code false} otherwise.
     */
    public void set_is_a_manager(boolean is_a_manager) {
        this.is_a_manager = is_a_manager;
    }

    /**
     * Gets the name of the employee.
     * 
     * @return The name of the employee.
     */
    public String get_employee_name() {
        return get_name(); 
    }

    /**
     * Gets the employee ID.
     * 
     * @return The employee ID.
     */
    public String get_employee_id() {
        return this.employee_id;
    }

    /**
     * Checks if the employee is a manager.
     * 
     * @return {@code true} if the employee is a manager, {@code false} otherwise.
     */
    public boolean get_is_a_manager() {
        return this.is_a_manager;
    }
}
