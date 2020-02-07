public class Customer {

    private int customerID;
    public double arrivalTime;
    public static int noOfCustomers = 0;

    /**
     * Constructor for the Customer.
     * @param customerID is the ID of that customer
     * @param arrivalTime is the arrivial time of that customer
     */
    public Customer(int customerID, double arrivalTime) {
        this.customerID = customerID;
        this.arrivalTime = arrivalTime;
        noOfCustomers++;
    }

    /**
     * returns the total no of customers.
     */
    public int getNoOfCustomers(){
        return this.noOfCustomers;

    }

    /**
     * toString method for printing out a customer.
     */
    @Override
        public  String toString() {
            return String.format(customerID + " arrives at " + String.format("%.3f", arrivalTime));
        }

}
