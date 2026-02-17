package p01_Common_BBB;

public class Bing extends Thread {
	
	private BBBSynchronizer sync;
	private int id;
	
	public Bing (int id, BBBSynchronizer sync) {
		this.sync = sync;
		this.id = id;
	}
	
	public void run () {
		while (true) {
			sync.enterExclusionForBING(id);
			sync.writeString("BING("+id+")-");
			sync.exitExclusionForBING(id);
		}
	}

}
