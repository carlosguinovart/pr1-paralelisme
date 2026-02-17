package p00_Implementations_TM;

import java.util.concurrent.atomic.AtomicBoolean;

import p00_Common_TM.TableManager;

public class CompareAndSetBasedTableManager extends TableManager {

	private AtomicBoolean abLock;
	/* You do not need more variable attributes than this and the inherited ones. Constants are welcome */
	
	public CompareAndSetBasedTableManager(int numCells, int numWriters) {
		super(numCells, numWriters);
		/* COMPLETE */
		
	}

	/* COMPLETE: Implement the abstract methods */
	/* Do not use Thread.yield or Thread.onSpinWait. use fixedDelay(1) instead
	
	/* private helper methods are welcome. Write them after the implementation of the abstract ones */
	

	private void fixedDelay (int ms) {
		try {Thread.sleep(ms);} catch (Exception e) {}
	}
	
}
