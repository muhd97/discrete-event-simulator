package cs2030.simulator;

public class GreedyCustomer extends Customer{

    /**
     * Constructor for the greedy customer.
     */		
    public GreedyCustomer(int customerID, double time){
        super(customerID, time);
    }

    @Override
        public String toString(){
            return this.getCustomerID() + "(greedy)";
        }
}	
