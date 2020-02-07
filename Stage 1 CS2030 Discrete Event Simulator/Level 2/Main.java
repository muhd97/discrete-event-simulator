import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Double> inputTime = new ArrayList<>();
        while (sc.hasNextDouble()) {
            inputTime.add(sc.nextDouble());
        }
        Server server = new Server(inputTime);
        server.startServing();
        System.out.println("Number of customers: " + server.getNumOfCustomers());
    }
}
