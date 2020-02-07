import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numSims = scanner.nextInt();
        List<Double> input = new ArrayList<>();
        while (scanner.hasNextDouble()) {
              input.add(scanner.nextDouble());
        }
        Simulator sim = new Simulator(numSims, input);
        System.out.println("[" + String.format("%.3f", sim.getAverageWaitingTime()) + " " + sim.getNumServed() + " " + sim.getNumLeft() + "]");
    }
}
