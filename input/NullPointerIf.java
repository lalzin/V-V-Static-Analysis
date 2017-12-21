package istic.vv;

public class NullPointerIf {

	String var1 = null;
	
	String var2 = "toto";
	
	Integer var3 = null;

	
	// RESULT = 4(ok) + 0(warning) + 1(alert)
		public void test() {
		
			String var4 = "titi";
			var4.length(); // NPE = NO
			
			var4 = null;
			
			if(var2.length()=4) {
				var2 = null;
			}
			
			var2.length(); // NPE = NO
			
			var3 = "tutu";
			var3.toString(); // NPE = NO
			
			var1.length(); // NPE = YES
			
			var2.toString(); // NPE = NO
		}
}
