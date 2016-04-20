/**
 * f. Class that implements a best-fit memory allocation strategy.
 * g. Finding the desired block could be more efficient as in a real system.
 */
import java.util.ArrayList;

public class BestFitStorage extends StorageStrategy {
    public ArrayList<Block> addProcess(ArrayList<Block> memory, Process incomingProcess){
        Computer.time.stats.totalProcessingTime += incomingProcess.duration;
        Computer.time.stats.totalJobs++;
        boolean available;
        int index;
        //This is a bit of a hack to help decide if there is an open block in memory otherwise just send the process to disk.
        if(Memory.firstOpenBlock(incomingProcess) == 9999){
            Disk.add(incomingProcess);
            return memory;
        } else{
            available = true;
            index = Memory.firstOpenBlock(incomingProcess);
        }
        //Actually searching for the best fit block.
        for(int i=index; i<memory.size(); i++){
            if(!memory.get(i).occupied && memory.get(i).size >= incomingProcess.size && memory.get(i).size <= memory.get(index).size){
                available = true;
                index = i;
                if(memory.get(index).size == incomingProcess.size){
                    break;
                }
            }
        }
        //Adds process to memory
        if(available){
            Block temp = memory.get(index).addJobToBlock(incomingProcess);
            if(temp != null){
                memory.add(index+1, temp);
            }
            Memory.addToProcessQueue(incomingProcess.timeStamp);
            return memory;
        }
        //Puts process on disk and compacts memory if this is when the manager dictates it.
        else{
            if(Memory.manager instanceof CompactDeny){
                memory = Memory.manager.compactMemory(memory);
            }
            Disk.add(incomingProcess);
            return memory;
        }
    }
}
