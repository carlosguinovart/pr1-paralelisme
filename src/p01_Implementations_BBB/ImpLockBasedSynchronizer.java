package p01_Implementations_BBB;

import java.util.Random;

import p01_Common_BBB.BBBSynchronizer;

public class ImpLockBasedSynchronizer implements BBBSynchronizer {

	private Random alea;
	private volatile int requiredBings;
	private volatile int requiredBangs;
	
	private volatile int state;
	
	/* COMPLETE */
	
	public ImpLockBasedSynchronizer () {
		alea = new Random();
		requiredBings = 2;
		requiredBangs = 3;
		/* COMPLETE IF NEEDED */
	}
	
	/* COMPLETE: implement the interface */

}
