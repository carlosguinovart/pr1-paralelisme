package p00_Common_TM;

public class InspectionTrigger extends Thread {

	private TableManager manager;
	
	public InspectionTrigger (TableManager manager) {
		this.manager = manager;
	}
	
	public void run () {
		while (true) {
			try {Thread.sleep(100);} catch (Exception e) {}
			manager.inspectionTime=true;
		}
	}

}
