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
    private RandomGenerator randomGen;

    /**
     * Constructor.
     * @param seedValue is the random seedValue
     * @param numServers is the number of servers
     * @param numCustomers is the number of customers
     * @param arrivalRate is the rate of customer arrival
     * @param serviceRate is the rate of server service
     */
    public Simulator(int seedValue, int numServers, int numCustomers,double arrivalRate, double serviceRate) {
        this.numCustomersHaveNotArrived = numCustomers;
        this.randomGen = new RandomGenerator(seedValue, arrivalRate, serviceRate);
        for (int i = 0; i < numServers; i++) {
            servers.add(new Server(i + 1));
        }
        if (numCustomersHaveNotArrived > 0) {
            numCustomersHaveNotArrived--;
            events.add(new ArriveEvent(0, new Customer()));
        }
        startSimulation();
    }

    /**
     * Adds a new customer if there is still a customer to add, otherwise do nothing
     */
    public void addNewCustomer() {
        if (numCustomersHaveNotArrived == 0) {
            return;
        }
        numCustomersHaveNotArrived--;
        double interTime = randomGen.genInterArrivalTime();
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
            event.startEvent(this);
        }
    }

}
