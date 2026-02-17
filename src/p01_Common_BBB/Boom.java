package p01_Common_BBB;

public class Boom extends Thread
{
	private BBBSynchronizer sync;
	private int id;
	
	public Boom (int id, BBBSynchronizer sync) {
		this.sync = sync;
		this.id = id;
	}
	
	public void run () {
		while (true) {
			if (sync.enterExclusionForBOOM(id))
				sync.writeString("BOOM("+id+")\n");
			else 
				sync.writeString("boom("+id+")\n");
			sync.exitExclusionForBOOM(id);
		}
	}
}
