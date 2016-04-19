import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Jacob on 4/17/16.
 */
public class Memory {

    private static ArrayList<Block> memory = new ArrayList<Block>();
    //Keeps track of the position in memory of waiting processes by keeping a queue of process timestamps.
    public static LinkedList<Long> processQueue = new LinkedList<Long>();
    public static MemoryManager manager;
    public StorageStrategy storageStrategy;

    public Memory(MemoryManager newManager, StorageStrategy newStrategy){
        memory.add(new Block(1800));
        manager = newManager;
        storageStrategy = newStrategy;
    }
    public void add(Block block){
        memory.add(block);
    }
    public void add(int i, Block block){
        memory.add(i, block);
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
    public void compactMemory(){
        memory = manager.compactMemory(memory);
    }
    public ArrayList<Block> getMemory(){
        return memory;
    }
    public void setMemory(ArrayList<Block> inMemory){
        memory = inMemory;
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
    public void admitProcess(Process incoming){
        if(getLargestOpenBlock().size >= Disk.nextJobOnDisk().size){
            memory = storageStrategy.addProcess(memory, Disk.getJobOnDisk());
            Disk.add(incoming);
        }else{
            memory = storageStrategy.addProcess(memory, incoming);
        }
    }
    public void loadCPU(){
        long id = processQueue.poll();
        for(int i=0; i<memory.size(); i++) {
            if (memory.get(i).occupied && memory.get(i).process.timeStamp == id) {
                Block returned = manager.loadIntoCPU(memory.get(i));
                memory.set(i, returned);
                if(returned.size != 0){
                    processQueue.addLast(id);
                }
                manager.coalesceMemory();
                break;
            }
        }
    }
    public static void addToProcessQueue(Long id){
        processQueue.addLast(id);
    }
}
