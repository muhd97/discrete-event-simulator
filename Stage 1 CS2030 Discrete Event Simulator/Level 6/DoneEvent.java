public class DoneEvent extends Event {
    private Server server;

    public DoneEvent(double time, Customer customer, Server server) {
        super(time, customer);
        this.server = server;
    }

    public Server getServer() {
        return this.server;
    }
}
