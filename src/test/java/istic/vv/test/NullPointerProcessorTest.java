package istic.vv.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import istic.vv.CycloProcessor;
import istic.vv.DataVar;
import istic.vv.ScannerVandV;
import spoon.Launcher;
import spoon.reflect.CtModel;

public class NullPointerProcessorTest {

	
	@Test
	public void testNPE1 () {

    	ArrayList<DataVar> listResults = ScannerVandV.nullPointerScanner("./input/NullPointerSimple.java");

    	
		
		
	}
	
}
