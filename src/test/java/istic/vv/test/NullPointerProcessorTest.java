package istic.vv.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import istic.vv.CycloProcessor;
import istic.vv.DataVar;
import istic.vv.DataVar.STATUS;
import istic.vv.ScannerVandV;
import spoon.Launcher;
import spoon.reflect.CtModel;

public class NullPointerProcessorTest {

	@Test
	public void testNPEIf () {

    	ArrayList<DataVar> listResults = ScannerVandV.nullPointerScanner("./input/NullPointerIf.java");

    	ArrayList<DataVar> listExpectedResult = new ArrayList<DataVar>();
    	
    	
    	 /*OK for [var4] with value ["titi"] at line 16
			WARNING for [var4] with value [null] at line 20
			OK for [var3] with value ["tutu"] at line 27
			ALERT for [var1] with value [null] at line 29
			OK for [var2] with value ["toto"] at line 31*/
    	
    	DataVar data1 = new DataVar("\"titi\"", STATUS.OK, 16, "var4");
    	DataVar data2 = new DataVar("null", STATUS.WARNING, 20, "var4");
    	DataVar data3 = new DataVar("\"tutu\"", STATUS.OK, 27, "var3");
    	DataVar data4 = new DataVar("null", STATUS.ALERT, 29, "var1");
    	DataVar data5 = new DataVar("\"toto\"", STATUS.OK, 31, "var2");

    	listExpectedResult.add(data1);
    	listExpectedResult.add(data2);
    	listExpectedResult.add(data3);
    	listExpectedResult.add(data4);
    	listExpectedResult.add(data5);
    	
    	assertTrue(ScannerVandV.equalsResultNPE(listResults,listExpectedResult));
		
	}
	
	@Test
	public void testNPESimple () {

    	ArrayList<DataVar> listResults = ScannerVandV.nullPointerScanner("./input/NullPointerSimple.java");

    	ArrayList<DataVar> listExpectedResult = new ArrayList<DataVar>();
    	
    	
    	 /*OK for [var4] with value ["titi"] at line 15
    	 ALERT for [var4] with value [null] at line 18
    	 OK for [var3] with value ["tutu"] at line 21
    	 ALERT for [var1] with value [null] at line 23
    	 OK for [var2] with value ["toto"] at line 25*/
    	
    	DataVar data1 = new DataVar("\"titi\"", STATUS.OK, 15, "var4");
    	DataVar data2 = new DataVar("null", STATUS.ALERT, 18, "var4");
    	DataVar data3 = new DataVar("\"tutu\"", STATUS.OK, 21, "var3");
    	DataVar data4 = new DataVar("null", STATUS.ALERT, 23, "var1");
    	DataVar data5 = new DataVar("\"toto\"", STATUS.OK, 25, "var2");

    	listExpectedResult.add(data1);
    	listExpectedResult.add(data2);
    	listExpectedResult.add(data3);
    	listExpectedResult.add(data4);
    	listExpectedResult.add(data5);
    	
    	assertTrue(ScannerVandV.equalsResultNPE(listResults,listExpectedResult));
		
	}
	
	@Test
	public void testNPESimpleError () {

    	ArrayList<DataVar> listResults = ScannerVandV.nullPointerScanner("./input/NullPointerSimple.java");

    	ArrayList<DataVar> listExpectedResult = new ArrayList<DataVar>();
    	
    	
    	assertFalse(ScannerVandV.equalsResultNPE(listResults,listExpectedResult));
		
	}
	

	@Test
	public void testNPEWhile () {

    	ArrayList<DataVar> listResults = ScannerVandV.nullPointerScanner("./input/NullPointerWhile.java");

    	ArrayList<DataVar> listExpectedResult = new ArrayList<DataVar>();
    	
    	
    	 /*OK for [var4] with value ["titi"] at line 16
		 WARNING for [var3] with value [null] at line 21
		 OK for [var3] with value ["tutu"] at line 25
		 ALERT for [var1] with value [null] at line 27
		 OK for [var2] with value ["toto"] at line 29*/
    	
    	DataVar data1 = new DataVar("\"titi\"", STATUS.OK, 16, "var4");
    	DataVar data2 = new DataVar("null", STATUS.WARNING, 21, "var3");
    	DataVar data3 = new DataVar("\"tutu\"", STATUS.OK, 25, "var3");
    	DataVar data4 = new DataVar("null", STATUS.ALERT, 27, "var1");
    	DataVar data5 = new DataVar("\"toto\"", STATUS.OK, 29, "var2");

    	listExpectedResult.add(data1);
    	listExpectedResult.add(data2);
    	listExpectedResult.add(data3);
    	listExpectedResult.add(data4);
    	listExpectedResult.add(data5);
    	
    	assertTrue(ScannerVandV.equalsResultNPE(listResults,listExpectedResult));
		
	}

	@Test
	public void testNPEMethods() {

    	ArrayList<DataVar> listResults = ScannerVandV.nullPointerScanner("./input/NullPointer2Methods.java");

    	ArrayList<DataVar> listExpectedResult = new ArrayList<DataVar>();
    	
    	
    	 /*OK for [var4] with value ["titi"] at line 17
			OK for [var3] with value ["tutu"] at line 22
			ALERT for [var1] with value [null] at line 24
			OK for [var2] with value ["toto"] at line 26
			OK for [var2] with value ["toto"] at line 32
			ALERT for [var3] with value [null] at line 34
			*/
			    	
    	DataVar data1 = new DataVar("\"titi\"", STATUS.OK, 17, "var4");
    	DataVar data2 = new DataVar("\"tutu\"", STATUS.OK, 22, "var3");
    	DataVar data3 = new DataVar("null", STATUS.ALERT, 24, "var1");
    	DataVar data4 = new DataVar("\"toto\"", STATUS.OK, 26, "var2");
    	DataVar data5 = new DataVar("\"toto\"", STATUS.OK, 32, "var2");
    	DataVar data6 = new DataVar("null", STATUS.ALERT, 34, "var3");

    	listExpectedResult.add(data1);
    	listExpectedResult.add(data2);
    	listExpectedResult.add(data3);
    	listExpectedResult.add(data4);
    	listExpectedResult.add(data5);
    	listExpectedResult.add(data6);
    	
    	assertTrue(ScannerVandV.equalsResultNPE(listResults,listExpectedResult));
		
	}
	
	@Test
	public void testNPEMethodsError() {

    	ArrayList<DataVar> listResults = ScannerVandV.nullPointerScanner("./input/NullPointer2Methods.java");

    	ArrayList<DataVar> listExpectedResult = new ArrayList<DataVar>();
    	
    	
    	 /*OK for [var4] with value ["titi"] at line 17
			OK for [var3] with value ["tutu"] at line 22
			ALERT for [var1] with value [null] at line 24
			OK for [var2] with value ["toto"] at line 26
			OK for [var2] with value ["toto"] at line 32
			ALERT for [var3] with value [null] at line 34
			*/
			    	
    	DataVar data1 = new DataVar("\"titi\"", STATUS.OK, 99, "var4");
    	DataVar data2 = new DataVar("\"tutu\"", STATUS.OK, 11, "var3");
    	DataVar data3 = new DataVar("null", STATUS.ALERT, 22, "var1");
    	DataVar data4 = new DataVar("\"toto\"", STATUS.OK, 33, "var2");
    	DataVar data5 = new DataVar("\"toto\"", STATUS.OK, 44, "var2");
    	DataVar data6 = new DataVar("null", STATUS.ALERT, 55, "var3");

    	listExpectedResult.add(data1);
    	listExpectedResult.add(data2);
    	listExpectedResult.add(data3);
    	listExpectedResult.add(data4);
    	listExpectedResult.add(data5);
    	listExpectedResult.add(data6);
    	
    	assertFalse(ScannerVandV.equalsResultNPE(listResults,listExpectedResult));
		
	}
}
