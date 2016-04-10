/**
 * f. Class that implements a best-fit memory allocation strategy.
 * g. Sorts memory blocks to make finding the proper open block easier.
 */
import java.util.Collections;

public class BestFitStorage extends StorageStrategy {
    //f. Sorts memory into ascending order based on block size, then traverses the list to find the first open block, giving me the smallest possible block to place the process.
    public void addProcess(Process incomingProcess){
        Collections.sort(memory);
        for(int i=0; i<memory.size(); i++){
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
