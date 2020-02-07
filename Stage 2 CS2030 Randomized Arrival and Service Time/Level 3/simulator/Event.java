package cs2030.simulator;


public abstract class Event implements Comparable<Event> {
    private double time;
    private Customer customer;

    public Event(double time, Customer customer) {
        this.time = time;
        this.customer = customer;
    }

    public double getTime() {
        return time;
    }

    public Customer getCustomer() {
        return customer;
    }
    public abstract void startEvent(Simulator s);

    @Override
        public int compareTo(Event event) {
            if (time < event.time) {
                return -1;
            }
            if (time > event.time) {
                return 1;
            }
            return customer.getID() - event.customer.getID(); 
        }

}
