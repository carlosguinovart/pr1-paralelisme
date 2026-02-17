package p01_Implementations_BBB;

import p01_Common_BBB.Bang;
import p01_Common_BBB.Bing;
import p01_Common_BBB.Boom;
import p01_Common_BBB.BBBSynchronizer;

public class LauncherBBB {

	public static void main (String [] args) {
		
		int INSTANCES = 20;
		
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		int selection;
		BBBSynchronizer synchro = null;
		Bing [] bings = new Bing[INSTANCES];
		Bang [] bangs = new Bang[INSTANCES];
		Boom [] booms = new Boom[INSTANCES];
		
		
		System.out.println("Select a synchronizer: ");
		System.out.println("\t1 for IMPLICIT-lock-based");
		System.out.println("\t2 for Compare&Set-based");
		System.out.print("> ");
		selection = scanner.nextInt();
		
		if (selection==1) {
			synchro = new ImpLockBasedSynchronizer();
			System.out.println("\nLaunching IMPLICIT-LOCK-based solution of BING-BANG-BOOM with n="+INSTANCES+"\n");
		}
		else if (selection==2) {
			synchro = new CSBasedSynchronizer();
			System.out.println("\nLaunching Comapare&Set-based solution of BING-BANG-BOOM with n="+INSTANCES+"\n");
		}
		else {
			System.exit(0);
		}
		
		System.out.println("Press enter to start");
		System.out.println(" To stop the frenzy, press enter again");
		scanner.nextLine();
		scanner.nextLine();
		System.out.println();
		
		for (int i=0; i<INSTANCES; i++) {
			bings[i] = new Bing(i, synchro);
			bangs[i] = new Bang(i, synchro);
			booms[i] = new Boom(i, synchro);
		}
		
		for (int i=0; i<INSTANCES; i++) {
			booms[i].start();
		}
		
		for (int i=0; i<INSTANCES; i++) {
			bangs[i].start();
		}
		
		for (int i=0; i<INSTANCES; i++) {
			bings[i].start();
		}
		
		scanner.nextLine();
		scanner.close();
		
		if (selection==1)
			System.out.println("\n\nIMPLICIT-LOCK-based TERMINATING... (last lines are irrelevant) \n");
		else 
			System.out.println("\n\nCompare&Set-based TERMINATING... (last lines are irrelevant) \n");
		
		
		System.exit(0);
		
	}
	
}
