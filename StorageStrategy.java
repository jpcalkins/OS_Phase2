/**
 * e. Time is my custom CPU clock, memory is an array of blocks that can grow as necessary, readyQueue is a list of jobs not yet in memory, processQueue is a list of process timestamps that allows me to keep track of which processes arrived when.
 * f. Class that memory allocation strategies inherit from. Lays out most of the basic functionality of the simulation.
 */
import java.util.*;

abstract public class StorageStrategy {

    abstract public ArrayList<Block> addProcess(ArrayList<Block> memory, Process incomingProcess);

    public StorageStrategy(){}
}
