/**
 * f. Class that implements a worst-fit memory allocation strategy.
 * g. Sorts memory blocks to make finding the proper open block easier.
 */
import java.util.Collections;

public class WorstFitStorage extends StorageStrategy {
    //f. Sorts memory into ascending order based upon block size, then traverses the list backwards to find the largest, open block to place the process into.
    public void addProcess(Process incomingProcess){
        Collections.sort(memory);
        for(int i=(memory.size()-1); i>=0; i--){
            if(memory.get(i).size >= incomingProcess.size && !memory.get(i).occupied){
                Block temp = memory.get(i).addJobToBlock(incomingProcess);
                if(temp != null){
                    memory.add(i+1, temp);
                }
                processQueue.add(incomingProcess.timeStamp);
                return;
            }
        }
        readyQueue.add(incomingProcess);
    }
}
