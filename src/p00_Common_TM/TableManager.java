package p00_Common_TM;

import static p00_Common_TM.Numbers.numbers;

import java.util.Arrays;


// This class cannot be modified in any way
public abstract class TableManager {
	
	public final int numCells; 
	public final int numWriters;
	
	protected int [] blockers;  // blockers[a] == 0 => no writer has blocked cells[a]
								// blockers[a] == i => writer i is blocking cells [a]
								// blockers[a] == -i => writer i is the last writer that blocked cells[a]
	                            //                      but it is no longer blocking it
	
	protected StringBuilder [] storageCells;
	
	protected volatile boolean inspectionTime = false;  // set by the inspectionTrigger 
	                                               		// and reset by method endInspection
	protected volatile int currentInspectorParity = 0;
	
	private volatile boolean beingInspected = false;
	
	public TableManager (int numCells, int numWriters) {
		this.numCells = numCells;
		this.numWriters = numWriters;
		this.blockers = new int[numCells];
		this.storageCells = new StringBuilder[numCells];
		for (int i=0; i<blockers.length; i++) {
			blockers[i]=0;
		}
	}
	
	// Writers use this method to block cells 
	// If this method returns true, cell numCell is blocked by writer thread with id writerId
	// if it returns false, writer thread with id writerId is not allowed to modify the contents of cell numCell
	// A writer CANNOT block a cell if...
	// - the cell is currently blocked by another writer
	// - this writer was the last writer that blocked it
	// - it has not the right parity 
	// - it is inspection time 
	public abstract boolean blockCell(int numCell, int writerId);
	
	// Writers use this method to block cells 
	// This method unblocks cell numCell. WriterId becomes the id of the writer that
	// last modified the cell. 
	public abstract void  unBlockCell (int numCell, int writerId);
		
	
	// Inspectors use this method to request permission to access the contents of the table. 
	// If this method returns, the inspector with id inspectorId is authorized to inspect 
	// the cells. 
	// Authorization is granted if...
	// - inspectionTime has been set to true
	// - all cells must be unblocked and...
	// - the id of the inspector has the right parity
	public abstract void startInspection (int inspectorId);
	
	// Inspectors use this method to notify that they have ended accessing the table 
	// this method must
	// set inspection time to false
	// set currentInspectorParity to (currentInspectorParity+1)%2;
	public abstract void endInspection ();
	
	
	// -----------------------------
	
	public void write (int numCell, int writerId, String message) {
		
		if (this.beingInspected) 
			throw new RuntimeException("Writer "+writerId+" is writing while inspection is underway");
		
		fixedDelay(10);
		
		if (blockers[numCell]!=writerId) 
			throw new RuntimeException("Writer "+writerId+" is writing cell "+numCell+" but it is not blocked. Blocker is "+blockers[numCell]); 
		
		if (storageCells[numCell]!=null && writerId==numberStringToInt(storageCells[numCell].toString()) )
			throw new RuntimeException("Writer "+writerId+" is writing cell "+numCell+" when it is the last writer of that cell"); 
		
		if (numCell%2!=writerId%2) 
			throw new RuntimeException("Writer "+writerId+" is writing cell "+numCell+" but parity does not allow this");
		
		// before writing make sure previous contents make sense (check prior corruption)
		if (storageCells[numCell]!=null && numberStringToInt(storageCells[numCell].toString())<0)
			throw new RuntimeException("Corrupted contents in cell ("+numCell+") "+storageCells[numCell]);
		
		storageCells[numCell] = new StringBuilder(message.length());
		for (int i = 0; i<message.length(); i++) {
			if (blockers[numCell]!=writerId) throw new RuntimeException("Writer "+writerId+" is writing cell "+numCell+" but it is not the blocker. Blocker is "+blockers[numCell]); 
			storageCells[numCell].append(message.charAt(i));
			someDelay();
		}
	}
	
	public void inspect (int inspectorId) {
		System.out.println("INSPECTOR "+inspectorId+" Inspects the contents of the cells:\n ");
		
		if (!inspectionTime) 
			throw new RuntimeException("Inspector "+inspectorId+" working when inspectionTime is false");
		
		if (beingInspected) 
			throw new RuntimeException("Inspector "+inspectorId+" CLASHED with another inspector!!!");
		
		beingInspected = true;
		
		if (inspectorId%2 != this.currentInspectorParity)
			throw new RuntimeException("Inspector "+inspectorId+" is inspecting with the wrong parity");
		
		for (int blocker : blockers) {
			if (blocker>0) 
				throw new RuntimeException("Inspector "+inspectorId+" is inspecting with cells still blocked");
		}
		
		for (int i=0; i<storageCells.length; i++) {
			System.out.print("Cell["+i+"] => ");
			if (storageCells[i]==null) {
				System.out.println("empty");
			}
			else {
				for (int c = 0; c<storageCells[i].length(); c++) {
					System.out.print(storageCells[i].charAt(c));
					fixedDelay(1);
				}
				System.out.println();
				if (numberStringToInt(storageCells[i].toString())%2 != i%2) 
					throw new RuntimeException(storageCells[i].toString()+ "Bad content for cell (parity)"+i);
			}
			fixedDelay(2);
		}
		System.out.println("---------------------\n");
		beingInspected=false;
	}
	
	private void someDelay() {
		if (Math.random()>=0.5) 
			Thread.yield();
		else 
			try {Thread.sleep(1);} catch (Exception e) {}
	}
	
	private void fixedDelay (int ms) {
		try {Thread.sleep(ms);} catch (Exception e) {}
	}
	
	private int numberStringToInt (String s) {
		return Arrays.asList(numbers).indexOf(s);
	}

}
