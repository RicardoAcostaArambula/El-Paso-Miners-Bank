/**
 * The {@code Employee} class extends the {@code Person} class and represents an employee in the system.
 * It contains attributes related to the employee's identification and managerial status.
 * The class provides methods to set and retrieve employee details.
 * 
 * @author Sebastian Nares, Ricardo Acosta
 */
public class Employee extends Person {
    // Attributes
    private String employeeId;
    private boolean isAManager;

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
     * @param employeeId  The ID of the employee.
     * @param isAManager {@code true} if the employee is a manager, {@code false} otherwise.
     */
    public Employee(String name, String employeeId, boolean isAManager) {
        super(name); 
        this.employeeId = employeeId;
        this.isAManager = isAManager;
    }

    /**
     * Constructs an {@code Employee} object with the specified name, address, social security number, 
     * employee ID, and managerial status.
     * 
     * @param name         The name of the employee.
     * @param address      The address of the employee.
     * @param ssn          The social security number of the employee.
     * @param employeeId  The ID of the employee.
     * @param isAManager {@code true} if the employee is a manager, {@code false} otherwise.
     */
    public Employee(String name, String address, int ssn, String employeeId, boolean isAManager) {
        super(name, address, ssn); 
        this.employeeId = employeeId;
        this.isAManager = isAManager;
    }

    /**
     * Sets the employee ID.
     * 
     * @param employeeId The new employee ID to be set.
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Sets whether the employee is a manager.
     * 
     * @param isAManager {@code true} if the employee is a manager, {@code false} otherwise.
     */
    public void setIsAManager(boolean isAManager) {
        this.isAManager = isAManager;
    }

    /**
     * Gets the name of the employee.
     * 
     * @return The name of the employee.
     */
    public String getEmployeeName() {
        return getName(); 
    }

    /**
     * Gets the employee ID.
     * 
     * @return The employee ID.
     */
    public String getEmployeeId() {
        return this.employeeId;
    }

    /**
     * Checks if the employee is a manager.
     * 
     * @return {@code true} if the employee is a manager, {@code false} otherwise.
     */
    public boolean getIsAManager() {
        return this.isAManager;
    }
}
