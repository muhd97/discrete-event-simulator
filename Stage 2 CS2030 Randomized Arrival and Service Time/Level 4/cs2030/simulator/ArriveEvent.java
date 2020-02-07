package cs2030.simulator;

import java.util.List;

public class ArriveEvent extends Event {

    public ArriveEvent(double time, Customer customer) {
        super(time, customer);
    }

    @Override
    public void runEvent(Simulator sim) {
        sim.addNewCustomerMaybe();
        double time = getTime();
        Customer customer = getCustomer();
        int customerID = customer.getID();
        Server assignedServer = null;
        Event.printMessage(time, " " + customerID + " arrives");
        customer.setWaitingTime(time);
        List<Server> serverList = sim.getServers();
        for (Server server : serverList) {
            if (server.getServerState() == ServerState.IDLE) {
                assignedServer = server;
                break;
            }
        }
        if (assignedServer == null) {
            // cannot find idle server
            // assign to a server without waiting customers
            for (Server server : serverList) {
                if (server.getWaitingCustomer() == null) {
                    assignedServer = server;
                    server.addCustomerToQueue(customer);
                    Event.printMessage(time,
                            " " + customerID + " waits to be served by " + server.getID());
                    break;
                }
            }
            if (assignedServer == null) {
                Event.printMessage(time, " " + customerID + " leaves");
                sim.customerLeft();
            }
        } else {
            Event.printMessage(time, " " + customerID + " served by " + assignedServer.getID());
            sim.customerServed(time - customer.getWaitingTime());
            assignedServer.setServerState(ServerState.SERVING);
            sim.getEvents().add(
                    new DoneEvent(time + assignedServer.getServiceTime(), 
                        customer, assignedServer)
            );
        }
    }
}
