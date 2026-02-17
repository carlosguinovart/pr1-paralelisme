package p01_Common_BBB;

public interface BBBSynchronizer {
	
	public void enterExclusionForBING (int id);
	public void exitExclusionForBING (int id);
	
	public void enterExclusionForBANG (int id);
	public void exitExclusionForBANG (int id);
	
	public boolean enterExclusionForBOOM (int id); // true for UPPERCASE; false for lowercase
	public void exitExclusionForBOOM (int id);
	
	public default void writeString (String s) {
		Analyser.writeString (s);
	}
}

