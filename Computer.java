import java.util.ArrayList;

/**
 * Created by Jacob on 4/11/16.
 */
public class Computer {

    public static Timer time = new Timer();
    public static StorageStrategy storageStrategy;
    public static MemoryManager memoryManager;
    public static ArrayList<Block> memory;

    public Computer(StorageStrategy newStorage, MemoryManager newManager){
        storageStrategy = newStorage;
        memoryManager = newManager;
        memory = new ArrayList<Block>();
        memory.add(new Block(1800));
    }
    public void startComputer(){
        Process firstProcess = Process.randJob(0);
        storageStrategy.addProcess(firstProcess);
        while(time.getCurrentTime() < memory.get(0).process.toa){
            Computer.time.incrementCurrentTime();
        }
        memoryManager.loadIntoCPU(memory.get(0));

    }
}
