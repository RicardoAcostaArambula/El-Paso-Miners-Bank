public class Account {
    /*attributes*/
    private String routing_number;
    /*constructor*/
    public Account(){
        
    }
    public Account(String routing_number){
        this.routing_number = routing_number;
    }
    /*setters*/
    public void set_routing_number(String routing_number){
        this.routing_number = routing_number;
    }
    /*getters*/
    public String get_routing_number(){
        return this.routing_number;
    }

}