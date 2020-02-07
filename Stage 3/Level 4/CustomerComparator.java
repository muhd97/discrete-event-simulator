package cs2030.simulator;

import java.util.PriorityQueue;
import java.util.Comparator;

public class CustomerComparator implements Comparator<Customer>{

    @Override
        public int compare(Customer c1, Customer c2){
            if (c1.getTime() == c2.getTime()){
                //If time is equal, compare the customer.
                if (c1.getCustomerID() > c2.getCustomerID()){
                    return 1;
                }
                else if (c1.getCustomerID() < c2.getCustomerID()){
                    return -1;
                }
                else{
                    //If its the same customer, do nothing.
                    return 0;
                }

            } 
            else if(c1.getTime() < c2.getTime()){
                return -1;
            }
            //If c1.getTime() > c2.getTime().
            return 1;		
        }    	
}
