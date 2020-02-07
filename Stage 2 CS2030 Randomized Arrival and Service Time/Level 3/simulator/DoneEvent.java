package cs2030.simulator;

public class DoneEvent extends Event {
    private Server server;

    public DoneEvent(double time, Customer customer, Server server) {
        super(time, customer);
        this.server = server;
    }

    public Server getServer() {
        return this.server;
}
        @Override
            public void startEvent(Simulator s) {
                double time = getTime();
                Customer customer = getCustomer();
                Server server = getServer();
                int customerID = customer.getID();
                int serverID = server.getID();
                System.out.println(String.format("%.3f", time) + " " + customerID + " done serving by " + serverID);
                server.setServerState(ServerState.IDLE);
                if (server.getWaitingCustomer() != null) {
                    customer = server.getWaitingCustomer();
                    server.addCustomerToQueue(null);
                    System.out.println(String.format("%.3f", time) + " " + customer.getID() + " served by " + serverID);
                    server.setServerState(ServerState.SERVING);
                    s.customerServed(time - customer.getWaitingTime());
                    s.getEvents().add(new DoneEvent(time + server.SERVICE_TIME, customer, server));
                }
            }
}
