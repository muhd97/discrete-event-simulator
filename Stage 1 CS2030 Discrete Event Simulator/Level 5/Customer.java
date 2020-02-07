public class Customer {
    
    private int customerID;
    public CustomerState state = CustomerState.BEFOREARRIVAL;
    public double timeState;
    private double waitingTime = -1;
    private CustomerState nextState;
   
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
    public void arrive(double currentTime) {
        if (state == CustomerState.BEFOREARRIVAL) {
            state = CustomerState.ARRIVED;
            System.out.println(String.format("%.3f", currentTime) + " " + customerID + " arrives");
            return;
        }
    }

    /**
     * Customer is being served.
     * @param servingEndTime is the time when customer is done being served
     * @param currentTime is the current time
     */
    public double serve(double servingEndTime, double currentTime) {
        state = CustomerState.SERVED;
        System.out.println(String.format("%.3f", currentTime) + " " + customerID + " served");
        timeState = servingEndTime;
        return currentTime - waitingTime;
    }

    /**
     * Customer will leave.
     * @param currentTime is the current time
     */
    public void leave(double currentTime) {  
        state = CustomerState.LEFT;        // ARRIVED -> LEFT 
        System.out.println(String.format("%.3f", currentTime) + " " + customerID + " leaves");
        timeState = -1;
    }

    /**
     * Customer is done being served.
     * @param currentTime is the current time
     */
    public void done(double currentTime) {
        state = CustomerState.DONE;
        System.out.println(String.format("%.3f", currentTime) + " " + customerID + " done");
        timeState = -1;
    }

    /**
     * Customer is waiting.
     * @param currentTime is the current time
     */
    public void wait(double currentTime) {
        state = CustomerState.WAITING;
        System.out.println(String.format("%.3f", currentTime) + " " + customerID + " waits");
        timeState = -1;
        waitingTime = currentTime;
    }

}
