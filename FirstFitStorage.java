/**
 * f. Class that implements a first-fit memory allocation strategy.
 */
import java.util.ArrayList;

public class FirstFitStorage extends StorageStrategy {
    //f. Traverses the memory list in order to find the first open block.
    public ArrayList<Block> addProcess(ArrayList<Block> memory, Process incomingProcess){
        Computer.time.stats.totalProcessingTime += incomingProcess.duration;
        Computer.time.stats.totalJobs++;
        for(int i=0; i<memory.size(); i++){
            if(memory.get(i).size >= incomingProcess.size && !memory.get(i).occupied){
                Block temp = memory.get(i).addJobToBlock(incomingProcess);
                if(temp != null){
                    memory.add(i+1, temp);
                }
                Memory.addToProcessQueue(incomingProcess.timeStamp);
                return memory;
            }
        }
        if(Memory.manager instanceof CompactDeny){
            memory = Memory.manager.compactMemory(memory);
        }
        Disk.add(incomingProcess);
        return memory;
    }
}
