import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Jacob on 4/11/16.
 */
public class Disk {
    public Queue<Process> disk;
    public final int MAXSIZE = 100;

    public Disk(){
        disk = new LinkedList<Process>();
    }
    public void addToDisk(Process process){
        if(disk.size() == MAXSIZE){
            System.out.println("Can't add job to disk. Disk is full.");
        }else{
            disk.add(process);
        }
    }
    public Process nextJobOnDisk(){
        return disk.peek();
    }
    public Process getJobOnDisk(){
        return disk.remove();
    }
}
