import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;
import cs2030.simulator.Customer;
import cs2030.simulator.CustomerComparator;
import cs2030.simulator.Server;
import cs2030.simulator.Event;
import cs2030.simulator.Simulator;
import cs2030.simulator.EventComparator;
import cs2030.simulator.State;

public class Main{

    public static void main(String[] args){					

        Scanner sc = new Scanner(System.in);

        //Read the seed value for pseudo random number generation(RandomGenerator)
        int seedValue = sc.nextInt(); 

        //Read the number of servers
        int numServers = sc.nextInt();

        //Read the maximum queue length for each server;
        int maxQueue = sc.nextInt();

        //Read the number of customers
        int numCustomers = sc.nextInt();

        //Read the arrival rate
        double arrivalRate = sc.nextDouble(); 

        //Read the service rate
        double serviceRate = sc.nextDouble(); 
       
        Simulator s = new Simulator(seedValue, numServers, maxQueue, numCustomers, arrivalRate, serviceRate);
        s.startSimulation();
        s.getStats();	
    }
}
