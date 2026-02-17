package p00_Implementations_TM;

import java.util.concurrent.locks.*;

import p00_Common_TM.TableManager;

public class LockBasedTableManager extends TableManager {

	
	private Lock lock = new ReentrantLock(true);
	
	/* You do not need more attributes than this and the inherited ones */
	
	public LockBasedTableManager(int numCells, int numWriters) {
		super(numCells, numWriters);
	}

	@Override
	public boolean blockCell(int numCell, int writerId) {
		// TODO Auto-generated method stub
		
		lock.lock();
		if( inspectionTime == true || blockers[numCell] > 0 || Math.abs(blockers[numCell]) == writerId) {
			return false;
		}
		blockers[numCell] = writerId;
		lock.unlock();
		return true;
		
	}

	@Override
	public void unBlockCell(int numCell, int writerId) {
		// TODO Auto-generated method stub
		lock.lock();
		if(blockers[numCell] == writerId) {
			blockers[numCell] =-writerId;
		}
		lock.unlock();
		
	}

	@Override
	public void startInspection(int inspectorId) {
		// TODO Auto-generated method stub
		
		while (true) {
			lock.lock();
			if(inspectionTime && checkAllUnblocked() )
				inspectorActive=true;
			lock.unlock();
		}
		
	}

	@Override
	public void endInspection() {
		// TODO Auto-generated method stub
		lock.lock();
		inspectionTime = false;
		inspectorActive = false;
		currentInspectorParity = (currentInspectorParity + 1) % 2;
		lock.unlock();
		
	}

	/* COMPLETE: Implement the abstract methods */
	
	/* private helper methods are welcome. Write them after the implementation of the abstract ones */
	private boolean checkAllUnblocked() {
		for(int i = 0; i<=blockers.length; i++) {
			if(blockers[i]<0) {
				return false;
			}
		}
		return true;
	}
	
	private boolean inspectorActive() {
		
	}
}
