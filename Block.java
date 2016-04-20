/**
 * f. Represents a block in memory and the process that may be occupying that block.
 */
public class Block implements Comparable<Block> {

    public int size;
    public boolean occupied;
    public Process process;

    //f. Constructs an empty block class with a size passed in the arguments.
    public Block(int size){
        this.size = size;
        this.occupied = false;
    }
    //f. Removes a process from a block, making it empty.
    public void removeJob(){
        this.occupied = false;
        this.process = null;
        MemoryManager.coalesceMemory();
    }
    //f. Adds a process to a block, making it occupied.
    public Block addJobToBlock(Process process){
        this.occupied = true;
        this.process = process;
        if(process.size < this.size){
            int prevSize = this.size;
            this.size = process.size;
            //returns new block of memory that is the remainder from added process
            return new Block(prevSize - process.size);
        }else{
            //process fit perfectly in block so no creation of new block necessary.
            return null;
        }
    }
    //f. comparable constructor so that I may compare block objects using <, >, =. This is necessary to keep an ordered list for best and worst fit allocation.
    public int compareTo(Block other){
        return Integer.compare(this.size, other.size);
    }
}
