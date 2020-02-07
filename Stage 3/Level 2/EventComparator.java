package cs2030.simulator;

import java.util.PriorityQueue;
import java.util.Comparator;

public class EventComparator implements Comparator<Event>{

    @Override
        public int compare(Event e1, Event e2){
            if (e1.getTime() == e2.getTime()){
                //if time is equal compare the customer
                if (e1.getCustomer().getCustomerID() > e2.getCustomer().getCustomerID()){
                    return 1;
                }
                else if ((e1.getCustomer()).getCustomerID() < (e2.getCustomer()).getCustomerID()){
                    return -1;
                }
                else{
                    //if customer is same, compare event Type(STATE)
                    if (e1.getState() > e2.getState()){
                        return 1;
                    }
                    else if (e1.getState() < e2.getState()){
                        return -1;
                    }
                    else{
                        return 0;
                    }
                }

            } 
            else if(e1.getTime() < e2.getTime()){
                return -1;
            }

            return 1;		
        }    	
}
