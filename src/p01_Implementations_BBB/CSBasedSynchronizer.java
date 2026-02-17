package p01_Implementations_BBB;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import p01_Common_BBB.BBBSynchronizer;

public class CSBasedSynchronizer implements BBBSynchronizer {

	private Random alea;
	private volatile int requiredBings;
	private volatile int requiredBangs;
	
	
	/* COMPLETE */
	
	public CSBasedSynchronizer () {
		alea = new Random();
		requiredBings = 2;
		requiredBangs = 3;
		
		/* COMPLETE IF NEEDED */
	}
	
	
	/* COMPLETE: implement the interface */

}
