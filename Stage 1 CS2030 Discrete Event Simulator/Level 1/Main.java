import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Customer> list = new ArrayList<>();
        int i = 1;
        while (sc.hasNextDouble()) {
            list.add(new Customer(i, sc.nextDouble()));
            i++;
        }
        for (Customer c : list) {
            System.out.println(c);
        }
        System.out.println("Number of customers: " + list.get(0).getNoOfCustomers()); 
    }
}
