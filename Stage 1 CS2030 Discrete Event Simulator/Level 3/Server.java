import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final double SERVICE_TIME = 1;
    private int numOfCustomers = 0;
    private List<Customer> listOfCustomers = new ArrayList<>();
    private double currentTime = 0;
    private ServerState serverState = ServerState.IDLE;

    /**
     * Constructor
     * @param arrivalTimings is the list of customer arrival timings
     */
    public Server(List<Double> arrivalTimings) {
        for (Double timing : arrivalTimings) {
            numOfCustomers++;
            listOfCustomers.add(new Customer(numOfCustomers, (double)timing));
        }
    }

    /**
     * Gets the next customer with the next earliest event time
     * @return the earliest customer or null if there is no customer
     */
    public Customer getNextEvent() {
        Customer nextCustomer = null;
        for (Customer customer : listOfCustomers) {
            if (customer.timeState < currentTime) {
                continue;
            }
            if (nextCustomer == null || nextCustomer.timeState > customer.timeState) {
                nextCustomer = customer;
            }
        }
        return nextCustomer;
    }
    /**
     * Begins the simulator.
     *
     */
    public void startServing() {
        Customer customer;
        while ((customer = getNextEvent()) != null) { 
            if (customer.timeState > currentTime) {
                currentTime = customer.timeState;
            }
            CustomerState state = customer.state;
            if (state == CustomerState.BEFOREARRIVAL) {
                customer.arrive(currentTime);
            } else if (state == CustomerState.ARRIVED) {
                if (serverState == ServerState.IDLE) {
                    customer.serve(currentTime + SERVICE_TIME, currentTime);
                    serverState = ServerState.SERVING;
                } else {
                    customer.leave(currentTime);
                }
            } else if (state == CustomerState.SERVED) {
                customer.done(currentTime);
                serverState = ServerState.IDLE;
                }
        }
    }

    /**
     * Gets the total number of customers.
     * @return Number of customers
     */
    public int getNumOfCustomers() {
        return this.numOfCustomers;
    }

}
