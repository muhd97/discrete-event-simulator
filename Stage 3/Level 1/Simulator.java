package cs2030.simulator;

import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Simulator{

    //Simulator attributes
    private int seedValue;
    private int numServers;
    private int maxQueue;
    private int numCustomers;
    private double arrivalRate;
    private double serviceRate; 

    //Comparators for Customer PriorityQueue(waitingCustomers) and Event PriorityQueue(queue)
    private Comparator<Customer> cComparator = new CustomerComparator();
    private Comparator<Event> eComparator = new EventComparator();

    //PriorityQueue for Events(queue)
    private PriorityQueue<Event> queue = new PriorityQueue<Event>(eComparator);

    //Variables for getStats()
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
     */	
    public Simulator(int seedValue, int numServers, int maxQueue, int numCustomers, double arrivalRate, double serviceRate){
        this.seedValue = seedValue;
        this.numServers = numServers;
        this.maxQueue = maxQueue;
        this.numCustomers = numCustomers;
        this.arrivalRate = arrivalRate;
        this.serviceRate = serviceRate;
    }

    /**
     * Adds a newly generated event into the PriorityQueue(queue)
     */
    public void addEvent(Event newEvent){
        queue.add(newEvent);
    }

    public void startSimulation(){
        RandomGenerator randomGen = new RandomGenerator(seedValue, arrivalRate, serviceRate);

        //Add in all the customers arriving(their ID and randomly generated time to a customerList[])
        Customer[] customerList = new Customer[numCustomers];
        customerList[0] = new Customer(1, 0.0);
        double customerTimeLeftOff = 0.0;
        for(int i = 1; i < numCustomers; i++){
            if(i == 1){
                customerTimeLeftOff = 0.0;
            }
            double newCustomerArrivalTime = customerTimeLeftOff + randomGen.genInterArrivalTime();

            customerList[i] = new Customer(i + 1, newCustomerArrivalTime);

            customerTimeLeftOff = newCustomerArrivalTime;
        }	

        //Initialize every server
        Server[] serverList = new Server[numServers];
        for(int i = 0; i < numServers; i++){
            serverList[i] = new Server(i + 1, 0, new PriorityQueue<Customer>(cComparator));
        }


        //Start the queue by adding the customer arriving into the Priority Queue(queue)
        Server initialiseServer = new Server(0, 0, new PriorityQueue<Customer>(cComparator));
        for(int i = 0; i < numCustomers; i++){
            Customer current = customerList[i];
            addEvent(new Event(current, initialiseServer, 1, current.getTime()));
        }

        while (queue.size() != 0){
            //Gets and removes the head of this queue
            Event currentEvent = queue.poll();

            //all the variables referring to the current event will be labelled with current
            Customer currentCustomer = currentEvent.getCustomer();
            Server currentServer = currentEvent.getServer();
            double currentTime = currentEvent.getTime();

            //Print current event
            currentEvent.print();

            if(currentEvent.getState() == State.ARRIVES){

                /*case A: 
                  if the customer arrives at a time greater than or equal to any server's next available time, 
                  then create a new event; 
                  allocate that specific server to that specific event; 
                  getState -> State.SERVES 
                  allocate new AvailableTime to the server and add it to the event
                  add event to the queue*/
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

                /*case B:
                  if the customer arrives at a time lesser than any server's next available time*/
                if(!serverFound){
                    boolean emptyWaitListFound = false;
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


                    /*case C:
                      if all the servers waitingCustomerLists is full then
                      create a new event;
                      getState -> State.LEAVES*/					
                    if(!emptyWaitListFound){
                        Event newEvent = new Event(currentCustomer, currentServer, State.LEAVES, currentTime);
                        addEvent(newEvent);
                    }
                }
            }

            /*case D: 
              if the customer is being served then
              instantiate a new event;
              change the event's time;
              getState -> State.DONE;*/
              else if(currentEvent.getState() == State.LEAVES){
                  numCustomersLeaves++;
              }else if(currentEvent.getState() == State.SERVES){
                  currentServer.setNextAvailableTime(currentTime + randomGen.genServiceTime()); 
                  Event newEvent = new Event(currentCustomer, currentServer, State.DONE, currentServer.getNextAvailableTime());
                  addEvent(newEvent);
                  //calculate each customer's waiting time by subtracting the current time with the customer's arrival time and then add it to the totalWaitingTime
                  double waitingTime = currentTime - currentCustomer.getTime();
                  totalWaitingTime += waitingTime;
              }else if(currentEvent.getState() == State.DONE){
                  numCustomersServed++;
                  /*check for any waiting customer,
                    create a new event; 
                    serving the next waiting customer*/
                  if(currentServer.getWaitingCustomersList().size() != 0){
                      Event newEvent = new Event(currentServer.getNextWaitingCustomer(), currentServer, State.SERVES, currentTime);
                      addEvent(newEvent);
                  }

              } 
        }
    }

    public void getStats(){
        double averageWaitingTime;
        //if no one is served
        if(numCustomersServed <= 0){
            averageWaitingTime = 0;
        }else{
            averageWaitingTime = totalWaitingTime / numCustomersServed;
        }
        System.out.println("[" + String.format("%.3f", averageWaitingTime) + " " + numCustomersServed + " " + numCustomersLeaves + "]");
    }
}
