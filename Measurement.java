/**
 * f. Class calculates statistics of how memory is utilized.
 */
import java.util.ArrayList;
import java.util.LinkedList;

public class Measurement {
    public int totalJobs;
    public int totalWaitTime;
    public int totalProcessingTime;
    //Total system memory, used to calculate average hole size and storage utilization.
    private final int TOTALMEMORY = 1800;
    //Smallest possible jobs size, used to calculate external fragmentation.
    private final int SMALLESTJOB = 50;

    //f. Constructs an empty set of stats to measure performance.
    public Measurement(){
        this.totalJobs = 0;
        this.totalWaitTime = 0;
        this.totalProcessingTime = 0;
    }
    //f. Calculates average wait time of jobs
    public int getAvgWait(){
        return totalWaitTime/totalJobs;
    }
    //f. Calculates average processing time.
    public int getAvgProcessing(){
        return totalProcessingTime/totalJobs;
    }
    //f. Calculates average turnaround time.
    public int getAvgTurnaround(){
        return (getAvgProcessing() + getAvgWait());
    }
    //f. Calculates external fragmentation
    public int getExternalFragmentation(){
        ArrayList<Block> memory = Computer.memory.getMemory();
        int fragmentation = 0;
        for(int i=0; i<memory.size(); i++){
            if(Computer.memory.get(i).size < SMALLESTJOB){
                fragmentation += Computer.memory.get(i).size;
            }
        }
        return fragmentation;
    }
    //f. Calculates average hole size
    public int getAvgHoleSize(){
        ArrayList<Block> memory = Computer.memory.getMemory();
        return (TOTALMEMORY/memory.size());
    }
    //f. For printing stats at 1000VTU intervals, because 1000 is also a multiple of 100, I print the stats for 100 VTU intervals as well.
    public void output1000(){
        LinkedList<Process> disk = Disk.getDisk();
        System.out.println("Pending List Contents:");
        for(int i=0; i<disk.size(); i++){
            System.out.print("Process " + (i+1) + ": " + disk.get(i).toString() + "\t");
            if((i+1)%4 == 0){
                System.out.println();
            }
        }
        System.out.println();
        output100();
    }
    //f. For printing stats at 100VTU intervals
    public void output100(){
        System.out.println("External Fragmentation:\t" + getExternalFragmentation() + "K bytes" +
        "\nAverage Hole Size:\t" + getAvgHoleSize() + "K bytes");
    }
    //f. For printing stats at 4000VTU time point
    public void output4000(){
        System.out.println("Printing stats for time point 4000 VTUs" +
        "\nAverage Turnaround Time:\t" + getAvgTurnaround()  + " VTUs" +
        "\nAverage Wait Time:\t" + getAvgWait() + " VTUs" +
        "\nAverage Processing Time:\t" + getAvgProcessing() + " VTUs");
        output1000();
    }
    //f. For printing stats at 5000VTU time point
    public void output5000(){
        LinkedList<Process> disk = Disk.getDisk();
        System.out.println("Pending List Contents:");
        for(int i=0; i<disk.size(); i++){
            System.out.print("Process " + (i+1) + ": " + disk.get(i).toString() + "\t");
            if((i+1)%4 == 0){
                System.out.println();
            }
        }
        System.out.println();
    }
}
