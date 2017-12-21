package istic.vv;

public class NullPointer2Methods {

	String var1 = null;
	
	String var2 = "toto";
	
	Integer var3 = null;

	
	// RESULT =0(warning) + 2(alert)
	
		public void test() {
		
			String var4 = "titi";
			var4.length(); // NPE = NO
			
			var4 = null;
			
			var3 = "tutu";
			var3.toString(); // NPE = NO
			
			var1.length(); // NPE = YES
			
			var2.toString(); // NPE = NO
		}
		
		public void test2() {
			
			
			var2.length();// NPE = NO
			
			var3.toString(); // NPE = YES
			
			
		}


}
