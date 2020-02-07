import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numSims = sc.nextInt();
        List<Double> input = new ArrayList<>();
        while (sc.hasNextDouble()) {
              input.add(sc.nextDouble());
        }
        Simulator sim = new Simulator(numSims, input);
        System.out.println("[" + String.format("%.3f", sim.getAverageWaitingTime()) + " " + sim.getNumServed() + " " + sim.getNumLeft() + "]");
    }
}
