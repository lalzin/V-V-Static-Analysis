package istic.vv;

public class SimpleTestNullPointer {

	String toto = "toto1";
	
	public void test(boolean condition1, boolean condition2) {
	
		String titi = "titi1";	
		
		System.out.println(titi);

		if(titi.length()==4) {
			toto = null;
		} else if (titi.length() == 3 ) {
			System.out.println("3");
		} else {
			System.out.println("default");
		}

	}
	
	String getToto() {
		return toto;
	}
}
