import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Jacob on 4/11/16.
 */
abstract public class MemoryManager {

    //public static ArrayList<Block> memory;
    //public Queue<Process> readyQueue;
    //Keeps track of the position in memory of waiting processes by keeping a queue of process timestamps.
    public Queue<Long> processQueue;

    abstract public void compactMemory();

    public MemoryManager(){
        this.processQueue = new LinkedList<Long>();
        //this.readyQueue = new LinkedList<Process>();
    }
    public void coalesceMemory(){
        for(int i=0; i<(Memory.size()-1); i++){
            if(Memory.get(i).occupied){
                continue;
            }else if(!Memory.get(i+1).occupied){
                Memory.set(i, new Block(Memory.get(i).size + Memory.get(i+1).size));
                Memory.remove(i+1);
            }
        }
    }
    public void loadIntoCPU(Block block){
        if(block.process.duration > 5){
            block.process.totalWaitTime += Computer.time.getCurrentTime() - block.process.startWaitTime;
            block.process.duration -= 5;
            if(block.process.duration == 0){
                block.removeJob();
                processQueue.poll();
            }
            Computer.time.incrementCurrentTime();
        }else{
            Computer.time.stats.totalWaitTime += (Computer.time.getCurrentTime() - block.process.startWaitTime) + block.process.totalWaitTime;
            Computer.time.incrementCurrentTime(block.process.duration);
            block.removeJob();
        }
        coalesceMemory();
    }
    abstract public void admitProcess(Process incoming);
}