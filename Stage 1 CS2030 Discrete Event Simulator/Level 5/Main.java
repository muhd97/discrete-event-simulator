import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Double> list = new ArrayList<>();
        while (sc.hasNextDouble()) {
            list.add(sc.nextDouble());
        }
        Server server = new Server(list);
        server.startServing();
        System.out.println("[" + String.format("%.3f", server.getAverageWaitingTime()) + " "+ server.getNumServed() + " " + server.getNumLeft() + "]");
    }
}
