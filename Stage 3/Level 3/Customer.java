package cs2030.simulator;

public class Customer{

    private int customerID;
    private double time;

    /**
     * Constructor for the Customer.
     */	
    public Customer(int customerID, double time){
        this.customerID = customerID;
        this.time = time;
    }

    /**
     * Gets the ID of the customer
     * @return ID of the customer
     */
    public int getCustomerID(){
        return this.customerID;
    }

    public double getTime(){
        return this.time;
    }

    @Override
        public String toString(){
            return String.format("%d", this.customerID);
        }

}
