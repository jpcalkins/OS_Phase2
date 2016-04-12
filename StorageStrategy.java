/**
 * e. Time is my custom CPU clock, memory is an array of blocks that can grow as necessary, readyQueue is a list of jobs not yet in memory, processQueue is a list of process timestamps that allows me to keep track of which processes arrived when.
 * f. Class that memory allocation strategies inherit from. Lays out most of the basic functionality of the simulation.
 */
import java.util.*;

abstract public class StorageStrategy {
    //public Timer time;
    //public static ArrayList<Block> memory;
    public Queue<Process> readyQueue;
    //Keeps track of the position in memory of waiting processes by keeping a queue of process timestamps.
    public Queue<Long> processQueue;


    abstract public void addProcess(Process incomingProcess);

    public StorageStrategy(){
        //time = new Timer();
        this.processQueue = new LinkedList<Long>();
        this.readyQueue = new LinkedList<Process>();
    }
    //Starts the simulation and keeps the simulation running.
    public void startComputer(){
        //Process firstProcess = Process.randJob(0);
        //addProcess(firstProcess);
//        while(Computer.time.getCurrentTime() < firstProcess.toa){
//            Computer.time.incrementCurrentTime();
//        }
        //time.incrementCurrentTime(firstProcess.toa + firstProcess.duration);
        //ejectFromMemory(processQueue.poll());
        while(true){
            generateJobs();
            if(processQueue.peek() != null){
                runProcess(processQueue.poll());
            }
        }
    }
    //Creates random jobs and adds them to readyQueue accordingly.
    public void generateJobs(){
        //Checks if there are any jobs that have been waiting for a memory hole to open.
        if(readyQueue.size() >= 1) {
            //Sets memory timeline to present CPU event
            Computer.time.returnToPresent();
            Process temp = readyQueue.poll();
            temp.setStartWaitTime(Computer.time.getPreviousTime());
            addProcess(temp);
            if(readyQueue.size() >= 1) {
                //process is still waiting for hole to open so no need to make more jobs.
                return;
            }
        }
        Process upcomingProcess = Process.randJob(Computer.time.getPreviousTime());
        int largestBlockSize = getLargestBlock().size;
        //Special case that occurs when memory events catch up to CPU events and there is nothing in the readyQueue because memory has become too fragmented and majority of jobs are too large.
        if(Computer.time.getCurrentTime() == Computer.time.getPreviousTime() && readyQueue.peek() == null){
            while(upcomingProcess.size > largestBlockSize) {
                upcomingProcess = Process.randJob(Computer.time.getPreviousTime());
                Computer.time.incrementCurrentTime(upcomingProcess.toa);
                Computer.time.incrementPrevTime(upcomingProcess.toa);
                readyQueue.add(upcomingProcess);
            }
        }else{
            //Checks that there is any possible block to place a process & checks if process was originated before the last event
            while (upcomingProcess.toa + Computer.time.getPreviousTime() <= Computer.time.getCurrentTime()) {
                //Increments time before latest event
                Computer.time.incrementPrevTime(upcomingProcess.toa);
                if (upcomingProcess.size > largestBlockSize) {
                    upcomingProcess = Process.randJob(Computer.time.getPreviousTime());
                    continue;
                }
                readyQueue.add(upcomingProcess);
                //Means that the process now in readyQueue needs to wait for a block to open so no jobs need to be generated after this one.
                if (upcomingProcess.size > this.getLargestOpenBlock().size) {
                    break;
                }
                upcomingProcess = Process.randJob(Computer.time.getPreviousTime());
            }
        }
        if(readyQueue.peek() != null){
            processReadyQueue();
        }
    }
    //Moves jobs into memory from readyQueue
    public void processReadyQueue(){
        while(readyQueue.size() > 0){
            //adds process to memory if there is an open block.
            if(readyQueue.peek().size <= this.getLargestOpenBlock().size){
                addProcess(readyQueue.poll());
            //Clears everything after the first process in readyQueue because it is waiting for an occupied block to open
            }else if(readyQueue.peek().size <= this.getLargestBlock().size){
                Process temp = readyQueue.poll();
                readyQueue.clear();
                readyQueue.add(temp);
                return;
            //rejects process if there is not a large enough block in memory
            }else{
                readyQueue.poll();
            }
        }
    }

    public Block getLargestOpenBlock(){
        Block largestOpenBlock = Computer.memory.get(0);
        for(int i=1; i<Computer.memory.size(); i++){
            if(Computer.memory.get(i).size > largestOpenBlock.size && !Computer.memory.get(i).occupied){
                largestOpenBlock = Computer.memory.get(i);
            }
        }
        return largestOpenBlock;
    }

    public Block getLargestBlock(){
        Block largestBlock = Computer.memory.get(0);
        for(int i=1; i<Computer.memory.size(); i++){
            if(Computer.memory.get(i).size > largestBlock.size){
                largestBlock = Computer.memory.get(i);
            }
        }
        return largestBlock;
    }
    //Simulates a process entering the CPU and moves my program to the next event, process leaving the CPU.
    public void runProcess(long id){
        for(int i=0; i<Computer.memory.size(); i++){
            if(Computer.memory.get(i).occupied && Computer.memory.get(i).process.timeStamp == id){
                Computer.time.stats.totalJobs++;
                Computer.time.stats.totalWaitTime += Computer.time.getCurrentTime()-Computer.memory.get(i).process.startWaitTime;
                Computer.time.stats.totalProcessingTime += Computer.memory.get(i).process.duration;
                Computer.time.incrementCurrentTime(Computer.memory.get(i).process.duration);
                ejectFromMemory(id);
                break;
            }
        }
    }
    //Clears a process from memory since it has been processed through the CPU
    public void ejectFromMemory(long id){
        for(int i=0; i<Computer.memory.size(); i++){
            if(Computer.memory.get(i).occupied && Computer.memory.get(i).process.timeStamp == id){
                Computer.memory.get(i).removeJob();
                break;
            }
        }
    }
    //Helper method that is used to pass memory to the measurement class to calculate statistics.
    public static ArrayList<Block> getMemory(){
        return Computer.memory;
    }
}
