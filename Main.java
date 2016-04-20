/**
 * a. Jacob Calkins
 * b. CS 4323
 * c. Simulation Project, Phase 2
 * d. 04/20/2016
 * e. Strategy is the selected storage strategy; best-fit, worst-fit, first-fit. Manager is the selected memory manager that dictates compaction.
 * f. Main class that starts the "computer" with the desired memory allocation strategy.
 */
import java.util.Scanner;

public class Main {

    private static StorageStrategy strategy;
    private static MemoryManager manager;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        if(args.length < 1){
            System.out.println("Run the program with a b flag to simulate best-fit storage strategy.\nRun the program with a w flag to simulate worst-fit storage strategy.\nRun the program with a f flag to simulate first-fit storage strategy.");
            System.exit(0);
        } else if (args[0].toLowerCase().equals("b")) {
            System.out.println("Best-fit memory allocation simulation");
            strategy = new BestFitStorage();
        } else if (args[0].toLowerCase().equals("f")) {
            System.out.println("First-fit memory allocation simulation");
            strategy = new FirstFitStorage();
        } else if (args[0].toLowerCase().equals("w")) {
            System.out.println("Worst-fit memory allocation simulation");
            strategy = new WorstFitStorage();
        } else {
            System.out.println("Run the program with a b flag to simulate best-fit storage strategy.\nRun the program with a w flag to simulate worst-fit storage strategy.\nRun the program with a f flag to simulate first-fit storage strategy.");
            System.exit(0);
        }
        System.out.println("Enter 1 to run the simulation with compaction occurring when a memory request is denied." +
                "\nEnter 2 to run the simulation with compaction occurring every 250 VTUs" +
                "\nEnter 3 to run the simulation with compaction occurring every 500 VTUs.");
        String compacter = scan.nextLine();
        if(compacter.equals("1")){
            manager = new CompactDeny();
        }else if(compacter.equals("2")){
            manager = new Compact250();
        }else if(compacter.equals("3")){
            manager = new Compact500();
        }
        Computer computer = new Computer(strategy, manager);
        computer.startComputer();
    }
}
