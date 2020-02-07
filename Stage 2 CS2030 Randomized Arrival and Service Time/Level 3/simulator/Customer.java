package cs2030.simulator;

public class Customer {
    
    private int customerID;
    private double startWaitingTime;
    private static int customerCount = 0;
    
    /**
     * Constructor for the Customer.
     */
    public Customer() {
        customerCount++;
        this.customerID = customerCount;
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
