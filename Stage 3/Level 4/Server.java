package cs2030.simulator;

import java.util.PriorityQueue;
import java.util.Comparator;

public class Server{

    private int serverID;
    private double nextAvailableTime;
    private Comparator<Customer> cComparator = new CustomerComparator();
    private PriorityQueue<Customer> waitingCustomers = new PriorityQueue<Customer>(cComparator);

    /**
     * Constructor for the server.
     */	
    public Server(int serverID, double nextAvailableTime, PriorityQueue<Customer> waitingCustomers){
        this.serverID = serverID; 
        this.nextAvailableTime = nextAvailableTime;
        this.waitingCustomers = waitingCustomers;	
    }

    /**
     * Gets the waiting customers list
     * @return the list of waiting customers.
     */
    public PriorityQueue<Customer> getWaitingCustomersList(){
        return this.waitingCustomers;
    }

    public boolean isWaitListNotFull(int maxQueue){
        //Returns true if waitingCustomers priority queue is empty.
        return (this.waitingCustomers.size() < maxQueue);
    }

    /**
     * Gets the ID of the server
     * @return the servers' ID.
     */
    public int getServerID(){
        return this.serverID;
    }

    public void setNextAvailableTime(double servetime){		
        this.nextAvailableTime = servetime;
    }

    public double getNextAvailableTime(){
        return this.nextAvailableTime;
    }

    public void addNextWaitingCustomer(Customer customer){
        this.waitingCustomers.add(customer);
    }

    /**
     * Gets the next waiting customer in the queue
     * @return next waiting customer.
     */
    public Customer getNextWaitingCustomer(){
        return this.waitingCustomers.poll();
    }

    @Override
        public String toString(){
            return "server " + serverID;
        }
}
