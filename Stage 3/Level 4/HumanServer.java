package cs2030.simulator;

import java.util.PriorityQueue;
import java.util.Comparator;

public class HumanServer extends Server{

    /**
     * Constructor for the human server.
     */	
    public HumanServer(int serverID, double nextAvailableTime, PriorityQueue<Customer> waitingCustomers){
        super(serverID, nextAvailableTime, waitingCustomers);	
    }	

    @Override
        public String toString(){
            return "server " + this.getServerID();
        }
}	
