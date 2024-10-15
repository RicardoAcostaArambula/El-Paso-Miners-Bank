public class Person {
    private String name;
    private String address;
    private int ssn;
    /*default*/
    public Person(){

    }
    public Person(String name){
        this.name = name;
    }
    public Person(String name, String address){
        this.name = name;
        this.address = address;
    }
    public Person(String name, String address, int ssn){
        this.name = name;
        this.address = address;
        this.ssn = ssn;
    }
    /*setters*/
    public void set_name(String name){
        this.name = name;
    }
    public void set_address(String address){
        this.address = address;
    }
    public void set_ssn(int ssn){
        this.ssn= ssn;
    }
    /*getters*/
    public String get_name(){
        return this.name;
    }
    public String get_address(){
        return this.address;
    }
    public int get_ssn(){
        return this.ssn;
    }
}