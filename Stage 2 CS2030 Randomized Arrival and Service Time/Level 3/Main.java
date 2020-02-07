import cs2030.simulator.Simulator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int seedValue = sc.nextInt();
        int numServers = sc.nextInt();
        int numCustomers = sc.nextInt();
        double arrivalRate = sc.nextDouble();
        double serviceRate = sc.nextDouble();
        Simulator s = new Simulator(seedValue, numServers, numCustomers, arrivalRate, serviceRate);
        System.out.println("[" + String.format("%.3f", s.getAverageWaitingTime()) + " " + s.getNumServed() + " " + s.getNumLeft() + "]");
    }
}
