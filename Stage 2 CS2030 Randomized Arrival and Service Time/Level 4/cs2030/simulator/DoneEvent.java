package cs2030.simulator;

public class DoneEvent extends Event {
    private Server server;

    public DoneEvent(double time, Customer customer, Server server) {
        super(time, customer);
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    @Override
    public void runEvent(Simulator sim) {
        double time = getTime();
        Customer customer = getCustomer();
        Server server = getServer();
        int customerID = customer.getID();
        int serverID = server.getID();
        Event.printMessage(time, " " + customerID + " done serving by " + serverID);
        server.setServerState(ServerState.IDLE);
        if (server.getWaitingCustomer() != null) {
            customer = server.getWaitingCustomer();
            server.addCustomerToQueue(null);
            Event.printMessage(time, " " + customer.getID() + " served by " + serverID);
            server.setServerState(ServerState.SERVING);
            sim.customerServed(time - customer.getWaitingTime());
            sim.getEvents().add(new DoneEvent(time + server.getServiceTime(), customer, server));
        }
    }
}
