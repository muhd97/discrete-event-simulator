import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;
import cs2030.simulator.Customer;
import cs2030.simulator.GreedyCustomer;
import cs2030.simulator.NormalCustomer;
import cs2030.simulator.CustomerComparator;
import cs2030.simulator.Server;
import cs2030.simulator.HumanServer;
import cs2030.simulator.SelfServer;
import cs2030.simulator.Event;
import cs2030.simulator.Simulator;
import cs2030.simulator.EventComparator;

public class Main{

    public static void main(String[] args){					

        Scanner sc = new Scanner(System.in);

        //Reads the seed value for pseudo random number generation(RandomGenerator).
        int seedValue = sc.nextInt(); 

        //Reads the number of servers.
        int numServers = sc.nextInt();

        //Reads the number of self-check servers.
        int numSelfServers = sc.nextInt();

        //Reads the maximum queue length for each server.
        int maxQueue = sc.nextInt();

        //Reads the number of customers.
        int numCustomers = sc.nextInt();

        //Reads the arrival rate.
        double arrivalRate = sc.nextDouble(); 

        //Reads the service rate.
        double serviceRate = sc.nextDouble(); 

        //Reads the resting rate.
        double restRate = sc.nextDouble();

        //Reads the probability of resting.
        double restProbability = sc.nextDouble();

        //Reads the probability of greedy customer.
        double greedProbability = sc.nextDouble();

        Simulator s = new Simulator(seedValue, numServers, numSelfServers, maxQueue, numCustomers, arrivalRate, serviceRate, restRate, restProbability, greedProbability);
        s.startSimulation();
        s.getStats();	
    }
}
