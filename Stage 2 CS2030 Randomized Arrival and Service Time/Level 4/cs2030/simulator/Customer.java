package cs2030.simulator;

public class Customer {
    
    private int customerID;
    private double startWaitingTime;
    private static int customerCounter = 0;

    public Customer() {
        customerID = ++customerCounter;
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
