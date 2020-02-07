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

    public abstract void runEvent(Simulator sim);

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

    public static void printMessage(double time, String str) {
        System.out.println(String.format("%.3f", time) + str);
    }
}
