package cs2030.simulator;

import java.util.PriorityQueue;
import java.util.Comparator;

public class SelfServer extends Server{

    /**
     * Constructor for the self server.
     */	
    public SelfServer(int serverID, double nextAvailableTime, PriorityQueue<Customer> waitingCustomers){
        super(serverID, nextAvailableTime, waitingCustomers);
    }

    @Override
        public String toString(){
            return "self-check " + this.getServerID();
        }
}	
