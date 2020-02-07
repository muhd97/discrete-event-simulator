public class Customer {

    private int customerID;
    public double timeState;
    public CustomerState state = CustomerState.BEFOREARRIVAL;
    

    /**
     * Constructor for the Customer.
     * @param customerID is the ID of that customer
     * @param arrivalTime is the arrivial time of that customer
     */
    public Customer(int customerID, double arrivalTime) {
        this.customerID = customerID;
        this.timeState = arrivalTime;
    }

    /**
     * Customer arrives.
     */
    public void arrive(double arrivalTime) {
        state = CustomerState.ARRIVED;
        System.out.println(customerID + " arrives at " + String.format("%.3f", arrivalTime));
    }

    /**
     * Customer is being served.
     * @param servingEndTime is the time when the customer is done being served
     * @param currentTime is the current time
     */
    public void serve(double servingEndingTime, double currentTime) {
        state = CustomerState.SERVED;
        System.out.println("Customer served; next service @ " + String.format("%.3f", currentTime + 1));
        timeState = servingEndingTime;
    }

    /**
     * Customer will leave.
     * @param currentTime is the current time
     */
    public void leave(double currentTime) {
        state = CustomerState.LEFT;
        System.out.println("Customer leaves");
        timeState = -1;
    }

    /**
     * Customer is done being served.
     * @param currentTime is the current time
     */
    public void done(double currentTime) {
        state = CustomerState.DONE;
        timeState = -1;
    }


}
