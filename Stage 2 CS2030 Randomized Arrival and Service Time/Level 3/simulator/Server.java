package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final double SERVICE_TIME = 1;
    private ServerState serverState = ServerState.IDLE;
    private Customer currentWaitingCustomer = null;
    private int serverID;
    private static int serverCount = 0;

    /**
     * Constructor
     */
    public Server(int id) {
        serverCount++;
        this.serverID = serverCount;
    }

    public int getID() {
        return this.serverID;
    }

    public Customer getWaitingCustomer() {
        return this.currentWaitingCustomer;
    }

    public ServerState getServerState() {
        return this.serverState;
    }

    public void setServerState(ServerState serverState) {
        this.serverState = serverState;
    }

    public void addCustomerToQueue(Customer customer) {
        this.currentWaitingCustomer = customer;
    }
}
