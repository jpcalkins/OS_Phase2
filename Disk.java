import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Jacob on 4/11/16.
 */
public class Disk {
    public static Queue<Process> disk;
    public static final int MAXSIZE = 100;

    public Disk(){
        disk = new LinkedList<Process>();
    }
    public static void add(Process process){
        if(disk.size() == MAXSIZE){
            System.out.println("Can't add job to disk. Disk is full.");
        }else{
            disk.add(process);
        }
    }
    public static Process nextJobOnDisk(){
        return disk.peek();
    }
    public static Process getJobOnDisk(){
        return disk.remove();
    }
}
