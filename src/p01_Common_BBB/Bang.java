package p01_Common_BBB;

public class Bang extends Thread{
	
	private BBBSynchronizer sync;
	private int id;
	
	public Bang (int id, BBBSynchronizer sync) {
		this.sync = sync;
		this.id = id;
	}
	
	public void run () {
		while (true) {
			sync.enterExclusionForBANG(id);
			sync.writeString("BanG("+id+")-");
			sync.exitExclusionForBANG(id);
		}
	}
}
