/**
 * f. Class that implements a worst-fit memory allocation strategy.
 * g. g. Finding the desired block could be more efficient as in a real system.
 */
import java.util.ArrayList;

public class WorstFitStorage extends StorageStrategy {
    public ArrayList<Block> addProcess(ArrayList<Block> memory, Process incomingProcess){
        Computer.time.stats.totalProcessingTime += incomingProcess.duration;
        Computer.time.stats.totalJobs++;
        boolean available;
        int index;
        if(Memory.firstOpenBlock(incomingProcess) == 9999){
            Disk.add(incomingProcess);
            return memory;
        } else{
            available = true;
            index = Memory.firstOpenBlock(incomingProcess);
        }
        for(int i=index; i < memory.size(); i++){
            if(!memory.get(i).occupied && memory.get(i).size >= incomingProcess.size && memory.get(i).size >= memory.get(index).size){
                available = true;
                index = i;
            }
        }
        if(available){
            Block temp = memory.get(index).addJobToBlock(incomingProcess);
            if(temp != null){
                memory.add(index+1, temp);
            }
            Memory.addToProcessQueue(incomingProcess.timeStamp);
            return memory;
        }else{
            if(Memory.manager instanceof CompactDeny){
                memory = Memory.manager.compactMemory(memory);
            }
            Disk.add(incomingProcess);
            return memory;
        }
    }
}
