/**
 * f. Abstract class that is for the memory manager involved in compaction and coalescence.
 */
import java.util.ArrayList;

abstract public class MemoryManager {

    public MemoryManager(){}

    public ArrayList<Block> compactMemory(ArrayList<Block> memory){
        int freeSpace = 0;
        int i = 0;
        while(i < memory.size()){
            if(!memory.get(i).occupied){
                freeSpace += memory.get(i).size;
                memory.remove(i);
                continue;
            }
            i++;
        }
        memory.add(new Block(freeSpace));
        return memory;
    }
    public static void coalesceMemory(){
        for(int i=0; i<(Computer.memory.size()-1); i++){
            if(Computer.memory.get(i).occupied){
                continue;
            }else if(!Computer.memory.get(i+1).occupied){
                Computer.memory.set(i, new Block(Computer.memory.get(i).size + Computer.memory.get(i+1).size));
                Computer.memory.remove(i+1);
            }
        }
    }
    //f. Method that physically loads a job into the "CPU"
    public Block loadIntoCPU(Block block){
        if(block.process.duration > 5){
            block.process.totalWaitTime += Computer.time.getCurrentTime() - block.process.startWaitTime;
            block.process.duration -= 5;
            Computer.time.incrementCurrentTime();
            block.process.setStartWaitTime(Computer.time.getCurrentTime());
        }else{
            Computer.time.stats.totalWaitTime += (Computer.time.getCurrentTime() - block.process.startWaitTime) + block.process.totalWaitTime;
            Computer.time.incrementCurrentTime(block.process.duration);
            block.removeJob();
        }
        return block;
    }
}