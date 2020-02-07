package cs2030.simulator;

public class Event{

    private Customer customer;
    private Server server;
    private double time;
    private int state;

    /**
     * Constructor for the Event.
     */	
    public Event(Customer customer, Server server, int state, double time){
        this.customer = customer;
        this.server = server;
        this.state = state;
        this.time = time;
    }

    /**
     * Gets the time.
     * @return the time.
     */
    public double getTime(){
        return this.time;
    }

    /**
     * Gets the customer.
     * @return the customer
     */	
    public Customer getCustomer(){
        return this.customer;
    }

    /**
     * Gets the server.
     * @return the server.
     */
    public Server getServer(){
        return this.server;
    }

    /**
     * Gets the state.
     * @return the state.
     */
    public int getState(){
        return this.state;
    }

    public void print(){
        switch(state){
            case State.ARRIVES: 
                System.out.println(String.format("%.3f", time) + " " + customer + " arrives");
                break;
            case State.SERVES:
                System.out.println(String.format("%.3f", time) + " " + customer + " served by " + server);
                break;
            case State.WAITS:
                System.out.println(String.format("%.3f", time) + " " + customer + " waits to be served by " + server);
                break;
            case State.LEAVES: 
                System.out.println(String.format("%.3f", time) + " " + customer + " leaves");
                break;
            case State.DONE:
                System.out.println(String.format("%.3f", time) + " " + customer + " done serving by " + server);
                break;
            case State.SERVER_REST:
                System.out.println(String.format("%.3f", time) + " " + server + " rest");
                break;
            case State.SERVER_BACK:
                System.out.println(String.format("%.3f", time) + " " + server + " back");
                break;
        }
    }
}
