import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Jacob on 4/11/16.
 */
public class Computer {

    public static Timer time = new Timer();
    public static StorageStrategy storageStrategy;
    //public static MemoryManager memoryManager;
    public static Memory memory;
    public Process nextProcess;
//    public static ArrayList<Block> memory;
//    public Queue<Process> readyQueue;
//    //Keeps track of the position in memory of waiting processes by keeping a queue of process timestamps.
//    public Queue<Long> processQueue;

    public Computer(StorageStrategy newStorage, MemoryManager newManager){
        storageStrategy = newStorage;
        memory = new Memory(newManager);
        nextProcess = Process.randJob(time.getPreviousTime());
        //memoryManager = newManager;
        //memory = new ArrayList<Block>();
        //memory.add(new Block(1800));
//        this.processQueue = new LinkedList<Long>();
//        this.readyQueue = new LinkedList<Process>();
    }
    public void startComputer(){
        Process firstProcess = Process.randJob(0);
        storageStrategy.addProcess(firstProcess);
        while(time.getCurrentTime() < memory.get(0).process.toa){
            time.incrementCurrentTime();
        }
        memory.loadIntoCPU(memory.get(0));
        while(true){
            generateJobs();
            if(memory.processQueue.peek() != null){
                runProcess(memory.processQueue.poll());
            }
        }
    }
    //Creates random jobs and
    public void generateJobs(){
        while(nextProcess.toa < time.getCurrentTime()){
            memory.admitProcess(nextProcess);
            nextProcess = Process.randJob(time.getPreviousTime());
        }
        //Checks if there are any jobs that have been waiting for a memory hole to open.
//        if(readyQueue.size() >= 1) {
//            //Sets memory timeline to present CPU event
//            time.returnToPresent();
//            Process temp = readyQueue.poll();
//            temp.setStartWaitTime(time.getPreviousTime());
//            addProcess(temp);
//            if(readyQueue.size() >= 1) {
//                //process is still waiting for hole to open so no need to make more jobs.
//                return;
//            }
//        }
        //nextProcess = Process.randJob(time.getPreviousTime());
        //int largestBlockSize = memory.getLargestBlock().size;
        //Special case that occurs when memory events catch up to CPU events and there is nothing in the readyQueue because memory has become too fragmented and majority of jobs are too large.
//        if(time.getCurrentTime() == time.getPreviousTime() && readyQueue.peek() == null){
//            while(nextProcess.size > largestBlockSize) {
//                nextProcess = Process.randJob(time.getPreviousTime());
//                time.incrementCurrentTime(nextProcess.toa);
//                time.incrementPrevTime(nextProcess.toa);
//                readyQueue.add(nextProcess);
//            }
//        }else{
            //Checks that there is any possible block to place a process & checks if process was originated before the last event
//            while (nextProcess.toa <= time.getCurrentTime()) {
//                //Increments time before latest event
//                time.incrementPrevTime(nextProcess.toa - time.getPreviousTime());
//                if (nextProcess.size > largestBlockSize) {
//                    nextProcess = Process.randJob(time.getPreviousTime());
//                    continue;
//                }
//                readyQueue.add(nextProcess);
//                //Means that the process now in readyQueue needs to wait for a block to open so no jobs need to be generated after this one.
//                if (nextProcess.size > this.getLargestOpenBlock().size) {
//                    break;
//                }
//                nextProcess = Process.randJob(time.getPreviousTime());
//            }
//        }
//        if(readyQueue.peek() != null){
//            processReadyQueue();
//        }
    }
    public void runProcess(long id){
        for(int i=0; i<memory.size(); i++){
            if(memory.get(i).occupied && memory.get(i).process.timeStamp == id){
                //time.stats.totalJobs++;
                //time.stats.totalProcessingTime += memory.get(i).process.duration;
                //time.stats.totalWaitTime += time.getCurrentTime()-memory.get(i).process.startWaitTime;
                //time.incrementCurrentTime(memory.get(i).process.duration);
                memory.loadIntoCPU(memory.get(i));
                //ejectFromMemory(id);
                break;
            }
        }
    }
}
