import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Jacob on 4/11/16.
 */
abstract public class MemoryManager {

    abstract public ArrayList<Block> admitProcess(ArrayList<Block> memory, Process incoming);

    public MemoryManager(){}

    public ArrayList<Block> compactMemory(ArrayList<Block> memory){
        int freeSpace = 0;
        int i = 0;
        while(i < memory.size()){
            if(!memory.get(i).occupied){
                freeSpace += memory.get(i).size;
                memory.remove(i);
            }
            i++;
        }
        memory.add(new Block(freeSpace));
        return memory;
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
    public Block loadIntoCPU(Block block){
        if(block.process.duration > 5){
            block.process.totalWaitTime += Computer.time.getCurrentTime() - block.process.startWaitTime;
            block.process.duration -= 5;
            if(block.process.duration == 0){
                block.removeJob();
                return block;
            }
            Computer.time.incrementCurrentTime();
            return block;
        }else{
            Computer.time.stats.totalWaitTime += (Computer.time.getCurrentTime() - block.process.startWaitTime) + block.process.totalWaitTime;
            Computer.time.incrementCurrentTime(block.process.duration);
            block.removeJob();
            return block;
        }
    }
}