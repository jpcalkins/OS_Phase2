import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Jacob on 4/11/16.
 */
abstract public class MemoryManager {

    //public static ArrayList<Block> memory;
    public Queue<Process> readyQueue;
    public Queue<Long> processQueue;

    abstract public void compactMemory();

    public MemoryManager(){
        this.processQueue = new LinkedList<Long>();
        this.readyQueue = new LinkedList<Process>();
    }
    public void coalesceMemory(){
        for(int i=0; i<(Computer.memory.size()-1); i++){
            if(Computer.memory.get(i).occupied){
                continue;
            }else if(!Computer.memory.get(i+1).occupied){
                Computer.memory.set(i, new Block(Computer.memory.get(i).size + Computer.memory.get(i+1).size));
                Computer.memory.remove(i+1);
            }
        }
    }
    public void loadIntoCPU(Block block){
        if(block.process.duration > 5){
            block.process.totalWaitTime += Computer.time.getCurrentTime() - block.process.startWaitTime;
            block.process.duration -= 5;
            if(block.process.duration == 0){
                Computer.time.stats.totalJobs++;
                Computer.time.stats.totalProcessingTime += block.process.duration;
                block.removeJob();
                processQueue.poll();
            }
            Computer.time.incrementCurrentTime();
        }else{
            Computer.time.stats.totalWaitTime += (Computer.time.getCurrentTime() - block.process.startWaitTime) + block.process.totalWaitTime;
            Computer.time.stats.totalJobs++;
            Computer.time.stats.totalProcessingTime += block.process.duration;
            Computer.time.incrementCurrentTime(block.process.duration);
            block.removeJob();
        }
        coalesceMemory();
    }
}