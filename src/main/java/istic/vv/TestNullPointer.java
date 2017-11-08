package istic.vv;

public class TestNullPointer {

	public void test(boolean condition1, boolean condition2) {
		String str;
		str = null;
		if(condition1 ) {
			 str = "fou";
		}
		else if ( condition2 ) {
			 str = "barre";
		}
		int i = str.length();//Possible NPE
		str.toString();
		String str2 = null;
		if( condition1 ) {
			 str2 = "fou";
		}
		else if ( condition2 ) {
			 str2 = "barre";
		}
		if (str2 != null) {
			int i2 = str2.length();//Fixed
		}
	}

	
}
