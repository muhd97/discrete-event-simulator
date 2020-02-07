package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Simulator {
    private List<Server> servers = new ArrayList<>();
    private PriorityQueue<Event> events = new PriorityQueue<>();
    private int numOfCustomers = 0;
    private double currentTime = 0;
    private double totalWaitingTime = 0;
    private int numCustomersServed = 0;
    private int numCustomersWhoLeft = 0;
    private int numCustomersHaveNotArrived;
    private RandomGenerator randGen;

    /**
     * Constructor.
     * @param seed The random seed
     * @param numServers The number of servers
     * @param numCustomers The number of customers
     * @param arrivalRate The rate of customer arrical
     * @param serviceRate The rate of service for servers
     */
    public Simulator(int seed, int numServers, int numCustomers,
        double arrivalRate, double serviceRate) {
        this.numCustomersHaveNotArrived = numCustomers;
        this.randGen = new RandomGenerator(seed, arrivalRate, serviceRate);
        for (int i = 0; i < numServers; i++) {
            servers.add(new Server(randGen));
        }
        if (numCustomersHaveNotArrived > 0) {
            numCustomersHaveNotArrived--;
            events.add(new ArriveEvent(0, new Customer()));
        }
        startSimulation();
    }

    /**
     * Adds a new customer if there are still customers to add, does nothing
     * otherwise.
     */
    public void addNewCustomerMaybe() {
        if (numCustomersHaveNotArrived == 0) {
            return;
        }
        numCustomersHaveNotArrived--;
        double interTime = randGen.genInterArrivalTime();
        Customer customer = new Customer();
        events.add(new ArriveEvent(currentTime + interTime, customer));
    }

    public List<Server> getServers() {
        return servers;
    }

    public PriorityQueue<Event> getEvents() {
        return events;
    }

    public void customerLeft() {
        numCustomersWhoLeft++;
    }

    public void customerServed(double time) {
        numCustomersServed++;
        totalWaitingTime += time;
    }

    /**
     * Gets average waiting time for customers served.
     * @return the average waiting time
     */
    public double getAverageWaitingTime() {
        return totalWaitingTime / numCustomersServed;
    }

    /**
     * Gets number of customers served.
     * @return Number of customers served
     */
    public int getNumServed() {
        return numCustomersServed;
    }

    /**
     * Gets the number of customers who left.
     * @return Number of customers who left
     */
    public int getNumLeft() {
        return numCustomersWhoLeft;
    }

    private Event getEarliestEvent() {
        if (events.size() == 0) {
            return null;
        }
        currentTime = events.peek().getTime();
        return events.poll();
    }

    private void startSimulation() {
        Event event;
        while ((event  = getEarliestEvent()) != null) { 
            event.runEvent(this);
        }
    }

}
