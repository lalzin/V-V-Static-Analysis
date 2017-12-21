package istic.vv;

public class NullPointerSimple {

	String var1 = null;
	
	String var2 = "toto";
	
	Integer var3 = null;

	// RESULT = 3(ok) + 0(warning) + 2(alert)
	public void test() {
	
		String var4 = "titi";
		var4.length(); // NPE = NO
		
		var4 = null;
		var4.toString(); // NPE = YES
		
		var3 = "tutu";
		var3.toString(); // NPE = NO
		
		var1.length(); // NPE = YES
		
		var2.toString(); // NPE = NO
	}
}
