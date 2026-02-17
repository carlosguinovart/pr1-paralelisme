package p00_Implementations_TM;

import p00_Common_TM.InspectionTrigger;
import p00_Common_TM.Inspector;
import p00_Common_TM.TableManager;
import p00_Common_TM.Writer;

public class LauncherTM {
	
	public static void main (String [] args) {
		final int CELLS = 10;
		final int WRITERS = 20;
		final int INSPECTORS = 10;
		
		int selection;
		TableManager manager = null;
		InspectionTrigger trigger = null;
		Writer [] writers = new Writer[WRITERS];
		Inspector [] inspectors = new Inspector[INSPECTORS];
		
		Thread.setDefaultUncaughtExceptionHandler(
				(t, e)->{
					System.err.println("ABNORMAL SITUATION:");
					System.err.println(e.getMessage());
					e.printStackTrace();
					System.exit(0);
				}
		);
		
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		
		System.out.println("Select a synchronizer: ");
		System.out.println("\t1 for LOCK based");
		System.out.println("\t2 for Compare&Set based");
		System.out.print("> ");
		selection = scanner.nextInt();
		
		if (selection==1) {
			System.out.println("\n-- LOCK --\n");
			manager = new LockBasedTableManager(CELLS, WRITERS);
		}
		else if (selection==2) {
			System.out.println("-- Comapare&Set --\n");
			manager = new CompareAndSetBasedTableManager(CELLS, WRITERS);
		}
		else {
			System.exit(0);
		}
		
		
		System.out.println("Press enter to start");
		System.out.println(" To stop, press enter again");
		scanner.nextLine();
		scanner.nextLine();
		System.out.println();
		
		trigger = new InspectionTrigger (manager);
		trigger.start();
		
		for (int i=0; i<WRITERS; i++) {
			writers[i] = new Writer(i+1, manager);
			writers[i].start();
		}

		for (int i=0; i<INSPECTORS; i++) {
			inspectors[i] = new Inspector(i, manager);
			inspectors[i].start();
		}
		
		// and there we go... 
		
		scanner.nextLine();
		scanner.close();
		Writer.close();
		
		System.out.println("\n\n TERMINATING...\n");
		
		System.exit(0);
	}
	
}
