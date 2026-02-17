package p00_Common_TM;

public class Inspector extends Thread {

	private TableManager manager;
	private int id;
	
	public Inspector (int id, TableManager manager) {
		this.id = id;
		this.manager = manager;
	}
	
	public void run () {
		while (true) {
			manager.startInspection(id);
			manager.inspect(id);
			manager.endInspection();
		}
	}
	
}

