package cs2030.simulator;

import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Simulator{

    //Simulator attributes.
    private int seedValue;
    private int numServers;
    private int numSelfServers;
    private int maxQueue;
    private int numCustomers;
    private double arrivalRate;
    private double serviceRate;
    private double restRate;
    private double restProbability;
    private double greedProbability;

    //Comparators for Customer PriorityQueue(waitingCustomers) and Event PriorityQueue(queue).
    private Comparator<Customer> cComparator = new CustomerComparator();
    private Comparator<Event> eComparator = new EventComparator();

    //PriorityQueue for Events(queue).
    private PriorityQueue<Event> queue = new PriorityQueue<Event>(eComparator);

    //Variables for getStats().
    private int numCustomersLeaves = 0;
    private int numCustomersServed = 0; 
    private double totalWaitingTime = 0.0;


    /**
     * Constructor.
     * @param seedValue is the seed value for pseudo random number generation(RandomGenerator)
     * @param numServers is the number of servers
     * @param numSelfServers is the number of self-check servers
     * @param maxQueue is the maximum queue length for each server
     * @param numCustomers is the number of customers
     * @param arrivalRate is the arrival rate 
     * @param serviceRate is the service rate 
     * @param restRate is the resting rate
     * @param restProbability is the probability of resting
     * @param greedProbability is the probability of a greedy customer occurring.
     */	
    public Simulator(int seedValue, int numServers, int numSelfServers, int maxQueue, int numCustomers, double arrivalRate, double serviceRate, double restRate, double restProbability, double greedProbability){
        this.seedValue = seedValue;
        this.numServers = numServers;
        this.numSelfServers = numSelfServers;
        this.maxQueue = maxQueue;
        this.numCustomers = numCustomers;
        this.arrivalRate = arrivalRate;
        this.serviceRate = serviceRate;
        this.restRate = restRate;
        this.restProbability = restProbability;
        this.greedProbability = greedProbability;
    }

    /**
     * Adds a newly generated event into the PriorityQueue(queue).
     */
    public void addEvent(Event newEvent){
        queue.add(newEvent);
    }

    /**
     * Starts the Discrete Event Simulator Plus.
     */
    public void startSimulation(){
        RandomGenerator randomGen = new RandomGenerator(seedValue, arrivalRate, serviceRate, restRate);

        //Add in all the customers arriving(their ID and randomly generated time to the customerList[]).
        Customer[] customerList = new Customer[numCustomers];
        if(randomGen.genCustomerType() >= greedProbability){	
            customerList[0] = new NormalCustomer(1, 0.0);	
        }		
        else{
            customerList[0] = new GreedyCustomer(1, 0.0);
        }
        double customerTimeLeftOff = 0.0;
        for(int i = 1; i < numCustomers; i++){
            if(i == 1){
                customerTimeLeftOff = 0.0;
            }
            double newCustomerArrivalTime = customerTimeLeftOff + randomGen.genInterArrivalTime();

            //Create a NormalCustomer or GreedyCustomer then add it into the custmomerList's ArrayList.
            if(randomGen.genCustomerType() >= greedProbability){
                customerList[i] = new NormalCustomer(i + 1, newCustomerArrivalTime);
            }
            else{
                customerList[i] = new GreedyCustomer(i + 1, newCustomerArrivalTime);
            }
            customerTimeLeftOff = newCustomerArrivalTime;
        }	

        //Initialise the servers.
        Server[] serverList = new Server[numServers + numSelfServers];
        for(int i = 0; i < numServers; i++){
            serverList[i] = new HumanServer(i + 1, 0, new PriorityQueue<Customer>(cComparator));
        }
        for(int i = numServers; i < serverList.length; i++){
            serverList[i] = new SelfServer(i + 1, 0, new PriorityQueue<Customer>(cComparator));
        }

        //Start the queue by adding the customer arriving into the Priority Queue(queue).
        Server initialiseServer = new Server(0, 0, new PriorityQueue<Customer>(cComparator));
        for(int i = 0; i < numCustomers; i++){
            Customer current = customerList[i];
            addEvent(new Event(current, initialiseServer, 1, current.getTime()));
        }

        while (queue.size() != 0){
            //Get and remove the head of the queue.
            Event currentEvent = queue.poll();

            //Label all the current events as current.
            Customer currentCustomer = currentEvent.getCustomer();
            Server currentServer = currentEvent.getServer();
            double currentTime = currentEvent.getTime();

            //Print current event.
            currentEvent.print();

            if(currentEvent.getState() == State.ARRIVES){

                /*Case A: 
                  If the customer arrives at a time greater than or equal to any server's next available time, 
                  create a new event; 
                  allocate that specific server to that specific event; 
                  getState -> State.SERVES 
                  allocate new AvailableTime to the server and add it to the event
                  then add the event to the queue.*/
                boolean serverFound = false;
                for(int i = 0; i < serverList.length; i++){	
                    if(currentTime >= serverList[i].getNextAvailableTime()){
                        currentServer = serverList[i];
                        Event newEvent = new Event(currentCustomer, currentServer, State.SERVES, currentTime);
                        serverFound = true;
                        addEvent(newEvent);
                        break;
                    }	
                }

                /*Case B:
                  If the customer arrives at a time lesser than any server's next available time.*/
                if(!serverFound){
                    boolean emptyWaitListFound = false;
                    /*Check the type of the customer
                      if its a GreedyCustomer then check all the servers through the serverList to get the lowest waitingCustomer.size()
                      smallest size found -> currentServer
                      instantiate a new event; 
                      then assign that server to that event; 
                      getState -> State.WAITS; 
                      then update Server.waitingCustomer.*/   
                    if(currentCustomer instanceof NormalCustomer){
                        for(int i = 0; i < serverList.length; i++){
                            currentServer = serverList[i];
                            if(currentServer.isWaitListNotFull(maxQueue)){
                                Event newEvent = new Event(currentCustomer, currentServer, State.WAITS, currentTime);
                                currentServer.addNextWaitingCustomer(currentCustomer);
                                serverFound = true;
                                addEvent(newEvent);
                                emptyWaitListFound = true;
                                break; 
                            }
                        }
                    }else if(currentCustomer instanceof GreedyCustomer){
                        int min = Integer.MAX_VALUE;
                        for(int i = 0; i < serverList.length; i++){
                            if(serverList[i].isWaitListNotFull(maxQueue) && serverList[i].getWaitingCustomersList().size() < min){
                                min = serverList[i].getWaitingCustomersList().size();
                                currentServer = serverList[i]; 
                            }
                        }
                        if(currentServer.getServerID() != 0){ 
                            serverFound = true;
                            Event newEvent = new Event(currentCustomer, currentServer, State.WAITS, currentTime);
                            currentServer.addNextWaitingCustomer(currentCustomer);
                            addEvent(newEvent);
                            emptyWaitListFound = true;	
                        }	
                    }		

                    /*Case C:
                      If all the servers waitingCustomerList is full,
                      create a new event;
                      getState -> State.LEAVES.*/					
                    if(!emptyWaitListFound){
                        Event newEvent = new Event(currentCustomer, currentServer, State.LEAVES, currentTime);
                        addEvent(newEvent);
                    }
                }
            }

            /*Case D: 
              If the customer is being served,
              instantiate a new event;
              change the event's time;
              getState -> State.DONE.*/
            else if(currentEvent.getState() == State.LEAVES){
                numCustomersLeaves++;
            }else if(currentEvent.getState() == State.SERVES){
                currentServer.setNextAvailableTime(currentTime + randomGen.genServiceTime()); 
                Event newEvent = new Event(currentCustomer, currentServer, State.DONE, currentServer.getNextAvailableTime());
                addEvent(newEvent);
                //Calculate each customer's waiting time by subtracting the current time with the customer's arrival time, then add it to the totalWaitingTime.
                double waitingTime = currentTime - currentCustomer.getTime();
                totalWaitingTime += waitingTime;
            }else if(currentEvent.getState() == State.DONE){
                numCustomersServed++;
                //If currentServer is a HumanServer, check for a break.
                if(currentServer instanceof HumanServer && randomGen.genRandomRest() < restProbability){
                    Event newEvent = new Event(currentCustomer, currentServer, State.SERVER_REST, currentTime);
                    addEvent(newEvent);
                    currentServer.setNextAvailableTime(currentTime + randomGen.genRestPeriod()); 
                    newEvent = new Event(currentCustomer, currentServer, State.SERVER_BACK, currentServer.getNextAvailableTime());
                    addEvent(newEvent);
                }
                /*If currentServer is a SelfServer or if the HumanServer doe snot need a break;
                  check for any waiting customer,
                  create a new event; 
                  serve the next waiting customer.*/
                else{
                    if(currentServer.getWaitingCustomersList().size() != 0){
                        Event newEvent = new Event(currentServer.getNextWaitingCustomer(), currentServer, State.SERVES, currentTime);
                        addEvent(newEvent);
                    }
                }
            }
            /*If after returning the server has a list of waiting customers,
              create a new event;
              serve the next waiting customer.*/
            else if(currentEvent.getState() == State.SERVER_BACK){
                if(currentServer.getWaitingCustomersList().size() != 0){
                    Event newEvent = new Event(currentServer.getNextWaitingCustomer(), currentServer, State.SERVES, currentTime);
                    addEvent(newEvent);
                }
            }
        }
    }

    public void getStats(){
        double averageWaitingTime;
        //If no one is served.
        if(numCustomersServed <= 0){
            averageWaitingTime = 0;
        }else{
            averageWaitingTime = totalWaitingTime / numCustomersServed;
        }
        System.out.println("[" + String.format("%.3f", averageWaitingTime) + " " + numCustomersServed + " " + numCustomersLeaves + "]");
    }
}
