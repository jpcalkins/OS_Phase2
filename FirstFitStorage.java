/**
 * f. Class that implements a first-fit memory allocation strategy.
 */
public class FirstFitStorage extends StorageStrategy {
    //f. Traverses the memory list in order to find the first open block.
    public void addProcess(Process incomingProcess){
        Computer.time.stats.totalProcessingTime += incomingProcess.duration;
        Computer.time.stats.totalJobs++;
        for(int i=0; i<Computer.memory.size(); i++){
            if(Computer.memory.get(i).size >= incomingProcess.size && !Computer.memory.get(i).occupied){
                Block temp = Computer.memory.get(i).addJobToBlock(incomingProcess);
                if(temp != null){
                    Computer.memory.add(i+1, temp);
                }
                processQueue.add(incomingProcess.timeStamp);
                return;
            }
        }
        readyQueue.add(incomingProcess);
    }
}
