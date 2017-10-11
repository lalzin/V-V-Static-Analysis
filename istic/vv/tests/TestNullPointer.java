package istic.vv.tests;

public class TestNullPointer {

	public void test(boolean condition1, boolean condition2) {
		String str = null;
		if(condition1 )
		 str = "fou";
		else if ( condition2 )
		 str = "barre";
		 
		int i = str.lenght;//Possible NPE

		String str = null;
		if( condition1 )
		 str = "fou";
		else if ( condition2 )
		 str = "barre";
		if (str != null)
		 int i = str.lenght;//Fixed
	}
	
	
}
