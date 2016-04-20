/**
 * Created by Jacob on 4/11/16.
 */
public class Computer {

    public static Timer time;
    public static Memory memory;
    public Process nextProcess;
    public static Disk disk = new Disk();

    public Computer(StorageStrategy newStorage, MemoryManager newManager){
        time = new Timer();
        memory = new Memory(newManager, newStorage);
        nextProcess = Process.randJob(time.getPreviousTime());
    }
    public void startComputer(){
        Process firstProcess = Process.randJob(0);
        while(time.getCurrentTime() < firstProcess.toa){
            time.incrementCurrentTime();
        }
        memory.admitProcess(firstProcess);
        memory.loadCPU();
        while(true){
            generateJobs();
            if(memory.processQueue.peek() != null){
                memory.loadCPU();
            }
        }
    }
    //Creates random jobs and sends them to memory
    public void generateJobs(){
        while(nextProcess.toa <= time.getCurrentTime()){
            memory.admitProcess(nextProcess);
            nextProcess = Process.randJob(time.getPreviousTime());
        }
    }
}
