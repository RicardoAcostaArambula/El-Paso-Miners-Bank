public class Employee extends Person {
    // Attributes
    private String employee_id;
    private boolean is_a_manager;

    // Constructors no-argument constructor of the Person class
    public Employee() {
        super(); 
    }

    public Employee(String name, String employee_id, boolean is_a_manager) {
        super(name); 
        this.employee_id = employee_id;
        this.is_a_manager = is_a_manager;
    }

    public Employee(String name, String address, int ssn, String employee_id, boolean is_a_manager) {
        super(name, address, ssn); 
        this.employee_id = employee_id;
        this.is_a_manager = is_a_manager;
    }

    // Setters
    public void set_employee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public void set_is_a_manager(boolean is_a_manager) {
        this.is_a_manager = is_a_manager;
    }

    // Getters
    public String get_employee_name() {
        return get_name(); 
    }

    public String get_employee_id() {
        return this.employee_id;
    }

    public boolean get_is_a_manager() {
        return this.is_a_manager;
    }
}
