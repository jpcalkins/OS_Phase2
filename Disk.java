/**
 * f. Class that mimics a disk for storage.
 */
import java.util.LinkedList;

public class Disk {
    public static LinkedList<Process> disk;
    public static final int MAXSIZE = 100;

    public Disk(){
        disk = new LinkedList<Process>();
    }
    public static void add(Process process){
        if(disk.size() < MAXSIZE){
            disk.add(process);
        }
    }
    public static Process nextJobOnDisk(){
        return disk.peek();
    }
    public static Process getJobOnDisk(){
        return disk.remove();
    }
    public static LinkedList<Process> getDisk(){
        return disk;
    }
}
