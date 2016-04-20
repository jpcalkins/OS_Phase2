/**
 * f. Class works as CPU clock but gives 2 time lines for event-based timing.
 */
public class Timer {
    //Current time keeps track of CPU events
    private static int currentTime;
    //Previous time tracks events in memory
    private static int previousTime;
    //Stats keeps track of fragmentation, rejected jobs, etc.
    public static Measurement stats;
    private final int QUANTUM = 5;

    public Timer(){
        currentTime = 0;
        previousTime = 0;
        stats = new Measurement();
    }

    public int getCurrentTime(){
        return currentTime;
    }
    public int getPreviousTime(){
        return previousTime;
    }
    //f. Increments CPU timeline
    public void incrementCurrentTime(int increment){
        currentTime += increment;
    }
    public void incrementCurrentTime(){
        currentTime += QUANTUM;
    }
    //f. Increments memory timeline and outputs any statistics if time has passed a necessary interval.
    public static void incrementPrevTime(int increment){
        //This chain of ifs help me establish if time has passed a moment when statistics need to be printed.
        if(previousTime + increment >= 5000){
            System.out.println("Printing stats for time point 5000 VTUs.");
            stats.output5000();
            System.out.println("System shutting down, have reached time limit.");
            System.exit(0);
        }else if(previousTime <= 4000 && previousTime + increment >= 4000){
            stats.output4000();
            System.out.println();
        }else if(currentTime >= 1000 && currentTime <= 4000) {
            int baseTime = previousTime % 1000;
            if (baseTime + increment >= 1000) {
                System.out.println("Printing stats for time point " + ((previousTime+increment) / 1000) * 1000 + " VTUs.");
                stats.output1000();
                System.out.println();
            } else if (baseTime % 100 + increment >= 100) {
                System.out.println("Printing stats for time point " + ((previousTime+increment) / 100) * 100 + " VTUs.");
                stats.output100();
                System.out.println();
            }
        }
        previousTime += increment;
        //Error Checking
        if (previousTime > currentTime) {
            System.out.println("ERROR! previous time, "+ previousTime +", has exceeded current time, "+ currentTime +".");
        }
    }
    //f. Sets memory timeline equla to CPU timeline.
    public void returnToPresent(){
        //Sets memory timeline equal to that of CPU timeline.
        if(previousTime < currentTime){
            incrementPrevTime(currentTime - previousTime);
        //Error checking case.
        }else if(previousTime > currentTime){
            System.out.println("ERROR! previous time has exceeded current time.");
        }
    }
    public static void setPreviousTime(int input){
        incrementPrevTime(input - previousTime);
    }
}
