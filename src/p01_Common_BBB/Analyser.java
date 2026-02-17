package p01_Common_BBB;

public class Analyser {
	private static StringBuffer sbuffer;
	private static java.util.Random alea = new java.util.Random();
	
	public static void writeString (String string ) {
		int n;
		sbuffer = new StringBuffer();
		for (int i=0; i<string.length(); i++) {
			System.out.print(string.charAt(i));
			n = alea.nextInt(100);
			if (n>=95) try {Thread.sleep(1);} catch(Exception ex) {}
			else if (n>=75) Thread.yield();
			sbuffer.append(string.charAt(i));
		}
		parse(sbuffer.toString());
		
	}
	
	private static void parse (String s) {
		int id;
		int nature;
		try {
			id = getId(s);
			if (s.substring(0, 4).equals("BING")) nature = nature_BING;
			else if (s.substring(0, 4).equals("BanG")) nature = nature_BANG;
			else if (s.substring(0, 4).equals("BOOM")) nature = nature_BOOM;
			else if (s.substring(0, 4).equals("boom")) nature = nature_BOOM;
			else throw new Exception();
			
			switch (expected) {
				case expected_BINGBANG: 
					if (nature==nature_BING) {
						countBings++;
						
						if (countBings==1) 
							firstBingId = id;
						
						if (countBings==3) {
							requiredBangs = 0;
							expected = expected_BOOM;
						}
						else 
							expected = expected_BINGBANG;
					}
					else if (nature==nature_BANG) {
						// this is a first bang
						firstBangId = id;
						countBangs = 1;
						requiredBangs = decideBangs(countBings);
						if (countBangs == requiredBangs)
							expected = expected_BOOM;
						else 
							expected = expected_BANG;
					}
					else {
						// unexpected BOOM
						System.err.println("ERROR "+s+" not expected Now");
						System.exit(1);
					}
					break;
					
				case expected_BING: 
					// a bing is never expected "per se"
					break;
					
				case expected_BANG:
					if (nature!= nature_BANG) {
						// only bangs expected here
						System.err.println("ERROR "+s+" not expected Now");
						System.exit(1);
					}
					countBangs++;
					if (countBangs == requiredBangs)
						expected = expected_BOOM;
					else 
						expected = expected_BANG;
					break;
					
				case expected_BOOM: 
					if (nature!=nature_BOOM) {
						System.err.println("ERROR "+s+" not expected Now");
						System.exit(1);
					}
					
					if (countBings>0 && id!=firstBingId) {
						System.err.println("ERROR "+id+" incorrect id for BOOM/boom. Expected "+firstBingId);
						System.exit(1);
					}
					if (countBings==0 && id%2!=firstBangId%2) {
						System.err.println("ERROR "+id+" incorrect id parity for boom. Expected parity like parity of "+firstBangId);
						System.exit(1);
					}
					
					boolean upperCase = Character.isUpperCase(s.charAt(0));
					boolean expectedUpperCase = countBings%2==0;
					if (upperCase!=expectedUpperCase) {
						System.err.println("ERROR "+s+" BAD CASE");
						System.exit(1);
					}
					
					countBings = 0;
					countBangs = 0;
					requiredBangs = 0;
					expected = expected_BINGBANG;
					break;
			}
		}
		catch (Exception e) {
			System.err.println("bad formed string: "+s);
			System.exit(1);
		}
	}
	
	private static int getId(String s) {
		int start = s.indexOf('(');
		int end = s.indexOf(')');
		return Integer.parseInt(s.substring(start+1, end));
	}
	
	private static final int nature_BING = 1;
	private static final int nature_BANG = 2;
	private static final int nature_BOOM = 3;
	
	private static final int expected_BING = 10;
	private static final int expected_BANG = 20;
	private static final int expected_BOOM = 30;
	private static final int expected_BINGBANG = 40;
	
	
	private static volatile int expected = expected_BINGBANG;
	private static volatile int countBings = 0;
	private static volatile int countBangs = 0;
	private static volatile int requiredBangs;
	
	private static volatile int firstBingId = -1;
	private static volatile int firstBangId = -1;
	
	private static int decideBangs (int bings) {
		if (bings==0) return 1;
		else if (bings == 1) return 2;
		else if (bings == 2) return 3;
		else return 0;
	}
	
}
