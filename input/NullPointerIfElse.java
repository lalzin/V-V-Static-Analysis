package istic.vv;

public class NullPointerIfElse{

	String var1 = null;
	
	String var2 = "toto";
	
	Integer var3 = null;

	
	// RESULT = 3(ok) + 1(warning) + 1(alert)
		public void test() {
		
			String var4 = "titi";
			var4.length(); // NPE = NO
			
			var4 = null;
			if(var2.length()=4) {
				var4.toString(); // NPE = YES
			}
			else {
				var4=null;
			}
			
			var3 = "tutu";
			var3.toString(); // NPE = NO
			
			var1.length(); // NPE = YES
			
			var2.toString(); // NPE = NO
		}


}
