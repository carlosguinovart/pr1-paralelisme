package p00_Implementations_TM;

import java.util.concurrent.atomic.AtomicBoolean;

import p00_Common_TM.TableManager;

public class CompareAndSetBasedTableManager extends TableManager {

	private AtomicBoolean abLock;
	/* You do not need more variable attributes than this and the inherited ones. Constants are welcome */
	
	public CompareAndSetBasedTableManager(int numCells, int numWriters) {
		super(numCells, numWriters);
		/* COMPLETE */
		abLock = new AtomicBoolean(false);
		
	}

	/* COMPLETE: Implement the abstract methods */
	/* Do not use Thread.yield or Thread.onSpinWait. use fixedDelay(1) instead
	
	/* private helper methods are welcome. Write them after the implementation of the abstract ones */
	

	private void fixedDelay (int ms) {
		try {Thread.sleep(ms);} catch (Exception e) {}
	}

	@Override
	public boolean blockCell(int numCell, int writerId) {
		// TODO Auto-generated method stub
		while(true) {
			while(!abLock.compareAndSet(false, true)) {
				//fixedDelay(1);
				return false;
			}
			if (inspectionTime) {
				abLock.set(false);
				return false;
			}

            if ( (numCell & 1) != (writerId & 1) ) {
            	abLock.set(false);
            	return false;
            }

            if (blockers[numCell] > 0) {
            	abLock.set(false);
            	return false;
            }

            if (Math.abs(blockers[numCell]) == writerId) {
            	abLock.set(false);
            	return false;
            }

            blockers[numCell] = writerId;
            abLock.set(false);
            return true;
		}
		
	}

	@Override
	public void unBlockCell(int numCell, int writerId) {
		// TODO Auto-generated method stub
		while(!abLock.compareAndSet(false, true)) {
			fixedDelay(1);
		}
		if (blockers[numCell] == writerId) {
            blockers[numCell] = -writerId;
        }
		abLock.set(false);
		
	}

	@Override
	public void startInspection(int inspectorId) {
		// TODO Auto-generated method stub
		while(true) {
			while (!abLock.compareAndSet(false, true)) {
				fixedDelay(1);
			}
			if(inspectionTime && ((inspectorId & 1) == currentInspectorParity) && checkAllUnblocked()){
				return;
			}
	        abLock.set(false);
		}
	}

	@Override
	public void endInspection() {
		// TODO Auto-generated method stub
		
		inspectionTime = false;
        currentInspectorParity = (currentInspectorParity + 1) % 2;
        abLock.set(false);
	}
	
	private boolean checkAllUnblocked() {
		for(int i = 0; i<blockers.length; i++) {
			if(blockers[i]>0) {
				return false;
			}
		}
		return true;
	}
	
}
