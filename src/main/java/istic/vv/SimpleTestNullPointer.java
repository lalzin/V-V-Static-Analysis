package istic.vv;

public class SimpleTestNullPointer {

	String toto = "toto1";
	
	public void test(boolean condition1, boolean condition2) {
	
		String titi = "titi1";	
		titi = null;
		toto = null; 
		
		System.out.println(titi);

		System.out.println(toto);

	}
	
	String getToto() {
		return toto;
	}
}
