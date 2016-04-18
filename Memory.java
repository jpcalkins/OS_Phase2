import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Jacob on 4/17/16.
 */
public class Memory {

    private static ArrayList<Block> memory;
    public Queue<Process> readyQueue;
    //Keeps track of the position in memory of waiting processes by keeping a queue of process timestamps.
    public Queue<Long> processQueue;
    public static MemoryManager manager;

    public Memory(MemoryManager newManager){
        memory = new ArrayList<Block>();
        memory.add(new Block(1800));
        manager = newManager;
        processQueue = new LinkedList<Long>();
        readyQueue = new LinkedList<Process>();
    }
    public void add(Block block){
        memory.add(block);
    }
    public Block get(int i){
        return memory.get(i);
    }
    public int size(){
        return memory.size();
    }
    public void set(int i, Block block){
        memory.set(i, block);
    }
    public void remove(int i){
        memory.remove(i);
    }
    public Block getLargestOpenBlock(){
        Block largestOpenBlock = memory.get(0);
        for(int i=1; i<memory.size(); i++){
            if(memory.get(i).size > largestOpenBlock.size && !memory.get(i).occupied){
                largestOpenBlock = memory.get(i);
            }
        }
        return largestOpenBlock;
    }

    public Block getLargestBlock(){
        Block largestBlock = memory.get(0);
        for(int i=1; i<memory.size(); i++){
            if(memory.get(i).size > largestBlock.size){
                largestBlock = memory.get(i);
            }
        }
        return largestBlock;
    }

    //Clears a process from memory since it has been processed through the CPU
    public void ejectFromMemory(long id){
        for(int i=0; i<memory.size(); i++){
            if(memory.get(i).occupied && memory.get(i).process.timeStamp == id){
                memory.get(i).removeJob();
                break;
            }
        }
    }
    public void loadIntoCPU(Block block){
        manager.loadIntoCPU(block);
    }
    public void admitProcess(Process incoming){
        manager.admitProcess(incoming);
    }
    //Helper method that is used to pass memory to the measurement class to calculate statistics.
    public static ArrayList<Block> getMemory(){
        return memory;
    }
}
