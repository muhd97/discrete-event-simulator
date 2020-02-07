package cs2030.simulator;

public class Customer {
    
    private int customerID;
    private double startWaitingTime;
    
    /**
     * Constructor for the Customer.
     * @param customerID is the ID of the customer
     */
    public Customer(int customerID) {
        this.customerID = customerID;
    }

    public int getID() {
        return customerID;
    }

    public double getWaitingTime() {
        return startWaitingTime;
    }

    public void setWaitingTime(double time) {
        this.startWaitingTime = time;
    }
}
