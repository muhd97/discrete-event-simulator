package cs2030.simulator;

public class State{

    //The different states of a customer/server in an event.
    public static final int ARRIVES = 1;
    public static final int SERVES = 2;
    public static final int	WAITS = 3;
    public static final int LEAVES = 4;
    public static final int DONE = 5;
    public static final int SERVER_REST = 6;
    public static final int SERVER_BACK = 7;
}
