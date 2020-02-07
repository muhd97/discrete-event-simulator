package cs2030.simulator;

import java.util.List;



public class ArriveEvent extends Event {
    public ArriveEvent(double time, Customer customer) {
        super(time, customer);
    }

    @Override
        public void startEvent(Simulator s) {
            double time = getTime();
            Customer customer = getCustomer();
            int customerID = customer.getID();
            Server assignedServer = null;
            System.out.println(String.format("%.3f", time) + " " + customerID + " arrives");
            customer.setWaitingTime(time);
            List<Server> serverList = s.getServers();
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
                        System.out.println(String.format("%.3f", time) + " " + customerID + " waits to be served by " + server.getID());
                        break;
                    }
                }
                if (assignedServer == null) {
                    System.out.println(String.format("%.3f", time) + " " + customerID + " leaves");
                    s.customerLeft();
                }
            } else  {
                System.out.println(String.format("%.3f", time) + " " + customerID + " served by " + assignedServer.getID());
                s.customerServed(time - customer.getWaitingTime());
                assignedServer.setServerState(ServerState.SERVING);
                s.getEvents().add(
                        new DoneEvent(time + Server.SERVICE_TIME, 
                            customer, assignedServer)
                        );
            }
        }
}
