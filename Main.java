/**
 * a. Jacob Calkins
 * b. CS 4323
 * c. Simulation Project, Phase 1
 * d. Sarath Kumar Maddinani
 * e. bestFit, firstFit, and worstfit are the different memory allocation strategies and are called based upon flags passed at program startup.
 * f. Main class that starts the "computer" with the desired memory allocation strategy.
 */
public class Main {

    public static BestFitStorage bestFit;
    public static FirstFitStorage firstFit;
    public static WorstFitStorage worstFit;

    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("Run the program with a b flag to simulate best-fit storage strategy.\nRun the program with a w flag to simulate worst-fit storage strategy.\nRun the program with a f flag to simulate first-fit storage strategy.");
        } else if (args[0].toLowerCase().equals("b")) {
            System.out.println("Best-fit memory allocation simulation");
            bestFit = new BestFitStorage();
            bestFit.startComputer();
        } else if (args[0].toLowerCase().equals("f")) {
            System.out.println("First-fit memory allocation simulation");
            firstFit = new FirstFitStorage();
            firstFit.startComputer();
        } else if (args[0].toLowerCase().equals("w")) {
            System.out.println("Worst-fit memory allocation simulation");
            worstFit = new WorstFitStorage();
            worstFit.startComputer();
        } else {
            System.out.println("Run the program with a b flag to simulate best-fit storage strategy.\nRun the program with a w flag to simulate worst-fit storage strategy.\nRun the program with a f flag to simulate first-fit storage strategy.");
        }
    }
}
