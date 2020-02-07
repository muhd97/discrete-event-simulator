package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private static int serverCounter = 0;
    private ServerState serverState = ServerState.IDLE;
    private Customer currentWaitingCustomer = null;
    private int serverID;
    private RandomGenerator randGen;

    public Server(RandomGenerator randGen) {
        this.serverID = ++serverCounter;
        this.randGen = randGen;
    }

    public int getID() {
        return serverID;
    }

    public Customer getWaitingCustomer() {
        return currentWaitingCustomer;
    }

    public ServerState getServerState() {
        return serverState;
    }

    public void setServerState(ServerState serverState) {
        this.serverState = serverState;
    }

    public void addCustomerToQueue(Customer customer) {
        this.currentWaitingCustomer = customer;
    }

    public double getServiceTime() {
        return randGen.genServiceTime();
    }
}
