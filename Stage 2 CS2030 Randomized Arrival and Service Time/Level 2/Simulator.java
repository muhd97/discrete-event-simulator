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

    /**
     * Constructor.
     * @param numServers the number of servers
     * @param arrivalTimes the list of arrival times for all the customers
     */
    public Simulator(int numServers, List<Double> arrivalTimes) {
        for (int i = 0; i < numServers; i++) {
            servers.add(new Server(i + 1));
        }
        for (double time : arrivalTimes) {
            numOfCustomers++;
            events.add(new ArriveEvent(time, new Customer(numOfCustomers)));
        }
        startSimulation();
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
public List<Server> getServers() {
        return servers;
    }

    public PriorityQueue<Event> getEvents() {
        return events;
    }
    private Event getEarliestEvent() {
        if (events.size() == 0) {
            return null;
        }
        return events.poll();
    }


    private void startSimulation() {
        Event event;
        while ((event  = getEarliestEvent()) != null) { 
            event.startEvent(this);
        }
    }

}
